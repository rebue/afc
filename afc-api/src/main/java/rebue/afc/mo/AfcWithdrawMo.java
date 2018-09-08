package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 提现信息
 *
 * 数据库表: AFC_WITHDRAW
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawMo implements Serializable {

    /**
     *    提现记录
     *
     *    数据库字段: AFC_WITHDRAW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    申请时记录-账户ID（买家账户ID）
     *
     *    数据库字段: AFC_WITHDRAW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    申请单号（唯一约束，以防重复申请）
     *
     *    数据库字段: AFC_WITHDRAW.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String orderId;

    /**
     *    提现状态(-1-作废；1-申请；2-处理中；3-已提现)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte withdrawState;

    /**
     *    交易标题
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String tradeTitle;

    /**
     *    交易详情
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String tradeDetail;

    /**
     *    提现金额(提现金额=实际到账金额+提现服务费)
     *
     *    数据库字段: AFC_WITHDRAW.AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal amount;

    /**
     *    实际到账金额
     *
     *    数据库字段: AFC_WITHDRAW.REAL_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal realAmount;

    /**
     *    提现服务费
     *
     *    数据库字段: AFC_WITHDRAW.SEVICE_CHARGE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal seviceCharge;

    /**
     *    申请时记录-申请人ID
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long applicantId;

    /**
     *    申请时记录-申请时间
     *
     *    数据库字段: AFC_WITHDRAW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    /**
     *    申请时记录-申请人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String applicantMac;

    /**
     *    申请时记录-申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String applicantIp;

    /**
     *    受理时记录-受理人ID
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long recieverId;

    /**
     *    受理时记录-受理时间
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recieverTime;

    /**
     *    受理时记录-受理人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String recieverMac;

    /**
     *    受理时记录-受理人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String recieverIp;

    /**
     *    提现成功或作废时记录-结束人ID
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long finisherId;

    /**
     *    提现成功或作废时记录-结束时间
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finisherTime;

    /**
     *    提现成功或作废时记录-结束人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String finisherMac;

    /**
     *    提现成功或作废时记录-结束人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String finisherIp;

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte withdrawType;

    /**
     *    提现联系电话
     *
     *    数据库字段: AFC_WITHDRAW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String contactTel;

    /**
     *    提现银行账号
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountNo;

    /**
     *    提现银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountName;

    /**
     *    提现开户银行
     *
     *    数据库字段: AFC_WITHDRAW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String openAccountBank;

    /**
     *    作废提现时记录-作废原因
     *
     *    数据库字段: AFC_WITHDRAW.CANCEL_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String cancelReason;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    提现记录
     *
     *    数据库字段: AFC_WITHDRAW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    提现记录
     *
     *    数据库字段: AFC_WITHDRAW.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    申请时记录-账户ID（买家账户ID）
     *
     *    数据库字段: AFC_WITHDRAW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    申请时记录-账户ID（买家账户ID）
     *
     *    数据库字段: AFC_WITHDRAW.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    申请单号（唯一约束，以防重复申请）
     *
     *    数据库字段: AFC_WITHDRAW.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *    申请单号（唯一约束，以防重复申请）
     *
     *    数据库字段: AFC_WITHDRAW.ORDER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *    提现状态(-1-作废；1-申请；2-处理中；3-已提现)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getWithdrawState() {
        return withdrawState;
    }

    /**
     *    提现状态(-1-作废；1-申请；2-处理中；3-已提现)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_STATE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setWithdrawState(Byte withdrawState) {
        this.withdrawState = withdrawState;
    }

    /**
     *    交易标题
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getTradeTitle() {
        return tradeTitle;
    }

    /**
     *    交易标题
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_TITLE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeTitle(String tradeTitle) {
        this.tradeTitle = tradeTitle;
    }

    /**
     *    交易详情
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getTradeDetail() {
        return tradeDetail;
    }

    /**
     *    交易详情
     *
     *    数据库字段: AFC_WITHDRAW.TRADE_DETAIL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setTradeDetail(String tradeDetail) {
        this.tradeDetail = tradeDetail;
    }

    /**
     *    提现金额(提现金额=实际到账金额+提现服务费)
     *
     *    数据库字段: AFC_WITHDRAW.AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     *    提现金额(提现金额=实际到账金额+提现服务费)
     *
     *    数据库字段: AFC_WITHDRAW.AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     *    实际到账金额
     *
     *    数据库字段: AFC_WITHDRAW.REAL_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    /**
     *    实际到账金额
     *
     *    数据库字段: AFC_WITHDRAW.REAL_AMOUNT
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    /**
     *    提现服务费
     *
     *    数据库字段: AFC_WITHDRAW.SEVICE_CHARGE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getSeviceCharge() {
        return seviceCharge;
    }

    /**
     *    提现服务费
     *
     *    数据库字段: AFC_WITHDRAW.SEVICE_CHARGE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setSeviceCharge(BigDecimal seviceCharge) {
        this.seviceCharge = seviceCharge;
    }

    /**
     *    申请时记录-申请人ID
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getApplicantId() {
        return applicantId;
    }

    /**
     *    申请时记录-申请人ID
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    /**
     *    申请时记录-申请时间
     *
     *    数据库字段: AFC_WITHDRAW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     *    申请时记录-申请时间
     *
     *    数据库字段: AFC_WITHDRAW.APPLY_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     *    申请时记录-申请人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getApplicantMac() {
        return applicantMac;
    }

    /**
     *    申请时记录-申请人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplicantMac(String applicantMac) {
        this.applicantMac = applicantMac;
    }

    /**
     *    申请时记录-申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getApplicantIp() {
        return applicantIp;
    }

    /**
     *    申请时记录-申请人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.APPLICANT_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setApplicantIp(String applicantIp) {
        this.applicantIp = applicantIp;
    }

    /**
     *    受理时记录-受理人ID
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getRecieverId() {
        return recieverId;
    }

    /**
     *    受理时记录-受理人ID
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRecieverId(Long recieverId) {
        this.recieverId = recieverId;
    }

    /**
     *    受理时记录-受理时间
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getRecieverTime() {
        return recieverTime;
    }

    /**
     *    受理时记录-受理时间
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRecieverTime(Date recieverTime) {
        this.recieverTime = recieverTime;
    }

    /**
     *    受理时记录-受理人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getRecieverMac() {
        return recieverMac;
    }

    /**
     *    受理时记录-受理人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRecieverMac(String recieverMac) {
        this.recieverMac = recieverMac;
    }

    /**
     *    受理时记录-受理人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getRecieverIp() {
        return recieverIp;
    }

    /**
     *    受理时记录-受理人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.RECIEVER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setRecieverIp(String recieverIp) {
        this.recieverIp = recieverIp;
    }

    /**
     *    提现成功或作废时记录-结束人ID
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getFinisherId() {
        return finisherId;
    }

    /**
     *    提现成功或作废时记录-结束人ID
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setFinisherId(Long finisherId) {
        this.finisherId = finisherId;
    }

    /**
     *    提现成功或作废时记录-结束时间
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Date getFinisherTime() {
        return finisherTime;
    }

    /**
     *    提现成功或作废时记录-结束时间
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_TIME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setFinisherTime(Date finisherTime) {
        this.finisherTime = finisherTime;
    }

    /**
     *    提现成功或作废时记录-结束人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getFinisherMac() {
        return finisherMac;
    }

    /**
     *    提现成功或作废时记录-结束人MAC地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setFinisherMac(String finisherMac) {
        this.finisherMac = finisherMac;
    }

    /**
     *    提现成功或作废时记录-结束人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getFinisherIp() {
        return finisherIp;
    }

    /**
     *    提现成功或作废时记录-结束人IP地址
     *
     *    数据库字段: AFC_WITHDRAW.FINISHER_IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setFinisherIp(String finisherIp) {
        this.finisherIp = finisherIp;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getWithdrawType() {
        return withdrawType;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setWithdrawType(Byte withdrawType) {
        this.withdrawType = withdrawType;
    }

    /**
     *    提现联系电话
     *
     *    数据库字段: AFC_WITHDRAW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     *    提现联系电话
     *
     *    数据库字段: AFC_WITHDRAW.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     *    提现银行账号
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     *    提现银行账号
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     *    提现银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     *    提现银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     *    提现开户银行
     *
     *    数据库字段: AFC_WITHDRAW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOpenAccountBank() {
        return openAccountBank;
    }

    /**
     *    提现开户银行
     *
     *    数据库字段: AFC_WITHDRAW.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }

    /**
     *    作废提现时记录-作废原因
     *
     *    数据库字段: AFC_WITHDRAW.CANCEL_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     *    作废提现时记录-作废原因
     *
     *    数据库字段: AFC_WITHDRAW.CANCEL_REASON
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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
        sb.append(", withdrawState=").append(withdrawState);
        sb.append(", tradeTitle=").append(tradeTitle);
        sb.append(", tradeDetail=").append(tradeDetail);
        sb.append(", amount=").append(amount);
        sb.append(", realAmount=").append(realAmount);
        sb.append(", seviceCharge=").append(seviceCharge);
        sb.append(", applicantId=").append(applicantId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", applicantMac=").append(applicantMac);
        sb.append(", applicantIp=").append(applicantIp);
        sb.append(", recieverId=").append(recieverId);
        sb.append(", recieverTime=").append(recieverTime);
        sb.append(", recieverMac=").append(recieverMac);
        sb.append(", recieverIp=").append(recieverIp);
        sb.append(", finisherId=").append(finisherId);
        sb.append(", finisherTime=").append(finisherTime);
        sb.append(", finisherMac=").append(finisherMac);
        sb.append(", finisherIp=").append(finisherIp);
        sb.append(", withdrawType=").append(withdrawType);
        sb.append(", contactTel=").append(contactTel);
        sb.append(", bankAccountNo=").append(bankAccountNo);
        sb.append(", bankAccountName=").append(bankAccountName);
        sb.append(", openAccountBank=").append(openAccountBank);
        sb.append(", cancelReason=").append(cancelReason);
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
        AfcWithdrawMo other = (AfcWithdrawMo) that;
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
