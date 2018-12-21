package rebue.afc.svc;

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.AfcTradeListRo;
import rebue.afc.to.AfcTradeTo;
import rebue.afc.to.GetAfcTradeTo;
import rebue.robotech.svc.MybatisBaseSvc;

/**
 * 账户交易(账户交易流水)
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
public interface AfcTradeSvc extends MybatisBaseSvc<AfcTradeMo, java.lang.Long> {

    /**
     * 添加一笔交易记录 1. 添加交易记录 2. 修改账户相应的金额字段 3. 添加账户流水
     */
    void addTrade(AfcTradeMo tradeMo);

//    /**
//     * 计算此订单的退款总额(通过订单ID)
//     */
//    BigDecimal getRefundedTotalByOrderId(String orderId);

    /**
     * 查询用户返现金交易信息
     *
     * @param qo
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    PageInfo<AfcTradeMo> listCashbackTrade(AfcTradeMo qo, int pageNum, int pageSize, String orderBy);

    /**
     * 查询用户余额交易信息
     *
     * @param qo
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    PageInfo<AfcTradeMo> listBalanceTrade(AfcTradeMo qo, int pageNum, int pageSize, String orderBy);
    
    /**
     * 查找个人账号交易流水
     */
    PageInfo<AfcTradeListRo> personTradeList(AfcTradeTo to, int pageNum, int pageSize);
    
    /**
     * 查找组织账号交易流水
     */
    PageInfo<AfcTradeListRo> orgTradeList(AfcTradeTo to, int pageNum, int pageSize);
    
    /**
     * 获取组织账户交易流水
     */
    PageInfo<AfcTradeMo> getOrgTradeList(GetAfcTradeTo to, int pageNum, int pageSize);
    
   
}
