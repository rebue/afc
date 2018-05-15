package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.dic.SettleTaskExecuteStateDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcSettleTaskMapper;
import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.pub.SettleNotifyPub;
import rebue.afc.ro.AddSettleTaskRo;
import rebue.afc.ro.SettleNotifyRo;
import rebue.afc.svc.AfcSettleSvc;
import rebue.afc.svc.AfcSettleTaskSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.AddSettleTaskTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.svr.feign.SucUserSvc;

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
public class AfcSettleTaskSvcImpl extends MybatisBaseSvcImpl<AfcSettleTaskMo, java.lang.Long, AfcSettleTaskMapper> implements AfcSettleTaskSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcSettleTaskSvcImpl.class);

    @Resource
    private AfcSettleTaskSvc    thisSvc;
    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcSettleSvc        settleSvc;

    @Resource
    private SettleNotifyPub     settleNotifyPub;

    @Resource
    private Mapper              dozerMapper;

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcSettleTaskMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 添加结算任务
     * 任务调度器会定时检查当前要执行的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AddSettleTaskRo addSettleTask(AddSettleTaskTo to) throws DuplicateKeyException {
        _log.info("添加结算任务: {}", to);
        if (to.getBuyerAccountId() == null || to.getSellerAccountId() == null || to.getSettleBuyerCashbackAmount() == null || to.getSettleBuyerCashbackTime() == null
                || StringUtils.isAnyBlank(to.getSettleBuyerCashbackTitle(), to.getOrderId(), to.getOrderDetailId(), to.getMac(), to.getIp())) {
            String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }

        // 如果要结算返现
        if (to.getSettleBuyerCashbackAmount().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("添加结算返现中金额的任务");
            AfcSettleTaskMo taskMo = dozerMapper.map(to, AfcSettleTaskMo.class);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_CASHBACKING.getCode());
            taskMo.setExecutePlanTime(new Date());                                      // 计划执行时间（立即执行）
            taskMo.setAccountId(to.getBuyerAccountId());                                // 买家的账户ID
            taskMo.setTradeAmount(to.getSettleBuyerCashbackAmount());                   // 返现金
            taskMo.setTradeTitle(to.getSettleBuyerCashbackTitle());                     // 结算给买家返现金的标题
            taskMo.setTradeDetail(to.getSettleBuyerCashbackDetail());                   // 结算给买家返现金的详情
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常

            _log.info("添加结算返现金的任务");
            taskMo.setId(null);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_CASHBACK.getCode());
            taskMo.setExecutePlanTime(to.getSettleBuyerCashbackTime());                 // 计划执行时间
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
        }

        // 如果要结算平台服务费
        if (to.getSettlePlatformServiceFeeAmount() != null && to.getSettlePlatformServiceFeeAmount().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("添加结算平台服务费的任务");
            AfcSettleTaskMo taskMo = dozerMapper.map(to, AfcSettleTaskMo.class);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_PLATFORM_SERVICE_FEE.getCode());
            taskMo.setExecutePlanTime(to.getSettlePlatformServiceFeeTime());            // 计划执行时间
            taskMo.setTradeAmount(to.getSettlePlatformServiceFeeAmount());              // 平台服务费
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
        }

        // 如果要结算供应商
        if (to.getSettleSupplierAmount() != null && to.getSettleSupplierAmount().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("添加结算供应商的任务");
            AfcSettleTaskMo taskMo = dozerMapper.map(to, AfcSettleTaskMo.class);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_SUPPLIER.getCode());
            taskMo.setExecutePlanTime(to.getSettleSupplierTime());                      // 计划执行时间
            taskMo.setAccountId(to.getSupplierAccountId());                             // 供应商的账户ID
            taskMo.setTradeAmount(to.getSettleSupplierAmount());                        // 结算供应商的金额(成本，打到余额)
            taskMo.setTradeTitle(to.getSettleSupplierTitle());                          // 结算供应商的标题
            taskMo.setTradeDetail(to.getSettleSupplierDetail());                        // 结算供应商的详情
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
        }

        // 如果要结算卖家
        if (to.getSettleSellerAmount() != null && to.getSettleSellerAmount().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("添加结算卖家的任务");
            AfcSettleTaskMo taskMo = dozerMapper.map(to, AfcSettleTaskMo.class);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_SELLER.getCode());
            taskMo.setExecutePlanTime(to.getSettleSellerTime());                        // 计划执行时间
            taskMo.setAccountId(to.getSellerAccountId());                               // 卖家的账户ID
            taskMo.setTradeAmount(to.getSettleSellerAmount());                          // 结算卖家的金额(利润，打到余额)
            taskMo.setTradeTitle(to.getSettleSellerTitle());                            // 结算卖家的标题
            taskMo.setTradeDetail(to.getSettleSellerDetail());                          // 结算卖家的详情
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
        }

        // 如果要结算已占用保证金
        if (to.getSettleDepositUsedAmount() != null && to.getSettleDepositUsedAmount().compareTo(BigDecimal.ZERO) > 0) {
            _log.info("添加结算供应商的任务");
            AfcSettleTaskMo taskMo = dozerMapper.map(to, AfcSettleTaskMo.class);
            taskMo.setTradeType((byte) TradeTypeDic.SETTLE_DEPOSIT_USED.getCode());
            taskMo.setExecutePlanTime(to.getSettleDepositUsedTime());                   // 计划执行时间
            taskMo.setAccountId(to.getSellerAccountId());                               // 卖家的账户ID
            taskMo.setTradeAmount(to.getSettleDepositUsedAmount());                     // 结算已占用保证金的金额(成本，打到余额)
            taskMo.setTradeTitle(to.getSettleDepositUsedTitle());                       // 结算已占用保证金的标题
            taskMo.setTradeDetail(to.getSettleDepositUsedDetail());                     // 结算已占用保证金的详情
            thisSvc.add(taskMo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
        }

        // 返回成功
        String msg = "添加结算任务成功";
        _log.info("{}: {}", msg, to);
        AddSettleTaskRo ro = new AddSettleTaskRo();
        ro.setResult(AddSettleTaskResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }

    /**
     * 获取将要执行的任务列表
     */
    @Override
    public List<Long> getTaskIdsThatShouldExecute() {
        return _mapper.selectByExecutePlanTimeBeforeNow();
    }

    /**
     * 订单是否已经结算完成
     * 
     * @param orderId
     *            销售订单ID
     */
    @Override
    public Boolean isSettleCompleted(String orderId) {
        return _mapper.isSettleCompleted(orderId, (byte) SettleTaskExecuteStateDic.DONE.getCode());
    }

    /**
     * 执行任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void executeTask(Long id) {
        _log.info("执行任务: {}", id);

        // 获取任务信息
        AfcSettleTaskMo taskMo = getById(id);
        if (taskMo == null)
            return;
        _log.info("任务信息: {}", taskMo);
        if (SettleTaskExecuteStateDic.NONE.getCode() != taskMo.getExecuteState().intValue()) {
            String msg = "任务不是未执行状态，不能执行";
            _log.error("{}: {}", msg, taskMo);
            throw new RuntimeException(msg);
        }

        // 计算当前时间
        Date now = new Date();

        // 判断交易类型
        switch (TradeTypeDic.getItem(taskMo.getTradeType())) {
        // 结算平台服务费
        case SETTLE_PLATFORM_SERVICE_FEE:
            settleSvc.settlePlatformServiceFee(taskMo, now);
            break;
        case SETTLE_CASHBACKING:
        case SETTLE_CASHBACK:
        case SETTLE_SUPPLIER:
        case SETTLE_SELLER:
        case SETTLE_DEPOSIT_USED:
            settleSvc.settleAccountFee(taskMo, now);
            break;
        default:
            String msg = "任务执行不支持此结算类型";
            _log.error(msg + ": {}", taskMo.getTradeType());
            throw new RuntimeException(msg);
        }

        _log.info("将任务状态改为已经执行");
        int rowCount = _mapper.done(now, taskMo.getId(), (byte) SettleTaskExecuteStateDic.DONE.getCode(), (byte) SettleTaskExecuteStateDic.NONE.getCode());
        if (rowCount != 1) {
            String msg = "执行结算任务不成功: 出现并发问题";
            _log.error("{}-{}", msg, taskMo);
            throw new RuntimeException(msg);
        }

        if (!thisSvc.isSettleCompleted(taskMo.getOrderId())) {
            _log.info("发送结算完成的通知");
            SettleNotifyRo msg = new SettleNotifyRo();
            msg.setOrderId(taskMo.getOrderId());
            msg.setSettleTime(now);
            settleNotifyPub.send(msg);
        }

    }

}
