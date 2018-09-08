package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台交易
 *
 * 数据库表: AFC_PLATFORM_TRADE
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcPlatformTradeMo implements Serializable {

    /**
     *    平台交易ID
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    交易类型（1：收取服务费(购买交易成功)  2：退回服务费(用户退款)）
     *
     *    数据库字段: AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte platformTradeType;

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderId;

    /**
     *    订单详情ID
     *                (如果平台交易类型是收取服务费，则填销售订单详情ID；如果是退款，则填写退款单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderDetailId;

    /**
     *    交易前的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal oldBalance;

    /**
     *    交易金额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal tradeAmount;

    /**
     *    交易后的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal newBalance;

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long modifiedTimestamp;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    平台交易ID
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    平台交易ID
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    交易类型（1：收取服务费(购买交易成功)  2：退回服务费(用户退款)）
     *
     *    数据库字段: AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getPlatformTradeType() {
        return platformTradeType;
    }

    /**
     *    交易类型（1：收取服务费(购买交易成功)  2：退回服务费(用户退款)）
     *
     *    数据库字段: AFC_PLATFORM_TRADE.PLATFORM_TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPlatformTradeType(Byte platformTradeType) {
        this.platformTradeType = platformTradeType;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *    订单详情ID
     *                (如果平台交易类型是收取服务费，则填销售订单详情ID；如果是退款，则填写退款单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderDetailId() {
        return orderDetailId;
    }

    /**
     *    订单详情ID
     *                (如果平台交易类型是收取服务费，则填销售订单详情ID；如果是退款，则填写退款单ID)
     *
     *    数据库字段: AFC_PLATFORM_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     *    交易前的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    /**
     *    交易前的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.OLD_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    /**
     *    交易金额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    /**
     *    交易金额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     *    交易后的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getNewBalance() {
        return newBalance;
    }

    /**
     *    交易后的余额
     *
     *    数据库字段: AFC_PLATFORM_TRADE.NEW_BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM_TRADE.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setModifiedTimestamp(Long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
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
     *    @mbg.generated 自动生成，如需修改，请删除本行
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
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}
