package rebue.afc.svr.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import rebue.afc.mo.VpayTradeTaskMo;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface VpayTradeTaskSvc {
    /**
     * 获取将要执行的任务列表
     */
    @GetMapping("/task/shouldexecute")
    List<VpayTradeTaskMo> getTasksThatShouldExecute();
}