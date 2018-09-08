package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 支付信息
 *
 * 数据库表: AFC_PAY
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcPayMo implements Serializable {

    /**
     *    支付ID
     *
     *    数据库字段: AFC_PAY.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_PAY.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PAY.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderId;

    /**
     *    支付类型ID(1.V支付;2.微信支付;3.支付宝;4.银联)
     *
     *    数据库字段: AFC_PAY.PAY_TYPE_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte payTypeId;

    /**
     *    支付账户ID(例如微信ID，支付宝ID，V支付的账户ID也就是本系统的用户ID)
     *
     *    数据库字段: AFC_PAY.PAY_ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String payAccountId;

    /**
     *    支付订单ID(V支付订单ID就是交易ID或流水ID)
     *
     *    数据库字段: AFC_PAY.PAY_ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String payOrderId;

    /**
     *    支付金额
     *
     *    数据库字段: AFC_PAY.PAY_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal payAmount;

    /**
     *    支付时间
     *
     *    数据库字段: AFC_PAY.PAY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    支付ID
     *
     *    数据库字段: AFC_PAY.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    支付ID
     *
     *    数据库字段: AFC_PAY.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_PAY.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    账户ID(账户ID也就是用户ID)
     *
     *    数据库字段: AFC_PAY.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PAY.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *    订单ID(销售订单ID)
     *
     *    数据库字段: AFC_PAY.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *    支付类型ID(1.V支付;2.微信支付;3.支付宝;4.银联)
     *
     *    数据库字段: AFC_PAY.PAY_TYPE_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getPayTypeId() {
        return payTypeId;
    }

    /**
     *    支付类型ID(1.V支付;2.微信支付;3.支付宝;4.银联)
     *
     *    数据库字段: AFC_PAY.PAY_TYPE_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPayTypeId(Byte payTypeId) {
        this.payTypeId = payTypeId;
    }

    /**
     *    支付账户ID(例如微信ID，支付宝ID，V支付的账户ID也就是本系统的用户ID)
     *
     *    数据库字段: AFC_PAY.PAY_ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getPayAccountId() {
        return payAccountId;
    }

    /**
     *    支付账户ID(例如微信ID，支付宝ID，V支付的账户ID也就是本系统的用户ID)
     *
     *    数据库字段: AFC_PAY.PAY_ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPayAccountId(String payAccountId) {
        this.payAccountId = payAccountId;
    }

    /**
     *    支付订单ID(V支付订单ID就是交易ID或流水ID)
     *
     *    数据库字段: AFC_PAY.PAY_ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getPayOrderId() {
        return payOrderId;
    }

    /**
     *    支付订单ID(V支付订单ID就是交易ID或流水ID)
     *
     *    数据库字段: AFC_PAY.PAY_ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    /**
     *    支付金额
     *
     *    数据库字段: AFC_PAY.PAY_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     *    支付金额
     *
     *    数据库字段: AFC_PAY.PAY_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     *    支付时间
     *
     *    数据库字段: AFC_PAY.PAY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     *    支付时间
     *
     *    数据库字段: AFC_PAY.PAY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
        sb.append(", orderId=").append(orderId);
        sb.append(", payTypeId=").append(payTypeId);
        sb.append(", payAccountId=").append(payAccountId);
        sb.append(", payOrderId=").append(payOrderId);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", payTime=").append(payTime);
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
        AfcPayMo other = (AfcPayMo) that;
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
