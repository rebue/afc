package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcWithdrawAccountMapper extends MybatisBaseMapper<AfcWithdrawAccountMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcWithdrawAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcWithdrawAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcWithdrawAccountMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcWithdrawAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcWithdrawAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawAccountMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawAccountMo> selectSelective(AfcWithdrawAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcWithdrawAccountMo record);
}
