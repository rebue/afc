package rebue.afc.svc;

import java.math.BigDecimal;
import java.util.Date;

import rebue.afc.mo.AfcSettleTaskMo;

/**
 * 处理结算相关的业务
 */
public interface AfcSettleSvc {

    /**
     * 结算此账户的费用
     * 
     * @param taskMo
     *            要执行的结算任务
     * @param now
     *            当前时间
     */
    void settleAccountFee(AfcSettleTaskMo taskMo, Date now);

    /**
     * 结算平台服务费
     * 将交易结算的服务费打到平台的余额
     * 
     * @param orderId
     *            销售订单ID
     * @param serviceFee
     *            服务费
     * @param now
     *            当前时间
     */
    void settlePlatformServiceFee(String orderId, BigDecimal serviceFee, Date now);

}
