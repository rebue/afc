package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.ro.ChargeRo;
import rebue.afc.ro.DepositGoodsTransferRo;
import rebue.afc.svc.AfcDepositSvc;
import rebue.afc.to.ChargeTo;
import rebue.afc.to.DepositGoodsInTo;
import rebue.afc.to.DepositGoodsReturnTo;
import rebue.afc.to.DepositGoodsTransferTo;

/**
 * 进货保证金
 */
@RestController
public class AfcDepositCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcDepositCtrl.class);

    @Resource
    private AfcDepositSvc       svc;

    /**
     * 进货保证金-充值
     */
    @PostMapping("/deposit/charge")
    ChargeRo charge(ChargeTo to) {
        _log.info("进货保证金-充值： {}", to);
        return svc.charge(to);
    }

    /**
     * 进货保证金-进货
     */
    @PostMapping("/deposit/goods/in")
    DepositGoodsTransferRo in(DepositGoodsInTo to) {
        _log.info("进货保证金-进货： {}", to);
        return svc.inGoods(to);
    }

    /**
     * 进货保证金-出货
     */
    @PostMapping("/deposit/goods/out")
    DepositGoodsTransferRo out(DepositGoodsReturnTo to) {
        _log.info("进货保证金-出货： {}", to);
        return svc.outGoods(to);
    }

    /**
     * 进货保证金-调货
     */
    @PostMapping("/deposit/goods/transfer")
    DepositGoodsTransferRo transfer(DepositGoodsTransferTo to) {
        _log.info("进货保证金-调货： {}", to);
        return svc.transferGoods(to);
    }
}
