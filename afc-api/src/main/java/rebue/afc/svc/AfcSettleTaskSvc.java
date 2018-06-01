package rebue.afc.svc;

import java.util.List;
import java.util.Map;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.to.AddSettleTasksTo;
import rebue.afc.to.GetCashBackTaskTo;
import rebue.robotech.svc.MybatisBaseSvc;

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
     * 订单是否已经结算完成
     * 
     * @param orderId
     *            销售订单ID
     */
    Boolean isSettleCompleted(String orderId);
    
    /**
     * 获取用户的待返现任务
     */
    List<AfcSettleTaskMo> getCashBackTask(GetCashBackTaskTo to);
    
}