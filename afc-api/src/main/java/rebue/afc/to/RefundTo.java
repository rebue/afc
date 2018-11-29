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
	 * 订单ID (实际是payOrderId)
	 */
	private String orderId;

	/**
	 * 是否自动计算退款(true:自动计算退款 false:自定义退款)
	 */
	private Boolean isAutoCalcRefund;

	/**
	 * 退款订单ID（退货单ID）
	 */
	private Long refundId;

	/**
	 * 买家的账户ID
	 */
	private Long buyerAccountId;
	/**
	 * 卖家的账户ID(组织ID)
	 */
	private Long sellerAccountId;
	/**
	 * 供应商的账户ID(组织ID)
	 */
	private Long supplierAccountId;
	/**
	 * 退款的标题
	 */
	private String tradeTitle;
	/**
	 * 退款的详情
	 */
	private String tradeDetail;
	/**
	 * 退款金额(自动计算退款) (单位为“元”，精确到小数点后2位)
	 */
	private BigDecimal refundAmount;
	/**
	 * 退回到买家余额的金额(自定义退款) (单位为“元”，精确到小数点后2位)
	 * 
	 * @deprecated
	 */
	@Deprecated
	private BigDecimal returnBalanceToBuyer;
	/**
	 * 退回到买家返现金的金额(自定义退款) (单位为“元”，精确到小数点后2位)
	 * 
	 * @deprecated
	 */
	@Deprecated
	private BigDecimal returnCashbackToBuyer;
	/**
	 * 返回到卖家的补偿金(退货退款产生的需补偿给卖家的金额，例如补偿运费)
	 */
	private BigDecimal returnCompensationToSeller;
	/**
	 * 操作人的用户ID
	 */
	private Long opId;
	/**
	 * 操作人的MAC地址
	 */
	private String mac;
	/**
	 * 操作人的IP地址
	 */
	private String ip;

}
