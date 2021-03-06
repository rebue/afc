package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcSettleTaskMapper;
import rebue.afc.mo.AfcSettleTaskMo;
import rebue.afc.msg.CommissionSettleDoneMsg;
import rebue.afc.msg.SettleDoneMsg;
import rebue.afc.pub.CommissionSettleDonePub;
import rebue.afc.pub.SettleDonePub;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.svc.AfcSettleSvc;
import rebue.afc.svc.AfcSettleTaskSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.AddSettleTasksDetailTo;
import rebue.afc.to.AddSettleTasksTo;
import rebue.afc.to.TaskTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.dic.TaskExecuteStateDic;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 结算任务
 *
 * 在单独使用不带任何参数的 @Transactional 注释时，
 * propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED，
 * 而且事务不会针对受控异常（checked exception）回滚。
 *
 * 注意：
 * 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcSettleTaskSvcImpl extends MybatisBaseSvcImpl<AfcSettleTaskMo, java.lang.Long, AfcSettleTaskMapper> implements AfcSettleTaskSvc {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(final AfcSettleTaskMo mo) {
        _log.info("添加结算任务");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    private static final Logger     _log = LoggerFactory.getLogger(AfcSettleTaskSvcImpl.class);

    @Resource
    private AfcSettleTaskSvc        thisSvc;

    @Resource
    private SucUserSvc              userSvc;

    @Resource
    private AfcTradeSvc             tradeSvc;

    @Resource
    private AfcSettleSvc            settleSvc;

    @Resource
    private SettleDonePub           settleNotifyPub;

    @Resource
    private CommissionSettleDonePub commissionSettleNotifyPub;

    @Resource
    private Mapper                  dozerMapper;

    /**
     * 添加结算任务 任务调度器会定时检查当前要执行的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AddSettleTasksRo addSettleTasks(final AddSettleTasksTo to) throws DuplicateKeyException {
        _log.info("添加结算任务: {}", to);
        if (to.getBuyerAccountId() == null || StringUtils.isAnyBlank(to.getOrderId(), to.getMac(), to.getIp())) {
            final String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            final AddSettleTasksRo ro = new AddSettleTasksRo();
            ro.setResult(AddSettleTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }
        // 立即执行的时间
        final Date now = new Date();
        // 遍历详情来添加任务
        for (final AddSettleTasksDetailTo detail : to.getDetails()) {
            if (detail.getSettleBuyerCashbackAmount() == null || StringUtils.isAnyBlank(detail.getSettleBuyerCashbackTitle(), detail.getOrderDetailId())) {
                final String msg = "参数有误";
                _log.error("{}: {}", msg, detail);
                throw new RuntimeException(msg);
            }
            // 如果要结算返现
            if (detail.getSettleBuyerCashbackAmount() != null && detail.getSettleBuyerCashbackAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算返现中金额的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setOrderId(to.getOrderId());
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_CASHBACKING.getCode());
                // 计划执行时间（延迟1分钟立即执行）
                taskMo.setExecutePlanTime(new Date(now.getTime() + 60000));
                // 买家的账户ID
                taskMo.setAccountId(to.getBuyerAccountId());
                // 返现金
                taskMo.setTradeAmount(detail.getSettleBuyerCashbackAmount());
                // 结算给买家返现金的标题
                taskMo.setTradeTitle(detail.getSettleBuyerCashbackTitle());
                // 结算给买家返现金的详情
                taskMo.setTradeDetail(detail.getSettleBuyerCashbackDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
                _log.info("添加结算返现金的任务");
                taskMo.setId(null);
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_CASHBACK.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleBuyerCashbackTime());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算上家佣金
            if (detail.getSettleUplineCommissionAmount() != null && detail.getSettleUplineCommissionAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算返佣中金额的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_COMMISSIONING.getCode());
                // 计划执行时间（延迟1分钟立即执行）
                taskMo.setExecutePlanTime(new Date(now.getTime() + 60000));
                // 上家的账户ID
                taskMo.setAccountId(detail.getUplineAccountId());
                // 上家的订单ID
                taskMo.setOrderId(detail.getUplineOrderId().toString());
                // 上家的订单详情ID
                taskMo.setOrderDetailId(detail.getUplineOrderDetailId().toString());
                // 返佣金
                taskMo.setTradeAmount(detail.getSettleUplineCommissionAmount());
                // 结算给上家返佣金的标题
                taskMo.setTradeTitle(detail.getSettleUplineCommissionTitle());
                // 结算给上家返佣金的详情
                taskMo.setTradeDetail(detail.getSettleUplineCommissionDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
                _log.info("添加结算返佣金的任务");
                taskMo.setId(null);
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_COMMISSION.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleUplineCommissionTime());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算本家佣金
            if (detail.getSettleSelfCommissionAmount() != null && detail.getSettleSelfCommissionAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算返佣中金额的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_COMMISSIONING.getCode());
                // 计划执行时间（延迟1分钟立即执行）
                taskMo.setExecutePlanTime(new Date(now.getTime() + 60000));
                // 本家的账户ID
                taskMo.setAccountId(to.getBuyerAccountId());
                // 本家的订单ID
                taskMo.setOrderId(to.getOrderId().toString());
                // 本家的订单详情ID
                taskMo.setOrderDetailId(detail.getOrderDetailId().toString());
                // 返佣金
                taskMo.setTradeAmount(detail.getSettleSelfCommissionAmount());
                // 结算给上家返佣金的标题
                taskMo.setTradeTitle(detail.getSettleSelfCommissionTitle());
                // 结算给上家返佣金的详情
                taskMo.setTradeDetail(detail.getSettleSelfCommissionDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
                _log.info("添加结算返佣金的任务");
                taskMo.setId(null);
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_COMMISSION.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleUplineCommissionTime());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算平台服务费
            if (detail.getSettlePlatformServiceFeeAmount() != null && detail.getSettlePlatformServiceFeeAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算平台服务费的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setOrderId(to.getOrderId());
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_PLATFORM_SERVICE_FEE.getCode());
                // 结算平台服务费的标题
                taskMo.setTradeTitle("结算平台服务费");
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettlePlatformServiceFeeTime());
                // 平台服务费
                taskMo.setTradeAmount(detail.getSettlePlatformServiceFeeAmount());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算供应商
            if (detail.getSettleSupplierAmount() != null && detail.getSettleSupplierAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算供应商的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setOrderId(to.getOrderId());
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_SUPPLIER.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleSupplierTime());
                // 供应商的账户ID
                taskMo.setAccountId(detail.getSupplierAccountId());
                // 结算供应商的金额(成本，打到余额)
                taskMo.setTradeAmount(detail.getSettleSupplierAmount());
                // 结算供应商的标题
                taskMo.setTradeTitle(detail.getSettleSupplierTitle());
                // 结算供应商的详情
                taskMo.setTradeDetail(detail.getSettleSupplierDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算卖家
            if (detail.getSettleSellerAmount() != null && detail.getSettleSellerAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算卖家的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setOrderId(to.getOrderId());
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_SELLER.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleSellerTime());
                // 卖家的账户ID
                taskMo.setAccountId(to.getSellerAccountId());
                // 结算卖家的金额(利润，打到余额)
                taskMo.setTradeAmount(detail.getSettleSellerAmount());
                // 结算卖家的标题
                taskMo.setTradeTitle(detail.getSettleSellerTitle());
                // 结算卖家的详情
                taskMo.setTradeDetail(detail.getSettleSellerDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
            // 如果要结算已占用保证金
            if (detail.getSettleDepositUsedAmount() != null && detail.getSettleDepositUsedAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算供应商的任务");
                final AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
                taskMo.setOrderId(to.getOrderId());
                taskMo.setMac(to.getMac());
                taskMo.setIp(to.getIp());
                taskMo.setTradeType((byte) TradeTypeDic.SETTLE_DEPOSIT_USED.getCode());
                // 计划执行时间
                taskMo.setExecutePlanTime(to.getSettleDepositUsedTime());
                // 卖家的账户ID
                taskMo.setAccountId(to.getSellerAccountId());
                // 结算已占用保证金的金额(成本，打到余额)
                taskMo.setTradeAmount(detail.getSettleDepositUsedAmount());
                // 结算已占用保证金的标题
                taskMo.setTradeTitle(detail.getSettleDepositUsedTitle());
                // 结算已占用保证金的详情
                taskMo.setTradeDetail(detail.getSettleDepositUsedDetail());
                // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常
                thisSvc.add(taskMo);
            }
        }
        // 返回成功
        final String msg = "添加结算任务成功";
        _log.info("{}: {}", msg, to);
        final AddSettleTasksRo ro = new AddSettleTasksRo();
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
    public Boolean isSettleCompleted(final String orderId) {
        return _mapper.isSettleCompleted(orderId, (byte) TaskExecuteStateDic.DONE.getCode());
    }

    /**
     * 执行任务
     *
     * @param taskMo
     *            要执行的任务
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public void executeTask(final Long id) {
        _log.info("准备执行任务: {}", id);
        // 获取任务信息
        final AfcSettleTaskMo taskMo = getById(id);
        if (taskMo == null) {
            return;
        }
        _log.info("任务信息: {}", taskMo);
        if (TaskExecuteStateDic.NONE.getCode() != taskMo.getExecuteState().intValue()) {
            final String msg = "任务不是未执行状态，不能执行";
            _log.error("{}: {}", msg, taskMo);
            throw new RuntimeException(msg);
        }
        // 计算当前时间
        final Date now = new Date();
        _log.info("开始执行任务");
        try {
            // 判断交易类型
            switch (TradeTypeDic.getItem(taskMo.getTradeType())) {
            // 结算平台服务费
            case SETTLE_PLATFORM_SERVICE_FEE:
                settleSvc.settlePlatformServiceFee(taskMo, now);
                break;
            case SETTLE_CASHBACKING:
            case SETTLE_CASHBACK:
            case SETTLE_COMMISSIONING:
            case SETTLE_COMMISSION:
            case SETTLE_SUPPLIER:
            case SETTLE_SELLER:
            case SETTLE_DEPOSIT_USED:
                settleSvc.settleAccountFee(taskMo, now);
                break;
            default:
                final String msg = "任务执行不支持此结算类型";
                _log.error(msg + ": {}", taskMo.getTradeType());
                throw new RuntimeException(msg);
            }
        } catch (final RuntimeException e) {
            final String msg = "执行结算的任务出现运行时异常";
            _log.error(msg, e);
            throw new RuntimeException(msg);
        }
        _log.info("将任务状态改为已经执行");
        final int rowCount = _mapper.done(now, taskMo.getId(), (byte) TaskExecuteStateDic.DONE.getCode(), (byte) TaskExecuteStateDic.NONE.getCode());
        if (rowCount != 1) {
            _log.info("影响行数为: {}", rowCount);
            final String msg = "执行结算任务不成功: 可能出现并发问题";
            _log.error("{}-{}", msg, taskMo);
            throw new RuntimeException(msg);
        }
        if (taskMo.getTradeType() == TradeTypeDic.SETTLE_COMMISSION.getCode()) {
            _log.info("发送返佣结算完成通知");
            final CommissionSettleDoneMsg msg = new CommissionSettleDoneMsg();
            msg.setOrderDetailId(taskMo.getOrderDetailId());
            msg.setSettleTime(now);
            commissionSettleNotifyPub.send(msg);
        }
        if (thisSvc.isSettleCompleted(taskMo.getOrderId())) {
            _log.info("发送结算完成的通知");
            final SettleDoneMsg msg = new SettleDoneMsg();
            msg.setOrderId(taskMo.getOrderId());
            msg.setSettleTime(now);
            settleNotifyPub.send(msg);
        }
    }

    /**
     * 暂停任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro suspendTask(final TaskTo to) {
        _log.info("暂停任务: {}", to);
        final Ro ro = new Ro();

        // 根据交易类型和订单详情ID查找任务
        final AfcSettleTaskMo condition = new AfcSettleTaskMo();
        condition.setTradeType((byte) to.getTradeType().getCode());
        condition.setOrderDetailId(to.getOrderDetailId());
        final AfcSettleTaskMo task = getOne(condition);
        if (task == null) {
            final String msg = "找不到任务";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.WARN);
            ro.setMsg(msg);
            return ro;
        }
        if (TaskExecuteStateDic.NONE.getCode() != task.getExecuteState()) {
            final String msg = "任务处在非执行状态，不能暂停";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }

        // rowCount为影响的行数
        final int rowCount = _mapper.updateExecuteState(to.getTradeType().getCode(), to.getOrderDetailId(), TaskExecuteStateDic.NONE.getCode(),
                TaskExecuteStateDic.SUSPEND.getCode());
        if (rowCount != 1) {
            _log.info("影响行数为: {}", rowCount);
            final String msg = "任务暂停失败: 可能出现并发问题";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }

        final String msg = "任务暂停成功";
        _log.info(msg);
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }

    /**
     * 恢复任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro resumeTask(final TaskTo to) {
        _log.info("恢复任务: {}", to);
        final Ro ro = new Ro();

        // 根据交易类型和订单详情ID查找任务
        final AfcSettleTaskMo condition = new AfcSettleTaskMo();
        condition.setTradeType((byte) to.getTradeType().getCode());
        condition.setOrderDetailId(to.getOrderDetailId());
        final AfcSettleTaskMo task = getOne(condition);
        if (task == null) {
            final String msg = "找不到任务";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.WARN);
            ro.setMsg(msg);
            return ro;
        }
        if (TaskExecuteStateDic.SUSPEND.getCode() != task.getExecuteState()) {
            final String msg = "任务处在非暂停状态，不能恢复执行";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }

        // rowCount为影响的行数
        final int rowCount = _mapper.updateExecuteState(to.getTradeType().getCode(), to.getOrderDetailId(), TaskExecuteStateDic.SUSPEND.getCode(),
                TaskExecuteStateDic.NONE.getCode());
        if (rowCount != 1) {
            _log.info("影响行数为: {}", rowCount);
            final String msg = "恢复任务失败: 可能出现并发问题";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }

        final String msg = "任务恢复成功";
        _log.info(msg);
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }

    /**
     * 取消任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro cancelTask(final TaskTo to) {
        _log.info("取消任务: {}", to);
        final Ro ro = new Ro();

        // 根据交易类型和订单详情ID查找任务
        final AfcSettleTaskMo condition = new AfcSettleTaskMo();
        condition.setTradeType((byte) to.getTradeType().getCode());
        condition.setOrderDetailId(to.getOrderDetailId());
        final AfcSettleTaskMo task = getOne(condition);
        if (task == null) {
            final String msg = "找不到任务";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.WARN);
            ro.setMsg(msg);
            return ro;
        }
        if (TaskExecuteStateDic.DONE.getCode() == task.getExecuteState()) {
            final String msg = "任务已被执行，不能取消";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }
        if (TaskExecuteStateDic.CANCEL.getCode() == task.getExecuteState()) {
            final String msg = "任务已被取消，不能重复取消";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.WARN);
            ro.setMsg(msg);
            return ro;
        }

        // 取消任务，rowCount为影响的行数
        final int rowCount = _mapper.cancelTask(task.getId(), task.getExecuteState(), TaskExecuteStateDic.CANCEL.getCode());
        if (rowCount != 1) {
            _log.info("影响行数为: {}", rowCount);
            final String msg = "任务取消失败: 可能出现并发问题";
            _log.error("{}-{}", msg, to);
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }

        _log.info("取消任务，需要补偿已经执行的结算");
        settleSvc.compensateCanceledSettle(task);

        final String msg = "任务取消成功";
        _log.info(msg);
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }

//    @Override
//    public List<AfcSettleTaskMo> getCashBackTask(GetCashBackTaskTo to) {
//        to.setTradType((byte) TradeTypeDic.SETTLE_CASHBACK.getCode());
//        _log.info("查找用户待返现任务参数为: {}", String.valueOf(to));
//        AfcSettleTaskMo qo = dozerMapper.map(to, AfcSettleTaskMo.class);
//        // 
//        // 
//        // 
//        PageInfo<AfcSettleTaskMo> result = PageHelper.startPage(to.getPageNum(), to.getPageSize(), true, false, null).doSelectPageInfo(() -> _mapper.selectSelective(qo));
//        _log.info("查询的结果为: {}", String.valueOf(result));
//        return result.getList();
//    }
}
