package rebue.afc.vpay.to;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * V支付-添加交易任务的传输对象
 *
 */
public class AddRebateTaskTo {
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
     * 交易类型
     * (只支持返款到供应商的余额、返款到买家的返现金、返款到加盟商的已占用保证金、返款到加盟商的余额、返款到平台的服务费5种类型)
     */
    private Byte   tradeType;

    /**
     * 交易总额
     */
    private Double tradeAmount;

    /**
     * 交易标题
     */
    private String tradeTitle;

    /**
     * 交易详情
     */
    private String tradeDetail;

    /**
     * 订单ID
     * (销售订单详情ID)
     */
    private String orderId;

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

    public Byte getTradeType() {
        return tradeType;
    }

    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        return "AddRebateTaskTo [executePlanTime=" + executePlanTime + ", accountId=" + accountId + ", tradeType=" + tradeType + ", tradeAmount=" + tradeAmount + ", tradeTitle="
                + tradeTitle + ", tradeDetail=" + tradeDetail + ", orderId=" + orderId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}