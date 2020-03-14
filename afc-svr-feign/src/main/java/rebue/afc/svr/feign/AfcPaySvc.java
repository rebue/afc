package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-pay")
public interface AfcPaySvc {

    /**
     * 获取单个账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/pay/deleteByOrderId")
    Ro delatePay(@RequestParam("orderId") final java.lang.Long orderId);


}
