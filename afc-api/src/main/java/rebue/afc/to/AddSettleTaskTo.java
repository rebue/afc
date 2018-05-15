package rebue.afc.to;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * V支付-添加交易任务的传输对象
 *
 */
public class AddSettleTaskTo {
    /**
     * 计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   executePlanTime;

    /**
     * 账户ID(账户ID也就是用户ID)
     */
    private Long   accountId;

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
     * 结算给供应商的金额（成本）
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
     * 结算给卖家的金额（利润）
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

    public Date getExecutePlanTime() {
        return executePlanTime;
    }

    public void setExecutePlanTime(Date executePlanTime) {
        this.executePlanTime = executePlanTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
        return "AddSettleTaskTo [executePlanTime=" + executePlanTime + ", accountId=" + accountId + ", settleBuyerCashbackAmount=" + settleBuyerCashbackAmount
                + ", settleBuyerCashbackTitle=" + settleBuyerCashbackTitle + ", settleBuyerCashbackDetail=" + settleBuyerCashbackDetail + ", settlePlatformServiceFeeAmount="
                + settlePlatformServiceFeeAmount + ", settlePlatformServiceFeeTitle=" + settlePlatformServiceFeeTitle + ", settlePlatformServiceFeeDetail="
                + settlePlatformServiceFeeDetail + ", settleSupplierAmount=" + settleSupplierAmount + ", settleSupplierTitle=" + settleSupplierTitle + ", settleSupplierDetail="
                + settleSupplierDetail + ", settleSellerAmount=" + settleSellerAmount + ", settleSellerTitle=" + settleSellerTitle + ", settleSellerDetail=" + settleSellerDetail
                + ", settleDepositUsedAmount=" + settleDepositUsedAmount + ", settleDepositUsedTitle=" + settleDepositUsedTitle + ", settleDepositUsedDetail="
                + settleDepositUsedDetail + ", orderId=" + orderId + ", orderDetailId=" + orderDetailId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}