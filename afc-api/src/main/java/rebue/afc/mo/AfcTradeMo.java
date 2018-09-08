package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 账户交易(账户交易流水)
 *
 * 数据库表: AFC_TRADE
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcTradeMo implements Serializable {

    /**
     *    交易ID(等于流水ID)
     *
     *    数据库字段: AFC_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_TRADE.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    交易类型
     *
     *    数据库字段: AFC_TRADE.TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte tradeType;

    /**
     *    交易总额（收入为正数，支出为负数）
     *
     *    数据库字段: AFC_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal tradeAmount;

    /**
     *    交易改变金额1，在交易类型是支付和退款时代表返现金改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT1
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal changeAmount1;

    /**
     *    交易改变金额2，在交易类型是支付和退款时代表余额改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT2
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal changeAmount2;

    /**
     *    交易标题
     *
     *    数据库字段: AFC_TRADE.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String tradeTitle;

    /**
     *    交易详情
     *
     *    数据库字段: AFC_TRADE.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String tradeDetail;

    /**
     *    交易时间
     *
     *    数据库字段: AFC_TRADE.TRADE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderId;

    /**
     *    订单详情ID(业务订单ID，结算是销售订单详情ID，退货是退货订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderDetailId;

    /**
     *    交易凭证号
     *
     *    数据库字段: AFC_TRADE.TRADE_VOUCHER_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String tradeVoucherNo;

    /**
     *    操作人ID
     *
     *    数据库字段: AFC_TRADE.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long opId;

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_TRADE.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String mac;

    /**
     *    IP地址
     *
     *    数据库字段: AFC_TRADE.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String ip;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    交易ID(等于流水ID)
     *
     *    数据库字段: AFC_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    交易ID(等于流水ID)
     *
     *    数据库字段: AFC_TRADE.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_TRADE.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_TRADE.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    交易类型
     *
     *    数据库字段: AFC_TRADE.TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getTradeType() {
        return tradeType;
    }

    /**
     *    交易类型
     *
     *    数据库字段: AFC_TRADE.TRADE_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    /**
     *    交易总额（收入为正数，支出为负数）
     *
     *    数据库字段: AFC_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    /**
     *    交易总额（收入为正数，支出为负数）
     *
     *    数据库字段: AFC_TRADE.TRADE_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     *    交易改变金额1，在交易类型是支付和退款时代表返现金改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT1
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getChangeAmount1() {
        return changeAmount1;
    }

    /**
     *    交易改变金额1，在交易类型是支付和退款时代表返现金改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT1
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setChangeAmount1(BigDecimal changeAmount1) {
        this.changeAmount1 = changeAmount1;
    }

    /**
     *    交易改变金额2，在交易类型是支付和退款时代表余额改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT2
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getChangeAmount2() {
        return changeAmount2;
    }

    /**
     *    交易改变金额2，在交易类型是支付和退款时代表余额改变了多少
     *
     *    数据库字段: AFC_TRADE.CHANGE_AMOUNT2
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setChangeAmount2(BigDecimal changeAmount2) {
        this.changeAmount2 = changeAmount2;
    }

    /**
     *    交易标题
     *
     *    数据库字段: AFC_TRADE.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getTradeTitle() {
        return tradeTitle;
    }

    /**
     *    交易标题
     *
     *    数据库字段: AFC_TRADE.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeTitle(String tradeTitle) {
        this.tradeTitle = tradeTitle;
    }

    /**
     *    交易详情
     *
     *    数据库字段: AFC_TRADE.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getTradeDetail() {
        return tradeDetail;
    }

    /**
     *    交易详情
     *
     *    数据库字段: AFC_TRADE.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeDetail(String tradeDetail) {
        this.tradeDetail = tradeDetail;
    }

    /**
     *    交易时间
     *
     *    数据库字段: AFC_TRADE.TRADE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     *    交易时间
     *
     *    数据库字段: AFC_TRADE.TRADE_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *    订单详情ID(业务订单ID，结算是销售订单详情ID，退货是退货订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderDetailId() {
        return orderDetailId;
    }

    /**
     *    订单详情ID(业务订单ID，结算是销售订单详情ID，退货是退货订单ID)
     *
     *    数据库字段: AFC_TRADE.ORDER_DETAIL_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     *    交易凭证号
     *
     *    数据库字段: AFC_TRADE.TRADE_VOUCHER_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getTradeVoucherNo() {
        return tradeVoucherNo;
    }

    /**
     *    交易凭证号
     *
     *    数据库字段: AFC_TRADE.TRADE_VOUCHER_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeVoucherNo(String tradeVoucherNo) {
        this.tradeVoucherNo = tradeVoucherNo;
    }

    /**
     *    操作人ID
     *
     *    数据库字段: AFC_TRADE.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getOpId() {
        return opId;
    }

    /**
     *    操作人ID
     *
     *    数据库字段: AFC_TRADE.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOpId(Long opId) {
        this.opId = opId;
    }

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_TRADE.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getMac() {
        return mac;
    }

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_TRADE.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     *    IP地址
     *
     *    数据库字段: AFC_TRADE.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getIp() {
        return ip;
    }

    /**
     *    IP地址
     *
     *    数据库字段: AFC_TRADE.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setIp(String ip) {
        this.ip = ip;
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
        sb.append(", accountId=").append(accountId);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", changeAmount1=").append(changeAmount1);
        sb.append(", changeAmount2=").append(changeAmount2);
        sb.append(", tradeTitle=").append(tradeTitle);
        sb.append(", tradeDetail=").append(tradeDetail);
        sb.append(", tradeTime=").append(tradeTime);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", tradeVoucherNo=").append(tradeVoucherNo);
        sb.append(", opId=").append(opId);
        sb.append(", mac=").append(mac);
        sb.append(", ip=").append(ip);
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
        AfcTradeMo other = (AfcTradeMo) that;
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
