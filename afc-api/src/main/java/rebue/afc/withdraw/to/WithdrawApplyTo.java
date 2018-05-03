package rebue.afc.withdraw.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提现申请的传输对象（参数）
 */
@ApiModel(value = "提现申请的传输对象（参数）")
public class WithdrawApplyTo {
    /**
     * 账户的用户ID
     */
    @ApiModelProperty(value = "账户的用户ID")
    private Long   userId;
    /**
     * 提现账户ID
     */
    @ApiModelProperty(value = "提现账户ID")
    private Long   withdrawAccountId;
    /**
     * 申请提现的单号
     */
    private String orderId;
    /**
     * 申请提现的标题
     */
    private String tradeTitle;
    /**
     * 申请提现的详情
     */
    private String tradeDetail;
    /**
     * 申请提现的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 申请人的用户ID
     */
    private Long   opId;
    /**
     * 申请人的MAC地址
     */
    private String mac;
    /**
     * 申请人的IP地址
     */
    private String ip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWithdrawAccountId() {
        return withdrawAccountId;
    }

    public void setWithdrawAccountId(Long withdrawAccountId) {
        this.withdrawAccountId = withdrawAccountId;
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
        return "WithdrawApplyTo [userId=" + userId + ", withdrawAccountId=" + withdrawAccountId + ", orderId=" + orderId
                + ", tradeTitle=" + tradeTitle + ", tradeDetail=" + tradeDetail + ", tradeAmount=" + tradeAmount
                + ", opId=" + opId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
