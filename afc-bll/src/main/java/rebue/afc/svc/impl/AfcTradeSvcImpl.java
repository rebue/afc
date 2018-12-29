package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcTradeMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.AfcTradeListRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.AfcTradeTo;
import rebue.afc.to.GetAfcTradeTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucOrgMo;
import rebue.suc.mo.SucUserMo;
import rebue.suc.ro.SucOrgRo;
import rebue.suc.svr.feign.SucOrgSvc;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 账户交易(账户交易流水)
 *
 * 在单独使用不带任何参数的 @Transactional 注释时， propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED， 而且事务不会针对受控异常（checked exception）回滚。
 *
 * 注意： 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcTradeSvcImpl extends MybatisBaseSvcImpl<AfcTradeMo, java.lang.Long, AfcTradeMapper> implements AfcTradeSvc {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(final AfcTradeMo mo) {
        _log.info("添加账户交易");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    private static final Logger _log = LoggerFactory.getLogger(AfcTradeSvcImpl.class);

    @Resource
    private AfcTradeSvc         thisSvc;

    @Resource
    private AfcAccountSvc       accountSvc;

    @Resource
    private AfcFlowSvc          flowSvc;

    @Resource
    private Mapper              dozerMapper;

    @Resource
    private SucUserSvc          sucUserSvc;

    @Resource
    private SucOrgSvc           sucOrgSvc;

    // /**
    // * 计算此订单的退款总额(通过订单ID)
    // */
    // @Override
    // public BigDecimal getRefundedTotalByOrderId(final String orderId) {
    // return
    // _mapper.getTotalByTradeTypeAndOrder(TradeTypeDic.REFUND_TO_BUYER.getCode(),
    // orderId);
    // }

    /**
     * 添加一笔交易记录 1. 添加交易记录 2. 修改账户相应的金额字段 3. 添加账户流水
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addTrade(final AfcTradeMo tradeMo) {
        _log.info("添加一笔交易记录: {}", tradeMo);
        // 账户ID
        final Long accountId = tradeMo.getAccountId();
        // 交易金额
        final BigDecimal tradeAmount = tradeMo.getTradeAmount();
        // 当前时间=交易时间
        final Date now = tradeMo.getTradeTime();
        // 查询旧账户信息
        final AfcAccountMo oldAccountMo = accountSvc.getById(accountId);
        if (oldAccountMo == null) {
            final String msg = "没有此账户";
            _log.error("{}-{}", msg, accountId);
            throw new RuntimeException(msg);
        }
        final AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(accountId);
        newAccountMo.setModifiedTimestamp(_idWorker.getId());
        // 判断交易类型
        switch (TradeTypeDic.getItem(tradeMo.getTradeType())) {
        case PAY: // XXX AFC : 交易 : （ 返现金-，余额-，优先扣减返现金 ）V支付-支付
            // 返现金-，余额-
            // 计算返现金和余额各扣除多少，优先扣返现金，再扣余额
            // 扣除前的返现金
            final BigDecimal oldCashback = oldAccountMo.getCashback();
            // 扣除前的余额
            final BigDecimal oldBalance = oldAccountMo.getBalance();
            // 扣除后的返现金
            BigDecimal newCashback = null;
            // 扣除后的余额
            BigDecimal newBalance = null;
            // 返现金将扣除多少
            BigDecimal subtractCashback = null;
            // 余额将扣除多少
            BigDecimal subtractBalance = null;
            // 如果返现金余额>=交易金额，说明返现金够减，直接从返现金中扣除交易金额
            if (oldCashback.compareTo(tradeAmount) >= 0) {
                // 扣除的返现金=交易金额
                subtractCashback = tradeAmount;
                // 扣除的余额为0（不扣除）
                subtractBalance = BigDecimal.ZERO;
                // 扣除后的返现金=扣除前返现金-交易金额
                newCashback = oldCashback.subtract(tradeAmount);
                // 余额不扣除，不用变，所以不用设置newBalance
            }
            // 否则就是剩余金额<0，说明返现金不够减，那就先减完返现金，再减余额
            else {
                _log.info("返现金不足，扣完返现金再扣余额");
                // 扣除的返现金=扣除前的返现金
                subtractCashback = oldCashback;
                // 扣除后的返现金=0
                newCashback = BigDecimal.ZERO;
                // 扣除的余额=交易金额-扣除前返现金
                subtractBalance = tradeAmount.subtract(oldCashback);
                // 扣除后的余额=扣除前的余额-扣除的余额
                newBalance = oldBalance.subtract(subtractBalance);
            }
            // 设置改变后的返现金和余额
            // 改变后的的返现金
            newAccountMo.setCashback(newCashback);
            if (newBalance != null) {
                // 改变后的余额
                newAccountMo.setBalance(newBalance);
            }
            // 设置交易的返现金和余额
            // 扣除的返现金
            tradeMo.setChangeAmount1(subtractCashback);
            // 扣除的余额
            tradeMo.setChangeAmount2(subtractBalance);
            break;
        case SETTLE_SUPPLIER: // XXX AFC : 交易 : （ 余额+ ）结算-结算成本(将成本打到供应商的余额);
        case SETTLE_SELLER: // XXX AFC : 交易 : （ 余额+ ）结算-结算卖家利润(将利润打到卖家的余额);
        case REFUND_COMPENSATION_TO_SELLER: // XXX AFC : 交易 : （ 余额+ ）退款补偿金-退款补偿金给卖家（补偿到卖家的余额）
        case CHARGE_BALANCE: // XXX AFC : 交易 : （ 余额+ ）余额充值或扣款
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            break;
        // case SETTLE_CASHBACKING: // XXX AFC : 交易 : （ 返现中金额+ ）结算-结算返现中金额(打到买家的返现中金额)
        // newAccountMo.setCashbacking(oldAccountMo.getCashbacking().add(tradeAmount));
        // break;
        case SETTLE_CASHBACK: // XXX AFC : 交易 : （ 返现中金额-，返现金+
                              // ）结算-结算返现金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
            // newAccountMo.setCashbacking(oldAccountMo.getCashbacking().subtract(tradeAmount));
            newAccountMo.setCashback(oldAccountMo.getCashback().add(tradeAmount));
            break;
        // // XXX AFC : 交易 : （ 返佣中金额+ ）结算-结算返佣中金额(打到上家的返佣中金额)
        // case SETTLE_COMMISSIONING:
        // // 返佣中金额+
        // newAccountMo.setCommissioning(oldAccountMo.getCommissioning().add(tradeAmount));
        // break;
        // XXX AFC : 交易 : （ 返佣中金额-，已返佣金总额+，余额+ ）
        // 结算-结算返佣金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
        case SETTLE_COMMISSION:
            // newAccountMo.setCommissioning(oldAccountMo.getCommissioning().subtract(tradeAmount));
            newAccountMo.setCommissionTotal(oldAccountMo.getCommissionTotal().add(tradeAmount));
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 已占用保证金- ）结算-结算已占用保证金(释放卖家的已占用保证金相应金额)
        case SETTLE_DEPOSIT_USED:
            // 已占用保证金+
            newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().subtract(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额+，返现金+ ）退款-退款到买家账户
        case REFUND_TO_BUYER:
            // 余额+，返现金+
            // 要退的返现金
            final BigDecimal cashbackAmount = tradeMo.getChangeAmount1();
            // 要退的余额
            final BigDecimal balanceAmount = tradeMo.getChangeAmount2();
            // 计算交易后的余额
            BigDecimal newBalanceAmount = oldAccountMo.getBalance().add(balanceAmount);
            // 计算交易后的返现金
            BigDecimal newCashbackAmount = oldAccountMo.getCashback().add(cashbackAmount);
            // 如果返现金不够扣，剩余的从余额中扣(退款时，原本要返现的返现金会相应收回，所以会有不够扣的情况)
            if (newCashbackAmount.compareTo(BigDecimal.ZERO) < 0) {
                _log.info("返现金不够扣，剩余的从余额中扣");
                newBalanceAmount = newBalanceAmount.add(newCashbackAmount);
                newCashbackAmount = BigDecimal.ZERO;
            }
            // 设置交易后的返现金和余额
            newAccountMo.setBalance(newBalanceAmount);
            newAccountMo.setCashback(newCashbackAmount);
            break;
        // // XXX AFC : 交易 : （ 返现金- ）退款-收回买家返现金
        // case GETBACK_SELLER:
        // break;
        // // XXX AFC : 交易 : （ 返现金- ）退款-收回卖家款项
        // case GETBACK_SUPPLIER:
        // break;
        // // XXX AFC : 交易 : （ 返现金- ）退款-收回释放的卖家已占用保证金
        // case GETBACK_DEPOSIT_USED:
        // break;
        // XXX AFC : 交易 : （ 进货保证金+ ）进货保证金-充值
        case DEPOSIT_CHARGE:
            // 进货保证金+
            newAccountMo.setDeposit(oldAccountMo.getDeposit().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 已占用进货保证金+ ）进货保证金-进货
        case DEPOSIT_GOODS_IN:
            // 已占用进货保证金+
            newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 已占用进货保证金- ）进货保证金-退货
        case DEPOSIT_GOODS_RETURN:
            // 已占用进货保证金-
            newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().subtract(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额-，提现中+ ）提现申请
        case WITHDRAW_APPLY:
            // 余额-，提现中+
            newAccountMo.setBalance(oldAccountMo.getBalance().subtract(tradeAmount));
            newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 提现中- ）确认提现成功（手动）
        case WITHDRAW_OK:
            // 提现中-
            newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().subtract(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额+，提现中- ）作废提现
        case WITHDRAW_CANCEL:
            // 余额+，提现中-
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            newAccountMo.setWithdrawing(oldAccountMo.getWithdrawing().subtract(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额- ）提现服务费
        case WITHDRAW_SEVICE_CHARGE:
            // 余额-
            newAccountMo.setBalance(oldAccountMo.getBalance().subtract(tradeAmount));
            break;
        // // XXX AFC : 交易 : （ 返佣中金额- ）补偿取消的任务-补偿扣除返佣中金额
        // case COMPENDSATE_SUBTRACT_COMMISSIONING:
        // // 返佣中金额-
        // newAccountMo.setCommissioning(oldAccountMo.getCommissioning().subtract(tradeAmount));
        // break;
        // XXX AFC : 交易 : (返现金+) 返现金充值或扣款
        case CHARGE_CASHBACK:
            newAccountMo.setCashback(oldAccountMo.getCashback().add(tradeAmount));
            break;
        default:
            final String msg = "不支持此交易类型";
            _log.error("{}: {}", msg, tradeMo.getTradeType());
            throw new RuntimeException(msg);
        }

        // 1. 添加交易记录
        // 如果重复提交，会抛出DuplicateKeyException运行时异常
        thisSvc.add(tradeMo);
        // 2. 修改账户相应的金额字段
        final HashMap<String, Object> map = new HashMap<>();
        if (newAccountMo.getBalance() != null && (newAccountMo.getBalance().compareTo(oldAccountMo.getBalance()) != 0)) {
            map.put("balance", newAccountMo.getBalance());
            map.put("oldBalance", oldAccountMo.getBalance());
        }
        if (newAccountMo.getCashback() != null && (newAccountMo.getCashback().compareTo(oldAccountMo.getCashback()) != 0)) {
            map.put("cashback", newAccountMo.getCashback());
            map.put("oldCashback", oldAccountMo.getCashback());
        }
        if (newAccountMo.getCommissionTotal() != null && (newAccountMo.getCommissionTotal().compareTo(oldAccountMo.getCommissionTotal()) != 0)) {
            map.put("commissionTotal", newAccountMo.getCommissionTotal());
            map.put("oldCommissionTotal", oldAccountMo.getCommissionTotal());
        }
        if (newAccountMo.getDeposit() != null && (newAccountMo.getDeposit().compareTo(oldAccountMo.getDeposit()) != 0)) {
            map.put("deposit", newAccountMo.getDeposit());
            map.put("oldDeposit", oldAccountMo.getDeposit());
        }
        if (newAccountMo.getDepositUsed() != null && (newAccountMo.getDepositUsed().compareTo(oldAccountMo.getDepositUsed()) != 0)) {
            map.put("depositUsed", newAccountMo.getDepositUsed());
            map.put("oldDepositUsed", oldAccountMo.getDepositUsed());
        }
        if (newAccountMo.getWithdrawing() != null && newAccountMo.getWithdrawing().compareTo(oldAccountMo.getWithdrawing()) != 0) {
            map.put("withdrawing", newAccountMo.getWithdrawing());
            map.put("oldWithdrawing", oldAccountMo.getWithdrawing());
        }
        map.put("id", accountId);
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        // 修改账户的金额
        accountSvc.modifyAmount(map);
        // 3. 添加账户流水
        final AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        // 流水ID=交易ID
        flowMo.setId(tradeMo.getId());
        flowMo.setAccountId(accountId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        // 如果重复提交，会抛出DuplicateKeyException运行时异常 }
        flowSvc.add(flowMo);
    }

    /**
     * 查询用户返现金交易信息
     *
     * @param qo
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public PageInfo<AfcTradeMo> listCashbackTrade(final AfcTradeMo qo, final int pageNum, final int pageSize, final String orderBy) {
        _log.info("list: qo-{}; pageNum-{}; orderBy-{}; pageSize-{}", qo, pageNum, pageSize, orderBy);
        return PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectCashbackTrade(qo));
    }

    /**
     * 查询用户余额交易信息
     *
     * @param qo
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public PageInfo<AfcTradeMo> listBalanceTrade(final AfcTradeMo qo, final int pageNum, final int pageSize, final String orderBy) {
        _log.info("list: qo-{}; pageNum-{}; orderBy-{}; pageSize-{}", qo, pageNum, pageSize, orderBy);
        return PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectBalanceTrade(qo));
    }

    /**
     * 查询个人账号交易记录
     */
    @Override
    public PageInfo<AfcTradeListRo> personTradeList(final AfcTradeTo to, final int pageNum, final int pageSize) {
        _log.info("list: to-{}; pageNum-{}; pageSize-{}", to, pageNum, pageSize);
        final Byte[] tradeType = to.getTradeType();
        if (tradeType != null && tradeType.length > 0) {
            String tradeTypeString = "(";
            for (int i = 0; i < tradeType.length; i++) {
                if (i == tradeType.length - 1) {
                    tradeTypeString = tradeTypeString + tradeType[i] + ")";
                } else {
                    tradeTypeString = tradeTypeString + tradeType[i] + ",";
                }
            }
            to.setTradeTypeString(tradeTypeString);
            _log.info("tradeTypeString：{}", tradeTypeString);
        }
        if (to.getAccountName() != null) {
            String accountIdStr = "(";
            final List<SucUserMo> userMoResultList = sucUserSvc.getByWxNick(to.getAccountName());
            _log.info("根据微信昵称查找用户信息为：{}", userMoResultList);
            if (userMoResultList.size() == 0) {
                // 如果用户查询为空则设置账号的ID为0，这样使查询结果为空
                accountIdStr = accountIdStr + "0)";
            } else {
                for (int i = 0; i < userMoResultList.size(); i++) {
                    if (i == userMoResultList.size() - 1) {
                        accountIdStr += userMoResultList.get(i).getId().toString() + ")";
                    } else {
                        accountIdStr += userMoResultList.get(i).getId().toString() + ",";
                    }
                }
            }
            to.setAccountIdStr(accountIdStr);
            _log.info("accountIdStr：{}", accountIdStr);
        }
        to.setAccountType((byte) 1);
        _log.info("查找用户交易流水请求参数：{}", to);
        PageInfo<AfcTradeListRo> tradeListResult = new PageInfo<>();
        tradeListResult = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> _mapper.selectTradeList(to));
        _log.info("查找用户交易流水返回结果:{}", tradeListResult);
        final List<AfcTradeListRo> tradeList = tradeListResult.getList();
        for (int i = 0; i < tradeList.size(); i++) {
            final AfcTradeListRo ro = tradeList.get(i);
            final SucUserMo sucMo = sucUserSvc.getById(ro.getAccountId());
            if (sucMo != null) {
                tradeList.get(i).setAccountName(sucMo.getWxNickname());
            } else {
                _log.info("没有查到此用户信息:{}", ro.getAccountId());
            }
            final SucUserMo opMo = sucUserSvc.getById(ro.getOpId());
            if (opMo != null) {
                tradeList.get(i).setOpName(opMo.getWxNickname());
            } else {
                _log.info("没有查到操作人用户信息:{}", ro.getAccountId());
            }
        }
        return tradeListResult;
    }

    /**
     * 查询组织账号交易记录
     */
    @Override
    public PageInfo<AfcTradeListRo> orgTradeList(final AfcTradeTo to, final int pageNum, final int pageSize) {
        _log.info("list: to-{}; pageNum-{}; pageSize-{}", to, pageNum, pageSize);
        final Byte[] tradeType = to.getTradeType();
        if (tradeType != null && tradeType.length > 0) {
            String tradeTypeString = "(";
            for (int i = 0; i < tradeType.length; i++) {
                if (i == tradeType.length - 1) {
                    tradeTypeString = tradeTypeString + tradeType[i] + ")";
                } else {
                    tradeTypeString = tradeTypeString + tradeType[i] + ",";
                }
            }
            to.setTradeTypeString(tradeTypeString);
            _log.info("tradeTypeString：{}", tradeTypeString);
        }
        if (to.getAccountName() != null) {
            String accountIdStr = "(";
            final SucOrgMo sucOrgMo = sucOrgSvc.getOne(to.getAccountName());
            _log.info("根据组织名称查找组织信息为：{}", sucOrgMo);
            if (sucOrgMo == null) {
                // 如果用户查询为空则设置账号的ID为0，这样使查询结果为空
                accountIdStr = accountIdStr + "0)";
            } else {
                accountIdStr += sucOrgMo.getId().toString() + ")";
            }
            to.setAccountIdStr(accountIdStr);
            _log.info("accountIdStr：{}", accountIdStr);
        }
        to.setAccountType((byte) 2);
        _log.info("查找组织交易流水请求参数：{}", to);
        PageInfo<AfcTradeListRo> tradeListResult = new PageInfo<>();
        tradeListResult = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> _mapper.selectTradeList(to));
        _log.info("查找组织交易流水返回结果:{}", tradeListResult);
        final List<AfcTradeListRo> tradeList = tradeListResult.getList();
        for (int i = 0; i < tradeList.size(); i++) {
            final AfcTradeListRo ro = tradeList.get(i);
            _log.info("根据组织账号ID获取组织信息:{}", ro.getAccountId());
            final SucOrgRo sucOrgRo = sucOrgSvc.getById(ro.getAccountId());
            _log.info("根据组织账号ID获取到的组织信息:{}", sucOrgRo.getRecord());
            if (sucOrgRo.getRecord() != null) {
                tradeList.get(i).setAccountName(sucOrgRo.getRecord().getName());
            } else {
                _log.info("没有查到此组织信息:{}", ro.getAccountId());
            }
            final SucUserMo opMo = sucUserSvc.getById(ro.getOpId());
            if (opMo != null) {
                tradeList.get(i).setOpName(opMo.getWxNickname());
            } else {
                _log.info("没有查到操作人用户信息:{}", ro.getAccountId());
            }
        }
        return tradeListResult;
    }

    /**
     * 获取组织信息
     */
    @Override
    public PageInfo<AfcTradeMo> getOrgTradeList(final GetAfcTradeTo to, final int pageNum, final int pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> _mapper.getTrade(to));
    }
}
