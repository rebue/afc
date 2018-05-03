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

import rebue.afc.dic.ChargeResultDic;
import rebue.afc.dic.DepositGoodsTransferResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.ro.DepositGoodsTransferRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcDepositSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.ChargeTo;
import rebue.afc.to.DepositGoodsInTo;
import rebue.afc.to.DepositGoodsReturnTo;
import rebue.afc.to.DepositGoodsTransferTo;
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
public class AfcDepositSvcImpl implements AfcDepositSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcDepositSvcImpl.class);

    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcFlowSvc          flowSvc;

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
     * XXX AFC : 交易 : 进货保证金-充值（ 进货保证金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ChargeRo charge(ChargeTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写充值的用户ID/充值单号/充值交易的标题/充值交易的金额/操作人账号/MAC/IP: {}", to);
            ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("充值进货保证金发现没有此操作人: " + to.getOpId());
            ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("充值进货保证金发现操作人已被锁定: " + opUserMo);
            ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.OP_LOCKED);
            return ro;
        }

        SucUserMo chargeUserMo = userSvc.getById(to.getUserId());
        if (chargeUserMo == null) {
            _log.error("充值进货保证金发现没有此用户: " + to.getUserId());
            ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.NOT_FOUND_USER);
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
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_CHARGE.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());    // 充值订单
        tradeMo.setTradeVoucherNo(to.getTradeVoucherNo());
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("进货保证金-充值重复提交");
            throw new RuntimeException("进货保证金-充值重复提交", e);
        }

        // 账户进货保证金加上充值金额
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setDeposit(oldAccountMo.getDeposit().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("deposit", newAccountMo.getDeposit());
        map.put("oldDeposit", oldAccountMo.getDeposit());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("进货保证金-充值重复提交");
            throw new RuntimeException("进货保证金-充值重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("进货保证金充值成功: {}", to);
        ChargeRo ro = new ChargeRo();
        ro.setResult(ChargeResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 进货保证金-进货（ 已占用进货保证金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo inGoods(DepositGoodsInTo to) {
        if (to.getUserId() == null || to.getOrderId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写进货加盟商的用户ID/进货单号/进货交易的标题/进货交易的金额/操作人账号/MAC/IP: {}", to);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("进货保证金-进货发现没有此操作人: " + to.getOpId());
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("进货保证金-进货发现操作人已被锁定: " + opUserMo);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.OP_LOCKED);
            return ro;
        }

        SucUserMo userMo = userSvc.getById(to.getUserId());
        if (userMo == null) {
            _log.error("进货保证金-进货发现没有此加盟商: " + to.getUserId());
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_ACCOUNT);
            return ro;
        }
        if (userMo.getIsLock()) {
            _log.error("进货保证金-进货发现加盟商被锁定: " + userMo);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.ACCOUNT_LOCKED);
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
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_GOODS_IN.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("进货保证金-进货重复提交");
            throw new RuntimeException("进货保证金-进货重复提交", e);
        }

        // 账户已占用进货保证金加上进货金额
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("depositUsed", newAccountMo.getDepositUsed());
        map.put("oldDepositUsed", oldAccountMo.getDepositUsed());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("进货保证金-进货重复提交");
            throw new RuntimeException("进货保证金-进货重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("进货保证金-进货成功: {}", to);
        DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
        ro.setResult(DepositGoodsTransferResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 进货保证金-出货（ 已占用进货保证金- ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo outGoods(DepositGoodsReturnTo to) {
        if (to.getUserId() == null || to.getOrderId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写出货加盟商的用户ID/出货单号/出货交易的标题/出货交易的金额/操作人账号/MAC/IP: {}", to);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("进货保证金-出货发现没有此操作人: " + to.getOpId());
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("进货保证金-出货发现操作人已被锁定: " + opUserMo);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.OP_LOCKED);
            return ro;
        }

        SucUserMo userMo = userSvc.getById(to.getUserId());
        if (userMo == null) {
            _log.error("进货保证金-出货发现没有此加盟商: " + to.getUserId());
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_ACCOUNT);
            return ro;
        }
        if (userMo.getIsLock()) {
            _log.error("进货保证金-出货发现加盟商被锁定: " + userMo);
            DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.ACCOUNT_LOCKED);
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
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_GOODS_RETURN.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("进货保证金-出货重复提交");
            throw new RuntimeException("进货保证金-出货重复提交", e);
        }

        // 账户已占用进货保证金减去出货金额
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
            _log.error("进货保证金-出货重复提交");
            throw new RuntimeException("进货保证金-出货重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("进货保证金-出货成功: {}", to);
        DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
        ro.setResult(DepositGoodsTransferResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 进货保证金-调货（ 已占用进货保证金+- ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo transferGoods(DepositGoodsTransferTo to) {
        DepositGoodsInTo inTo = dozerMapper.map(to, DepositGoodsInTo.class);
        inTo.setUserId(to.getInUserId());
        DepositGoodsTransferRo ro = inGoods(inTo);
        if (ro.getResult() != DepositGoodsTransferResultDic.SUCCESS)
            return ro;
        DepositGoodsReturnTo outTo = dozerMapper.map(to, DepositGoodsReturnTo.class);
        outTo.setUserId(to.getOutUserId());
        return outGoods(outTo);
    }
}
