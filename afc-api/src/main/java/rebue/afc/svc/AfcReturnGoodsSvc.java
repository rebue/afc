package rebue.afc.svc;

import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.afc.returngoods.to.ReturnGoodsByBuyerTo;

public interface AfcReturnGoodsSvc {

    /**
     * 退货-买家退货
     */
    ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to);

}