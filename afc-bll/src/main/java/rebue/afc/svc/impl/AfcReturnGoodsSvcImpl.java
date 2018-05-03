package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.returngoods.dic.ReturnGoodsByBuyerResultDic;
import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.afc.returngoods.to.ReturnGoodsByBuyerTo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcReturnGoodsSvc;
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
public class AfcReturnGoodsSvcImpl implements AfcReturnGoodsSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcReturnGoodsSvcImpl.class);

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
     * XXX AFC : 交易 : 退货-买家退货（ 余额+，返现金+ ）
     */
    @Override
    public ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to) {
        if (to.getUserId() == null || to.getBalanceAmount() == null || to.getCashbackAmount() == null
                || to.getOpId() == null || StringUtils.isAnyBlank(to.getReturnGoodsOrderId(), to.getSaleOrderId(),
                        to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写买家的用户ID/退货单ID/销售单ID/退货的标题/退货到余额的金额/退货到返现金的金额/操作人账号/MAC/IP: {}", to);
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("退货-买家退货发现没有此操作人: " + to.getOpId());
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("退货-买家退货发现操作人已被锁定: " + opUserMo);
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.OP_LOCKED);
            return ro;
        }

        SucUserMo userMo = userSvc.getById(to.getUserId());
        if (userMo == null) {
            _log.error("退货-买家退货发现没有此用户: " + to.getUserId());
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_USER);
            return ro;
        }
        if (userMo.getIsLock()) {
            _log.error("退货-买家退货发现用户被锁定: " + userMo);
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.USER_LOCKED);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
        condition.setAccountId(to.getUserId());
        condition.setOrderId(to.getSaleOrderId());
        List<AfcPayMo> pays = paySvc.list(condition);
        if (pays.isEmpty()) {
            _log.error("账户没有支付过此销售单: " + to);
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_ORDERID);
            return ro;
        }

        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getBalanceAmount().toString()).add(new BigDecimal(to.getCashbackAmount().toString()));

        // 检查退货金额不能超过支付金额
        // 计算支付总额
        BigDecimal payTotal = new BigDecimal(0);
        for (AfcPayMo afcPayMo : pays) {
            payTotal = payTotal.add(afcPayMo.getPayAmount());
        }
        // 计算曾经退货总额
        BigDecimal returnGoodsTotal = tradeSvc.getReturnGoodsTotalAmountByOrder(to.getSaleOrderId());
        if (returnGoodsTotal == null)
            returnGoodsTotal = new BigDecimal(0);
        // 比较大小
        if (payTotal.compareTo(returnGoodsTotal.add(tradeAmount)) < 0) {
            _log.error("退货金额不能超过销售金额: " + to);
            ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
            ro.setResult(ReturnGoodsByBuyerResultDic.NOT_ENOUGH_MONEY);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.RETURN_GOODS_BY_BUYER.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        // XXX AFC : 交易订单号 : 退货-买家退货 : 订单ID=销售单ID||退货单ID
        tradeMo.setOrderId(to.getSaleOrderId() + "||" + to.getReturnGoodsOrderId());
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("退货-买家退货重复提交");
            throw new RuntimeException("退货-买家退货重复提交", e);
        }

        // 余额+, 返现金+
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setBalance(oldAccountMo.getBalance().add(new BigDecimal(to.getBalanceAmount().toString())));
        newAccountMo.setCashback(oldAccountMo.getCashback().add(new BigDecimal(to.getCashbackAmount().toString())));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("balance", newAccountMo.getBalance());
        map.put("oldBalance", oldAccountMo.getBalance());
        map.put("cashback", newAccountMo.getCashback());
        map.put("oldCashback", oldAccountMo.getCashback());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("退货-买家退货重复提交");
            throw new RuntimeException("退货-买家退货重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("退货-买家退货成功: {}", to);
        ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
        ro.setResult(ReturnGoodsByBuyerResultDic.SUCCESS);
        return ro;
    }

}
