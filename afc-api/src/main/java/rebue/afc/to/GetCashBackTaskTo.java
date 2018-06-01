package rebue.afc.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
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
	 * 从第几条记录开始查询
	 */
	private byte start;
	/**
	 * 查询的记录的条数
	 */
	private byte limit;
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
	public byte getStart() {
		return start;
	}
	public void setStart(byte start) {
		this.start = start;
	}
	public byte getLimit() {
		return limit;
	}
	public void setLimit(byte limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return "GetCashBackTaskTo [accountId=" + accountId + ", executeState=" + executeState + ", tradType=" + tradType
				+ ", start=" + start + ", limit=" + limit + "]";
	}

}
