package rebue.afc.msg;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import rebue.afc.dic.PayTypeDic;

/**
 * 支付完成的消息
 */
@JsonInclude(Include.NON_NULL)
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
     * 支付订单号
     */
    private String     payOrderId;
    /**
     * 订单号
     */
    private String     orderId;
    /**
     * 支付完成时间
     */
    private Date       payTime;

    public PayTypeDic getPayType() {
        return payType;
    }

    public void setPayType(PayTypeDic payType) {
        this.payType = payType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(String payAccountId) {
        this.payAccountId = payAccountId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "PayNotifyRo [payType=" + payType + ", userId=" + userId + ", payAccountId=" + payAccountId + ", payAmount=" + payAmount + ", payOrderId=" + payOrderId
                + ", orderId=" + orderId + ", payTime=" + payTime + "]";
    }

}
