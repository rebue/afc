package rebue.afc.to;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * V支付-添加交易任务的传输对象
 *
 */
public class AddSettleTaskTo {
    /**
     * 买家的账户ID
     */
    private Long   buyerAccountId;
    /**
     * 卖家的账户ID
     */
    private Long   sellerAccountId;
    /**
     * 供应商的账户ID
     */
    private Long   supplierAccountId;

    /**
     * 结算给买家返现金的金额
     */
    private Double settleBuyerCashbackAmount;
    /**
     * 结算给买家返现金的标题
     */
    private String settleBuyerCashbackTitle;
    /**
     * 结算给买家返现金的详情
     */
    private String settleBuyerCashbackDetail;
    /**
     * 结算给买家返现金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   settleBuyerCashbackTime;

    /**
     * 结算给平台服务费的金额
     */
    private Double settlePlatformServiceFeeAmount;
    /**
     * 结算给平台服务费的标题
     */
    private String settlePlatformServiceFeeTitle;
    /**
     * 结算给平台服务费的详情
     */
    private String settlePlatformServiceFeeDetail;
    /**
     * 结算给平台服务费的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   settlePlatformServiceFeeTime;

    /**
     * 结算给供应商的金额（成本，打到余额）
     */
    private Double settleSupplierAmount;
    /**
     * 结算给供应商的标题
     */
    private String settleSupplierTitle;
    /**
     * 结算给供应商的详情
     */
    private String settleSupplierDetail;
    /**
     * 结算给供应商的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   settleSupplierTime;

    /**
     * 结算给卖家的金额（利润，打到余额）
     */
    private Double settleSellerAmount;
    /**
     * 结算给卖家的标题
     */
    private String settleSellerTitle;
    /**
     * 结算给卖家的详情
     */
    private String settleSellerDetail;
    /**
     * 结算给卖家的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   settleSellerTime;

    /**
     * 结算卖家已占用保证金的金额
     */
    private Double settleDepositUsedAmount;
    /**
     * 结算卖家已占用保证金的标题
     */
    private String settleDepositUsedTitle;
    /**
     * 结算卖家已占用保证金的详情
     */
    private String settleDepositUsedDetail;
    /**
     * 结算卖家已占用保证金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   settleDepositUsedTime;

    /**
     * 订单ID
     * (销售订单ID)
     */
    private String orderId;
    /**
     * 订单详情ID
     * (销售订单详情ID)
     */
    private String orderDetailId;

    /**
     * MAC地址
     */
    private String mac;
    /**
     * IP地址
     */
    private String ip;

