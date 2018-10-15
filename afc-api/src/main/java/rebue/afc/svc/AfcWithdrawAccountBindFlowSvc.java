package rebue.afc.svc;

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcWithdrawAccountBindFlowMo;
import rebue.afc.ro.AfcWithdrawAccountBindFlowRo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 提现账户绑定流程
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcWithdrawAccountBindFlowSvc extends MybatisBaseSvc<AfcWithdrawAccountBindFlowMo, java.lang.Long> {

	/**
	 * 添加提现账户绑定流程
	 * 
	 * @param mo
	 * @return
	 */
	Ro addEx(AfcWithdrawAccountBindFlowMo mo);

	/**
	 * 根据申请人id查询最新一条申请提现账户记录
	 * 
	 * @param applicantId
	 * @return
	 */
	AfcWithdrawAccountBindFlowMo getNewOneByApplicantId(Long applicantId);

	/**
	 * 查询申请提现账号记录
	 * 
	 * @param mo
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	PageInfo<AfcWithdrawAccountBindFlowRo> listEx(AfcWithdrawAccountBindFlowMo mo, int pageNum, int pageSize,
			String orderBy);

	/**
	 * 审核通过
	 * @param mo
	 * @return
	 */
	Ro review(AfcWithdrawAccountBindFlowMo mo);

	/**
	 * 拒绝申请
	 * @param mo
	 * @return
	 */
	int reject(AfcWithdrawAccountBindFlowMo mo);
}
