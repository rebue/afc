package rebue.afc.svc.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.platform.dic.PlatformTradeTypeDic;
import rebue.afc.platform.svc.AfcPlatformSvc;
import rebue.afc.platform.svc.AfcPlatformTradeSvc;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcSettleSvc;
import rebue.afc.svc.AfcTradeSvc;

/**
 * 处理结算相关的业务
 */
@Service
/**
 * <pre>
 * 在单独使用不带任何参数 的 @Transactional 注释时，
 * propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED，
 * 而且事务不会针对受控异常（checked exception）回滚。
 * 注意：
 * 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 * </pre>
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AfcSettleSvcImpl implements AfcSettleSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcSettleSvcImpl.class);

    @Resource
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcFlowSvc          flowSvc;
    @Resource
    private AfcPlatformSvc      platformSvc;
    @Resource
    private AfcPlatformTradeSvc platformTradeSvc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * 结算此账户的费用
     * 将交易结算的费用打到相应的账户
     * 
     * @param taskMo
     *            要执行的结算任务
     * @param now
     *            当前时间
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void settleAccountFee(final AfcSettleTaskMo taskMo, final Date now) {
        _log.info("结算此账户的费用: {}", taskMo);

        // 添加一笔交易
        final AfcTradeMo tradeMo = dozerMapper.map(taskMo, AfcTradeMo.class);
        // tradeMo.setId(taskMo.getId());已克隆过来，交易ID=任务ID，防止一个任务多次结算
        tradeMo.setTradeTime(now);
        tradeMo.setOpId(0L);                    // 操作人设为0表示系统自动产生的交易
        tradeSvc.addTrade(tradeMo);
    }

    /**
     * 结算平台服务费
     * 将交易结算的服务费打到平台的余额
     * 
     * @param orderId
     *            销售订单ID
     * @param serviceFee
     *            服务费
     * @param now
     *            当前时间
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void settlePlatformServiceFee(final AfcSettleTaskMo taskMo, final Date now) {
        _log.info("结算平台服务费: ", taskMo);

        // 添加一笔平台交易
        final AfcPlatformTradeMo tradeMo = dozerMapper.map(taskMo, AfcPlatformTradeMo.class);
        tradeMo.setPlatformTradeType((byte) PlatformTradeTypeDic.CHARGE_SEVICE_FEE.getCode());  // 交易类型（1：收取服务费(购买交易成功) 2：退回服务费(用户退款)）
        tradeMo.setModifiedTimestamp(now.getTime());                                            // 修改时间戳
        platformTradeSvc.addTrade(tradeMo);      // 如果重复提交，会抛出DuplicateKeyException运行时异常
    }

    /**
     * 取消任务，需要补偿已经执行的结算
     * 
     * @param canceledTask
     *            被取消的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void compensateCanceledSettle(final AfcSettleTaskMo canceledTask) {
        // 判断交易类型
        switch (TradeTypeDic.getItem(canceledTask.getTradeType())) {
        // 取消返佣任务
        case SETTLE_COMMISSION:
            _log.info("添加一笔扣除返佣中金额的交易: {}", canceledTask);
            final AfcTradeMo tradeMo = dozerMapper.map(canceledTask, AfcTradeMo.class);
            tradeMo.setId(null);                    // 不能克隆取消任务的ID过来
            tradeMo.setTradeTitle("退款补偿扣减返佣中金额");
            tradeMo.setTradeDetail(canceledTask.getTradeDetail());
            tradeMo.setTradeType((byte) TradeTypeDic.COMPENDSATE_SUBTRACT_COMMISSIONING.getCode());
            tradeMo.setTradeTime(new Date());
            tradeMo.setOpId(0L);                    // 操作人设为0表示系统自动产生的交易
            tradeSvc.addTrade(tradeMo);
            break;
        default:
            _log.info("不用补偿的交易类型");
        }
    }

}
