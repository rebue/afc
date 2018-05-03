package rebue.afc.returngoods.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.afc.returngoods.to.ReturnGoodsByBuyerTo;
import rebue.afc.svc.AfcReturnGoodsSvc;

@Api(tags = "退货")
@RestController
public class AfcReturnGoodsCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcReturnGoodsCtrl.class);

    @Resource
    private AfcReturnGoodsSvc   svc;

    @ApiOperation("退货-买家退货")
    @PostMapping("/returngoods/bybuyer")
    ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to) {
        _log.info("退货-买家退货： {}", to);
        return svc.returnGoodsByBuyer(to);
    }
}
