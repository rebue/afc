package rebue.afc.to;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class AddSettleTasksDetailTo {
    /**
     * 结算给买家返现金的金额
     */
    private BigDecimal settleBuyerCashbackAmount;
    /**
     * 结算给买家返现金的标题
     */
    private String     settleBuyerCashbackTitle;
    /**
     * 结算给买家返现金的详情
     */
    private String     settleBuyerCashbackDetail;

    /**
     * 上家的账户ID
     */
    private Long       uplineAccountId;
    /**
     * 上家的订单ID
     */
    private Long       uplineOrderId;
    /**
     * 上家的订单详情ID
     */
    private Long       uplineOrderDetailId;
    /**
     * 结算给上家佣金的金额
     */
    private BigDecimal settleUplineCommissionAmount;
    /**
     * 结算给上家佣金的标题
     */
    private String     settleUplineCommissionTitle;
    /**
     * 结算给上家佣金的详情
     */
    private String     settleUplineCommissionDetail;

    /**
     * 结算给平台服务费的金额
     */
    private BigDecimal settlePlatformServiceFeeAmount;

    /**
     * 供应商的账户ID
     */
    private Long       supplierAccountId;
    /**
     * 结算给供应商的金额（成本，打到余额）
     */
    private BigDecimal settleSupplierAmount;
    /**
     * 结算给供应商的标题
     */
    private String     settleSupplierTitle;
    /**
     * 结算给供应商的详情
     */
    private String     settleSupplierDetail;

    /**
     * 结算给卖家的金额（利润，打到余额）
     */
    private BigDecimal settleSellerAmount;
    /**
     * 结算给卖家的标题
     */
    private String     settleSellerTitle;
    /**
     * 结算给卖家的详情
     */
    private String     settleSellerDetail;

    /**
     * 结算卖家已占用保证金的金额
     */
    private BigDecimal settleDepositUsedAmount;
    /**
     * 结算卖家已占用保证金的标题
     */
    private String     settleDepositUsedTitle;
    /**
     * 结算卖家已占用保证金的详情
     */
    private String     settleDepositUsedDetail;

    /**
     * 订单详情ID
     * (销售订单详情ID)
     */
    private String     orderDetailId;

}
