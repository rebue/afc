package rebue.afc.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * 进货保证金-出货的传输对象(参数)
 */
@ApiModel(value = "进货保证金-出货的传输对象(参数)")
@JsonInclude(Include.NON_NULL)
public class DepositGoodsReturnTo {
    /**
     * 出货加盟商的用户ID
     */
    private Long   userId;
    /**
     * 调货单号
     */
    private String orderId;
    /**
     * 调货交易的标题
     */
    private String tradeTitle;
    /**
     * 调货交易的详情
     */
    private String tradeDetail;
    /**
     * 调货交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 操作人的用户ID
     */
    private Long   opId;
    /**
     * 操作人的MAC地址
     */
    private String mac;
    /**
     * 操作人的IP地址
     */
    private String ip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeTitle() {
        return tradeTitle;
    }

    public void setTradeTitle(String tradeTitle) {
        this.tradeTitle = tradeTitle;
    }

    public String getTradeDetail() {
        return tradeDetail;
    }

    public void setTradeDetail(String tradeDetail) {
        this.tradeDetail = tradeDetail;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "DepositGoodsInTo [userId=" + userId + ", orderId=" + orderId + ", tradeTitle=" + tradeTitle
                + ", tradeDetail=" + tradeDetail + ", tradeAmount=" + tradeAmount + ", opId=" + opId + ", mac=" + mac
                + ", ip=" + ip + "]";
    }

}
