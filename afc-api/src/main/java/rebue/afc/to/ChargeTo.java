package rebue.afc.to;

import io.swagger.annotations.ApiModel;

/**
 * 账户-充值的传输对象（参数）
 */
@ApiModel(value = "账户-充值的传输对象（参数）")
public class ChargeTo {
    /**
     * 账户ID
     */
    private Long   userId;
    /**
     * 充值单号
     */
    private String orderId;
    /**
     * 充值交易的标题
     */
    private String tradeTitle;
    /**
     * 充值交易的详情
     */
    private String tradeDetail;
    /**
     * 充值交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 充值交易的交易凭证号
     */
    private String tradeVoucherNo;
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

    public String getTradeVoucherNo() {
        return tradeVoucherNo;
    }

    public void setTradeVoucherNo(String tradeVoucherNo) {
        this.tradeVoucherNo = tradeVoucherNo;
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
        return "ChargeTo [userId=" + userId + ", orderId=" + orderId + ", tradeTitle=" + tradeTitle + ", tradeDetail="
                + tradeDetail + ", tradeAmount=" + tradeAmount + ", tradeVoucherNo=" + tradeVoucherNo + ", opId=" + opId
                + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
