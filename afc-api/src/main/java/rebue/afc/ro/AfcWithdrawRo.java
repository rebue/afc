package rebue.afc.ro;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawRo {

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
     * 申请人姓名
     */
    private String applicantName;
}
