package rebue.afc.svc;

import rebue.afc.dic.PayTypeDic;
import rebue.afc.mo.AfcPayMo;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 支付信息
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcPaySvc extends MybatisBaseSvc<AfcPayMo, java.lang.Long> {

    /**
     *  通过支付类型和订单号查询支付情况
     */
    AfcPayMo getByOrderId(PayTypeDic payType, String orderId);
}
