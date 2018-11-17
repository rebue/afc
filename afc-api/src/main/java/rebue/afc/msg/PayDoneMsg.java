package rebue.afc.msg;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import rebue.afc.dic.PayTypeDic;

/**
 * 支付完成的消息
 */
@JsonInclude(Include.NON_NULL)
@Data
public class PayDoneMsg {
    /**
     * 支付类型
     */
    private PayTypeDic payType;
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
     * 支付金额1，在交易类型是V支付时代表返现金支付了多少
     */
    private BigDecimal payAmount1;
    /**
     * 支付金额2，在交易类型是V支付时代表余额支付了多少
     */
    private BigDecimal payAmount2;
    /**
     * V支付、微信、支付宝等支付的交易ID
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
