package rebue.afc.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.mo.AfcPlatformFlowMo;
import rebue.afc.ro.RebateRo;
import rebue.afc.svc.AfcRebateSvc;
import rebue.afc.to.RebateTo;

@Api(tags = "返款")
@RestController
public class AfcRebateCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcRebateCtrl.class);

    @Resource
    private AfcRebateSvc        svc;

    @ApiOperation("返款到供应商的余额")
    @PostMapping("/rebate/provider/balance")
    RebateRo rebateProviderBalance(RebateTo to) {
        _log.info("返款到供应商的余额： {}", to);
        return svc.rebateProviderBalance(to);
    }

    @ApiOperation("返款到买家的返现金")
    @PostMapping("/rebate/buyer/cashback")
    RebateRo rebateBuyerCashback(RebateTo to) {
        _log.info("返款到买家的返现金： {}", to);
        return svc.rebateBuyerCashback(to);
    }

    @ApiOperation("返款到加盟商的已占用保证金")
    @PostMapping("/rebate/seller/depositused")
    RebateRo rebateSellerDepositUsed(RebateTo to) {
        _log.info("返款到加盟商的已占用保证金： {}", to);
        return svc.rebateSellerDepositUsed(to);
    }

    @ApiOperation("返款到加盟商的余额")
    @PostMapping("/rebate/seller/balance")
    RebateRo rebateSellerBalance(RebateTo to) {
        _log.info("返款到加盟商的余额： {}", to);
        return svc.rebateSellerBalance(to);
    }

    /**
     * 返款至平台服务费
     * Title: rebatePlatformServiceFee
     * Description: 
     * @param mo
     * @return
     * @date 2018年5月3日 下午2:16:05
     */
    @PostMapping("/rebate/platform/depositused")
    Map<String, Object> rebatePlatformServiceFee(AfcPlatformFlowMo mo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			return svc.rebatePlatformServiceFee(mo);
		} catch (RuntimeException e) {
			String msg = e.getMessage();
			if (msg.equals("参数有误")) {
				resultMap.put("result", -1);
				resultMap.put("msg", msg);
			} else if (msg.equals("该订单未支付")) {
				resultMap.put("result", -2);
				resultMap.put("msg", msg);
			} else if (msg.equals("添加或修改平台信息失败")) {
				resultMap.put("result", -3);
				resultMap.put("msg", msg);
			} else if (msg.equals("添加平台流水信息出错")) {
				resultMap.put("result", -4);
				resultMap.put("msg", msg);
			} else {
				resultMap.put("result", -5);
				resultMap.put("msg", "返款至平台服务费失败");
			}
			return resultMap;
		}
    }
}
