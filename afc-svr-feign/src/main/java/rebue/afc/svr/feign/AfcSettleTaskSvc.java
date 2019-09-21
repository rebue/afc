package rebue.afc.svr.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.to.AddSettleTasksTo;
import rebue.afc.to.TaskTo;
import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

/**
 * @deprecated
 */
@Deprecated
@FeignClient(name = "afc-svr", configuration = FeignConfig.class, contextId = "afc-svr-settle-task")
public interface AfcSettleTaskSvc {
    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    @PostMapping("/settle/tasks")
    AddSettleTasksRo addSettleTasks(@RequestBody AddSettleTasksTo to);

    /**
     * 获取将要执行的结算任务列表
     */
    @GetMapping("/settle/task/shouldexecute")
    List<Long> getTaskIdsThatShouldExecute();

    /**
     * 执行结算任务
     * 
     * @param taskMo
     *               要执行的任务
     */
    @PostMapping("/settle/task/execute")
    void executeTask(@RequestParam("id") Long id);

    /**
     * 暂停任务
     */
    @PostMapping("/settle/task/suspend")
    Ro suspendTask(@RequestBody TaskTo to);

    /**
     * 恢复任务
     */
    @PostMapping("/settle/task/resume")
    Ro resumeTask(@RequestBody TaskTo to);

    /**
     * 取消任务
     */
    @PostMapping("/settle/task/cancel")
    Ro cancelTask(@RequestBody TaskTo to);

//    /**
//     * 获取用户的待返现任务
//     */
//    @GetMapping("/settle/task/cashbacktask")
//    List<AfcSettleTaskMo> getCashBackTask(@RequestParam("accountId") long accountId, @RequestParam("executestate") byte executestate, @RequestParam("tradtype") byte tradtype,
//            @RequestParam("pageNum") byte pageNum, @RequestParam("pageSize") byte pageSize);

}