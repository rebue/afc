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
 * The persistent class for the AFC_ACCOUNT database table.
 * @mbg.generated 自动生成，如需修改，请删除本行
 */
@Entity
@Table(name = "AFC_ACCOUNT")
@Getter
@Setter
@ToString
public class AfcAccountJo implements Serializable {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *  账户ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 19)
    private Long id;

    /**
     *  余额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "BALANCE", nullable = false, precision = 18, scale = 4)
    private BigDecimal balance;

    /**
     *  最后结算余额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "SETTLE_BALANCE", nullable = false, precision = 18, scale = 4)
    private BigDecimal settleBalance;

    /**
     *  最后结算时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "SETTLE_TIME", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date settleTime;

    /**
     *  已返佣金总额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "COMMISSION_TOTAL", nullable = false, precision = 18, scale = 4)
    private BigDecimal commissionTotal;

    /**
     *  作废-待返佣金
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "COMMISSIONING", nullable = false, precision = 18, scale = 4)
    private BigDecimal commissioning;

    /**
     *  提现中总额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "WITHDRAWING", nullable = false, precision = 18, scale = 4)
    private BigDecimal withdrawing;

    /**
     *  返现金余额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "CASHBACK", nullable = false, precision = 18, scale = 4)
    private BigDecimal cashback;

    /**
     *  作废-返现中的金额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "CASHBACKING", nullable = false, precision = 18, scale = 4)
    private BigDecimal cashbacking;

    /**
     *  进货保证金
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "DEPOSIT", nullable = false, precision = 18, scale = 4)
    private BigDecimal deposit;

    /**
     *  已占用的进货保证金
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "DEPOSIT_USED", nullable = false, precision = 18, scale = 4)
    private BigDecimal depositUsed;

    /**
     *  修改时间戳
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "MODIFIED_TIMESTAMP", nullable = false, length = 19)
    private Long modifiedTimestamp;

    /**
     *  账户类型
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 3)
    private Byte accountType;

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
        AfcAccountJo other = (AfcAccountJo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
