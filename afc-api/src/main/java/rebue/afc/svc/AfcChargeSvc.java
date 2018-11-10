package rebue.afc.svc;

import rebue.afc.mo.AfcAccountMo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.to.ChargeTo;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 充值
 */
public interface AfcChargeSvc extends MybatisBaseSvc<AfcAccountMo, java.lang.Long> {
	
	/**
     * 余额或返现金-充值
     */
    ChargeRo charge(ChargeTo to);
	
}
