package rebue.afc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import rebue.afc.mo.AfcTradeMo;
import rebue.robotech.mapper.MybatisBaseMapper;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AfcTradeMapper extends MybatisBaseMapper<AfcTradeMo, Long> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int insert(AfcTradeMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int insertSelective(AfcTradeMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	AfcTradeMo selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int updateByPrimaryKeySelective(AfcTradeMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	int updateByPrimaryKey(AfcTradeMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	List<AfcTradeMo> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	List<AfcTradeMo> selectSelective(AfcTradeMo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	boolean existByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table AFC_TRADE
	 *
	 * @mbg.generated 2018-05-26 19:47:00
	 */
	boolean existSelective(AfcTradeMo record);

	/**
	 * 计算同种交易同一订单的总金额
	 */
	BigDecimal getTotalAmountByTradeTypeAndOrder(
			@Param("tradeType") int tradeType, @Param("orderId") String orderId);
}