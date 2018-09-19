package rebue.afc.ro;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 查询账户款项的返回结果
 */
@JsonInclude(Include.NON_NULL)
@Data
public class AccountFundsRo {
    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 提现中总额
     */
    private BigDecimal withdrawing;

    /**
     * 返现金余额
     */
    private BigDecimal cashback;

    /**
     * 返现中的金额
     */
    private BigDecimal cashbacking;

    /**
     * 返佣金总额
     */
    private BigDecimal commissionTotal;

    /**
     * 返佣中的金额
     */
    private BigDecimal commissioning;

    /**
     * 进货保证金
     * 提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额
     */
    private BigDecimal deposit;

    /**
     * 已占用的进货保证金
     */
    private BigDecimal depositUsed;

}
