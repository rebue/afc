package rebue.afc.ro;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 查询交易流水返回类
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class AfcTradeListRo {
	/**
	 * 账号ID
	 */
	private Long accountId;
	
	/**
	 * 账户微信名
	 */
	private String accountName;
	
	/**
	 * 交易类型
	 */
	private Byte tradeType;
	
	/**
	 * 交易时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;
	
	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	/**
	 *操作人ID  
	 */
	private Long opId;
	/**
	 * 操作人名称
	 */
	private String opName;
	
}
