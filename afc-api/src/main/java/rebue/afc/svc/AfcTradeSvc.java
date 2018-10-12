package rebue.afc.svc;

import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import rebue.afc.mo.AfcTradeMo;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 账户交易(账户交易流水)
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcTradeSvc extends MybatisBaseSvc<AfcTradeMo, java.lang.Long> {

    /**
     *  添加一笔交易记录 1. 添加交易记录 2. 修改账户相应的金额字段 3. 添加账户流水
     */
    void addTrade(AfcTradeMo tradeMo);

    /**
     *  计算此订单的退款总额(通过销售订单ID)
     */
    BigDecimal getRefundTotalAmountByOrderId(String saleOrderId);

    /**
     *  查询用户返现金交易信息
     *
     *  @param qo
     *  @param pageNum
     *  @param pageSize
     *  @param orderBy
     *  @return
     */
    PageInfo<AfcTradeMo> cashbackTradeList(AfcTradeMo qo, int pageNum, int pageSize, String orderBy);

    /**
     *  查询用户余额交易信息
     *
     *  @param qo
     *  @param pageNum
     *  @param pageSize
     *  @param orderBy
     *  @return
     */
    PageInfo<AfcTradeMo> balanceTradeList(AfcTradeMo qo, int pageNum, int pageSize, String orderBy);
}
