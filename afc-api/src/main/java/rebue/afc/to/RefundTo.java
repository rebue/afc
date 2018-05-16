package rebue.afc.to;

import java.math.BigDecimal;

/**
 * 退款到买家的传输对象（参数）
 */
public class RefundTo {
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String     orderId;
    /**
     * 订单详情ID
     * (退款订单ID)
     */
    private String     orderDetailId;
    /**
     * 买家的账户ID
     */
    private Long       buyerAccountId;
    /**
     * 卖家的账户ID
     */
    private Long       sellerAccountId;
    /**
     * 供应商的账户ID
     */
    private Long       supplierAccountId;
    /**
     * 退款的标题
     */
    private String     tradeTitle;
    /**
     * 退款的详情
     */
    private String     tradeDetail;
    /**
     * 退回到买家余额的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal returnBalanceToBuyer;
    /**
     * 退回到买家返现金的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal returnCashbackToBuyer;
    /**
     * 收回结算给平台的服务费的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackServiceFeeFromPlatform;
    /**
     * 收回结算给供应商成本的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackCostFromSupplier;
    /**
     * 收回结算给卖家利润的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackProfitFromSeller;
    /**
     * 收回结算给卖家释放已占用保证金的金额
     * (单位为“元”，精确到小数点后4位)
     */
    private BigDecimal getbackDepositUsedFromSeller;
    /**
     * 操作人的用户ID
     */
    private Long       opId;
    /**
     * 操作人的MAC地址
     */
    private String     mac;
    /**
     * 操作人的IP地址
     */
    private String     ip;

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

    public Long getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(Long buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public Long getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(Long sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public Long getSupplierAccountId() {
        return supplierAccountId;
    }

    public void setSupplierAccountId(Long supplierAccountId) {
        this.supplierAccountId = supplierAccountId;
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

    public BigDecimal getReturnBalanceToBuyer() {
        return returnBalanceToBuyer;
    }

    public void setReturnBalanceToBuyer(BigDecimal returnBalanceToBuyer) {
        this.returnBalanceToBuyer = returnBalanceToBuyer;
    }

    public BigDecimal getReturnCashbackToBuyer() {
        return returnCashbackToBuyer;
    }

    public void setReturnCashbackToBuyer(BigDecimal returnCashbackToBuyer) {
        this.returnCashbackToBuyer = returnCashbackToBuyer;
    }

    public BigDecimal getGetbackServiceFeeFromPlatform() {
        return getbackServiceFeeFromPlatform;
    }

    public void setGetbackServiceFeeFromPlatform(BigDecimal getbackServiceFeeFromPlatform) {
        this.getbackServiceFeeFromPlatform = getbackServiceFeeFromPlatform;
    }

    public BigDecimal getGetbackCostFromSupplier() {
        return getbackCostFromSupplier;
    }

    public void setGetbackCostFromSupplier(BigDecimal getbackCostFromSupplier) {
        this.getbackCostFromSupplier = getbackCostFromSupplier;
    }

    public BigDecimal getGetbackProfitFromSeller() {
        return getbackProfitFromSeller;
    }

    public void setGetbackProfitFromSeller(BigDecimal getbackProfitFromSeller) {
        this.getbackProfitFromSeller = getbackProfitFromSeller;
    }

    public BigDecimal getGetbackDepositUsedFromSeller() {
        return getbackDepositUsedFromSeller;
    }

    public void setGetbackDepositUsedFromSeller(BigDecimal getbackDepositUsedFromSeller) {
        this.getbackDepositUsedFromSeller = getbackDepositUsedFromSeller;
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
        return "RefundTo [orderId=" + orderId + ", orderDetailId=" + orderDetailId + ", buyerAccountId=" + buyerAccountId + ", sellerAccountId=" + sellerAccountId
                + ", supplierAccountId=" + supplierAccountId + ", tradeTitle=" + tradeTitle + ", tradeDetail=" + tradeDetail + ", returnBalanceToBuyer=" + returnBalanceToBuyer
                + ", returnCashbackToBuyer=" + returnCashbackToBuyer + ", getbackServiceFeeFromPlatform=" + getbackServiceFeeFromPlatform + ", getbackCostFromSupplier="
                + getbackCostFromSupplier + ", getbackProfitFromSeller=" + getbackProfitFromSeller + ", getbackDepositUsedFromSeller=" + getbackDepositUsedFromSeller + ", opId="
                + opId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
