package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcWithdrawMapper extends MybatisBaseMapper<AfcWithdrawMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcWithdrawMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcWithdrawMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcWithdrawMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcWithdrawMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcWithdrawMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawMo> selectSelective(AfcWithdrawMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcWithdrawMo record);
}
