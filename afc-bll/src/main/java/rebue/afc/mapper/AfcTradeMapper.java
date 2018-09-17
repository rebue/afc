package rebue.afc.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rebue.afc.mo.AfcTradeMo;
import rebue.robotech.mapper.MybatisBaseMapper;

@Mapper
public interface AfcTradeMapper extends MybatisBaseMapper<AfcTradeMo, Long> {

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	int insert(AfcTradeMo record);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	int insertSelective(AfcTradeMo record);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	AfcTradeMo selectByPrimaryKey(Long id);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	int updateByPrimaryKeySelective(AfcTradeMo record);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	int updateByPrimaryKey(AfcTradeMo record);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	List<AfcTradeMo> selectAll();

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	List<AfcTradeMo> selectSelective(AfcTradeMo record);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	boolean existByPrimaryKey(Long id);

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	boolean existSelective(AfcTradeMo record);

	/**
	 * 计算同种交易同一订单的总金额
	 */
	BigDecimal getTotalAmountByTradeTypeAndOrder(@Param("tradeType") int tradeType, @Param("orderId") String orderId);

	/**
	 * 查询用户返现金交易信息
	 * 
	 * @param record
	 * @return
	 */
	List<AfcTradeMo> selectCashbackTrade(AfcTradeMo record);

	/**
	 * 查询用户余额交易信息
	 * 
	 * @param record
	 * @return
	 */
	List<AfcTradeMo> selectBalanceTrade(AfcTradeMo record);
}
