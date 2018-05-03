package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

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

import rebue.afc.dic.RebateResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.RebateRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcRebateSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.RebateTo;
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
public class AfcRebateSvcImpl implements AfcRebateSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcRebateSvcImpl.class);

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
     * XXX AFC : 交易 : 返款到供应商的余额（ 余额+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RebateRo rebateProviderBalance(RebateTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写供应商的用户ID/销售订单号/返款交易的标题/返款交易的金额/MAC/IP: {}", to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.PARAM_ERROR);
            return ro;
        }

        if (!userSvc.exist(to.getUserId())) {
            _log.error("返款到供应商的余额发现没有此用户: " + to.getUserId());
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
//        payMo.setAccountId(to.getUserId());
        condition.setOrderId(to.getOrderId());
        if (!paySvc.existSelective(condition)) {
            _log.error("账户没有支付过此销售单: " + to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_ORDERID);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.REBATE_PROVIDER_BALANCE.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(0L);         // 操作人设为0表示系统自动产生的交易
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("返款到供应商的余额重复提交");
            throw new RuntimeException("返款到供应商的余额重复提交", e);
        }

        // 余额+
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("balance", newAccountMo.getBalance());
        map.put("oldBalance", oldAccountMo.getBalance());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("返款到供应商的余额重复提交");
            throw new RuntimeException("返款到供应商的余额重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("返款到供应商的余额成功: {}", to);
        RebateRo ro = new RebateRo();
        ro.setResult(RebateResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 返款到买家的返现金（ 返现金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RebateRo rebateBuyerCashback(RebateTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写买家的用户ID/销售订单号/返款交易的标题/返款交易的金额/MAC/IP: {}", to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.PARAM_ERROR);
            return ro;
        }

        if (!userSvc.exist(to.getUserId())) {
            _log.error("返款到买家的返现金发现没有此用户: " + to.getUserId());
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
        condition.setAccountId(to.getUserId());
        condition.setOrderId(to.getOrderId());
        if (!paySvc.existSelective(condition)) {
            _log.error("账户没有支付过此销售单: " + to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_ORDERID);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.REBATE_BUYER_CASHBACK.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(0L);         // 操作人设为0表示系统自动产生的交易
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("返款到买家的返现金重复提交");
            throw new RuntimeException("返款到买家的返现金重复提交", e);
        }

        // 账户返现金加上返款金额
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setCashback(oldAccountMo.getCashback().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("cashback", newAccountMo.getCashback());
        map.put("oldCashback", oldAccountMo.getCashback());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("返款到买家的返现金重复提交");
            throw new RuntimeException("返款到买家的返现金重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("返款到买家的返现金成功: {}", to);
        RebateRo ro = new RebateRo();
        ro.setResult(RebateResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 返款到加盟商的已占用保证金（ 已占用返现金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RebateRo rebateSellerDepositUsed(RebateTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写加盟商的用户ID/销售订单号/返款交易的标题/返款交易的金额/MAC/IP: {}", to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.PARAM_ERROR);
            return ro;
        }

        if (!userSvc.exist(to.getUserId())) {
            _log.error("返款到加盟商的已占用保证金发现没有此用户: " + to.getUserId());
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
//        payMo.setAccountId(to.getUserId());
        condition.setOrderId(to.getOrderId());
        if (!paySvc.existSelective(condition)) {
            _log.error("账户没有支付过此销售单: " + to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_ORDERID);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.REBATE_SELLER_DEPOSIT_USED.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(0L);         // 操作人设为0表示系统自动产生的交易
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("返款到加盟商的已占用保证金重复提交");
            throw new RuntimeException("返款到加盟商的已占用保证金重复提交", e);
        }

        // 账户已占用保证金减去返款金额
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().subtract(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("depositUsed", newAccountMo.getDepositUsed());
        map.put("oldDepositUsed", oldAccountMo.getDepositUsed());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("返款到加盟商的已占用保证金重复提交");
            throw new RuntimeException("返款到加盟商的已占用保证金重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("返款到加盟商的已占用保证金成功: {}", to);
        RebateRo ro = new RebateRo();
        ro.setResult(RebateResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 返款到加盟商的余额（ 余额+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RebateRo rebateSellerBalance(RebateTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写加盟商的用户ID/销售订单号/返款交易的标题/返款交易的金额/MAC/IP: {}", to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.PARAM_ERROR);
            return ro;
        }

        if (!userSvc.exist(to.getUserId())) {
            _log.error("返款到加盟商的余额发现没有此用户: " + to.getUserId());
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 检查账户有没有支付过此销售单
        AfcPayMo condition = new AfcPayMo();
//        payMo.setAccountId(to.getUserId());
        condition.setOrderId(to.getOrderId());
        if (!paySvc.existSelective(condition)) {
            _log.error("账户没有支付过此销售单: " + to);
            RebateRo ro = new RebateRo();
            ro.setResult(RebateResultDic.NOT_FOUND_ORDERID);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.REBATE_SELLER_BALANCE.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(0L);         // 操作人设为0表示系统自动产生的交易
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("返款到加盟商的余额重复提交");
            throw new RuntimeException("返款到加盟商的余额重复提交", e);
        }

        // 账户余额加上返款金额
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("balance", newAccountMo.getBalance());
        map.put("oldBalance", oldAccountMo.getBalance());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("返款到加盟商的余额重复提交");
            throw new RuntimeException("返款到加盟商的余额重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("返款到加盟商的余额成功: {}", to);
        RebateRo ro = new RebateRo();
        ro.setResult(RebateResultDic.SUCCESS);
        return ro;
    }

}
