package rebue.afc.svc;

import java.util.List;
import rebue.afc.mo.AfcWithdrawAccountMo;
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
}
