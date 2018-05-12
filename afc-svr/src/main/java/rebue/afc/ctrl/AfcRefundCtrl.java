package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.ro.RefundToBuyerRo;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.to.RefundToBuyerTo;

@Api(tags = "退款")
@RestController
public class AfcRefundCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcRefundCtrl.class);

    @Resource
    private AfcRefundSvc        svc;

    @ApiOperation("退款-退款到买家账户")
    @PostMapping("/refund/tobuyer")
    RefundToBuyerRo refundToBuyer(@RequestBody RefundToBuyerTo to) {
        _log.info("退款-退款到买家账户： {}", to);
        return svc.refundToBuyer(to);
    }

}
