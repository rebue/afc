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
 * The persistent class for the AFC_WITHDRAW database table.
 * @mbg.generated 自动生成，如需修改，请删除本行
 */
@Entity
@Table(name = "AFC_WITHDRAW")
@Getter
@Setter
@ToString
public class AfcWithdrawJo implements Serializable {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *  提现记录
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 19)
    private Long id;

    /**
     *  申请时记录-账户ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ACCOUNT_ID", nullable = false, length = 19)
    private Long accountId;

    /**
     *  申请单号
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ORDER_ID", nullable = false, length = 150)
    private String orderId;

    /**
     *  提现状态
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "WITHDRAW_STATE", nullable = false, length = 3)
    private Byte withdrawState;

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
     *  提现金额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT", nullable = false, precision = 18, scale = 4)
    private BigDecimal amount;

    /**
     *  实际到账金额
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "REAL_AMOUNT", nullable = false, precision = 18, scale = 4)
    private BigDecimal realAmount;

    /**
     *  提现服务费
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "SEVICE_CHARGE", nullable = false, precision = 18, scale = 4)
    private BigDecimal seviceCharge;

    /**
     *  申请时记录-申请人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLICANT_ID", nullable = false, length = 19)
    private Long applicantId;

    /**
     *  申请时记录-申请时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLY_TIME", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date applyTime;

    /**
     *  申请时记录-申请人MAC地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLICANT_MAC", nullable = false, length = 30)
    private String applicantMac;

    /**
     *  申请时记录-申请人IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLICANT_IP", nullable = false, length = 150)
    private String applicantIp;

    /**
     *  受理时记录-受理人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "RECIEVER_ID", nullable = true, length = 19)
    private Long recieverId;

    /**
     *  受理时记录-受理时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "RECIEVER_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date recieverTime;

    /**
     *  受理时记录-受理人MAC地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "RECIEVER_MAC", nullable = true, length = 30)
    private String recieverMac;

    /**
     *  受理时记录-受理人IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "RECIEVER_IP", nullable = true, length = 150)
    private String recieverIp;

    /**
     *  提现成功或作废时记录-结束人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "FINISHER_ID", nullable = true, length = 19)
    private Long finisherId;

    /**
     *  提现成功或作废时记录-结束时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "FINISHER_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date finisherTime;

    /**
     *  提现成功或作废时记录-结束人MAC地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "FINISHER_MAC", nullable = true, length = 30)
    private String finisherMac;

    /**
     *  提现成功或作废时记录-结束人IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "FINISHER_IP", nullable = true, length = 150)
    private String finisherIp;

    /**
     *  提现类型
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "WITHDRAW_TYPE", nullable = false, length = 3)
    private Byte withdrawType;

    /**
     *  提现联系电话
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "CONTACT_TEL", nullable = false, length = 50)
    private String contactTel;

    /**
     *  提现银行账号
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "BANK_ACCOUNT_NO", nullable = false, length = 100)
    private String bankAccountNo;

    /**
     *  提现银行账户名称
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "BANK_ACCOUNT_NAME", nullable = false, length = 150)
    private String bankAccountName;

    /**
     *  提现开户银行
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "OPEN_ACCOUNT_BANK", nullable = false, length = 150)
    private String openAccountBank;

    /**
     *  作废提现时记录-作废原因
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "CANCEL_REASON", nullable = true, length = 100)
    private String cancelReason;

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
        AfcWithdrawJo other = (AfcWithdrawJo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
