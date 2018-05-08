package rebue.afc.vpay.ctrl;

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

import rebue.afc.mo.VpayTradeTaskMo;
import rebue.afc.vpay.dic.AddRebateTaskResultDic;
import rebue.afc.vpay.ro.AddRebateTaskRo;
import rebue.afc.vpay.svc.VpayTradeTaskSvc;
import rebue.afc.vpay.to.AddRebateTaskTo;

/**
 * 交易任务
 * 
 * @author zbz
 *
 */
@RestController
public class VpayTradeTaskCtrl {
    private final static Logger _log = LoggerFactory.getLogger(VpayTradeTaskCtrl.class);

    @Resource
    private VpayTradeTaskSvc    svc;

    /**
     * 添加返款任务
     * 添加返款任务，任务调度器会定时检查当前要执行的任务
     */
    @PostMapping("/task/rebate")
    AddRebateTaskRo addRebateTast(@RequestBody AddRebateTaskTo to) {
        _log.info("添加返款任务: {}", to);
        try {
            return svc.addRebateTast(to);
        } catch (DuplicateKeyException e) {
            String msg = "重复添加任务";
            _log.error("{}: {}", msg, to);
            AddRebateTaskRo ro = new AddRebateTaskRo();
            ro.setResult(AddRebateTaskResultDic.ALREADY_ADD);
            ro.setMsg(msg);
            return ro;
        }
    }

    /**
     * 获取将要执行的任务列表
     */
    @GetMapping("/task/shouldexecute")
    List<VpayTradeTaskMo> getTasksThatShouldExecute() {
        return svc.getTasksThatShouldExecute();
    }

    /**
     * 执行任务
     * 
     * @param id
     *            执行任务的ID
     */
    @PostMapping("/task/execute")
    Boolean executeTask(@RequestParam("id") Long id) {
        return svc.executeTask(id);
    }

}
