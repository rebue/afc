package rebue.afc.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 账户-充值的传输对象（参数）
 */
@ApiModel(value = "账户-充值的传输对象（参数）")
@JsonInclude(Include.NON_NULL)
@Data
public class ChargeTo {
	/**
     * 账号ID
     */
    private Long   id;
    /**
     * 用户ID
     */
    private Long   userId;
    /**
     * 充值单号
     */
    private String orderId;
    /**
     * 充值交易的标题
     */
    private String tradeTitle;
    /**
     * 充值交易的详情
     */
    private String tradeDetail;
    /**
     * 充值交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 充值交易的交易凭证号
     */
    private String tradeVoucherNo;
    /**
     * 操作人的用户ID
     */
    private Long   opId;
    /**
     * 操作人的MAC地址
     */
    private String mac;
    /**
     * 操作人的IP地址
     */
    private String ip;
    
    /**
     * 充值类型
     */
    private int tradeType;
    

}
