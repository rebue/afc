package rebue.afc.svc;

import java.util.List;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTaskRo;
import rebue.afc.to.AddSettleTaskTo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcSettleTaskSvc extends MybatisBaseSvc<AfcSettleTaskMo, java.lang.Long> {
    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    AddSettleTaskRo addSettleTask(AddSettleTaskTo to);

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
}