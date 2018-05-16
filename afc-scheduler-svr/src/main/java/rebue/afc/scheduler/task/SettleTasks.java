package rebue.afc.scheduler.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import rebue.afc.svr.feign.AfcSettleTaskSvc;

/**
 * 定时结算的任务
 */
@Component
public class SettleTasks {
    private final static Logger _log = LoggerFactory.getLogger(SettleTasks.class);

    @Resource
    private AfcSettleTaskSvc    taskSvc;

    @Scheduled(fixedDelayString = "${afc.scheduler.tradeFixedDelay}")
    public void executeTasks() {
        _log.info("定时执行需要结算的任务");
        try {
            _log.info("获取需要执行的任务列表");
            List<Long> tasks = taskSvc.getTaskIdsThatShouldExecute();
            _log.info("需要执行的任务数有{}条", tasks.size());
            for (Long id : tasks) {
                try {
                    taskSvc.executeTask(id);
                } catch (RuntimeException e) {
                    _log.error("执行需要结算的任务出现异常: " + id, e);
                }
            }
        } catch (RuntimeException e) {
            _log.error("获取需要执行的任务列表出现异常", e);
        }
    }

}
