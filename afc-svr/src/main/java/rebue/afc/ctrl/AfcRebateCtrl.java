package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.ro.RebateRo;
import rebue.afc.svc.AfcRebateSvc;
import rebue.afc.to.RebateTo;

/**
 * 返款
 */
@RestController
public class AfcRebateCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcRebateCtrl.class);

    @Resource
    private AfcRebateSvc        svc;

    /**
     * 返款到供应商的余额
     */
    @PostMapping("/rebate/provider/balance")
    RebateRo rebateProviderBalance(RebateTo to) {
        _log.info("返款到供应商的余额： {}", to);
        return svc.rebateProviderBalance(to);
    }

    /**
     * 返款到买家的返现金
     */
    @PostMapping("/rebate/buyer/cashback")
    RebateRo rebateBuyerCashback(RebateTo to) {
        _log.info("返款到买家的返现金： {}", to);
        return svc.rebateBuyerCashback(to);
    }

    /**
     * 返款到加盟商的已占用保证金
     */
    @PostMapping("/rebate/seller/depositused")
    RebateRo rebateSellerDepositUsed(RebateTo to) {
        _log.info("返款到加盟商的已占用保证金： {}", to);
        return svc.rebateSellerDepositUsed(to);
    }

    /**
     * 返款到加盟商的余额
     */
    @PostMapping("/rebate/seller/balance")
    RebateRo rebateSellerBalance(RebateTo to) {
        _log.info("返款到加盟商的余额： {}", to);
        return svc.rebateSellerBalance(to);
    }

}
