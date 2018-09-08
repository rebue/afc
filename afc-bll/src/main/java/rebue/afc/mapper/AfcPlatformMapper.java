package rebue.afc.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import rebue.afc.mo.AfcPlatformMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcPlatformMapper extends MybatisBaseMapper<AfcPlatformMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcPlatformMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcPlatformMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcPlatformMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcPlatformMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcPlatformMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPlatformMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcPlatformMo> selectSelective(AfcPlatformMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcPlatformMo record);

    /**
     *  修改平台余额
     *
     *  @param newBalance
     *             修改后的余额
     *  @param newModifiedTimestamp
     *             修改的时间戳
     *  @param oldModifiedTimestamp
     *             旧的修改时间(用来防止并发修改)
     *  @param id
     *             要修改的平台的ID
     */
    @Update("update AFC_PLATFORM set BALANCE=#{newBalance},MODIFIED_TIMESTAMP=#{newModifiedTimestamp} " + "where ID=#{id} and BALANCE=#{oldBalance} and MODIFIED_TIMESTAMP=#{oldModifiedTimestamp}")
    int modifyBalance(@Param("newBalance") BigDecimal newBalance, @Param("newModifiedTimestamp") Long newModifiedTimestamp, @Param("oldBalance") BigDecimal oldBalance, @Param("oldModifiedTimestamp") Long oldModifiedTimestamp, @Param("id") Long id);
}
