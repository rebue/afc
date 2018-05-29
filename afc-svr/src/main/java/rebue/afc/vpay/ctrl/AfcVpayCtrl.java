package rebue.afc.vpay.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.ro.PayOrderQueryRo;
import rebue.afc.ro.PayRo;
import rebue.afc.ro.PrepayRo;
import rebue.afc.vpay.svc.AfcVpaySvc;
import rebue.afc.vpay.to.AfcVpayPayTo;
import rebue.afc.vpay.to.AfcVpayPrepayTo;

/**
 * V支付
 */
@RestController
public class AfcVpayCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcVpayCtrl.class);

    @Resource
    private AfcVpaySvc          svc;

    /**
     * V支付-预支付
     */
    @PostMapping("/vpay/prepay")
    PrepayRo prepay(AfcVpayPrepayTo to) {
        _log.info("V支付-预支付: {}", to);
        return svc.prepay(to);
    }

    /**
     * V支付-支付
     */
    @PostMapping("/vpay/pay")
    PayRo pay(AfcVpayPayTo to) {
        _log.info("V支付-支付: {}", to);
        return svc.pay(to);
    }

    /**
     * V支付-查询订单
     */
    @GetMapping("/vpay/queryorder")
    PayOrderQueryRo queryOrder(@RequestParam("orderId") String orderId) {
        _log.info("V支付-查询订单: {}", orderId);
        return svc.queryOrder(orderId);
    }

}
