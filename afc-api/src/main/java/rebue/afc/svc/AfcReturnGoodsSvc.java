package rebue.afc.svc;

import java.util.Map;

import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.afc.returngoods.to.ReturnGoodsByBuyerTo;

public interface AfcReturnGoodsSvc {

    /**
     * 退货-买家退货
     */
    ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to);

    /**
	 * 用户退货退款并扣减返现金额 Title: returnRefundAndSubtractCashback Description:
	 * 
	 * @param to
	 * @return
	 * @date 2018年5月8日 下午2:04:06
	 */
	Map<String, Object> returnRefundAndSubtractCashback(ReturnGoodsByBuyerTo to);

}