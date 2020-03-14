package rebue.afc.sgjz.msg;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import rebue.afc.sgjz.dic.SgjzPayWayDic;

/**
 * 手工记账的支付完成通知的消息体
 */
@JsonInclude(Include.NON_NULL)
@Data
public class SgjzPayDoneMsg {
    /**
     * 用户ID(买家的用户ID，如果没有绑定买家，那么买家id将为suc/suc-svr/co中的静态用户id -1(负1))
     */
    private Long          userId;
    /**
     * 手工记账的支付方式
     */
    private SgjzPayWayDic payWay;
    /**
     * 支付交易的金额(单位为元)
     */
    private BigDecimal    payAmount;
    /**
     * 订单号
     */
    private String        orderId;
    /**
     * 支付完成时间
     */
    private Date          payTime;
    /**
     * 手工记账的操作人ID(在前期没有登录功能没有做好之前，这个同样是suc/suc-svr/co中的静态用户id -1(负1)
     */
    private Long          sgjzOpId;
}