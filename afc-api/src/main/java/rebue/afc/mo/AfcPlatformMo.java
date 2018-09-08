package rebue.afc.mo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台信息
 *
 * 数据库表: AFC_PLATFORM
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@JsonInclude(Include.NON_NULL)
public class AfcPlatformMo implements Serializable {

    /**
     *    平台信息ID
     *
     *    数据库字段: AFC_PLATFORM.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long id;

    /**
     *    余额
     *
     *    数据库字段: AFC_PLATFORM.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private BigDecimal balance;

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Long modifiedTimestamp;

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final long serialVersionUID = 1L;

    /**
     *    平台信息ID
     *
     *    数据库字段: AFC_PLATFORM.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getId() {
        return id;
    }

    /**
     *    平台信息ID
     *
     *    数据库字段: AFC_PLATFORM.ID
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *    余额
     *
     *    数据库字段: AFC_PLATFORM.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     *    余额
     *
     *    数据库字段: AFC_PLATFORM.BALANCE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM.MODIFIED_TIMESTAMP
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    public Long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    /**
     *    修改时间戳
     *
     *    数据库字段: AFC_PLATFORM.MODIFIED_TIMESTAMP
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
        sb.append(", balance=").append(balance);
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
        AfcPlatformMo other = (AfcPlatformMo) that;
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
