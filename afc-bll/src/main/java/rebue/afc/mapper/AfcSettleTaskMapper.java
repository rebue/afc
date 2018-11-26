package rebue.afc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcSettleTaskMapper extends MybatisBaseMapper<AfcSettleTaskMo, Long> {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    int insert(AfcSettleTaskMo record);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    int insertSelective(AfcSettleTaskMo record);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    AfcSettleTaskMo selectByPrimaryKey(Long id);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    int updateByPrimaryKeySelective(AfcSettleTaskMo record);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    int updateByPrimaryKey(AfcSettleTaskMo record);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    List<AfcSettleTaskMo> selectAll();

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    List<AfcSettleTaskMo> selectSelective(AfcSettleTaskMo record);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    boolean existByPrimaryKey(Long id);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    boolean existSelective(AfcSettleTaskMo record);

    /**
     * 获取计划执行时间在当前时间之前的任务列表
     */
    @Select("select ID from AFC_SETTLE_TASK where EXECUTE_STATE=0 and EXECUTE_PLAN_TIME<=SYSDATE()")
    List<Long> selectByExecutePlanTimeBeforeNow();

    /**
     * 执行任务完成
     *
     * @param now
     *            当前时间
     * @param id
     *            任务ID
     * @param doneState
     *            已执行状态
     * @param noneState
     *            未执行状态
     */
    @Update("update AFC_SETTLE_TASK set EXECUTE_STATE=#{doneState}, EXECUTE_FACT_TIME=#{now} where ID=#{id} and EXECUTE_STATE=#{noneState}")
    int done(@Param("now") Date now, @Param("id") Long id, @Param("doneState") Byte doneState, @Param("noneState") Byte noneState);

    /**
     * 改变任务执行状态
     *
     * @param tradeType
     *            交易类型
     * @param orderDetailId
     *            订单详情ID
     * @param beforeState
     *            执行之前的状态
     * @param afterState
     *            执行之后的状态
     */
    @Update("update AFC_SETTLE_TASK set EXECUTE_STATE=#{afterState} where TRADE_TYPE=#{tradeType} and ORDER_DETAIL_ID=#{orderDetailId} and EXECUTE_STATE=#{beforeState}")
    int updateExecuteState(@Param("tradeType") Integer tradeType, @Param("orderDetailId") String orderDetailId, @Param("beforeState") Integer beforeState,
            @Param("afterState") Integer afterState);

    /**
     * 取消任务
     *
     * @param tradeType
     *            交易类型
     * @param orderDetailId
     *            订单详情ID
     * @param beforeState
     *            执行之前的状态
     * @param cancelState
     *            取消状态
     */
    @Update("update AFC_SETTLE_TASK set EXECUTE_STATE=#{cancelState} where ID=#{id} and EXECUTE_STATE=#{beforeState}")
    int cancelTask(@Param("id") Long id, @Param("beforeState") Byte beforeState, @Param("cancelState") Integer cancelState);

    /**
     * 订单是否已经结算完成
     *
     * @param orderId
     *            销售订单ID
     */
    @Select("select CASE WHEN COUNT(*) > 0 THEN FALSE ELSE TRUE END from AFC_SETTLE_TASK where ORDER_ID=#{orderId} and EXECUTE_STATE!=#{doneState}")
    Boolean isSettleCompleted(@Param("orderId") String orderId, @Param("doneState") Byte doneState);
}
