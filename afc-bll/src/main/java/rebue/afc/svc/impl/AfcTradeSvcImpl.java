package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcTradeMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;

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
public class AfcTradeSvcImpl extends MybatisBaseSvcImpl<AfcTradeMo, java.lang.Long, AfcTradeMapper> implements AfcTradeSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcTradeSvcImpl.class);

    @Resource
    private AfcTradeSvc         thisSvc;
    @Resource
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcFlowSvc          flowSvc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcTradeMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 计算此订单的退款总额(通过销售订单ID)
     */
    @Override
    public BigDecimal getRefundTotalAmountByOrderId(String orderId) {
        return _mapper.getTotalAmountByTradeTypeAndOrder(TradeTypeDic.REFUND_TO_BUYER.getCode(), orderId);
    }

    /**
     * 添加一笔交易记录
     * 1. 添加交易记录
     * 2. 修改账户相应的金额字段
     * 3. 添加账户流水
     */
    @Override
    public void addTrade(AfcTradeMo tradeMo) {
        _log.info("添加一笔交易记录");
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
            BigDecimal oldCashback = oldAccountMo.getCashback();                // 扣除前的返现金
            BigDecimal newCashback = null;                                      // 扣除后的返现金
            BigDecimal subtractCashback = null;                                 // 返现金将扣除多少
            BigDecimal oldBalance = oldAccountMo.getBalance();                  // 扣除前的余额
            BigDecimal newBalance = null;                                       // 扣除后的余额
            BigDecimal subtractBalance = null;                                  // 余额将扣除多少
            // 如果返现金余额>=交易金额，说明返现金够减，直接从返现金中扣除交易金额
            if (oldCashback.compareTo(tradeAmount) >= 0) {
                subtractCashback = tradeAmount;                                 // 扣除的返现金=交易金额
                newCashback = oldCashback.subtract(tradeAmount);                // 扣除后的返现金=扣除前返现金-交易金额
            }
            // 否则就是剩余金额<0，说明返现金不够减，那就先减完返现金，再减余额
            else {
                _log.info("返现金不足，扣完返现金再扣余额");
                subtractCashback = oldCashback;                                 // 扣除的返现金=扣除前的返现金
                newCashback = BigDecimal.ZERO;                                  // 扣除后的返现金=0
                subtractBalance = tradeAmount.subtract(oldCashback);            // 扣除的余额=交易金额-扣除前返现金
                newBalance = oldBalance.subtract(subtractBalance);              // 扣除后的余额=扣除前的余额-扣除的余额
            }

            // 设置改变后的返现金和余额
            newAccountMo.setCashback(newCashback);              // 改变后的的返现金
            if (newBalance != null)
                newAccountMo.setBalance(newBalance);            // 改变后的余额
            // 设置交易的返现金和余额
            tradeMo.setChangeAmount1(subtractCashback);         // 扣除的返现金
            tradeMo.setChangeAmount2(subtractBalance);          // 扣除的余额
            break;
        // XXX AFC : 交易 : （ 余额+ ）结算-结算成本(将成本打到供应商的余额)
        // XXX AFC : 交易 : （ 余额+ ）结算-结算卖家利润(将利润打到卖家的余额)
        case SETTLE_SUPPLIER:
        case SETTLE_SELLER:
            // 余额+
            newAccountMo.setBalance(oldAccountMo.getBalance().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返现中金额+ ）结算-结算返现中金额(打到买家的返现中金额)
        case SETTLE_CASHBACKING:
            // 返现中金额+
            newAccountMo.setCashbacking(oldAccountMo.getCashbacking().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 返现中金额-，返现金+ ）结算-结算返现金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
        case SETTLE_CASHBACK:
            // 返现中金额-，返现金+
            newAccountMo.setCashbacking(oldAccountMo.getCashbacking().subtract(tradeAmount));
            newAccountMo.setCashback(oldAccountMo.getCashback().add(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 已占用保证金+ ）结算-结算已占用保证金(释放卖家的已占用保证金相应金额)
        case SETTLE_DEPOSIT_USED:
            // 已占用保证金+
            newAccountMo.setDepositUsed(oldAccountMo.getDepositUsed().subtract(tradeAmount));
            break;
        // XXX AFC : 交易 : （ 余额+，返现金+ ）退款-退款到买家账户
        case REFUND_TO_BUYER:
            // 余额+，返现金+
            BigDecimal cashbackAmount = tradeMo.getChangeAmount1();   // 要退的返现金
            BigDecimal balanceAmount = tradeMo.getChangeAmount1();    // 要退的余额
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
        default:
            String msg = "不支持此交易类型";
            _log.error("{}: {}", msg, tradeMo.getTradeType());
            throw new RuntimeException(msg);
        }

        // 1. 添加交易记录
        thisSvc.add(tradeMo);                  // 如果重复提交，会抛出DuplicateKeyException运行时异常

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
        // 执行sql
        if (accountSvc.trade(map) != 1) {
            String msg = "修改账户的金额不成功: 出现并发问题";
            _log.error("{}-{}", msg, tradeMo);
            throw new DuplicateKeyException(msg);
        }

        // 3. 添加账户流水
        AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
        dozerMapper.map(newAccountMo, flowMo);
        flowMo.setId(tradeMo.getId());      // 流水ID=交易ID
        flowMo.setAccountId(accountId);
        flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
        flowSvc.add(flowMo);                // 如果重复提交，会抛出DuplicateKeyException运行时异常 }
    }
}
