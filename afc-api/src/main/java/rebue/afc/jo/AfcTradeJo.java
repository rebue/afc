package rebue.afc.jo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the AFC_TRADE database table.
 * @mbg.generated 自动生成，如需修改，请删除本行
 */
@Entity
@Table(name = "AFC_TRADE")
@Getter
@Setter
@ToString
public class AfcTradeJo implements Serializable {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *  交易ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 19)
    private Long id;

    /**
     *  账户ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ACCOUNT_ID", nullable = false, length = 19)
    private Long accountId;

    /**
     *  交易类型
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "TRADE_TYPE", nullable = false, length = 3)
    private Byte tradeType;

    /**
     *  交易总额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "TRADE_AMOUNT", nullable = false, precision = 18, scale = 4)
    private BigDecimal tradeAmount;

    /**
     *  交易改变金额1，在交易类型是支付和退款时代表返现金改变了多少
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "CHANGE_AMOUNT1", nullable = true, precision = 18, scale = 4)
    private BigDecimal changeAmount1;

    /**
     *  交易改变金额2，在交易类型是支付和退款时代表余额改变了多少
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "CHANGE_AMOUNT2", nullable = true, precision = 18, scale = 4)
    private BigDecimal changeAmount2;

    /**
     *  交易标题
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "TRADE_TITLE", nullable = false, length = 50)
    private String tradeTitle;

    /**
     *  交易详情
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "TRADE_DETAIL", nullable = true, length = 150)
    private String tradeDetail;

    /**
     *  交易时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "TRADE_TIME", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tradeTime;

    /**
     *  订单ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ORDER_ID", nullable = false, length = 150)
    private String orderId;

    /**
     *  订单详情ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "ORDER_DETAIL_ID", nullable = true, length = 150)
    private String orderDetailId;

    /**
     *  交易凭证号
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "TRADE_VOUCHER_NO", nullable = true, length = 150)
    private String tradeVoucherNo;

    /**
     *  操作人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "OP_ID", nullable = false, length = 19)
    private Long opId;

    /**
     *  MAC地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "MAC", nullable = false, length = 30)
    private String mac;

    /**
     *  IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "IP", nullable = false, length = 150)
    private String ip;

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AfcTradeJo other = (AfcTradeJo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
