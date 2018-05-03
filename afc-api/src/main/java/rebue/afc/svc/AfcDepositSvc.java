package rebue.afc.svc;

import rebue.afc.ro.ChargeRo;
import rebue.afc.ro.DepositGoodsTransferRo;
import rebue.afc.to.DepositGoodsInTo;
import rebue.afc.to.DepositGoodsReturnTo;
import rebue.afc.to.DepositGoodsTransferTo;
import rebue.afc.to.ChargeTo;

public interface AfcDepositSvc {

    /**
     * 进货保证金-充值
     */
    ChargeRo charge(ChargeTo to);

    /**
     * 进货保证金-进货
     */
    DepositGoodsTransferRo inGoods(DepositGoodsInTo to);

    /**
     * 进货保证金-出货
     */
    DepositGoodsTransferRo outGoods(DepositGoodsReturnTo to);

    /**
     * 进货保证金-调货
     */
    DepositGoodsTransferRo transferGoods(DepositGoodsTransferTo to);

}
