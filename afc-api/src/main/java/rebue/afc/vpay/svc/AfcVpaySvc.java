package rebue.afc.vpay.svc;

import rebue.afc.ro.PayRo;
import rebue.afc.ro.PrepayRo;
import rebue.afc.ro.PayOrderQueryRo;
import rebue.afc.vpay.to.AfcVpayPayTo;
import rebue.afc.vpay.to.AfcVpayPrepayTo;

public interface AfcVpaySvc {

    /**
     * V支付-预支付
     */
    PrepayRo prepay(AfcVpayPrepayTo to);

    /**
     * V支付-支付
     */
    PayRo pay(AfcVpayPayTo to);

    /**
     * V支付-查询订单
     */
    PayOrderQueryRo queryOrder(String orderId);

}