package rebue.afc.to;

import io.swagger.annotations.ApiModel;

/**
 * 返款的传输对象（参数）
 */
@ApiModel(value = "返款的传输对象（参数）")
public class RebateTo {
    /**
     * 用户ID
     */
    private Long   userId;
    /**
     * 销售订单ID
     */
    private String orderId;
    /**
     * 返款交易的标题
     */
    private String tradeTitle;
    /**
     * 返款交易的详情
     */
    private String tradeDetail;
    /**
     * 返款交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 发起服务器的MAC地址
     */
    private String mac;
    /**
     * 发起服务器的IP地址
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
        return "RebateTo [userId=" + userId + ", orderId=" + orderId + ", tradeTitle=" + tradeTitle + ", tradeDetail="
                + tradeDetail + ", tradeAmount=" + tradeAmount + ", mac=" + mac + ", ip=" + ip + "]";
    }
}
