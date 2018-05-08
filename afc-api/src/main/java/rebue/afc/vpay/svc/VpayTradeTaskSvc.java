package rebue.afc.vpay.svc;

import java.util.List;

import rebue.afc.mo.VpayTradeTaskMo;
import rebue.afc.vpay.ro.AddRebateTaskRo;
import rebue.afc.vpay.to.AddRebateTaskTo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface VpayTradeTaskSvc extends MybatisBaseSvc<VpayTradeTaskMo, java.lang.Long> {
    /**
     * 添加交易任务
     */
    AddRebateTaskRo addRebateTast(AddRebateTaskTo to);

    /**
     * 获取将要执行的任务列表
     */
    List<VpayTradeTaskMo> getTasksThatShouldExecute();

    /**
     * 执行任务
     * 
     * @param id
     *            执行任务的ID
     */
    Boolean executeTask(Long id);
}