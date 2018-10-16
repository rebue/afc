package rebue.afc.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import rebue.afc.dic.TradeTypeDic;

/**
 * 任务的传输对象（参数）
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TaskTo {
    /**
     * 交易类型
     */
    private TradeTypeDic tradeType;
    /**
     * 订单详情ID
     * (销售订单详情ID)
     */
    private String       orderDetailId;
}
