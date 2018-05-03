package rebue.afc.vpay.to;

import io.swagger.annotations.ApiModel;

/**
 * 财务中心的V支付-预支付的传输对象
 */
@ApiModel(value = "财务中心的V支付-预支付", description = "财务中心的V支付-预支付的参数")
public class AfcVpayPrepayTo {
    /**
     * 用户ID
     */
    private Long   userId;
    /**
     * 销售订单ID
     */
    private String orderId;
    /**
     * 支付交易的标题
     */
    private String tradeTitle;
    /**
     * 支付交易的详情
     */
    private String tradeDetail;
    /**
     * 支付交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 用户的MAC地址
     */
    private String mac;
    /**
     * 用户的IP地址
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
        return "AfcVpayPrepayTo [userId=" + userId + ", orderId=" + orderId + ", tradeTitle=" + tradeTitle
                + ", tradeDetail=" + tradeDetail + ", tradeAmount=" + tradeAmount + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
