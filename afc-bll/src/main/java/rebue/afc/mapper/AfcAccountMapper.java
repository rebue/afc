package rebue.afc.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcAccountMapper extends MybatisBaseMapper<AfcAccountMo, Long> {

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int deleteByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insert(AfcAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int insertSelective(AfcAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    AfcAccountMo selectByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKeySelective(AfcAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    int updateByPrimaryKey(AfcAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcAccountMo> selectAll();

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    List<AfcAccountMo> selectSelective(AfcAccountMo record);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existByPrimaryKey(Long id);

    /**
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    boolean existSelective(AfcAccountMo record);

    /**
     *  产生了一笔交易(影响余额/返现金/货款)，修改相应的字段及记录时间戳
     */
    int modifyAmount(HashMap<String, ?> map);
}
