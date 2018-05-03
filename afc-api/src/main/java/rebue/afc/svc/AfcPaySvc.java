package rebue.afc.svc;

import rebue.robotech.svc.MybatisBaseSvc;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.dic.PayTypeDic;

public interface AfcPaySvc extends MybatisBaseSvc<AfcPayMo, java.lang.Long> {

	/**
	 * 通过支付类型和订单号查询支付情况
	 */
	AfcPayMo getByOrderId(PayTypeDic payType, String orderId);

}