package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.ro.WithdrawNumberForMonthRo;
import rebue.afc.to.ApplyWithdrawTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.ro.IdRo;
import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-withdraw")

public interface AfcWithdrawSvc {

    @PostMapping("/withdraw/apply")
    IdRo apply(@RequestBody ApplyWithdrawTo to);

    /**
     * 查询当前月提现次数
     * 
     * @param mo
     * @return
     */
    @GetMapping("/withdraw/get-withdraw-number-for-month")
    WithdrawNumberForMonthRo getWithdrawNumberForMonth(@RequestBody AfcWithdrawMo mo);

    /**
     * 
     * @param 确认提现成功
     * @return
     */
    @PutMapping("/withdraw/ok")
    Ro ok(@RequestBody WithdrawOkTo to);

}
