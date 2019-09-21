package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.afc.mo.AfcAccountMo;
import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-account")
public interface AfcAccountSvc {

    /**
     * 获取单个账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/account/getbyid")
    AfcAccountMo getById(@RequestParam("id") final java.lang.Long id);

    /**
     * 修改账户信息
     * 
     * @param mo
     * @return
     */
    @PutMapping("/afc/account")
    Ro modify(@RequestBody final AfcAccountMo mo);
}
