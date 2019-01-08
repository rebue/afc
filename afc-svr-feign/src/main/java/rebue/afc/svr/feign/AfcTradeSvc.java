package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.OrgWithdrawRo;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcTradeSvc {

    /**
     * 添加一笔交易
     * 
     * @param mo
     */
    @PostMapping("/afc/trade/addex")
    void addTrade(@RequestBody AfcTradeMo mo);
    
    
    /**
     * 获取组织已经提现总额

     */
    @GetMapping("/afc/orgWithdrawTotal")
    OrgWithdrawRo getOrgWithdrawTotal(@RequestParam("accountId") Long accountId);
}
