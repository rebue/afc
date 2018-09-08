package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 账户流水(将账户每一次变更作个记录)
 *
 * 数据库表: AFC_FLOW
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcFlowMo implements Serializable {

    /**
     *    流水ID(等于交易ID)
     *
     *    数据库字段: AFC_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    旧修改时间戳
     *
     *    数据库字段: AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long oldModifiedTimestamp;

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    余额
     *
     *    数据库字段: AFC_FLOW.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal balance;

    /**
     *    最后结算余额
     *
     *    数据库字段: AFC_FLOW.SETTLE_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal settleBalance;

    /**
     *    最后结算时间
     *
     *    数据库字段: AFC_FLOW.SETTLE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date settleTime;

    /**
     *    已返佣金总额
     *
     *    数据库字段: AFC_FLOW.COMMISSION_TOTAL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal commissionTotal;

    /**
     *    待返佣金
     *
     *    数据库字段: AFC_FLOW.COMMISSIONING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal commissioning;

    /**
     *    提现中总额
     *
     *    数据库字段: AFC_FLOW.WITHDRAWING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal withdrawing;

    /**
     *    返现金余额
     *
     *    数据库字段: AFC_FLOW.CASHBACK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal cashback;

    /**
     *    返现中的金额
     *
     *    数据库字段: AFC_FLOW.CASHBACKING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal cashbacking;

    /**
     *    进货保证金
     *                提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额
     *
     *    数据库字段: AFC_FLOW.DEPOSIT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal deposit;

    /**
     *    已占用的进货保证金
     *
     *    数据库字段: AFC_FLOW.DEPOSIT_USED
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal depositUsed;

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_FLOW.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long modifiedTimestamp;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    流水ID(等于交易ID)
     *
     *    数据库字段: AFC_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    流水ID(等于交易ID)
     *
     *    数据库字段: AFC_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    旧修改时间戳
     *
     *    数据库字段: AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getOldModifiedTimestamp() {
        return oldModifiedTimestamp;
    }

    /**
     *    旧修改时间戳
     *
     *    数据库字段: AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOldModifiedTimestamp(Long oldModifiedTimestamp) {
        this.oldModifiedTimestamp = oldModifiedTimestamp;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    余额
     *
     *    数据库字段: AFC_FLOW.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     *    余额
     *
     *    数据库字段: AFC_FLOW.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     *    最后结算余额
     *
     *    数据库字段: AFC_FLOW.SETTLE_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getSettleBalance() {
        return settleBalance;
    }

    /**
     *    最后结算余额
     *
     *    数据库字段: AFC_FLOW.SETTLE_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setSettleBalance(BigDecimal settleBalance) {
        this.settleBalance = settleBalance;
    }

    /**
     *    最后结算时间
     *
     *    数据库字段: AFC_FLOW.SETTLE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getSettleTime() {
        return settleTime;
    }

    /**
     *    最后结算时间
     *
     *    数据库字段: AFC_FLOW.SETTLE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    /**
     *    已返佣金总额
     *
     *    数据库字段: AFC_FLOW.COMMISSION_TOTAL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getCommissionTotal() {
        return commissionTotal;
    }

    /**
     *    已返佣金总额
     *
     *    数据库字段: AFC_FLOW.COMMISSION_TOTAL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setCommissionTotal(BigDecimal commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    /**
     *    待返佣金
     *
     *    数据库字段: AFC_FLOW.COMMISSIONING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getCommissioning() {
        return commissioning;
    }

    /**
     *    待返佣金
     *
     *    数据库字段: AFC_FLOW.COMMISSIONING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setCommissioning(BigDecimal commissioning) {
        this.commissioning = commissioning;
    }

    /**
     *    提现中总额
     *
     *    数据库字段: AFC_FLOW.WITHDRAWING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getWithdrawing() {
        return withdrawing;
    }

    /**
     *    提现中总额
     *
     *    数据库字段: AFC_FLOW.WITHDRAWING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setWithdrawing(BigDecimal withdrawing) {
        this.withdrawing = withdrawing;
    }

    /**
     *    返现金余额
     *
     *    数据库字段: AFC_FLOW.CASHBACK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getCashback() {
        return cashback;
    }

    /**
     *    返现金余额
     *
     *    数据库字段: AFC_FLOW.CASHBACK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    /**
     *    返现中的金额
     *
     *    数据库字段: AFC_FLOW.CASHBACKING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getCashbacking() {
        return cashbacking;
    }

    /**
     *    返现中的金额
     *
     *    数据库字段: AFC_FLOW.CASHBACKING
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setCashbacking(BigDecimal cashbacking) {
        this.cashbacking = cashbacking;
    }

    /**
     *    进货保证金
     *                提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额
     *
     *    数据库字段: AFC_FLOW.DEPOSIT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     *    进货保证金
     *                提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额
     *
     *    数据库字段: AFC_FLOW.DEPOSIT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     *    已占用的进货保证金
     *
     *    数据库字段: AFC_FLOW.DEPOSIT_USED
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getDepositUsed() {
        return depositUsed;
    }

    /**
     *    已占用的进货保证金
     *
     *    数据库字段: AFC_FLOW.DEPOSIT_USED
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setDepositUsed(BigDecimal depositUsed) {
        this.depositUsed = depositUsed;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_FLOW.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_FLOW.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setModifiedTimestamp(Long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", oldModifiedTimestamp=").append(oldModifiedTimestamp);
        sb.append(", accountId=").append(accountId);
        sb.append(", balance=").append(balance);
        sb.append(", settleBalance=").append(settleBalance);
        sb.append(", settleTime=").append(settleTime);
        sb.append(", commissionTotal=").append(commissionTotal);
        sb.append(", commissioning=").append(commissioning);
        sb.append(", withdrawing=").append(withdrawing);
        sb.append(", cashback=").append(cashback);
        sb.append(", cashbacking=").append(cashbacking);
        sb.append(", deposit=").append(deposit);
        sb.append(", depositUsed=").append(depositUsed);
        sb.append(", modifiedTimestamp=").append(modifiedTimestamp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AfcFlowMo other = (AfcFlowMo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}
