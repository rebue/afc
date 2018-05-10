package rebue.afc.svr.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcSettleTaskSvc {
    /**
     * 获取将要执行的任务列表
     */
    @GetMapping("/task/shouldexecute")
    List<Long> getTaskIdsThatShouldExecute();

    /**
     * 执行任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @PostMapping("/task/execute")
    void executeTask(@RequestParam("id") Long id);

}