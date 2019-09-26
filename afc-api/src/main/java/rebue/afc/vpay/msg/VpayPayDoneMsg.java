package rebue.afc.vpay.msg;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * V支付的支付完成通知的消息体
 */
@JsonInclude(Include.NON_NULL)
@Data
public class VpayPayDoneMsg {
    /**
     * 用户ID
     */
    private Long       userId;
    /**
     * 支付账户ID
     */
    private String     payAccountId;
    /**
     * 支付交易的金额(单位为元)
     */
    private BigDecimal payAmount;
    /**
     * 支付返现金的金额(单位为元)
     */
    private BigDecimal payAmount1;
    /**
     * 支付余额的金额(单位为元)
     */
    private BigDecimal payAmount2;
    /**
     * 支付交易ID
     */
    private String     tradeId;
    /**
     * 订单号
     */
    private String     orderId;
    /**
     * 支付完成时间
     */
    private Date       payTime;

}
