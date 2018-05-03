package rebue.afc.svc;

import rebue.robotech.svc.MybatisBaseSvc;
import rebue.afc.mo.AfcWithdrawAccountMo;
import java.util.List;

public interface AfcWithdrawAccountSvc
		extends
			MybatisBaseSvc<AfcWithdrawAccountMo, java.lang.Long> {

	/**
	 * 是否存在指定的用户
	 */
	Boolean existByUserId(Long userId);

	/**
	 * 查询用户的账户信息
	 * 
	 * @return
	 */
	List<AfcWithdrawAccountMo> listByUserId(Long userId);

}