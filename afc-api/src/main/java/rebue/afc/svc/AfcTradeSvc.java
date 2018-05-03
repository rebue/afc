package rebue.afc.svc;

import java.math.BigDecimal;

import rebue.afc.mo.AfcTradeMo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcTradeSvc extends MybatisBaseSvc<AfcTradeMo, java.lang.Long> {

    /**
     * 计算销售单的用户退货总额
     */
    BigDecimal getReturnGoodsTotalAmountByOrder(String saleOrderId);

}