package rebue.afc.svc;

import rebue.afc.ro.RefundToBuyerRo;
import rebue.afc.to.RefundToBuyerTo;

public interface AfcRefundSvc {

    /**
     * 退款-退款到买家账户
     */
    RefundToBuyerRo refundToBuyer(RefundToBuyerTo to);

}