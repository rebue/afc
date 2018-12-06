package rebue.afc.svc;

import rebue.afc.mo.AfcRefundMo;
import rebue.afc.to.RefundImmediateTo;
import rebue.afc.to.RefundApprovedTo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 退款日志
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcRefundSvc extends MybatisBaseSvc<AfcRefundMo, java.lang.Long> {

    /**
     * 经过审核的退款(买家申请，卖家同意后进行的退款)
     */
    Ro refundApproved(RefundApprovedTo to);

    /**
     * 直接退款(因错误支付、卖家取消发货等原因，直接退款原路返回)
     */
    Ro refundImmediate(RefundImmediateTo to);
}
