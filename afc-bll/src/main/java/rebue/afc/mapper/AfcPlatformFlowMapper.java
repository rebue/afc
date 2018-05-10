package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcPlatformFlowMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcPlatformFlowMapper extends MybatisBaseMapper<AfcPlatformFlowMo, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    int insert(AfcPlatformFlowMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    int insertSelective(AfcPlatformFlowMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    AfcPlatformFlowMo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    int updateByPrimaryKeySelective(AfcPlatformFlowMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    int updateByPrimaryKey(AfcPlatformFlowMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    List<AfcPlatformFlowMo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    List<AfcPlatformFlowMo> selectSelective(AfcPlatformFlowMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    boolean existByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_FLOW
     *
     * @mbg.generated 2018-05-10 11:15:22
     */
    boolean existSelective(AfcPlatformFlowMo record);
}