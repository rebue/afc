package rebue.afc.to;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 添加结算任务的传输对象
 *
 */
@JsonInclude(Include.NON_NULL)
public class AddSettleTasksTo {
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String                       orderId;
    /**
     * 买家的账户ID
     */
    private Long                         buyerAccountId;
    /**
     * 卖家的账户ID
     */
    private Long                         sellerAccountId;

    /**
     * 结算给买家返现金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleBuyerCashbackTime;
    /**
     * 结算给平台服务费的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settlePlatformServiceFeeTime;
    /**
     * 结算给供应商的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleSupplierTime;
    /**
     * 结算给卖家的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleSellerTime;
    /**
     * 结算卖家已占用保证金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleDepositUsedTime;

    /**
     * MAC地址
     */
    private String                       mac;
    /**
     * IP地址
     */
    private String                       ip;

    private List<AddSettleTasksDetailTo> details;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Date getSettleBuyerCashbackTime() {
        return settleBuyerCashbackTime;
    }

    public void setSettleBuyerCashbackTime(Date settleBuyerCashbackTime) {
        this.settleBuyerCashbackTime = settleBuyerCashbackTime;
    }

    public Date getSettlePlatformServiceFeeTime() {
        return settlePlatformServiceFeeTime;
    }

    public void setSettlePlatformServiceFeeTime(Date settlePlatformServiceFeeTime) {
        this.settlePlatformServiceFeeTime = settlePlatformServiceFeeTime;
    }

    public Date getSettleSupplierTime() {
        return settleSupplierTime;
    }

    public void setSettleSupplierTime(Date settleSupplierTime) {
        this.settleSupplierTime = settleSupplierTime;
    }

    public Date getSettleSellerTime() {
        return settleSellerTime;
    }

    public void setSettleSellerTime(Date settleSellerTime) {
        this.settleSellerTime = settleSellerTime;
    }

    public Date getSettleDepositUsedTime() {
        return settleDepositUsedTime;
    }

    public void setSettleDepositUsedTime(Date settleDepositUsedTime) {
        this.settleDepositUsedTime = settleDepositUsedTime;
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

    public List<AddSettleTasksDetailTo> getDetails() {
        return details;
    }

    public void setDetails(List<AddSettleTasksDetailTo> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AddSettleTasksTo [orderId=" + orderId + ", buyerAccountId=" + buyerAccountId + ", sellerAccountId=" + sellerAccountId + ", settleBuyerCashbackTime="
                + settleBuyerCashbackTime + ", settlePlatformServiceFeeTime=" + settlePlatformServiceFeeTime + ", settleSupplierTime=" + settleSupplierTime + ", settleSellerTime="
                + settleSellerTime + ", settleDepositUsedTime=" + settleDepositUsedTime + ", mac=" + mac + ", ip=" + ip + ", details=" + details + "]";
    }

}