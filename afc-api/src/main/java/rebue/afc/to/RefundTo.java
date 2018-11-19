package rebue.afc.to;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 退款到买家的传输对象（参数）
 */
@JsonInclude(Include.NON_NULL)
@Data
public class RefundTo {
    /**
     * 订单ID
     * (实际是payOrderId)
     */
    private String     orderId;
    /**
     * 订单详情ID
     * (退款订单ID)
     */
    private String     orderDetailId;
    /**
     * 买家的账户ID
     */
    private Long       buyerAccountId;
    /**
     * 卖家的账户ID
     */
    private Long       sellerAccountId;
    /**
     * 供应商的账户ID
     */
    private Long       supplierAccountId;
    /**
     * 退款的标题
     */
    private String     tradeTitle;
    /**
     * 退款的详情
     */
    private String     tradeDetail;
    /**
     * 退款金额(自动计算退款)
     * (单位为“元”，精确到小数点后2位)
     */
    private BigDecimal refundAmount;
    /**
     * 退回到买家余额的金额(自定义退款)
     * (单位为“元”，精确到小数点后2位)
     * 
     * @deprecated
     */
    @Deprecated
    private BigDecimal returnBalanceToBuyer;
    /**
     * 退回到买家返现金的金额(自定义退款)
     * (单位为“元”，精确到小数点后2位)
     * 
     * @deprecated
     */
    @Deprecated
    private BigDecimal returnCashbackToBuyer;
    /**
     * 收回结算给平台的服务费的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackServiceFeeFromPlatform;
    /**
     * 收回结算给供应商成本的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackCostFromSupplier;
    /**
     * 收回结算给卖家利润的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackProfitFromSeller;
    /**
     * 收回结算给卖家释放已占用保证金的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackDepositUsedFromSeller;
    /**
     * 操作人的用户ID
     */
    private Long       opId;
    /**
     * 操作人的MAC地址
     */
    private String     mac;
    /**
     * 操作人的IP地址
     */
    private String     ip;

}
