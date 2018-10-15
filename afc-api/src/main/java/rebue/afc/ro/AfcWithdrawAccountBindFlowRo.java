package rebue.afc.ro;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawAccountBindFlowRo {

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
	     * 申请人姓名
	     */
	    private String applicantName;
}
