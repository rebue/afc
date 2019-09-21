package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-platform-trade")
public interface AfcPlatformTradeTradeSvc {

    /**
     * 添加一笔平台交易
     */
    @PostMapping("/afc/platformtrade/addex")
    void addTrade(@RequestBody AfcPlatformTradeMo mo);

}
