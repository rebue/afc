package rebue.afc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import rebue.afc.mo.AfcSettleTaskMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcSettleTaskMapper extends MybatisBaseMapper<AfcSettleTaskMo, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    int insert(AfcSettleTaskMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    int insertSelective(AfcSettleTaskMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    AfcSettleTaskMo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    int updateByPrimaryKeySelective(AfcSettleTaskMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    int updateByPrimaryKey(AfcSettleTaskMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    List<AfcSettleTaskMo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    List<AfcSettleTaskMo> selectSelective(AfcSettleTaskMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    boolean existByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_SETTLE_TASK
     *
     * @mbg.generated 2018-05-10 15:25:05
     */
    boolean existSelective(AfcSettleTaskMo record);

    /**
     * 获取计划执行时间在当前时间之前的任务列表
     */
    List<AfcSettleTaskMo> selectByExecutePlanTimeBeforeNow();

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
     * @return
     */
    @Update("update AFC_SETTLE_TASK set EXECUTE_STATE=#{doneState}, EXECUTE_FACT_TIME=#{now} where ID=#{id} and EXECUTE_STATE=#{noneState}")
    int done(@Param("now") Date now, @Param("id") Long id, @Param("doneState") Byte doneState, @Param("noneState") Byte noneState);
}