package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;
import rebue.afc.ro.PayRo;
import rebue.afc.ro.PrepayRo;
import rebue.afc.vpay.to.AfcVpayPayTo;
import rebue.afc.vpay.to.AfcVpayPrepayTo;
import rebue.sbs.feign.FeignConfig;

/**
 * 创建时间：2018年5月10日 上午10:36:51
 * 项目名称：afc-svr-feign
 * 
 * @author daniel
 * @version 1.0
 * @since JDK 1.8
 *        文件名称：AfcVpaySvc.java
 *        类说明： v支付支付內部接口
 */
@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-v-pay")
public interface AfcVpaySvc {

    @ApiOperation("V支付-支付")
    @PostMapping("/vpay/pay")
    PayRo pay(@RequestBody AfcVpayPayTo to);

    @ApiOperation("V支付-预支付")
    @PostMapping("/vpay/prepay")
    PrepayRo prepay(@RequestBody AfcVpayPrepayTo to);
}
