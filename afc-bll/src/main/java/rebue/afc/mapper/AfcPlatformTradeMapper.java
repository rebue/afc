package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcPlatformTradeMapper extends MybatisBaseMapper<AfcPlatformTradeMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcPlatformTradeMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcPlatformTradeMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcPlatformTradeMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcPlatformTradeMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcPlatformTradeMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPlatformTradeMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPlatformTradeMo> selectSelective(AfcPlatformTradeMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcPlatformTradeMo record);
}
