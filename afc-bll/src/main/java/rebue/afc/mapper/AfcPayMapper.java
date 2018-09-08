package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rebue.afc.mo.AfcPayMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcPayMapper extends MybatisBaseMapper<AfcPayMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcPayMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcPayMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcPayMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcPayMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcPayMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPayMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPayMo> selectSelective(AfcPayMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcPayMo record);

    /**
     */
    public AfcPayMo selectByPayTypeAndOrderId(@Param("payTypeId") Integer payTypeId, @Param("orderId") String orderId);
}
