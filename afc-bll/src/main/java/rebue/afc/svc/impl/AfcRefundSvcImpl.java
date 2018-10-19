package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.RefundResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.platform.dic.PlatformTradeTypeDic;
import rebue.afc.platform.svc.AfcPlatformTradeSvc;
import rebue.afc.ro.RefundRo;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.RefundTo;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

@Service
/**
 * <pre>
 * 在单独使用不带任何参数 的 @Transactional 注释时，
 * propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED，
 * 而且事务不会针对受控异常（checked exception）回滚。
 * 注意：
 * 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 * </pre>
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AfcRefundSvcImpl implements AfcRefundSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcRefundSvcImpl.class);

    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcPlatformTradeSvc platformTradeSvc;
    @Resource
    private AfcPaySvc           paySvc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * 退款
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RefundRo refund(RefundTo to) {
        _log.info("退款: {}", to);

        if (to.getBuyerAccountId() == null || (to.getReturnBalanceToBuyer() == null && to.getReturnCashbackToBuyer() == null) || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getOrderDetailId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            String msg = "参数不正确";
            _log.error("{}: {}", msg, to);
            throw new RuntimeException(msg);
        }

        _log.info("退款查询操作人信息的参数为：{}", to.getOpId());
        SucUserMo opMo = userSvc.getById(to.getOpId());
        _log.info("退款查询操作人信息的返回值为：{}", opMo);
        if (opMo == null) {
            String msg = "退款时发现没有此操作人";
            _log.error("{}，操作人编号为：{}", msg, to.getOpId());
            throw new RuntimeException(msg);
        }

        if (opMo.getIsLock()) {
            String msg = "退款时发现操作人已被锁定";
            _log.error("{}，操作人编号为：{}", msg, opMo);
            throw new RuntimeException(msg);
        }

        _log.info("退款查询买家信息的参数为：{}", to.getBuyerAccountId());
        SucUserMo buyerMo = userSvc.getById(to.getBuyerAccountId());
        _log.info("退款查询买家信息的返回值为：{}", buyerMo);
        if (buyerMo == null) {
            String msg = "退款时发现没有此买家";
            _log.error("{}，账户ID为：{}", msg, to.getBuyerAccountId());
            throw new RuntimeException(msg);
        }
        if (buyerMo.getIsLock()) {
            String msg = "退款时发现买家已被锁定";
            _log.error("{}，账户ID为：{}", msg, buyerMo);
            throw new RuntimeException(msg);
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
        condition.setAccountId(to.getBuyerAccountId());
        condition.setOrderId(to.getOrderId());
        _log.info("退款查询支付订单信息的参数为：{}", condition);
        List<AfcPayMo> pays = paySvc.list(condition);
        _log.info("退款查询支付订单信息的返回值为：{}", String.valueOf(pays));
        if (pays.isEmpty()) {
            String msg = "退款时发现买家未支付此销售单";
            _log.error("{}，销售单号为：{}", msg, to.getOrderId());
            throw new RuntimeException(msg);
        }

        // 得到退款总额
        BigDecimal tradeAmount = to.getReturnBalanceToBuyer().add(to.getReturnCashbackToBuyer(),new MathContext(10));

        // 检查退款金额不能超过支付金额
        // 计算支付总额 TODO SQL直查
        BigDecimal payTotal = BigDecimal.ZERO;
        for (AfcPayMo afcPayMo : pays) {
            payTotal = payTotal.add(afcPayMo.getPayAmount(), new MathContext(10));
        }
        _log.info("退款查询退货总额的参数为：{}", to.getOrderId());
        // 计算已退款总额
        BigDecimal refundTotal = tradeSvc.getRefundTotalAmountByOrderId(to.getOrderId());
        _log.info("退款查询已退款总额的返回值为：{}", refundTotal);
        if (refundTotal == null)
            refundTotal = BigDecimal.ZERO;
        
        _log.info("退款比较大小的参数为：payTotal==={}, refundTotal==={}, tradeAmount==={}, refundTotal.add(tradeAmount, new MathContext(10)) === {}", payTotal, refundTotal, tradeAmount, refundTotal.add(tradeAmount, new MathContext(4)));
        // 比较大小
        if (payTotal.compareTo(refundTotal.add(tradeAmount, new MathContext(10))) < 0) {
            String msg = "退款总额不能超过支付总额";
            _log.error("{}: {}", to);
            throw new RuntimeException(msg);
        }

        Date now = new Date();

        // 退款到买家账户（ 余额+，返现金+ ）
        {
            AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            tradeMo.setTradeType((byte) TradeTypeDic.REFUND_TO_BUYER.getCode());
            tradeMo.setAccountId(to.getBuyerAccountId());
            tradeMo.setTradeAmount(tradeAmount);
            tradeMo.setChangeAmount1(to.getReturnCashbackToBuyer());
            tradeMo.setChangeAmount2(to.getReturnBalanceToBuyer());
            tradeMo.setTradeTime(now);
            tradeSvc.addTrade(tradeMo);
        }

        // 如果要收回平台服务费(结算给平台的服务费的金额)
        if (to.getGetbackServiceFeeFromPlatform() != null && to.getGetbackServiceFeeFromPlatform().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("收回平台服务费(结算给平台的服务费的金额)");
            // 添加一笔平台交易
            AfcPlatformTradeMo platformTradeMo = dozerMapper.map(to, AfcPlatformTradeMo.class);
            platformTradeMo.setPlatformTradeType((byte) PlatformTradeTypeDic.GETBACK_SEVICE_FEE.getCode());     // 交易类型（1：收取服务费(购买交易成功) 2：退回服务费(用户退款)）
            platformTradeMo.setTradeAmount(to.getGetbackServiceFeeFromPlatform());                              // 收取的服务费
            platformTradeMo.setModifiedTimestamp(now.getTime());                                                // 修改时间戳
            platformTradeSvc.addTrade(platformTradeMo);      // 如果重复提交，会抛出DuplicateKeyException运行时异常
        }

        // 如果要结算供应商
        if (to.getGetbackCostFromSupplier() != null && to.getGetbackCostFromSupplier().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("收回供应商款项(结算给供应商的金额)");
            AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_SUPPLIER.getCode());
            tradeMo.setAccountId(to.getSupplierAccountId());
            tradeMo.setTradeAmount(to.getGetbackCostFromSupplier());
            tradeMo.setTradeTitle("收回供应商款项(结算给供应商的金额)");
            tradeMo.setTradeTime(now);
            tradeSvc.addTrade(tradeMo);
        }

        // 如果要收回卖家款项(结算给卖家利润和释放已占用保证金的金额)
        if (to.getGetbackProfitFromSeller() != null && to.getGetbackProfitFromSeller().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("收回卖家款项(结算给卖家利润和释放已占用保证金的金额)");
            AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_SELLER.getCode());
            tradeMo.setAccountId(to.getSellerAccountId());
            tradeMo.setTradeAmount(to.getGetbackProfitFromSeller());
            tradeMo.setTradeTitle("收回卖家款项(结算给卖家利润和释放已占用保证金的金额)");
            tradeMo.setTradeTime(now);
            tradeSvc.addTrade(tradeMo);
        }

        // 收回已占用保证金(结算给卖家释放已占用保证金的金额)
        if (to.getGetbackDepositUsedFromSeller() != null && to.getGetbackDepositUsedFromSeller().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("收回已占用保证金(结算给卖家释放已占用保证金的金额)");
            AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_DEPOSIT_USED.getCode());
            tradeMo.setAccountId(to.getSellerAccountId());
            tradeMo.setTradeAmount(to.getGetbackDepositUsedFromSeller());
            tradeMo.setTradeTitle("收回已占用保证金(结算给卖家释放已占用保证金的金额)");
            tradeMo.setTradeTime(new Date());   // 两个卖家款项，独立使用当前时间
            tradeSvc.addTrade(tradeMo);
        }

        // 返回成功
        _log.info("退款-买家退款成功: {}", to);
        RefundRo ro = new RefundRo();
        ro.setResult(RefundResultDic.SUCCESS);
        return ro;
    }

}
