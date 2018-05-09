package rebue.afc.returngoods.ctrl;

import java.util.HashMap;
import java.util.Map;

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
    @PostMapping("/returngoods/tobuyer")
    ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to) {
        _log.info("退货-买家退货： {}", to);
        return svc.returnGoodsByBuyer(to);
    }
    
    /**
     * 用户退货退款并扣减返现金额
     * Title: returnRefundAndSubtractCashback
     * Description: 
     * @param to
     * @return
     * @date 2018年5月9日 上午9:32:48
     */
	@PostMapping("/returngoods/and/subtractcashback/tobuyer")
    Map<String, Object> returnRefundAndSubtractCashback(ReturnGoodsByBuyerTo to) {
    	_log.info("用户退货退款并扣减返现金额的参数为：{}", to.toString());
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			return svc.returnRefundAndSubtractCashback(to);
		} catch (RuntimeException e) {
			String msg = e.getMessage();
			if (msg.equals("买家退货重复提交")) {
				resultMap.put("result", -8);
				resultMap.put("msg", msg);
			} else {
				resultMap.put("result", -9);
				resultMap.put("msg", "买家退货退款失败");
			}
			return resultMap;
		}
    }
}
