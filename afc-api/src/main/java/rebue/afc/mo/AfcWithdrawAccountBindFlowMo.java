package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 提现账户绑定流程
 *
 * 数据库表: AFC_WITHDRAW_ACCOUNT_BIND_FLOW
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawAccountBindFlowMo implements Serializable {

    /**
     *    流程ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    流程状态(-1：已拒绝；1：待审核； 2：已审核)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.FLOW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte flowState;

    /**
     *    拒绝原因
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REJECT_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String rejectReason;

    /**
     *    申请人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long applicantId;

    /**
     *    申请时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    /**
     *    申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String applicantIp;

    /**
     *    审核人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long reviewerId;

    /**
     *    审核时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEW_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reviewTime;

    /**
     *    审核人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String reviewerIp;

    /**
     *    账户ID（绑定账户的ID）
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte withdrawType;

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String contactTel;

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountNo;

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountName;

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String openAccountBank;

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long modifiedTimestamp;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    流程ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    流程ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    流程状态(-1：已拒绝；1：待审核； 2：已审核)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.FLOW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getFlowState() {
        return flowState;
    }

    /**
     *    流程状态(-1：已拒绝；1：待审核； 2：已审核)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.FLOW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setFlowState(Byte flowState) {
        this.flowState = flowState;
    }

    /**
     *    拒绝原因
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REJECT_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     *    拒绝原因
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REJECT_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     *    申请人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getApplicantId() {
        return applicantId;
    }

    /**
     *    申请人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    /**
     *    申请时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     *    申请时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     *    申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getApplicantIp() {
        return applicantIp;
    }

    /**
     *    申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplicantIp(String applicantIp) {
        this.applicantIp = applicantIp;
    }

    /**
     *    审核人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getReviewerId() {
        return reviewerId;
    }

    /**
     *    审核人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     *    审核时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEW_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getReviewTime() {
        return reviewTime;
    }

    /**
     *    审核时间
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEW_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    /**
     *    审核人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getReviewerIp() {
        return reviewerIp;
    }

    /**
     *    审核人IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.REVIEWER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setReviewerIp(String reviewerIp) {
        this.reviewerIp = reviewerIp;
    }

    /**
     *    账户ID（绑定账户的ID）
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    账户ID（绑定账户的ID）
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getWithdrawType() {
        return withdrawType;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setWithdrawType(Byte withdrawType) {
        this.withdrawType = withdrawType;
    }

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOpenAccountBank() {
        return openAccountBank;
    }

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT_BIND_FLOW.MODIFIED_TIMESTAMP
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
        sb.append(", flowState=").append(flowState);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append(", applicantId=").append(applicantId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", applicantIp=").append(applicantIp);
        sb.append(", reviewerId=").append(reviewerId);
        sb.append(", reviewTime=").append(reviewTime);
        sb.append(", reviewerIp=").append(reviewerIp);
        sb.append(", accountId=").append(accountId);
        sb.append(", withdrawType=").append(withdrawType);
        sb.append(", contactTel=").append(contactTel);
        sb.append(", bankAccountNo=").append(bankAccountNo);
        sb.append(", bankAccountName=").append(bankAccountName);
        sb.append(", openAccountBank=").append(openAccountBank);
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
        AfcWithdrawAccountBindFlowMo other = (AfcWithdrawAccountBindFlowMo) that;
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
