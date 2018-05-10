package rebue.afc.svc;

import java.util.List;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.ro.AddSettleTaskRo;
import rebue.afc.to.AddSettleTaskTo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcSettleTaskSvc extends MybatisBaseSvc<AfcSettleTaskMo, java.lang.Long> {
    /**
     * 添加交易任务
     */
    AddSettleTaskRo addSettleTask(AddSettleTaskTo to);

    /**
     * 获取要执行的任务列表
     */
    List<AfcSettleTaskMo> getTasksThatShouldExecute();

    /**
     * 执行任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    void executeTask(AfcSettleTaskMo taskMo);
}