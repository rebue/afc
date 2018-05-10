package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Database Table Remarks:
 *   账户流水(将账户每一次变更作个记录)
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table AFC_FLOW
 *
 * @mbg.generated do_not_delete_during_merge 2018-05-10 15:25:05
 */
@ApiModel(value = "AfcFlowMo", description = "账户流水(将账户每一次变更作个记录)")
@JsonInclude(Include.NON_NULL)
public class AfcFlowMo implements Serializable {
    /**
     * Database Column Remarks:
     *   流水ID(等于交易ID)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "流水ID(等于交易ID)")
    private Long id;

    /**
     * Database Column Remarks:
     *   旧修改时间戳
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "旧修改时间戳")
    private Long oldModifiedTimestamp;

    /**
     * Database Column Remarks:
     *   账户ID(账户ID也就是用户ID)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.ACCOUNT_ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "账户ID(账户ID也就是用户ID)")
    private Long accountId;

    /**
     * Database Column Remarks:
     *   余额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    /**
     * Database Column Remarks:
     *   最后结算余额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.SETTLE_BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "最后结算余额")
    private BigDecimal settleBalance;

    /**
     * Database Column Remarks:
     *   最后结算时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.SETTLE_TIME
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "最后结算时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date settleTime;

    /**
     * Database Column Remarks:
     *   提现中总额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.WITHDRAWING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "提现中总额")
    private BigDecimal withdrawing;

    /**
     * Database Column Remarks:
     *   返现金余额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.CASHBACK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "返现金余额")
    private BigDecimal cashback;

    /**
     * Database Column Remarks:
     *   返现中的金额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.CASHBACKING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "返现中的金额")
    private BigDecimal cashbacking;

    /**
     * Database Column Remarks:
     *   进货保证金
     *               提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.DEPOSIT
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "进货保证金\n"
             +"            提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额")
    private BigDecimal deposit;

    /**
     * Database Column Remarks:
     *   已占用的进货保证金
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.DEPOSIT_USED
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "已占用的进货保证金")
    private BigDecimal depositUsed;

    /**
     * Database Column Remarks:
     *   修改时间戳(添加或更新本条记录时的时间戳)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_FLOW.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @ApiModelProperty(value = "修改时间戳(添加或更新本条记录时的时间戳)")
    private Long modifiedTimestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table AFC_FLOW
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.ID
     *
     * @return the value of AFC_FLOW.ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.ID
     *
     * @param id the value for AFC_FLOW.ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     * @return the value of AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public Long getOldModifiedTimestamp() {
        return oldModifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     * @param oldModifiedTimestamp the value for AFC_FLOW.OLD_MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setOldModifiedTimestamp(Long oldModifiedTimestamp) {
        this.oldModifiedTimestamp = oldModifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.ACCOUNT_ID
     *
     * @return the value of AFC_FLOW.ACCOUNT_ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.ACCOUNT_ID
     *
     * @param accountId the value for AFC_FLOW.ACCOUNT_ID
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.BALANCE
     *
     * @return the value of AFC_FLOW.BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.BALANCE
     *
     * @param balance the value for AFC_FLOW.BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.SETTLE_BALANCE
     *
     * @return the value of AFC_FLOW.SETTLE_BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getSettleBalance() {
        return settleBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.SETTLE_BALANCE
     *
     * @param settleBalance the value for AFC_FLOW.SETTLE_BALANCE
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setSettleBalance(BigDecimal settleBalance) {
        this.settleBalance = settleBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.SETTLE_TIME
     *
     * @return the value of AFC_FLOW.SETTLE_TIME
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public Date getSettleTime() {
        return settleTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.SETTLE_TIME
     *
     * @param settleTime the value for AFC_FLOW.SETTLE_TIME
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.WITHDRAWING
     *
     * @return the value of AFC_FLOW.WITHDRAWING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getWithdrawing() {
        return withdrawing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.WITHDRAWING
     *
     * @param withdrawing the value for AFC_FLOW.WITHDRAWING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setWithdrawing(BigDecimal withdrawing) {
        this.withdrawing = withdrawing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.CASHBACK
     *
     * @return the value of AFC_FLOW.CASHBACK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getCashback() {
        return cashback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.CASHBACK
     *
     * @param cashback the value for AFC_FLOW.CASHBACK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.CASHBACKING
     *
     * @return the value of AFC_FLOW.CASHBACKING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getCashbacking() {
        return cashbacking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.CASHBACKING
     *
     * @param cashbacking the value for AFC_FLOW.CASHBACKING
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setCashbacking(BigDecimal cashbacking) {
        this.cashbacking = cashbacking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.DEPOSIT
     *
     * @return the value of AFC_FLOW.DEPOSIT
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.DEPOSIT
     *
     * @param deposit the value for AFC_FLOW.DEPOSIT
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.DEPOSIT_USED
     *
     * @return the value of AFC_FLOW.DEPOSIT_USED
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public BigDecimal getDepositUsed() {
        return depositUsed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.DEPOSIT_USED
     *
     * @param depositUsed the value for AFC_FLOW.DEPOSIT_USED
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setDepositUsed(BigDecimal depositUsed) {
        this.depositUsed = depositUsed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_FLOW.MODIFIED_TIMESTAMP
     *
     * @return the value of AFC_FLOW.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_FLOW.MODIFIED_TIMESTAMP
     *
     * @param modifiedTimestamp the value for AFC_FLOW.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    public void setModifiedTimestamp(Long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_FLOW
     *
     * @mbg.generated 2018-05-10 15:25:05
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
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_FLOW
     *
     * @mbg.generated 2018-05-10 15:25:05
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
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        ;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_FLOW
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}