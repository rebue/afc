package rebue.afc.ro;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询账户款项的返回结果
 */
@ApiModel(value = "查询账户款项的返回结果")
@JsonInclude(Include.NON_NULL)
public class AccountFundsRo {
    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    /**
     * 提现中总额
     */
    @ApiModelProperty(value = "提现中总额")
    private BigDecimal withdrawing;

    /**
     * 返现金余额
     */
    @ApiModelProperty(value = "返现金余额")
    private BigDecimal cashback;

    /**
     * 返现中的金额
     */
    @ApiModelProperty(value = "返现中的金额")
    private BigDecimal cashbacking;

    /**
     * 进货保证金
     */
    @ApiModelProperty(value = "进货保证金 提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额")
    private BigDecimal deposit;

    /**
     * 已占用的进货保证金
     */
    @ApiModelProperty(value = "已占用的进货保证金")
    private BigDecimal depositUsed;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(BigDecimal withdrawing) {
        this.withdrawing = withdrawing;
    }

    public BigDecimal getCashback() {
        return cashback;
    }

    public void setCashback(BigDecimal cashback) {
        this.cashback = cashback;
    }

    public BigDecimal getCashbacking() {
        return cashbacking;
    }

    public void setCashbacking(BigDecimal cashbacking) {
        this.cashbacking = cashbacking;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getDepositUsed() {
        return depositUsed;
    }

    public void setDepositUsed(BigDecimal depositUsed) {
        this.depositUsed = depositUsed;
    }

    @Override
    public String toString() {
        return "AccountFundsRo [balance=" + balance + ", withdrawing=" + withdrawing + ", cashback=" + cashback
                + ", cashbacking=" + cashbacking + ", deposit=" + deposit + ", depositUsed=" + depositUsed + "]";
    }

}
