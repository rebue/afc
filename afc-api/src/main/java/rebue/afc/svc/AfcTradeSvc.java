package rebue.afc.svc;

import java.math.BigDecimal;

import rebue.afc.mo.AfcTradeMo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcTradeSvc extends MybatisBaseSvc<AfcTradeMo, java.lang.Long> {

    /**
     * 添加一笔交易记录
     * 1. 添加交易记录
     * 2. 修改账户相应的金额字段
     * 3. 添加账户流水
     */
    void addTrade(AfcTradeMo tradeMo);

    /**
     * 计算此订单的退款总额(通过销售订单ID)
     */
    BigDecimal getRefundTotalAmountByOrderId(String saleOrderId);

}