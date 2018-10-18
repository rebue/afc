package rebue.afc.svc;

import java.util.List;

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.ro.AfcWithdrawAccountInfoRo;
import rebue.afc.ro.AfcWithdrawAccountRo;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 提现账户
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcWithdrawAccountSvc extends MybatisBaseSvc<AfcWithdrawAccountMo, java.lang.Long> {

    /**
     *  是否存在指定的用户
     */
    Boolean existByUserId(Long userId);

    /**
     *  查询用户的账户信息
     *
     *  @return
     */
    List<AfcWithdrawAccountMo> listByUserId(Long userId);

    /**
     * 获取提现账户信息
     * @param userId
     * @return
     */
	AfcWithdrawAccountInfoRo getWithdrawAccountInfo(Long userId);

	/**
	 * 重写查询用户提现账号信息
	 * @param mo
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	PageInfo<AfcWithdrawAccountRo> listEx(AfcWithdrawAccountMo mo, int pageNum, int pageSize, String orderBy);
}
