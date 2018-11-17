package rebue.afc.ro;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 支付订单的查询结果
 */
@JsonInclude(Include.NON_NULL)
@Data
public class PayOrderQueryRo {
    /**
     * 支付交易的金额(单位为“元”，精确到小数点后4位)
     */
    private Double tradeAmount;
    /**
     * 支付的交易ID
     */
    private String tradeId;
    /**
     * 支付完成时间
     */
    private Date   payTime;

}
