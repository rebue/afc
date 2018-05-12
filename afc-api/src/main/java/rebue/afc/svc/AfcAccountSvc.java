package rebue.afc.svc;

import rebue.robotech.svc.MybatisBaseSvc;
import rebue.afc.mo.AfcAccountMo;
import java.util.HashMap;

public interface AfcAccountSvc
		extends
			MybatisBaseSvc<AfcAccountMo, java.lang.Long> {

	/**
	 * 产生了一笔交易(影响余额/返现金/返现中金额/货款)，修改相应的字段及记录时间戳
	 * 
	 * @param map
	 * @return
	 */
	int trade(HashMap<String, ?> map);

}