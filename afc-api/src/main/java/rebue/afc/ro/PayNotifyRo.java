package rebue.afc.ro;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.dic.PayTypeDic;

/**
 * 支付完成通知的解析结果
 */
@ApiModel(value = "支付通知的解析结果", description = "支付通知的解析结果")
@JsonInclude(Include.NON_NULL)
public class PayNotifyRo {
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型", required = true)
    private PayTypeDic payType;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Long       userId;
    /**
     * 支付账户ID
     */
    @ApiModelProperty(value = "支付账户ID", required = true)
    private String     payAccountId;
    /**
     * 支付交易的金额(单位为元)
     */
    @ApiModelProperty(value = "支付交易的金额(单位为元)", required = true)
    private Double     payAmount;
    /**
     * 支付订单号
     */
    @ApiModelProperty(value = "支付订单号", required = true)
    private String     payOrderId;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号", required = true)
    private String     orderId;
    /**
     * 支付完成时间
     */
    @ApiModelProperty(value = "支付完成时间", required = true)
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
        return "PayNotifyRo [payType=" + payType + ", userId=" + userId + ", payAccountId=" + payAccountId
                + ", payAmount=" + payAmount + ", payOrderId=" + payOrderId + ", orderId=" + orderId + ", payTime="
                + payTime + "]";
    }

}
