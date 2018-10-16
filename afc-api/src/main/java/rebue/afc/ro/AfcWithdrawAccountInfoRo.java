package rebue.afc.ro;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawAccountInfoRo {

	/**
     *    提现账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    是否默认提现账户
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IS_DEF
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Boolean isDef;

    /**
     *    账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long accountId;

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Byte withdrawType;

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String contactTel;

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountNo;

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String bankAccountName;

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String openAccountBank;
    
    /**
     * 提现次数
     */
    private int withdrawNumber;
    
    /**
     *    余额
     *
     *    数据库字段: AFC_ACCOUNT.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String balance;
    
    /**
     *    提现服务费
     *
     *    数据库字段: AFC_WITHDRAW.SEVICE_CHARGE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal seviceCharge;
}
