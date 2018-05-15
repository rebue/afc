package rebue.afc.ctrl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.svc.AfcSettleTaskSvc;
import rebue.afc.to.AddSettleTasksTo;

/**
 * 交易任务
 * 
 * @author zbz
 *
 */
@RestController
public class AfcSettleTaskCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcSettleTaskCtrl.class);

    @Resource
    private AfcSettleTaskSvc    svc;

    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    @PostMapping("/settle/tasks")
    AddSettleTasksRo addSettleTasks(@RequestBody AddSettleTasksTo to) {
        _log.info("添加结算任务: {}", to);
        try {
            return svc.addSettleTasks(to);
        } catch (DuplicateKeyException e) {
            String msg = "重复添加任务";
            _log.error("{}: {}", msg, to);
            AddSettleTasksRo ro = new AddSettleTasksRo();
            ro.setResult(AddSettleTaskResultDic.ALREADY_ADD);
            ro.setMsg(msg);
            return ro;
        } catch (Exception e) {
            String msg = "添加任务失败-" + e.getMessage();
            _log.error(msg + ":" + to, e);
            AddSettleTasksRo ro = new AddSettleTasksRo();
            ro.setResult(AddSettleTaskResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }
    }

    /**
     * 获取将要执行的结算任务列表
     */
    @GetMapping("/settle/task/shouldexecute")
    List<Long> getTasksThatShouldExecute() {
        return svc.getTaskIdsThatShouldExecute();
    }

    /**
     * 执行结算任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @PostMapping("/settle/task/execute")
    void executeTask(@RequestParam("id") Long id) {
        svc.executeTask(id);
    }

}
