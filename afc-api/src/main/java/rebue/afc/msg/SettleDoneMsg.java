package rebue.afc.msg;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 结算完成通知的解析结果
 */
@JsonInclude(Include.NON_NULL)
public class SettleDoneMsg {
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String orderId;
    /**
     * 结算完成时间
     */
    private Date   settleTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    @Override
    public String toString() {
        return "SettleNotifyRo [orderId=" + orderId + ", settleTime=" + settleTime + "]";
    }

}
