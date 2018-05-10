package rebue.afc.ctrl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTaskRo;
import rebue.afc.svc.AfcSettleTaskSvc;
import rebue.afc.to.AddSettleTaskTo;

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
     * 添加返款任务
     * 添加返款任务，任务调度器会定时检查当前要执行的任务
     */
    @PostMapping("/task/settle")
    AddSettleTaskRo addSettleTast(@RequestBody AddSettleTaskTo to) {
        _log.info("添加返款任务: {}", to);
        try {
            return svc.addSettleTask(to);
        } catch (DuplicateKeyException e) {
            String msg = "重复添加任务";
            _log.error("{}: {}", msg, to);
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.ALREADY_ADD);
            ro.setMsg(msg);
            return ro;
        }
    }

    /**
     * 获取将要执行的任务列表
     */
    @GetMapping("/task/shouldexecute")
    List<AfcSettleTaskMo> getTasksThatShouldExecute() {
        return svc.getTasksThatShouldExecute();
    }

    /**
     * 执行任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @PostMapping("/task/execute")
    void executeTask(@RequestBody AfcSettleTaskMo taskMo) {
        svc.executeTask(taskMo);
    }

}
