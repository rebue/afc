package rebue.afc.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * TODO 删除
 * 查询用户待返现任务请求参数
 */
@ApiModel(value = "查询用户待返现任务的传输对象（参数）")
@JsonInclude(Include.NON_NULL)
public class GetCashBackTaskTo {
    /**
     * 用户账号
     */
    private long accountId;
    /**
     * 任务运行状态
     */
    private byte executeState;
    /**
     * 任务类型
     */
    private byte tradType;

    /**
     * 查询页数
     */
    private byte pageNum;
    /**
     * 页面的记录数
     */
    private byte pageSize;

    public byte getPageNum() {
        return pageNum;
    }

    public void setPageNum(byte pageNum) {
        this.pageNum = pageNum;
    }

    public byte getPageSize() {
        return pageSize;
    }

    public void setPageSize(byte pageSize) {
        this.pageSize = pageSize;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public byte getExecuteState() {
        return executeState;
    }

    public void setExecuteState(byte executeState) {
        this.executeState = executeState;
    }

    public byte getTradType() {
        return tradType;
    }

    public void setTradType(byte tradType) {
        this.tradType = tradType;
    }

    @Override
    public String toString() {
        return "GetCashBackTaskTo [accountId=" + accountId + ", executeState=" + executeState + ", tradType=" + tradType + ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
    }

}
