package rebue.afc.returngoods.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 买家退货的传输对象（参数）
 */
@ApiModel(value = "买家退货的传输对象（参数）")
public class ReturnGoodsByBuyerTo {
    /**
     * 买家的用户ID
     */
    @ApiModelProperty(value = "买家的用户ID")
    private Long   userId;
    /**
     * 退货单ID
     */
    @ApiModelProperty(value = "退货单ID")
    private String returnGoodsOrderId;
    /**
     * 销售单ID
     */
    @ApiModelProperty(value = "销售单ID")
    private String saleOrderId;
    /**
     * 退货的标题
     */
    @ApiModelProperty(value = "退货的标题")
    private String tradeTitle;
    /**
     * 退货的详情
     */
    @ApiModelProperty(value = "退货的详情")
    private String tradeDetail;
    /**
     * 退货到余额的金额(单位为“元”，精确到小数点后4位)
     */
    @ApiModelProperty(value = "退货到余额的金额(单位为“元”，精确到小数点后4位)")
    private Double balanceAmount;
    /**
     * 退货到返现金的金额(单位为“元”，精确到小数点后4位)
     */
    @ApiModelProperty(value = "退货到返现金的金额(单位为“元”，精确到小数点后4位)")
    private Double cashbackAmount;
    /**
     * 操作人的用户ID
     */
    @ApiModelProperty(value = "操作人的用户ID")
    private Long   opId;
    /**
     * 操作人的MAC地址
     */
    @ApiModelProperty(value = "操作人的MAC地址")
    private String mac;
    /**
     * 操作人的IP地址
     */
    @ApiModelProperty(value = "操作人的IP地址")
    private String ip;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReturnGoodsOrderId() {
        return returnGoodsOrderId;
    }

    public void setReturnGoodsOrderId(String returnGoodsOrderId) {
        this.returnGoodsOrderId = returnGoodsOrderId;
    }

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
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

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(Double cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
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
        return "ReturnGoodsByBuyerTo [userId=" + userId + ", returnGoodsOrderId=" + returnGoodsOrderId
                + ", saleOrderId=" + saleOrderId + ", tradeTitle=" + tradeTitle + ", tradeDetail=" + tradeDetail
                + ", balanceAmount=" + balanceAmount + ", cashbackAmount=" + cashbackAmount + ", opId=" + opId
                + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
