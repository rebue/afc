package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.ro.ChargeRo;
import rebue.afc.svc.AfcChargeSvc;
import rebue.afc.to.ChargeTo;

@Api(tags = "余额或返现金充值")
@RestController
public class AfcChargeCtrl {
	
	private final static Logger _log = LoggerFactory.getLogger(AfcDepositCtrl.class);

    @Resource
    private AfcChargeSvc       svc;
    
    @ApiOperation("余额/返现金-充值")
    @PostMapping("/charge")
    ChargeRo charge(ChargeTo to) {
        _log.info("进货保证金-充值： {}", to);
        return svc.charge(to);
    }

}
