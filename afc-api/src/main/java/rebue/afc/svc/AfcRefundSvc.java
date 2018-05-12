package rebue.afc.svc;

import rebue.afc.returngoods.ro.RefundToBuyerRo;
import rebue.afc.returngoods.to.RefundToBuyerTo;

public interface AfcRefundSvc {

    /**
     * 退款-退款到买家账户
     */
    RefundToBuyerRo refundToBuyer(RefundToBuyerTo to);

}