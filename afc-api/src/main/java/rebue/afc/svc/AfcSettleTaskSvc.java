package rebue.afc.svc;

import java.util.List;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.to.AddSettleTasksTo;
import rebue.afc.to.TaskTo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 结算任务
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcSettleTaskSvc extends MybatisBaseSvc<AfcSettleTaskMo, java.lang.Long> {

    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    AddSettleTasksRo addSettleTasks(AddSettleTasksTo to);

    /**
     * 获取要执行的任务ID列表
     */
    List<Long> getTaskIdsThatShouldExecute();

    /**
     * 执行任务
     *
     * @param id
     *            要执行的任务ID
     */
    void executeTask(Long id);

    /**
     * 暂停任务
     */
    Ro suspendTask(TaskTo to);

    /**
     * 恢复任务
     */
    Ro resumeTask(TaskTo to);

    /**
     * 取消任务
     */
    Ro cancelTask(TaskTo to);

    /**
     * 订单是否已经结算完成
     *
     * @param orderId
     *            销售订单ID
     */
    Boolean isSettleCompleted(String orderId);

//    /**
//     * 获取用户的待返现任务
//     */
//    List<AfcSettleTaskMo> getCashBackTask(GetCashBackTaskTo to);
}
