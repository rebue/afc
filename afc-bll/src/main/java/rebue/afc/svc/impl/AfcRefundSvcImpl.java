package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.returngoods.dic.RefundToBuyerResultDic;
import rebue.afc.returngoods.ro.RefundToBuyerRo;
import rebue.afc.returngoods.to.RefundToBuyerTo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;
import rebue.wheel.idworker.IdWorker3;

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
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcFlowSvc          flowSvc;
    @Resource
    private AfcPaySvc           paySvc;

    @Resource
    private Mapper              dozerMapper;

    @Value("${appid:0}")
    private int                 _appid;

    protected IdWorker3         _idWorker;

    @PostConstruct
    public void init() {
        _idWorker = new IdWorker3(_appid);
    }

    /**
     * 退款-退款到买家账户（ 余额+，返现金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RefundToBuyerRo refundToBuyer(RefundToBuyerTo to) {
//        // 账户ID
//        Long accountId = to.getUserId();
//        // 操作人编号
//        Long opId = to.getOpId();
//        // 退到余额金额
//        Double balanceAmount = to.getBalanceAmount();
//        // 退到返现金金额
//        Double cashbackAmount = to.getCashbackAmount();
//        // 退款订单ID
//        String refundOrderId = to.getReturnGoodsOrderId();
//        // 销售订单ID
//        String saleOrderId = to.getSaleOrderId();

        if (to.getAccountId() == null || to.getChangeAmount1() == null || to.getChangeAmount2() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getOrderDetailId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            String msg = "参数不对";
            _log.error("{}: {}", msg, to);
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }

        _log.info("用户退款查询操作人信息的参数为：{}", to.getOpId());
        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        _log.info("用户退货退款查询操作人信息的返回值为：{}", opUserMo.toString());
        if (opUserMo.getId() == null) {
            String msg = "用户退货退款查询用户信息时发现没有此操作人";
            _log.error("{}，操作人编号为：{}", msg, to.getOpId());
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.NOT_FOUND_OP);
            ro.setMsg(msg);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            String msg = "用户退款时发现操作人已被锁定";
            _log.error("{}，操作人编号为：{}", msg, opUserMo);
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.OP_LOCKED);
            ro.setMsg(msg);
            return ro;
        }

        _log.info("用户退款查询用户信息的参数为：{}", to.getAccountId());
        SucUserMo userMo = userSvc.getById(to.getAccountId());
        _log.info("用户退款查询用户信息的返回值为：{}", userMo.toString());
        if (userMo.getId() == null) {
            String msg = "用户退货退款时发现没有此用户";
            _log.error("{}，账户ID为：{}", msg, to.getAccountId());
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.NOT_FOUND_USER);
            ro.setMsg(msg);
            return ro;
        }
        if (userMo.getIsLock()) {
            String msg = "用户退款时发现用户已被锁定";
            _log.error("{}，账户ID为：{}", msg, userMo);
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.USER_LOCKED);
            ro.setMsg(msg);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
        condition.setAccountId(to.getAccountId());
        condition.setOrderId(to.getOrderId());
        _log.info("用户退款查询支付订单信息的参数为：{}", condition.toString());
        List<AfcPayMo> pays = paySvc.list(condition);
        _log.info("用户退款查询支付订单信息的返回值为：{}", String.valueOf(pays));
        if (pays.isEmpty()) {
            String msg = "用户退货退款时发现用户未支付此销售单";
            _log.error("{}，销售单号为：{}", msg, to.getOrderId());
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.NOT_FOUND_ORDERID);
            ro.setMsg(msg);
            return ro;
        }

        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getChangeAmount2()).add(new BigDecimal(to.getChangeAmount1().toString()));

        // 检查退款金额不能超过支付金额
        // 计算支付总额 TODO SQL直查
        BigDecimal payTotal = BigDecimal.ZERO;
        for (AfcPayMo afcPayMo : pays) {
            payTotal = payTotal.add(afcPayMo.getPayAmount());
        }
        _log.info("用户退货退款查询退货总额的参数为：{}", to.getOrderId());
        // 计算已退款总额
        BigDecimal refundTotal = tradeSvc.getRefundTotalAmountByOrderId(to.getOrderId());
        _log.info("用户退款查询退款总额的返回值为：{}", refundTotal);
        if (refundTotal == null)
            refundTotal = BigDecimal.ZERO;
        // 比较大小
        if (payTotal.compareTo(refundTotal.add(tradeAmount)) < 0) {
            String msg = "退款总额不能超过支付总额";
            _log.error("{}: {}", to);
            RefundToBuyerRo ro = new RefundToBuyerRo();
            ro.setResult(RefundToBuyerResultDic.NOT_ENOUGH_MONEY);
            ro.setMsg(msg);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();

        _log.info("用户退货退款查询用户账号信息的参数为：{}", to.getAccountId());
        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getAccountId());
        _log.info("用户退货退款查询用户账号信息的返回值为：{}", oldAccountMo.toString());

        // 添加一笔交易
        AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
        tradeMo.setTradeType((byte) TradeTypeDic.REFUND_TO_BUYER.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTime(now);
        tradeSvc.addTrade(tradeMo);

        // 返回成功
        _log.info("退货-买家退货成功: {}", to);
        RefundToBuyerRo ro = new RefundToBuyerRo();
        ro.setResult(RefundToBuyerResultDic.SUCCESS);
        return ro;
    }

}
