package rebue.afc.vpay.ro;

import java.math.BigDecimal;
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
    private BigDecimal payChangeAmount1;
    /**
     * 支付余额的金额(单位为元)
     */
    private BigDecimal payChangeAmount2;
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

    public BigDecimal getPayChangeAmount1() {
        return payChangeAmount1;
    }

    public void setPayChangeAmount1(BigDecimal payChangeAmount1) {
        this.payChangeAmount1 = payChangeAmount1;
    }

    public BigDecimal getPayChangeAmount2() {
        return payChangeAmount2;
    }

    public void setPayChangeAmount2(BigDecimal payChangeAmount2) {
        this.payChangeAmount2 = payChangeAmount2;
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
        return "VpayNotifyRo [userId=" + userId + ", payAccountId=" + payAccountId + ", payAmount=" + payAmount + ", payChangeAmount1=" + payChangeAmount1 + ", payChangeAmount2="
                + payChangeAmount2 + ", payOrderId=" + payOrderId + ", orderId=" + orderId + ", payTime=" + payTime + "]";
    }

}
