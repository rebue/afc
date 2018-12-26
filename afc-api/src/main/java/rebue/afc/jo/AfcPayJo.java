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
 * The persistent class for the AFC_PAY database table.
 * @mbg.generated 自动生成，如需修改，请删除本行
 */
@Entity
@Table(name = "AFC_PAY")
@Getter
@Setter
@ToString
public class AfcPayJo implements Serializable {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *  支付ID
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
     *  订单ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ORDER_ID", nullable = false, length = 150)
    private String orderId;

    /**
     *  支付类型ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "PAY_TYPE_ID", nullable = false, length = 3)
    private Byte payTypeId;

    /**
     *  支付账户ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "PAY_ACCOUNT_ID", nullable = false, length = 150)
    private String payAccountId;

    /**
     *  支付的交易ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "TRADE_ID", nullable = false, length = 150)
    private String tradeId;

    /**
     *  支付金额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "PAY_AMOUNT", nullable = false, precision = 18, scale = 4)
    private BigDecimal payAmount;

    /**
     *  支付时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "PAY_TIME", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date payTime;

    /**
     *  支付金额1，在交易类型是V支付时代表返现金支付了多少
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "PAY_AMOUNT1", nullable = true, precision = 18, scale = 4)
    private BigDecimal payAmount1;

    /**
     *  支付金额2，在交易类型是V支付时代表余额支付了多少
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "PAY_AMOUNT2", nullable = true, precision = 18, scale = 4)
    private BigDecimal payAmount2;

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
        AfcPayJo other = (AfcPayJo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
