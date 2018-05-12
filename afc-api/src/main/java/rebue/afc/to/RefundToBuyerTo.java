package rebue.afc.to;

/**
 * 退款到买家的传输对象（参数）
 */
public class RefundToBuyerTo {
    /**
     * 买家的账户ID
     */
    private Long   accountId;
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String orderId;
    /**
     * 订单详情ID
     * (退款订单ID)
     */
    private String orderDetailId;
    /**
     * 退款的标题
     */
    private String tradeTitle;
    /**
     * 退款的详情
     */
    private String tradeDetail;
    /**
     * 退款到余额的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private Double changeAmount1;
    /**
     * 退款到返现金的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private Double changeAmount2;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public Double getChangeAmount1() {
        return changeAmount1;
    }

    public void setChangeAmount1(Double changeAmount1) {
        this.changeAmount1 = changeAmount1;
    }

    public Double getChangeAmount2() {
        return changeAmount2;
    }

    public void setChangeAmount2(Double changeAmount2) {
        this.changeAmount2 = changeAmount2;
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
        return "RefundToBuyerTo [accountId=" + accountId + ", orderId=" + orderId + ", orderDetailId=" + orderDetailId + ", tradeTitle=" + tradeTitle + ", tradeDetail="
                + tradeDetail + ", changeAmount1=" + changeAmount1 + ", changeAmount2=" + changeAmount2 + ", opId=" + opId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
