package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcFlowMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcFlowMapper extends MybatisBaseMapper<AfcFlowMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcFlowMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcFlowMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcFlowMo> selectSelective(AfcFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcFlowMo record);
}
