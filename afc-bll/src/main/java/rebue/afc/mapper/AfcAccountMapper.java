package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.robotech.mapper.MybatisBaseMapper;
import java.util.HashMap;

@Mapper
public interface AfcAccountMapper extends MybatisBaseMapper<AfcAccountMo, Long> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int insert(AfcAccountMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int insertSelective(AfcAccountMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	AfcAccountMo selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int updateByPrimaryKeySelective(AfcAccountMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int updateByPrimaryKey(AfcAccountMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	List<AfcAccountMo> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	List<AfcAccountMo> selectSelective(AfcAccountMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	boolean existByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_ACCOUNT
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	boolean existSelective(AfcAccountMo record);

	/**
	 * 产生了一笔交易(影响余额/返现金/货款)，修改相应的字段及记录时间戳
	 */
	int modifyAmount(HashMap<String, ?> map);
}