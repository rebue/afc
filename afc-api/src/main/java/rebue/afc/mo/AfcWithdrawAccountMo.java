package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

/**
 * 提现账户
 *
 * 数据库表: AFC_WITHDRAW_ACCOUNT
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcWithdrawAccountMo implements Serializable {

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
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long modifiedTimestamp;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    提现账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    提现账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    是否默认提现账户
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IS_DEF
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Boolean getIsDef() {
        return isDef;
    }

    /**
     *    是否默认提现账户
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IS_DEF
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setIsDef(Boolean isDef) {
        this.isDef = isDef;
    }

    /**
     *    账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     *    账户ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.ACCOUNT_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Byte getWithdrawType() {
        return withdrawType;
    }

    /**
     *    提现类型(1-银行卡,2-支付宝)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.WITHDRAW_TYPE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setWithdrawType(Byte withdrawType) {
        this.withdrawType = withdrawType;
    }

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     *    联系电话
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.CONTACT_TEL
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     *    银行账号
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NO
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     *    银行账户名称
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.BANK_ACCOUNT_NAME
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getOpenAccountBank() {
        return openAccountBank;
    }

    /**
     *    开户银行
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OPEN_ACCOUNT_BANK
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }

    /**
     *    操作人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getOpId() {
        return opId;
    }

    /**
     *    操作人ID
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.OP_ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setOpId(Long opId) {
        this.opId = opId;
    }

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getMac() {
        return mac;
    }

    /**
     *    MAC地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MAC
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     *    IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public String getIp() {
        return ip;
    }

    /**
     *    IP地址
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.IP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     *    修改时间戳(添加或更新本条记录时的时间戳)
     *
     *    数据库字段: AFC_WITHDRAW_ACCOUNT.MODIFIED_TIMESTAMP
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
        sb.append(", isDef=").append(isDef);
        sb.append(", accountId=").append(accountId);
        sb.append(", withdrawType=").append(withdrawType);
        sb.append(", contactTel=").append(contactTel);
        sb.append(", bankAccountNo=").append(bankAccountNo);
        sb.append(", bankAccountName=").append(bankAccountName);
        sb.append(", openAccountBank=").append(openAccountBank);
        sb.append(", opId=").append(opId);
        sb.append(", mac=").append(mac);
        sb.append(", ip=").append(ip);
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
        AfcWithdrawAccountMo other = (AfcWithdrawAccountMo) that;
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
