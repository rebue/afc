package rebue.afc.to;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class AfcTradeTo {

	/**
	 * 用户微信名
	 */
	String wxNickName;
	
	/**
	 * 用户手机号
	 */
	String userMobile;
	
	/**
	 * 账号ID
	 */
	Long accountId ;
	
	/**
	 * 账号ID转为String
	 */
	
	String accountIdStr;
	
	/**
	 * 交易开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date tradeTimeStart;
	
	/**
	 * 交易结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date tradeTimeEnd;
	
	/**
	 * 交易类型
	 */
	private Byte[] tradeType; 
	
	/**
	 *交易类型，转为String 
	 */
	String tradeTypeString ;

}
