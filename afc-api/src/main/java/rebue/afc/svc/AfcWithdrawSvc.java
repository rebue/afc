package rebue.afc.svc;

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.ro.AfcWithdrawRo;
import rebue.afc.ro.WithdrawNumberForMonthRo;
import rebue.afc.to.ApplyWithdrawTo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.ro.IdRo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 提现信息
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcWithdrawSvc extends MybatisBaseSvc<AfcWithdrawMo, java.lang.Long> {

    /**
     * 申请提现
     */
    IdRo apply(ApplyWithdrawTo to);

    /**
     * 处理提现
     */
    WithdrawDealRo deal(WithdrawDealTo to);

    /**
     * 确认提现成功（手动）
     */
    Ro ok(WithdrawOkTo to);

    /**
     * 作废提现
     */
    WithdrawCancelRo cancel(WithdrawCancelTo to);

    /**
     * 获取用户提现次数和手续费
     * 
     * @param accountId
     * @return
     */
    WithdrawNumberForMonthRo getWithdrawNumberForMonth(AfcWithdrawMo mo);

    /**
     * 重写查询提现信息
     * 
     * @param mo
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    PageInfo<AfcWithdrawRo> lisrEx(AfcWithdrawMo mo, int pageNum, int pageSize, String orderBy);

    /**
     * 查询用户提现中的信息
     * 
     * @param accountId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    PageInfo<AfcWithdrawMo> selectWithdrawApplying(Long accountId, int pageNum, int pageSize, String orderBy);
}
