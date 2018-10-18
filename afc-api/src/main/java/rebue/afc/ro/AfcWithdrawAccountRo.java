package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawAccountRo {

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
     *    操作人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long opId;

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String mac;

    /**
     *    IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String ip;
    
    /**
     * 用户姓名
     */
    private String userName;
}
