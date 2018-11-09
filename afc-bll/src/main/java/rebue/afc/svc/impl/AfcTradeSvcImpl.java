package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcTradeMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;

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
    public int add(AfcTradeMo mo) {
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

    /**
     * 计算此订单的退款总额(通过销售订单ID)
     */
    @Override
    public BigDecimal getRefundTotalAmountByOrderId(String orderId) {
        return _mapper.getTotalAmountByTradeTypeAndOrder(TradeTypeDic.REFUND_TO_BUYER.getCode(), orderId);
    }

    /**
     * 添加一笔交易记录 1. 添加交易记录 2. 修改账户相应的金额字段 3. 添加账户流水
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addTrade(AfcTradeMo tradeMo) {
        _log.info("添加一笔交易记录: {}", tradeMo);
        // 账户ID
        Long accountId = tradeMo.getAccountId();
        // 交易金额
        BigDecimal tradeAmount = tradeMo.getTradeAmount();
        // 当前时间=交易时间
        Date now = tradeMo.getTradeTime();
        // 查询旧账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(accountId);
        if (oldAccountMo == null) {
            String msg = "没有此账户";
            _log.error("{}-{}", msg, accountId);
            throw new RuntimeException(msg);
        }
        AfcAccountMo newAccountMo = new AfcAccountMo();
        newAccountMo.setId(accountId);
        newAccountMo.setModifiedTimestamp(now.getTime());
        // 判断交易类型
        switch (TradeTypeDic.getItem(tradeMo.getTradeType())) {
        // XXX AFC : 交易 : （ 返现金-，余额- ）V支付-支付
        case PAY:
            // 返现金-，余额-
            // 计算返现金和余额各扣除多少，先扣返现金，再扣余额
            // 扣除前的返现金
            BigDecimal oldCashback = oldAccountMo.getCashback();
            // 扣除后的返现金
            BigDecimal newCashback = null;
            // 返现金将扣除多少
            BigDecimal subtractCashback = null;
            // 扣除前的余额
            BigDecimal oldBalance = oldAccountMo.getBalance();
            // 扣除后的余额
            BigDecimal newBalance = null;
            // 余额将扣除多少
            BigDecimal subtractBalance = null;
            // 如果返现金余额>=交易金额，说明返现金够减，直接从返现金中扣除交易金额
            if (oldCashback.compareTo(tradeAmount) >= 0) {
                // 扣除的返现金=交易金额
                subtractCashback = tradeAmount;
                // 扣除后的返现金=扣除前返现金-交易金额
                newCashback = oldCashback.subtract(tradeAmount);
            } else // 否则就是剩余金额<0，说明返现金不够减，那就先减完返现金，再减余额
            {
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
            if (newBalance != null)
                // 改变后的余额
                newAccountMo.setBalance(newBalance);
            // 设置交易的返现金和余额
            // 扣除的返现金
            tradeMo.setChangeAmount1(subtractCashback);
            // 扣除的余额
            tradeMo.setChangeAmount2(subtractBalance);
            break;
        // XXX AFC : 交易 : （ 余额+ ）结算-结算成本(将成本打到供应商的余额)
        case SETTLE_SUPPLIER:
            // 余额+
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返现中金额+ ）结算-结算返现中金额(打到买家的返现中金额)
        case SETTLE_CASHBACKING:
            // 返现中金额+
            newAccountMo.setCashbacking(oldAccountMo.getCashbacking().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返现中金额-，返现金+
        // ）结算-结算返现金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
        case SETTLE_CASHBACK:
            // 返现中金额-，返现金+
            newAccountMo.setCashbacking(oldAccountMo.getCashbacking().subtract(tradeAmount));
            newAccountMo.setCashback(oldAccountMo.getCashback().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返佣中金额+ ）结算-结算返佣中金额(打到上家的返佣中金额)
        case SETTLE_COMMISSIONING:
            // 返佣中金额+
            newAccountMo.setCommissioning(oldAccountMo.getCommissioning().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返佣中金额-，已返佣金总额+，余额+
        // ）结算-结算返佣金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
        case SETTLE_COMMISSION:
            // 返佣中金额-，已返佣金总额+，余额+
            newAccountMo.setCommissioning(oldAccountMo.getCommissioning().subtract(tradeAmount));
            newAccountMo.setCommissionTotal(oldAccountMo.getCommissionTotal().add(tradeAmount));
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额+ ）结算-结算卖家利润(将利润打到卖家的余额)
        case SETTLE_SELLER:
            // 余额+
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
            BigDecimal cashbackAmount = tradeMo.getChangeAmount1();
            // 要退的余额
            BigDecimal balanceAmount = tradeMo.getChangeAmount2();
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
        // XXX AFC : 交易 : （ 返现金- ）退款-收回买家返现金
        case GETBACK_SELLER:
            break;
        // XXX AFC : 交易 : （ 返现金- ）退款-收回卖家款项
        case GETBACK_SUPPLIER:
            break;
        // XXX AFC : 交易 : （ 返现金- ）退款-收回释放的卖家已占用保证金
        case GETBACK_DEPOSIT_USED:
            break;
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
        // XXX AFC : 交易 : （ 返佣中金额- ）补偿取消的任务-补偿扣除返佣中金额
        case COMPENDSATE_SUBTRACT_COMMISSIONING:
            // 返佣中金额-
            newAccountMo.setCommissioning(oldAccountMo.getCommissioning().subtract(tradeAmount));
            break;
        //XXX AFC : 交易 : (余额+) 余额充值或扣款
        case CHARGE_BALANCE:
        	// 余额+
        	newAccountMo.setBalance(oldAccountMo.getBalance().subtract(tradeAmount));
            break;
          //XXX AFC : 交易 : (返现金+) 返现金充值或扣款
        case CHARGE_CASHBACK:
        	// 余额+
        	newAccountMo.setCashback(oldAccountMo.getBalance().subtract(tradeAmount));
            break;
        	
        default:
            String msg = "不支持此交易类型";
            _log.error("{}: {}", msg, tradeMo.getTradeType());
            throw new RuntimeException(msg);
        }
        // 1. 添加交易记录
        // 如果重复提交，会抛出DuplicateKeyException运行时异常
        thisSvc.add(tradeMo);
        // 2. 修改账户相应的金额字段
        HashMap<String, Object> map = new HashMap<>();
        if (newAccountMo.getBalance() != null && !newAccountMo.getBalance().equals(oldAccountMo.getBalance())) {
            map.put("balance", newAccountMo.getBalance());
            map.put("oldBalance", oldAccountMo.getBalance());
        }
        if (newAccountMo.getCashbacking() != null && !newAccountMo.getCashbacking().equals(oldAccountMo.getCashbacking())) {
            map.put("cashbacking", newAccountMo.getCashbacking());
            map.put("oldCashbacking", oldAccountMo.getCashbacking());
        }
        if (newAccountMo.getCashback() != null && !newAccountMo.getCashback().equals(oldAccountMo.getCashback())) {
            map.put("cashback", newAccountMo.getCashback());
            map.put("oldCashback", oldAccountMo.getCashback());
        }
        if (newAccountMo.getCommissioning() != null && !newAccountMo.getCommissioning().equals(oldAccountMo.getCommissioning())) {
            map.put("commissioning", newAccountMo.getCommissioning());
            map.put("oldCommissioning", oldAccountMo.getCommissioning());
        }
        if (newAccountMo.getCommissionTotal() != null && !newAccountMo.getCommissionTotal().equals(oldAccountMo.getCommissionTotal())) {
            map.put("commissionTotal", newAccountMo.getCommissionTotal());
            map.put("oldCommissionTotal", oldAccountMo.getCommissionTotal());
        }
        if (newAccountMo.getDeposit() != null && !newAccountMo.getDeposit().equals(oldAccountMo.getDeposit())) {
            map.put("deposit", newAccountMo.getDeposit());
            map.put("oldDeposit", oldAccountMo.getDeposit());
        }
        if (newAccountMo.getDepositUsed() != null && !newAccountMo.getDepositUsed().equals(oldAccountMo.getDepositUsed())) {
            map.put("depositUsed", newAccountMo.getDepositUsed());
            map.put("oldDepositUsed", oldAccountMo.getDepositUsed());
        }
        if (newAccountMo.getWithdrawing() != null && !newAccountMo.getWithdrawing().equals(oldAccountMo.getWithdrawing())) {
            map.put("withdrawing", newAccountMo.getWithdrawing());
            map.put("oldWithdrawing", oldAccountMo.getWithdrawing());
        }
        map.put("id", accountId);
        map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
        map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
        // 修改账户的金额
        accountSvc.modifyAmount(map);
        // 3. 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
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
    public PageInfo<AfcTradeMo> cashbackTradeList(AfcTradeMo qo, int pageNum, int pageSize, String orderBy) {
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
    public PageInfo<AfcTradeMo> balanceTradeList(AfcTradeMo qo, int pageNum, int pageSize, String orderBy) {
        _log.info("list: qo-{}; pageNum-{}; orderBy-{}; pageSize-{}", qo, pageNum, pageSize, orderBy);
        return PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectBalanceTrade(qo));
    }
}
