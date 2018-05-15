package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcWithdrawMapper extends MybatisBaseMapper<AfcWithdrawMo, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    int insert(AfcWithdrawMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    int insertSelective(AfcWithdrawMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    AfcWithdrawMo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    int updateByPrimaryKeySelective(AfcWithdrawMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    int updateByPrimaryKey(AfcWithdrawMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    List<AfcWithdrawMo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    List<AfcWithdrawMo> selectSelective(AfcWithdrawMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    boolean existByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_WITHDRAW
     *
     * @mbg.generated 2018-05-15 15:43:27
     */
    boolean existSelective(AfcWithdrawMo record);
}