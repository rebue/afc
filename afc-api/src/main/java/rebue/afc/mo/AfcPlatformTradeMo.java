package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Database Table Remarks:
 *   平台交易
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table AFC_PLATFORM_TRADE
 *
 * @mbg.generated do_not_delete_during_merge 2018-05-26 19:47:00
 */
@ApiModel(value = "AfcPlatformTradeMo", description = "平台交易")
@JsonInclude(Include.NON_NULL)
public class AfcPlatformTradeMo implements Serializable {
    /**
     * Database Column Remarks:
     *   平台交易ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "平台交易ID")
    private Long id;

    /**
     * Database Column Remarks:
     *   交易类型（1：收取服务费(购买交易成功)  2：退回服务费(用户退款)）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "交易类型（1：收取服务费(购买交易成功)  2：退回服务费(用户退款)）")
    private Byte platformTradeType;

    /**
     * Database Column Remarks:
     *   订单ID(销售订单ID)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.ORDER_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "订单ID(销售订单ID)")
    private String orderId;

    /**
     * Database Column Remarks:
     *   订单详情ID
     *               (如果平台交易类型是收取服务费，则填销售订单详情ID；如果是退款，则填写退款单ID)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "订单详情ID\n"
             +"            (如果平台交易类型是收取服务费，则填销售订单详情ID；如果是退款，则填写退款单ID)")
    private String orderDetailId;

    /**
     * Database Column Remarks:
     *   交易前的余额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "交易前的余额")
    private BigDecimal oldBalance;

    /**
     * Database Column Remarks:
     *   交易金额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradeAmount;

    /**
     * Database Column Remarks:
     *   交易后的余额
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "交易后的余额")
    private BigDecimal newBalance;

    /**
     * Database Column Remarks:
     *   修改时间戳
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @ApiModelProperty(value = "修改时间戳")
    private Long modifiedTimestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.ID
     *
     * @return the value of AFC_PLATFORM_TRADE.ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.ID
     *
     * @param id the value for AFC_PLATFORM_TRADE.ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     * @return the value of AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public Byte getPlatformTradeType() {
        return platformTradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     * @param platformTradeType the value for AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setPlatformTradeType(Byte platformTradeType) {
        this.platformTradeType = platformTradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.ORDER_ID
     *
     * @return the value of AFC_PLATFORM_TRADE.ORDER_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.ORDER_ID
     *
     * @param orderId the value for AFC_PLATFORM_TRADE.ORDER_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     * @return the value of AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public String getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     * @param orderDetailId the value for AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     * @return the value of AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     * @param oldBalance the value for AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     * @return the value of AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     * @param tradeAmount the value for AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     * @return the value of AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public BigDecimal getNewBalance() {
        return newBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     * @param newBalance the value for AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     * @return the value of AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     * @param modifiedTimestamp the value for AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    public void setModifiedTimestamp(Long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platformTradeType=").append(platformTradeType);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", oldBalance=").append(oldBalance);
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", newBalance=").append(newBalance);
        sb.append(", modifiedTimestamp=").append(modifiedTimestamp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AfcPlatformTradeMo other = (AfcPlatformTradeMo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
        ;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-26 19:47:00
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}