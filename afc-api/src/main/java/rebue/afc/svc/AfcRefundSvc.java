package rebue.afc.svc;

import rebue.afc.ro.RefundRo;
import rebue.afc.to.RefundTo;

public interface AfcRefundSvc {

    /**
     * 退款
     */
    RefundRo refund(RefundTo to);

}