package rebue.afc.to;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 添加结算任务的传输对象
 *
 */
public class AddSettleTasksTo {
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String                       orderId;

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
        return "AddSettleTasksTo [orderId=" + orderId + ", settleBuyerCashbackTime=" + settleBuyerCashbackTime + ", settlePlatformServiceFeeTime=" + settlePlatformServiceFeeTime
                + ", settleSupplierTime=" + settleSupplierTime + ", settleSellerTime=" + settleSellerTime + ", settleDepositUsedTime=" + settleDepositUsedTime + ", mac=" + mac
                + ", ip=" + ip + ", details=" + details + "]";
    }

}