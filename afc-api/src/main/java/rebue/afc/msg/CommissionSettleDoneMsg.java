package rebue.afc.msg;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 返佣结算完成通知的解析结果
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CommissionSettleDoneMsg {
    /**
     * 订单详情ID
     * (销售订单ID)
     */
    private String orderDetailId;
    /**
     * 结算完成时间
     */
    private Date   settleTime;


}
