package rebue.afc.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.svc.AfcSettleTaskSvc;
import rebue.afc.to.AddSettleTasksTo;
import rebue.afc.to.GetCashBackTaskTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;

/**
 * 交易任务
 *
 * @author zbz
 */
@RestController
public class AfcSettleTaskCtrl {

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String _uniqueFilesName = "某字段内容";

    /**
     * 添加结算任务
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/settletask")
    Ro add(@RequestBody AfcSettleTaskMo mo) throws Exception {
        _log.info("add AfcSettleTaskMo:" + mo);
        Ro ro = new Ro();
        try {
            int result = svc.add(mo);
            if (result == 1) {
                String msg = "添加成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                String msg = "添加失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (DuplicateKeyException e) {
            String msg = "添加失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (RuntimeException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 修改结算任务
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/settletask")
    Ro modify(@RequestBody AfcSettleTaskMo mo) throws Exception {
        _log.info("modify AfcSettleTaskMo:" + mo);
        Ro ro = new Ro();
        try {
            if (svc.modify(mo) == 1) {
                String msg = "修改成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                String msg = "修改失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (DuplicateKeyException e) {
            String msg = "修改失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (RuntimeException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 删除结算任务
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/settletask")
    Ro del(@RequestParam("id") java.lang.Long id) {
        _log.info("save AfcSettleTaskMo:" + id);
        int result = svc.del(id);
        Ro ro = new Ro();
        if (result == 1) {
            String msg = "删除成功";
            _log.info("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.SUCCESS);
            return ro;
        } else {
            String msg = "删除失败，找不到该记录";
            _log.error("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 查询结算任务
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/settletask")
    PageInfo<AfcSettleTaskMo> list(AfcSettleTaskMo mo, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("list AfcSettleTaskMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        PageInfo<AfcSettleTaskMo> result = svc.list(mo, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个结算任务
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/settletask/getbyid")
    AfcSettleTaskMo getById(@RequestParam("id") java.lang.Long id) {
        _log.info("get AfcSettleTaskMo by id: " + id);
        return svc.getById(id);
    }

    private static final Logger _log = LoggerFactory.getLogger(AfcSettleTaskCtrl.class);

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
    List<Long> getTaskIdsThatShouldExecute() {
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

    /**
     * 获取用户的待返现任务
     */
    @GetMapping("/settle/task/cashbacktask")
    List<AfcSettleTaskMo> getCashBackTask(GetCashBackTaskTo to) {
        return svc.getCashBackTask(to);
    }
}
