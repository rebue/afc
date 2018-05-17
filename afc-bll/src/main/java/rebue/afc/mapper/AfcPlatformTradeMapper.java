package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcPlatformTradeMapper extends MybatisBaseMapper<AfcPlatformTradeMo, Long> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    int insert(AfcPlatformTradeMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    int insertSelective(AfcPlatformTradeMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    AfcPlatformTradeMo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    int updateByPrimaryKeySelective(AfcPlatformTradeMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    int updateByPrimaryKey(AfcPlatformTradeMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    List<AfcPlatformTradeMo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    List<AfcPlatformTradeMo> selectSelective(AfcPlatformTradeMo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    boolean existByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AFC_PLATFORM_TRADE
     *
     * @mbg.generated 2018-05-17 10:57:31
     */
    boolean existSelective(AfcPlatformTradeMo record);
}