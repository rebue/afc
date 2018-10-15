package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcWithdrawAccountBindFlowMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcWithdrawAccountBindFlowMapper extends MybatisBaseMapper<AfcWithdrawAccountBindFlowMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcWithdrawAccountBindFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcWithdrawAccountBindFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcWithdrawAccountBindFlowMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcWithdrawAccountBindFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcWithdrawAccountBindFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawAccountBindFlowMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcWithdrawAccountBindFlowMo> selectSelective(AfcWithdrawAccountBindFlowMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcWithdrawAccountBindFlowMo record);
    
    /**
     * 根据申请人id查询最新的一条申请记录
     * @param applicantId
     * @return
     */
    AfcWithdrawAccountBindFlowMo selectNewOneByApplicantId(Long applicantId);
    
    /**
     * 拒绝审核
     * @param record
     * @return
     */
    int rejectReview(AfcWithdrawAccountBindFlowMo record);
}
