package rebue.afc.svc;

import java.util.Map;

import rebue.afc.mo.AfcPlatformFlowMo;
import rebue.afc.ro.RebateRo;
import rebue.afc.to.RebateTo;

public interface AfcRebateSvc {

    /**
     * 返款到供应商的余额
     */
    RebateRo rebateProviderBalance(RebateTo to);

    /**
     * 返款到买家的返现金
     */
    RebateRo rebateBuyerCashback(RebateTo to);

    /**
     * 返款到加盟商的已占用保证金
     */
    RebateRo rebateSellerDepositUsed(RebateTo to);

    /**
     * 返款到加盟商的余额
     */
    RebateRo rebateSellerBalance(RebateTo to);

    /**
     * 返款至平台服务费
     * Title: rebatePlatformServiceFee
     * Description: 
     * @param mo
     * @return
     * @date 2018年5月4日 下午12:47:10
     */
	Map<String, Object> rebatePlatformServiceFee(AfcPlatformFlowMo mo);

}
