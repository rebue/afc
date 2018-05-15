package rebue.afc.svr.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.afc.ro.AddSettleTaskRo;
import rebue.afc.to.AddSettleTaskTo;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcSettleTaskSvc {
    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    @PostMapping("/settle/tasks")
    AddSettleTaskRo addSettleTask(@RequestBody AddSettleTaskTo to);

    /**
     * 获取将要执行的结算任务列表
     */
    @GetMapping("/settle/task/shouldexecute")
    List<Long> getTaskIdsThatShouldExecute();

    /**
     * 执行结算任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @PostMapping("/settle/task/execute")
    void executeTask(@RequestParam("id") Long id);

}