package rebue.afc.to;

import java.math.BigDecimal;

public class AddSettleTasksDetailTo {
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
     * 结算给买家返现金的金额
     */
    private BigDecimal settleBuyerCashbackAmount;
    /**
     * 结算给买家返现金的标题
     */
    private String     settleBuyerCashbackTitle;
    /**
     * 结算给买家返现金的详情
     */
    private String     settleBuyerCashbackDetail;

    /**
     * 结算给平台服务费的金额
     */
    private BigDecimal settlePlatformServiceFeeAmount;

    /**
     * 结算给供应商的金额（成本，打到余额）
     */
    private BigDecimal settleSupplierAmount;
    /**
     * 结算给供应商的标题
     */
    private String     settleSupplierTitle;
    /**
     * 结算给供应商的详情
     */
    private String     settleSupplierDetail;

    /**
     * 结算给卖家的金额（利润，打到余额）
     */
    private BigDecimal settleSellerAmount;
    /**
     * 结算给卖家的标题
     */
    private String     settleSellerTitle;
    /**
     * 结算给卖家的详情
     */
    private String     settleSellerDetail;

    /**
     * 结算卖家已占用保证金的金额
     */
    private BigDecimal settleDepositUsedAmount;
    /**
     * 结算卖家已占用保证金的标题
     */
    private String     settleDepositUsedTitle;
    /**
     * 结算卖家已占用保证金的详情
     */
    private String     settleDepositUsedDetail;

    /**
     * 订单详情ID
     * (销售订单详情ID)
     */
    private String     orderDetailId;

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

    public BigDecimal getSettleBuyerCashbackAmount() {
        return settleBuyerCashbackAmount;
    }

    public void setSettleBuyerCashbackAmount(BigDecimal settleBuyerCashbackAmount) {
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

    public BigDecimal getSettlePlatformServiceFeeAmount() {
        return settlePlatformServiceFeeAmount;
    }

    public void setSettlePlatformServiceFeeAmount(BigDecimal settlePlatformServiceFeeAmount) {
        this.settlePlatformServiceFeeAmount = settlePlatformServiceFeeAmount;
    }

    public BigDecimal getSettleSupplierAmount() {
        return settleSupplierAmount;
    }

    public void setSettleSupplierAmount(BigDecimal settleSupplierAmount) {
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

    public BigDecimal getSettleSellerAmount() {
        return settleSellerAmount;
    }

    public void setSettleSellerAmount(BigDecimal settleSellerAmount) {
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

    public BigDecimal getSettleDepositUsedAmount() {
        return settleDepositUsedAmount;
    }

    public void setSettleDepositUsedAmount(BigDecimal settleDepositUsedAmount) {
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

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @Override
    public String toString() {
        return "AddSettleTasksDetailTo [buyerAccountId=" + buyerAccountId + ", sellerAccountId=" + sellerAccountId + ", supplierAccountId=" + supplierAccountId
                + ", settleBuyerCashbackAmount=" + settleBuyerCashbackAmount + ", settleBuyerCashbackTitle=" + settleBuyerCashbackTitle + ", settleBuyerCashbackDetail="
                + settleBuyerCashbackDetail + ", settlePlatformServiceFeeAmount=" + settlePlatformServiceFeeAmount + ", settleSupplierAmount=" + settleSupplierAmount
                + ", settleSupplierTitle=" + settleSupplierTitle + ", settleSupplierDetail=" + settleSupplierDetail + ", settleSellerAmount=" + settleSellerAmount
                + ", settleSellerTitle=" + settleSellerTitle + ", settleSellerDetail=" + settleSellerDetail + ", settleDepositUsedAmount=" + settleDepositUsedAmount
                + ", settleDepositUsedTitle=" + settleDepositUsedTitle + ", settleDepositUsedDetail=" + settleDepositUsedDetail + ", orderDetailId=" + orderDetailId + "]";
    }

}
