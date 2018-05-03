package rebue.afc.svc;

import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawApplyTo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcWithdrawSvc extends MybatisBaseSvc<AfcWithdrawMo, java.lang.Long> {

    /**
     * 申请提现
     */
    WithdrawApplyRo apply(WithdrawApplyTo to);

    /**
     * 处理提现
     */
    WithdrawDealRo deal(WithdrawDealTo to);

    /**
     * 确认提现成功（手动）
     */
    WithdrawOkRo ok(WithdrawOkTo to);

    /**
     * 作废提现
     */
    WithdrawCancelRo cancel(WithdrawCancelTo to);

}