package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.afc.mo.AfcPlatformMo;
import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-platform")
public interface AfcPlatformSvc {

    /**
     * 获取单个平台信息
     */
    @GetMapping("/afc/platform/getbyid")
    AfcPlatformMo getById(@RequestParam("id") java.lang.Long id);

    /**
     * 修改平台信息
     */
    @PutMapping("/afc/platform")
    Ro modify(@RequestBody AfcPlatformMo mo);
}
