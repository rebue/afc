package rebue.afc.ro;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付订单的查询结果
 */
@ApiModel(value = "查询支付订单的结果", description = "查询支付订单的返回结果")
@JsonInclude(Include.NON_NULL)
public class PayOrderQueryRo {
    /**
     * 支付交易的金额(单位为“元”，精确到小数点后4位)
     */
    @ApiModelProperty(value = "支付交易的金额(单位为元)", required = true)
    private Double tradeAmount;
    /**
     * 支付订单号
     */
    @ApiModelProperty(value = "支付订单号", required = true)
    private String payOrderId;
    /**
     * 支付完成时间
     */
    @ApiModelProperty(value = "支付完成时间", required = true)
    private Date   payTime;

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "PayOrderQueryRo [tradeAmount=" + tradeAmount + ", payOrderId=" + payOrderId + ", payTime=" + payTime
                + "]";
    }

}
