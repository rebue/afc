package rebue.afc.scheduler.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import rebue.afc.mo.VpayTradeTaskMo;
import rebue.afc.svr.feign.VpayTradeTaskSvc;

/**
 * 定时交易的任务
 */
@Component
public class TradeTask {
    private final static Logger _log = LoggerFactory.getLogger(TradeTask.class);

    @Resource
    private VpayTradeTaskSvc    vpayTradeTaskSvc;

    @Scheduled(fixedDelayString = "${afc.scheduler.tradeFixedDelay}")
    public void executeTask() {
        _log.info("定时执行需要交易的任务");
        try {
            _log.info("获取需要执行的任务列表");
            List<VpayTradeTaskMo> tasks = vpayTradeTaskSvc.getTasksThatShouldExecute();
            _log.info("需要执行的任务数有{}条", tasks.size());
            for (VpayTradeTaskMo taskMo : tasks) {
                try {
                    vpayTradeTaskSvc.executeTask(taskMo);
                } catch (RuntimeException e) {
                    _log.error("执行需要交易的任务出现异常: " + taskMo, e);
                }
            }
        } catch (RuntimeException e) {
            _log.error("获取需要执行的任务列表出现异常", e);
        }
    }

}
