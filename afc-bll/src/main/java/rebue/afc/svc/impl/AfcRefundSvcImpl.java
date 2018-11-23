package rebue.afc.svc.impl;

import java.math.BigDecimal;
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

import rebue.afc.dic.PayTypeDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcRefundMapper;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcRefundMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.platform.svc.AfcPlatformTradeSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.RefundTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 退款日志
 *
 * 在单独使用不带任何参数的 @Transactional 注释时，
 * propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED，
 * 而且事务不会针对受控异常（checked exception）回滚。
 *
 * 注意：
 * 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcRefundSvcImpl extends MybatisBaseSvcImpl<AfcRefundMo, java.lang.Long, AfcRefundMapper> implements AfcRefundSvc {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcRefundSvcImpl.class);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(final AfcRefundMo mo) {
        _log.info("添加退款日志");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcPlatformTradeSvc platformTradeSvc;
    @Resource
    private AfcPaySvc           paySvc;
    @Resource
    private AfcRefundSvc        refundSvc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * 退款
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro refund(final RefundTo to) {
        _log.info("退款: {}", to);
        final Ro ro = new Ro();

        // 退款补偿金参数为空设置默认值
        if (to.getReturnCompensationToSeller() != null) {
            to.setReturnCompensationToSeller(BigDecimal.ZERO);
        }

        if (to.getBuyerAccountId() == null //
                || (to.getRefundAmount() == null && to.getReturnBalanceToBuyer() == null && to.getReturnCashbackToBuyer() == null) //
                || to.getOpId() == null//
                || StringUtils.isAnyBlank(to.getOrderId(), to.getOrderDetailId(), to.getTradeTitle(), to.getIp())) {
            final String msg = "参数不正确";
            _log.error("{}: {}", msg, to);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }

        _log.info("退款查询操作人信息：opId-{}", to.getOpId());
        if (to.getOpId() != 0) {
            // 0为系统自动退款
            final SucUserMo opMo = userSvc.getById(to.getOpId());
            _log.info("退款查询操作人信息的返回值为：{}", opMo);
            if (opMo == null) {
                final String msg = "退款时发现没有此操作人";
                _log.error("{}，操作人编号为：{}", msg, to.getOpId());
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
            if (opMo.getIsLock()) {
                final String msg = "退款时发现操作人已被锁定";
                _log.error("{}，操作人编号为：{}", msg, opMo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        }
        _log.info("退款查询买家信息的参数为：{}", to.getBuyerAccountId());
        final SucUserMo buyerMo = userSvc.getById(to.getBuyerAccountId());
        _log.info("退款查询买家信息的返回值为：{}", buyerMo);
        if (buyerMo == null) {
            final String msg = "退款时发现没有此买家";
            _log.error("{}，账户ID为：{}", msg, to.getBuyerAccountId());
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
        if (buyerMo.getIsLock()) {
            final String msg = "退款时发现买家已被锁定";
            _log.error("{}，账户ID为：{}", msg, buyerMo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }

        _log.info("获取订单支付日志列表：orderId-{}, buyAccountId-{}", to.getOrderId(), to.getBuyerAccountId());
        final AfcPayMo payCondition = new AfcPayMo();
        payCondition.setOrderId(to.getOrderId());
        payCondition.setAccountId(to.getBuyerAccountId());
        final List<AfcPayMo> pays = paySvc.list(payCondition);
        _log.info("获取订单支付日志列表的返回值为：{}", pays);

        _log.debug("检查账户有没有支付过此订单");
        if (pays.isEmpty()) {
            final String msg = "退款时发现买家从未支付过此订单";
            _log.error("{}: orderId-{}, buyAccountId-{}", msg, to.getOrderId(), to.getBuyerAccountId());
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }

        _log.debug("获取订单退款列表：orderId-{}, buyAccountId-{}", to.getOrderId(), to.getBuyerAccountId());
        final AfcRefundMo refundConditions = new AfcRefundMo();
        refundConditions.setOrderId(to.getOrderId());
        refundConditions.setAccountId(to.getBuyerAccountId());
        final List<AfcRefundMo> refunds = refundSvc.list(refundConditions);
        _log.debug("获取订单退款列表的返回值为：{}", refunds);

        // 默认为自动计算退款
        boolean isAutoCalcRefund = true;
        // 根据退款金额是否为空判断是否自动计算退款，如不为空就是自动计算退款，否则是自定义退款，退款金额=退款到余额+退款到返现金
        if (to.getRefundAmount() == null) {
            isAutoCalcRefund = false;
            to.setRefundAmount(to.getReturnBalanceToBuyer().add(to.getReturnCashbackToBuyer()));
        }
        // 本次退款总额 = 退款金额 + 补偿金
        final BigDecimal refundTotal = to.getRefundAmount().add(to.getReturnCompensationToSeller());

        _log.debug("计算支付总额，支付V支付的返现金总额，支付V支付的余额总额：orderId-{}, buyAccountId-{}", to.getOrderId(), to.getBuyerAccountId());
        BigDecimal paidTotal = BigDecimal.ZERO;
        BigDecimal payCashbackTotal = BigDecimal.ZERO;
        BigDecimal payBalanceTotal = BigDecimal.ZERO;
        for (final AfcPayMo pay : pays) {
            paidTotal = paidTotal.add(pay.getPayAmount());
            // 支付类型如果是V支付，计算余额和返现金总额
            if (pay.getPayTypeId() == PayTypeDic.VPAY.getCode()) {
                payCashbackTotal = payCashbackTotal.add(pay.getPayAmount1());
                payBalanceTotal = payBalanceTotal.add(pay.getPayAmount2());
            }
        }
        _log.debug("计算支付总额: 支付总额-{}，支付V支付的返现金总额-{}，支付V支付的余额总额-{}", paidTotal, payCashbackTotal, payBalanceTotal);

        _log.info("计算已退款总额：orderId-{}, buyAccountId-{}", to.getOrderId(), to.getBuyerAccountId());
        BigDecimal refundedTotal = BigDecimal.ZERO;
        BigDecimal refundedCashbackTotal = BigDecimal.ZERO;
        BigDecimal refundedBalanceTotal = BigDecimal.ZERO;
        for (final AfcRefundMo refund : refunds) {
            refundedTotal = refundedTotal.add(refund.getRefundTotal());
            refundedCashbackTotal = refundedCashbackTotal.add(refund.getRefundAmount1());
            refundedBalanceTotal = refundedBalanceTotal.add(refund.getRefundAmount2());
        }
        _log.debug("计算已退款总额: 已退款总额-{}，已退款V支付的返现金总额-{}，已退款V支付的余额总额-{}", paidTotal, refundedCashbackTotal, refundedBalanceTotal);

        _log.debug("判断是否支付总额<退款总额+本次退款总额：支付总额-{}, 已退款总额-{}, 本次退款金额-{}", paidTotal, refundedTotal, refundTotal);
        if (paidTotal.compareTo(refundedTotal.add(refundTotal)) < 0) {
            final String msg = "退款总额不能超过支付的总额";
            _log.error("{}: {}", msg, to);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }

        // 如果是自定义退款
        if (!isAutoCalcRefund) {
            _log.debug("判断是否支付的总余额<退款总余额+本次退款余额");
            if (payBalanceTotal.compareTo(refundedBalanceTotal.add(to.getReturnBalanceToBuyer())) < 0) {
                final String msg = "退款总余额额不能超过支付的总余额";
                _log.error("{}: {}", msg, to);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
            _log.debug("判断是否支付的总返现金<退款总返现金+本次退款返现金");
            if (payCashbackTotal.compareTo(refundedCashbackTotal.add(to.getReturnCashbackToBuyer())) < 0) {
                final String msg = "退款总返现金不能超过支付的总返现金";
                _log.error("{}: {}", msg, to);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        }

        final Date now = new Date();

        {
            _log.info("退款到买家账户（ 余额+，返现金+ ）");
            final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            // 交易类型: 退款到买家
            tradeMo.setTradeType((byte) TradeTypeDic.REFUND_TO_BUYER.getCode());
            // 交易账号: 退款到买家的账号
            tradeMo.setAccountId(to.getBuyerAccountId());
            // 交易时间
            tradeMo.setTradeTime(now);
            // 交易金额: 退款金额
            tradeMo.setTradeAmount(to.getRefundAmount());
            // 如果是自动计算退款，优先退款到余额，再到返现金
            if (isAutoCalcRefund) {
                // 可退余额=支付总余额-已退总余额
                final BigDecimal refundableBalance = payBalanceTotal.subtract(refundedBalanceTotal);
                _log.debug("可退余额为: {}", refundableBalance);
                // 可退余额扣减退款所得的剩余金额
                final BigDecimal remainingAmout = refundableBalance.subtract(to.getRefundAmount());
                _log.debug("可退余额扣减退款所得的剩余金额: {}", remainingAmout);
                // 如果剩余金额>=0，那么全部退款到余额
                if (remainingAmout.compareTo(BigDecimal.ZERO) >= 0) {
                    to.setReturnBalanceToBuyer(to.getRefundAmount());       // 退款余额=退款金额
                    to.setReturnCashbackToBuyer(BigDecimal.ZERO);           // 退款返现金=0
                }
                // 否则，说明余额不够扣，要继续扣除返现金
                else {
                    to.setReturnBalanceToBuyer(refundableBalance);          // 退款余额=可退余额
                    to.setReturnCashbackToBuyer(remainingAmout.abs());      // 退款返现金=剩余金额的绝对值
                }
            }
            tradeMo.setChangeAmount1(to.getReturnCashbackToBuyer());
            tradeMo.setChangeAmount2(to.getReturnBalanceToBuyer());
            _log.debug("添加退款到用户的交易记录: {}", tradeMo);
            tradeSvc.addTrade(tradeMo);
        }

        if (to.getReturnCompensationToSeller().equals(BigDecimal.ZERO)) {
            _log.info("退款补偿金到卖家账户（ 余额+ ）");
            final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            // 交易类型: 退款到家
            tradeMo.setTradeType((byte) TradeTypeDic.REFUND_COMPENSATION_TO_SELLER.getCode());
            // 交易账号: 退款到卖家的账号
            tradeMo.setAccountId(to.getSellerAccountId());
            tradeMo.setTradeTitle("退款补偿金");
            // 交易时间
            tradeMo.setTradeTime(now);
            // 交易金额: 退款补偿金额
            tradeMo.setTradeAmount(to.getReturnCompensationToSeller());
            _log.debug("添加退款到用户的交易记录: {}", tradeMo);
            tradeSvc.addTrade(tradeMo);
        }

        final AfcRefundMo refundMo = new AfcRefundMo();
        refundMo.setAccountId(to.getBuyerAccountId());
        refundMo.setOrderId(to.getOrderId());
        refundMo.setRefundTime(now);
        refundMo.setRefundTotal(to.getRefundAmount());
        refundMo.setRefundCompensation(to.getReturnCompensationToSeller());
        refundMo.setRefundAmount1(to.getReturnCashbackToBuyer());
        refundMo.setRefundAmount2(to.getReturnBalanceToBuyer());
        refundMo.setRefundTitle(to.getTradeTitle());
        refundMo.setRefundDetail(to.getTradeDetail());
        refundMo.setOpId(to.getOpId());
        refundMo.setIp(to.getIp());
        _log.debug("添加退款日志: {}", refundMo);
        refundSvc.add(refundMo);

//        // 如果要收回平台服务费(结算给平台的服务费的金额)
//        if (to.getGetbackServiceFeeFromPlatform() != null && to.getGetbackServiceFeeFromPlatform().compareTo(BigDecimal.ZERO) > 0) {
//            _log.info("收回平台服务费(结算给平台的服务费的金额)");
//            // 添加一笔平台交易
//            final AfcPlatformTradeMo platformTradeMo = dozerMapper.map(to, AfcPlatformTradeMo.class);
//            // 交易类型（1：收取服务费(购买交易成功) 2：退回服务费(用户退款)）
//            platformTradeMo.setPlatformTradeType((byte) PlatformTradeTypeDic.GETBACK_SEVICE_FEE.getCode());
//            // 收取的服务费
//            platformTradeMo.setTradeAmount(to.getGetbackServiceFeeFromPlatform());
//            // 修改时间戳
//            platformTradeMo.setModifiedTimestamp(now.getTime());
//            // 如果重复提交，会抛出DuplicateKeyException运行时异常
//            platformTradeSvc.addTrade(platformTradeMo);
//        }
//
//        // 如果要结算供应商
//        if (to.getGetbackCostFromSupplier() != null && to.getGetbackCostFromSupplier().compareTo(BigDecimal.ZERO) > 0) {
//            _log.info("收回供应商款项(结算给供应商的金额)");
//            final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
//            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_SUPPLIER.getCode());
//            tradeMo.setAccountId(to.getSupplierAccountId());
//            tradeMo.setTradeAmount(to.getGetbackCostFromSupplier());
//            tradeMo.setTradeTitle("收回供应商款项(结算给供应商的金额)");
//            tradeMo.setTradeTime(now);
//            tradeSvc.addTrade(tradeMo);
//        }
//
//        // 如果要收回卖家款项(结算给卖家利润和释放已占用保证金的金额)
//        if (to.getGetbackProfitFromSeller() != null && to.getGetbackProfitFromSeller().compareTo(BigDecimal.ZERO) > 0) {
//            _log.info("收回卖家款项(结算给卖家利润和释放已占用保证金的金额)");
//            final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
//            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_SELLER.getCode());
//            tradeMo.setAccountId(to.getSellerAccountId());
//            tradeMo.setTradeAmount(to.getGetbackProfitFromSeller());
//            tradeMo.setTradeTitle("收回卖家款项(结算给卖家利润和释放已占用保证金的金额)");
//            tradeMo.setTradeTime(now);
//            tradeSvc.addTrade(tradeMo);
//        }
//
//        // 收回已占用保证金(结算给卖家释放已占用保证金的金额)
//        if (to.getGetbackDepositUsedFromSeller() != null && to.getGetbackDepositUsedFromSeller().compareTo(BigDecimal.ZERO) > 0) {
//            _log.info("收回已占用保证金(结算给卖家释放已占用保证金的金额)");
//            final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
//            tradeMo.setTradeType((byte) TradeTypeDic.GETBACK_DEPOSIT_USED.getCode());
//            tradeMo.setAccountId(to.getSellerAccountId());
//            tradeMo.setTradeAmount(to.getGetbackDepositUsedFromSeller());
//            tradeMo.setTradeTitle("收回已占用保证金(结算给卖家释放已占用保证金的金额)");
//            // 两个卖家款项，独立使用当前时间
//            tradeMo.setTradeTime(new Date());
//            tradeSvc.addTrade(tradeMo);
//        }

        // 返回成功
        final String msg = "退款成功";
        _log.info("{}: {}", msg, to);
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }
}
