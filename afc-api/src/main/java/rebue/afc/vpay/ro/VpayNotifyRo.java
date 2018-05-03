package rebue.afc.vpay.ro;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * V支付的支付完成通知的解析结果
 */
@JsonInclude(Include.NON_NULL)
public class VpayNotifyRo {
    /**
     * 用户ID
     */
    private Long   userId;
    /**
     * 支付账户ID
     */
    private String payAccountId;
    /**
     * 支付交易的金额(单位为元)
     */
    private Double payAmount;
    /**
     * 支付订单号
     */
    private String payOrderId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 支付完成时间
     */
    private Date   payTime;

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

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
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
        return "VpayNotifyRo [userId=" + userId + ", payAccountId=" + payAccountId + ", payAmount=" + payAmount + ", payOrderId=" + payOrderId + ", orderId=" + orderId
                + ", payTime=" + payTime + "]";
    }

}
