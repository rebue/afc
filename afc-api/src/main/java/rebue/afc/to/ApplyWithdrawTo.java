package rebue.afc.to;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ApplyWithdrawTo {

	/**
	 * 申请单号
	 */
	private String orderId;
	
	/**
	 * 账户姓名
	 */
	private String bankAccountName;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 提现账号
	 */
	private String bankAccountNo;
	
	/**
	 * 提现类型
	 */
	private Byte withdrawType;
	
	/**
	 * 开户银行
	 */
	private String openAccountBank;
	
	/**
	 * 联系电话
	 */
	private String contactTel;
	
	/**
	 * 提现金额
	 */
	private BigDecimal withdrawAmount;
	
	/**
	 * 申请人id
	 */
	private Long applicantId;
	
	/**
	 * 申请人ip地址
	 */
	private String ip;
	
	/**
     * 申请提现的标题
     */
    private String tradeTitle;
    
    /**
     * 申请提现的详情
     */
    private String tradeDetail;
}
