package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcWithdrawMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.svc.AfcWithdrawAccountSvc;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.afc.withdraw.dic.WithdrawApplyResultDic;
import rebue.afc.withdraw.dic.WithdrawCancelResultDic;
import rebue.afc.withdraw.dic.WithdrawDealResultDic;
import rebue.afc.withdraw.dic.WithdrawOkResultDic;
import rebue.afc.withdraw.dic.WithdrawStateDic;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawApplyTo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
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
public class AfcWithdrawSvcImpl extends MybatisBaseSvcImpl<AfcWithdrawMo, java.lang.Long, AfcWithdrawMapper>
        implements AfcWithdrawSvc {
    private final static Logger   _log = LoggerFactory.getLogger(AfcWithdrawSvcImpl.class);

    @Resource
    private SucUserSvc            userSvc;
    @Resource
    private AfcAccountSvc         accountSvc;
    @Resource
    private AfcWithdrawAccountSvc withdrawAccountSvc;
    @Resource
    private AfcTradeSvc           tradeSvc;
    @Resource
    private AfcFlowSvc            flowSvc;

    @Resource
    private Mapper                dozerMapper;

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcWithdrawMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * XXX AFC : 交易 : 提现申请（ 余额-，提现中+ ）
     */
    @Override
    public WithdrawApplyRo apply(WithdrawApplyTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
            _log.warn("没有填写申请提现账户的用户ID/申请提现的金额/申请人的用户ID/MAC/IP: {}", to);
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("提现申请发现没有此申请人: " + to.getOpId());
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("提现申请发现申请人已被锁定: " + opUserMo);
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.OP_LOCKED);
            return ro;
        }

        SucUserMo withdrawUserMo = userSvc.getById(to.getUserId());
        if (withdrawUserMo == null) {
            _log.error("没有发现提现的用户: " + to.getUserId());
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.NOT_FOUND_WITHDRAW_USER);
            return ro;
        }

        if (withdrawUserMo.getIsLock()) {
            _log.error("提现的用户已经被锁定: " + withdrawUserMo);
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.WITHDRAW_USER_LOCKED);
            return ro;
        }

        // 用户的提现账户
        AfcWithdrawAccountMo withdrawAccountMo;
        // 如果没有传withdrawAccountId(提现账户参数)，获取用户的默认提现账户
        if (to.getWithdrawAccountId() == null) {
            withdrawAccountMo = getDefaultWithdrawAccount(to.getUserId());
        } else {
            withdrawAccountMo = withdrawAccountSvc.getById(to.getWithdrawAccountId());
        }

        if (withdrawAccountMo == null) {
            _log.error("没有发现用户的提现账户: userId-" + to.getUserId() + ", withdrawAccountId-" + to.getWithdrawAccountId());
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.NOT_FOUND_WITHDRAW_ACCOUNT);
            return ro;
        }

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());

        // 判断提现账户的余额是否足够（小于提现金额）
        if (oldAccountMo.getBalance().doubleValue() < to.getTradeAmount()) {
            _log.error("提现用户的余额不足: " + oldAccountMo);
            WithdrawApplyRo ro = new WithdrawApplyRo();
            ro.setResult(WithdrawApplyResultDic.NOT_ENOUGH_MONEY);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 添加提现申请
        AfcWithdrawMo withdrawMo = new AfcWithdrawMo();
        withdrawMo.setAccountId(oldAccountMo.getId());
        withdrawMo.setWithdrawState((byte) WithdrawStateDic.APPLY.getCode());
        withdrawMo.setOrderId(to.getOrderId());                                             // 申请提现的单号
        withdrawMo.setTradeTitle(to.getTradeTitle());
        withdrawMo.setTradeDetail(to.getTradeDetail());
        withdrawMo.setAmount(tradeAmount);                                                  // 提现金额
        withdrawMo.setSeviceCharge(new BigDecimal(0));                                      // 提现服务费 TODO 添加查询提服务费的接口
        withdrawMo.setRealAmount(tradeAmount.subtract(withdrawMo.getSeviceCharge()));       // 实际到账金额(提现金额-提现服务费)
        withdrawMo.setApplicantId(to.getOpId());
        withdrawMo.setApplyTime(now);
        withdrawMo.setApplicantMac(to.getMac());
        withdrawMo.setApplicantIp(to.getIp());
        // 记录当前提现账户详细信息
        withdrawMo.setWithdrawType(withdrawAccountMo.getWithdrawType());
        withdrawMo.setContactTel(withdrawAccountMo.getContactTel());
        withdrawMo.setBankAccountNo(withdrawAccountMo.getBankAccountNo());
        withdrawMo.setBankAccountName(withdrawAccountMo.getBankAccountName());
        withdrawMo.setOpenAccountBank(withdrawAccountMo.getOpenAccountBank());
        add(withdrawMo);

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_APPLY.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(to.getTradeTitle());
        tradeMo.setTradeDetail(to.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(to.getOrderId());  // 申请提现的单号
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("申请提现重复提交");
            throw new RuntimeException("申请提现重复提交", e);
        }

        // 余额-，提现中+
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(to.getUserId());
        newAccountMo.setBalance(oldAccountMo.getBalance().subtract(tradeAmount));
        newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().add(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", to.getUserId());
        map.put("balance", newAccountMo.getBalance());
        map.put("oldBalance", oldAccountMo.getBalance());
        map.put("withdrawing", newAccountMo.getWithdrawing());
        map.put("oldWithdrawing", oldAccountMo.getWithdrawing());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("申请提现重复提交");
            throw new RuntimeException("申请提现重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(to.getUserId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("申请提现提交成功: {}", to);
        WithdrawApplyRo ro = new WithdrawApplyRo();
        ro.setResult(WithdrawApplyResultDic.SUCCESS);
        ro.setId(withdrawMo.getId());
        return ro;
    }

    /**
     * 获取用户的默认提现账户
     */
    private AfcWithdrawAccountMo getDefaultWithdrawAccount(Long userId) {
        AfcWithdrawAccountMo withdrawAccountMo;
        List<AfcWithdrawAccountMo> withdrawAccountMos = withdrawAccountSvc.listByUserId(userId);
        withdrawAccountMo = withdrawAccountMos.get(0);
        return withdrawAccountMo;
    }

    /**
     * 处理提现
     */
    @Override
    public WithdrawDealRo deal(WithdrawDealTo to) {
        if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
            _log.warn("没有填写处理人的用户ID/MAC/IP: {}", to);
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("处理提现发现没有此处理人: " + to.getOpId());
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("处理提现发现处理人已被锁定: " + opUserMo);
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.OP_LOCKED);
            return ro;
        }

        AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
        if (withdrawMo == null) {
            _log.error("处理提现发现没有此申请: " + to.getId());
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.NOT_FOUND_APPLICATION);
            return ro;
        }

        if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()) {
            _log.error("处理提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.ALREADY_DEALED);
            return ro;
        }

        // 查询提现账户的V支付账户信息
        AfcAccountMo accountMo = accountSvc.getById(withdrawMo.getAccountId());
        // 判断用户是否被锁定
        if (userSvc.isLocked(accountMo.getId())) {
            _log.error("提现的用户已经被锁定: " + accountMo);
            WithdrawDealRo ro = new WithdrawDealRo();
            ro.setResult(WithdrawDealResultDic.WITHDRAW_USER_LOCKED);
            return ro;
        }

        // 处理提现
        AfcWithdrawMo modifyMo = new AfcWithdrawMo();
        modifyMo.setId(withdrawMo.getId());
        modifyMo.setWithdrawState((byte) WithdrawStateDic.PROCESSING.getCode());
        modify(modifyMo);

        // 返回成功
        _log.info("处理提现提交成功: {}", to);
        WithdrawDealRo ro = new WithdrawDealRo();
        ro.setResult(WithdrawDealResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 确认提现成功（手动）（ 提现中- ）
     */
    @Override
    public WithdrawOkRo ok(WithdrawOkTo to) {
        if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
            _log.warn("没有填写提现记录的ID/确认人的用户ID/MAC/IP: {}", to);
            WithdrawOkRo ro = new WithdrawOkRo();
            ro.setResult(WithdrawOkResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("确认提现成功（手动）发现没有此确认人: " + to.getOpId());
            WithdrawOkRo ro = new WithdrawOkRo();
            ro.setResult(WithdrawOkResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("确认提现成功（手动）发现确认人已被锁定: " + opUserMo);
            WithdrawOkRo ro = new WithdrawOkRo();
            ro.setResult(WithdrawOkResultDic.OP_LOCKED);
            return ro;
        }

        AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
        if (withdrawMo == null) {
            _log.error("确认提现成功（手动）发现没有此申请: " + to.getId());
            WithdrawOkRo ro = new WithdrawOkRo();
            ro.setResult(WithdrawOkResultDic.NOT_FOUND_APPLICATION);
            return ro;
        }

        if (withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
            _log.error("确认提现成功（手动）发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
            WithdrawOkRo ro = new WithdrawOkRo();
            ro.setResult(WithdrawOkResultDic.ALREADY_DEALED);
            return ro;
        }

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(withdrawMo.getAccountId());

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = withdrawMo.getAmount();
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 确认提现
        AfcWithdrawMo modifyMo = new AfcWithdrawMo();
        modifyMo.setId(withdrawMo.getId());
        modifyMo.setWithdrawState((byte) WithdrawStateDic.OK.getCode());
        modify(modifyMo);

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(oldAccountMo.getId());
        tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_OK.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(withdrawMo.getTradeTitle()); // 交易标题
        tradeMo.setTradeDetail(withdrawMo.getTradeDetail()); // 交易详情
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(withdrawMo.getOrderId());  // 申请提现的单号
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("确认提现重复提交");
            throw new RuntimeException("确认提现重复提交", e);
        }

        // 提现中-
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(oldAccountMo.getId());
        newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().subtract(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", oldAccountMo.getId());
        map.put("withdrawing", newAccountMo.getWithdrawing());
        map.put("oldWithdrawing", oldAccountMo.getWithdrawing());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("确认提现重复提交");
            throw new RuntimeException("确认提现重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(oldAccountMo.getId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("确认提现成功（手动）成功: {}", to);
        WithdrawOkRo ro = new WithdrawOkRo();
        ro.setResult(WithdrawOkResultDic.SUCCESS);
        return ro;
    }

    /**
     * XXX AFC : 交易 : 作废提现（ 余额+，提现中- ）
     */
    @Override
    public WithdrawCancelRo cancel(WithdrawCancelTo to) {
        if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
            _log.warn("没有填写提现记录的ID/操作人的用户ID/MAC/IP: {}", to);
            WithdrawCancelRo ro = new WithdrawCancelRo();
            ro.setResult(WithdrawCancelResultDic.PARAM_ERROR);
            return ro;
        }

        SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("作废提现发现没有此操作人: " + to.getOpId());
            WithdrawCancelRo ro = new WithdrawCancelRo();
            ro.setResult(WithdrawCancelResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("作废提现发现操作人已被锁定: " + opUserMo);
            WithdrawCancelRo ro = new WithdrawCancelRo();
            ro.setResult(WithdrawCancelResultDic.OP_LOCKED);
            return ro;
        }

        AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
        if (withdrawMo == null) {
            _log.error("作废提现发现没有此申请: " + to.getId());
            WithdrawCancelRo ro = new WithdrawCancelRo();
            ro.setResult(WithdrawCancelResultDic.NOT_FOUND_APPLICATION);
            return ro;
        }

        if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()
                && withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
            _log.error("作废提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
            WithdrawCancelRo ro = new WithdrawCancelRo();
            ro.setResult(WithdrawCancelResultDic.ALREADY_DEALED);
            return ro;
        }

        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(withdrawMo.getAccountId());

        // 计算当前时间
        Date now = new Date();
        // 得到交易金额
        BigDecimal tradeAmount = withdrawMo.getAmount();
        // 生成本次交易的ID
        Long tradeId = _idWorker.getId();

        // 作废提现
        AfcWithdrawMo modifyMo = new AfcWithdrawMo();
        modifyMo.setId(withdrawMo.getId());
        modifyMo.setWithdrawState((byte) WithdrawStateDic.CANCEL.getCode());
        modifyMo.setCancelReason(to.getReason());   // 作废原因
        modify(modifyMo);

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setId(tradeId);
        tradeMo.setAccountId(oldAccountMo.getId());
        tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_CANCEL.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle("作废提现");
        tradeMo.setTradeDetail("作废提现");
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(withdrawMo.getOrderId());  // 申请提现的单号
        tradeMo.setOpId(to.getOpId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("作废提现重复提交");
            throw new RuntimeException("作废提现重复提交", e);
        }

        // 余额+，提现中-
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(oldAccountMo.getId());
        newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
        newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().subtract(tradeAmount));
        newAccountMo.setModifiedTimestamp(now.getTime());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", oldAccountMo.getId());
        map.put("balance", newAccountMo.getBalance());
        map.put("oldBalance", oldAccountMo.getBalance());
        map.put("withdrawing", newAccountMo.getWithdrawing());
        map.put("oldWithdrawing", oldAccountMo.getWithdrawing());
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        if (accountSvc.trade(map) != 1) {
            _log.error("申请提现重复提交");
            throw new RuntimeException("申请提现重复提交");
        }

        // 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setAccountId(oldAccountMo.getId());
        flowMo.setId(tradeId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);

        // 返回成功
        _log.info("作废提现成功: {}", to);
        WithdrawCancelRo ro = new WithdrawCancelRo();
        ro.setResult(WithdrawCancelResultDic.SUCCESS);
        return ro;
    }

}