    public Long getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(Long buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public Long getSupplierAccountId() {
        return supplierAccountId;
    }

    public void setSupplierAccountId(Long supplierAccountId) {
        this.supplierAccountId = supplierAccountId;
    }

    public Long getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(Long sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public Double getSettleBuyerCashbackAmount() {
        return settleBuyerCashbackAmount;
    }

    public void setSettleBuyerCashbackAmount(Double settleBuyerCashbackAmount) {
        this.settleBuyerCashbackAmount = settleBuyerCashbackAmount;
    }

    public String getSettleBuyerCashbackTitle() {
        return settleBuyerCashbackTitle;
    }

    public void setSettleBuyerCashbackTitle(String settleBuyerCashbackTitle) {
        this.settleBuyerCashbackTitle = settleBuyerCashbackTitle;
    }

    public String getSettleBuyerCashbackDetail() {
        return settleBuyerCashbackDetail;
    }

    public void setSettleBuyerCashbackDetail(String settleBuyerCashbackDetail) {
        this.settleBuyerCashbackDetail = settleBuyerCashbackDetail;
    }

    public Date getSettleBuyerCashbackTime() {
        return settleBuyerCashbackTime;
    }

    public void setSettleBuyerCashbackTime(Date settleBuyerCashbackTime) {
        this.settleBuyerCashbackTime = settleBuyerCashbackTime;
    }

    public Double getSettlePlatformServiceFeeAmount() {
        return settlePlatformServiceFeeAmount;
    }

    public void setSettlePlatformServiceFeeAmount(Double settlePlatformServiceFeeAmount) {
        this.settlePlatformServiceFeeAmount = settlePlatformServiceFeeAmount;
    }

    public String getSettlePlatformServiceFeeTitle() {
        return settlePlatformServiceFeeTitle;
    }

    public void setSettlePlatformServiceFeeTitle(String settlePlatformServiceFeeTitle) {
        this.settlePlatformServiceFeeTitle = settlePlatformServiceFeeTitle;
    }

    public String getSettlePlatformServiceFeeDetail() {
        return settlePlatformServiceFeeDetail;
    }

    public void setSettlePlatformServiceFeeDetail(String settlePlatformServiceFeeDetail) {
        this.settlePlatformServiceFeeDetail = settlePlatformServiceFeeDetail;
    }

    public Date getSettlePlatformServiceFeeTime() {
        return settlePlatformServiceFeeTime;
    }

    public void setSettlePlatformServiceFeeTime(Date settlePlatformServiceFeeTime) {
        this.settlePlatformServiceFeeTime = settlePlatformServiceFeeTime;
    }

    public Double getSettleSupplierAmount() {
        return settleSupplierAmount;
    }

    public void setSettleSupplierAmount(Double settleSupplierAmount) {
        this.settleSupplierAmount = settleSupplierAmount;
    }

    public String getSettleSupplierTitle() {
        return settleSupplierTitle;
    }

    public void setSettleSupplierTitle(String settleSupplierTitle) {
        this.settleSupplierTitle = settleSupplierTitle;
    }

    public String getSettleSupplierDetail() {
        return settleSupplierDetail;
    }

    public void setSettleSupplierDetail(String settleSupplierDetail) {
        this.settleSupplierDetail = settleSupplierDetail;
    }

    public Date getSettleSupplierTime() {
        return settleSupplierTime;
    }

    public void setSettleSupplierTime(Date settleSupplierTime) {
        this.settleSupplierTime = settleSupplierTime;
    }

    public Double getSettleSellerAmount() {
        return settleSellerAmount;
    }

    public void setSettleSellerAmount(Double settleSellerAmount) {
        this.settleSellerAmount = settleSellerAmount;
    }

    public String getSettleSellerTitle() {
        return settleSellerTitle;
    }

    public void setSettleSellerTitle(String settleSellerTitle) {
        this.settleSellerTitle = settleSellerTitle;
    }

    public String getSettleSellerDetail() {
        return settleSellerDetail;
    }

    public void setSettleSellerDetail(String settleSellerDetail) {
        this.settleSellerDetail = settleSellerDetail;
    }

    public Date getSettleSellerTime() {
        return settleSellerTime;
    }

    public void setSettleSellerTime(Date settleSellerTime) {
        this.settleSellerTime = settleSellerTime;
    }

    public Double getSettleDepositUsedAmount() {
        return settleDepositUsedAmount;
    }

    public void setSettleDepositUsedAmount(Double settleDepositUsedAmount) {
        this.settleDepositUsedAmount = settleDepositUsedAmount;
    }

    public String getSettleDepositUsedTitle() {
        return settleDepositUsedTitle;
    }

    public void setSettleDepositUsedTitle(String settleDepositUsedTitle) {
        this.settleDepositUsedTitle = settleDepositUsedTitle;
    }

    public String getSettleDepositUsedDetail() {
        return settleDepositUsedDetail;
    }

    public void setSettleDepositUsedDetail(String settleDepositUsedDetail) {
        this.settleDepositUsedDetail = settleDepositUsedDetail;
    }

    public Date getSettleDepositUsedTime() {
        return settleDepositUsedTime;
    }

    public void setSettleDepositUsedTime(Date settleDepositUsedTime) {
        this.settleDepositUsedTime = settleDepositUsedTime;
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
        return "AddSettleTaskTo [buyerAccountId=" + buyerAccountId + ", supplierAccountId=" + supplierAccountId + ", sellerAccountId=" + sellerAccountId
                + ", settleBuyerCashbackAmount=" + settleBuyerCashbackAmount + ", settleBuyerCashbackTitle=" + settleBuyerCashbackTitle + ", settleBuyerCashbackDetail="
                + settleBuyerCashbackDetail + ", settleBuyerCashbackTime=" + settleBuyerCashbackTime + ", settlePlatformServiceFeeAmount=" + settlePlatformServiceFeeAmount
                + ", settlePlatformServiceFeeTitle=" + settlePlatformServiceFeeTitle + ", settlePlatformServiceFeeDetail=" + settlePlatformServiceFeeDetail
                + ", settlePlatformServiceFeeTime=" + settlePlatformServiceFeeTime + ", settleSupplierAmount=" + settleSupplierAmount + ", settleSupplierTitle="
                + settleSupplierTitle + ", settleSupplierDetail=" + settleSupplierDetail + ", settleSupplierTime=" + settleSupplierTime + ", settleSellerAmount="
                + settleSellerAmount + ", settleSellerTitle=" + settleSellerTitle + ", settleSellerDetail=" + settleSellerDetail + ", settleSellerTime=" + settleSellerTime
                + ", settleDepositUsedAmount=" + settleDepositUsedAmount + ", settleDepositUsedTitle=" + settleDepositUsedTitle + ", settleDepositUsedDetail="
                + settleDepositUsedDetail + ", settleDepositUsedTime=" + settleDepositUsedTime + ", orderId=" + orderId + ", orderDetailId=" + orderDetailId + ", mac=" + mac
                + ", ip=" + ip + "]";
    }

}