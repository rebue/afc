package rebue.afc.svc;

import rebue.afc.mo.AfcRefundMo;
import rebue.afc.to.RefundGoBackTo;
import rebue.afc.to.RefundTo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 退款日志
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcRefundSvc extends MybatisBaseSvc<AfcRefundMo, java.lang.Long> {

    /**
     * 退款
     */
    Ro refund(RefundTo to);

    /**
     * 错误支付，直接退款原路返回
     */
    Ro refundGoBack(RefundGoBackTo to);
}
