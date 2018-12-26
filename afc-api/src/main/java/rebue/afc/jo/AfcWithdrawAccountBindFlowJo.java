package rebue.afc.jo;

import java.io.Serializable;
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
 * The persistent class for the AFC_WITHDRAW_ACCOUNT_BIND_FLOW database table.
 * @mbg.generated 自动生成，如需修改，请删除本行
 */
@Entity
@Table(name = "AFC_WITHDRAW_ACCOUNT_BIND_FLOW")
@Getter
@Setter
@ToString
public class AfcWithdrawAccountBindFlowJo implements Serializable {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *  流程ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 19)
    private Long id;

    /**
     *  流程状态
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "FLOW_STATE", nullable = false, length = 3)
    private Byte flowState;

    /**
     *  拒绝原因
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "REJECT_REASON", nullable = true, length = 100)
    private String rejectReason;

    /**
     *  申请人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLICANT_ID", nullable = false, length = 19)
    private Long applicantId;

    /**
     *  申请时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLY_TIME", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date applyTime;

    /**
     *  申请人IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "APPLICANT_IP", nullable = false, length = 150)
    private String applicantIp;

    /**
     *  审核人ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "REVIEWER_ID", nullable = true, length = 19)
    private Long reviewerId;

    /**
     *  审核时间
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "REVIEW_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date reviewTime;

    /**
     *  审核人IP地址
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = true)
    @Column(name = "REVIEWER_IP", nullable = true, length = 150)
    private String reviewerIp;

    /**
     *  账户ID
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "ACCOUNT_ID", nullable = false, length = 19)
    private Long accountId;

    /**
     *  提现类型
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "WITHDRAW_TYPE", nullable = false, length = 3)
    private Byte withdrawType;

    /**
     *  联系电话
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "CONTACT_TEL", nullable = false, length = 50)
    private String contactTel;

    /**
     *  银行账号
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "BANK_ACCOUNT_NO", nullable = false, length = 100)
    private String bankAccountNo;

    /**
     *  银行账户名称
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "BANK_ACCOUNT_NAME", nullable = false, length = 150)
    private String bankAccountName;

    /**
     *  开户银行
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "OPEN_ACCOUNT_BANK", nullable = false, length = 150)
    private String openAccountBank;

    /**
     *  修改时间戳
     *
     *  @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Basic(optional = false)
    @Column(name = "MODIFIED_TIMESTAMP", nullable = false, length = 19)
    private Long modifiedTimestamp;

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
        AfcWithdrawAccountBindFlowJo other = (AfcWithdrawAccountBindFlowJo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
