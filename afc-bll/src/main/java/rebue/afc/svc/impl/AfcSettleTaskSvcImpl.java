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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.dic.SettleTaskExecuteStateDic;
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
import rebue.afc.to.GetCashBackTaskTo;
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
    public int add(AfcSettleTaskMo mo) {
        _log.info("添加结算任务");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    private static final Logger _log = LoggerFactory.getLogger(AfcSettleTaskSvcImpl.class);

    @Resource
    private AfcSettleTaskSvc    thisSvc;

    @Resource
    private SucUserSvc          userSvc;

    @Resource
    private AfcTradeSvc         tradeSvc;

    @Resource
    private AfcSettleSvc        settleSvc;

    @Resource
    private SettleDonePub       settleNotifyPub;
    
    @Resource
    private CommissionSettleDonePub       commissionSettleNotifyPub;

    @Resource
    private Mapper              dozerMapper;

    /**
     * 添加结算任务 任务调度器会定时检查当前要执行的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AddSettleTasksRo addSettleTasks(AddSettleTasksTo to) throws DuplicateKeyException {
        _log.info("添加结算任务: {}", to);
        if (to.getBuyerAccountId() == null || StringUtils.isAnyBlank(to.getOrderId(), to.getMac(), to.getIp())) {
            String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            AddSettleTasksRo ro = new AddSettleTasksRo();
            ro.setResult(AddSettleTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }
        // 立即执行的时间
        Date now = new Date();
        // 遍历详情来添加任务
        for (AddSettleTasksDetailTo detail : to.getDetails()) {
            if (detail.getSettleBuyerCashbackAmount() == null || StringUtils.isAnyBlank(detail.getSettleBuyerCashbackTitle(), detail.getOrderDetailId())) {
                String msg = "参数有误";
                _log.error("{}: {}", msg, detail);
                throw new RuntimeException(msg);
            }
            // 如果要结算返现
            if (detail.getSettleBuyerCashbackAmount() != null && detail.getSettleBuyerCashbackAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算返现中金额的任务");
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
            // 如果要结算佣金
            if (detail.getSettleUplineCommissionAmount() != null && detail.getSettleUplineCommissionAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算返佣中金额的任务");
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
            // 如果要结算平台服务费
            if (detail.getSettlePlatformServiceFeeAmount() != null && detail.getSettlePlatformServiceFeeAmount().compareTo(BigDecimal.ZERO) > 0) {
                _log.info("添加结算平台服务费的任务");
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
                AfcSettleTaskMo taskMo = dozerMapper.map(detail, AfcSettleTaskMo.class);
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
        String msg = "添加结算任务成功";
        _log.info("{}: {}", msg, to);
        AddSettleTasksRo ro = new AddSettleTasksRo();
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
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public void executeTask(Long id) {
        _log.info("准备执行任务: {}", id);
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
                String msg = "任务执行不支持此结算类型";
                _log.error(msg + ": {}", taskMo.getTradeType());
                throw new RuntimeException(msg);
            }
        } catch (RuntimeException e) {
            String msg = "执行结算的任务出现运行时异常";
            _log.error(msg, e);
            throw new RuntimeException(msg);
        }
        _log.info("将任务状态改为已经执行");
        int rowCount = _mapper.done(now, taskMo.getId(), (byte) SettleTaskExecuteStateDic.DONE.getCode(), (byte) SettleTaskExecuteStateDic.NONE.getCode());
        if (rowCount != 1) {
            String msg = "执行结算任务不成功: 出现并发问题";
            _log.error("{}-{}", msg, taskMo);
            throw new RuntimeException(msg);
        }
        if(taskMo.getTradeType()==TradeTypeDic.SETTLE_COMMISSION.getCode()) {
        	_log.info("发送返佣结算完成通知");
        	CommissionSettleDoneMsg msg = new CommissionSettleDoneMsg();
            msg.setOrderDetailId(taskMo.getOrderDetailId());
            msg.setSettleTime(now);
            commissionSettleNotifyPub.send(msg);
        }
        if (!thisSvc.isSettleCompleted(taskMo.getOrderId())) {
            _log.info("发送结算完成的通知");
            SettleDoneMsg msg = new SettleDoneMsg();
            msg.setOrderId(taskMo.getOrderId());
            msg.setSettleTime(now);
            settleNotifyPub.send(msg);
        }
    }

    @Override
    public List<AfcSettleTaskMo> getCashBackTask(GetCashBackTaskTo to) {
        to.setTradType((byte) TradeTypeDic.SETTLE_CASHBACK.getCode());
        _log.info("查找用户待返现任务参数为: {}", String.valueOf(to));
        AfcSettleTaskMo qo = dozerMapper.map(to, AfcSettleTaskMo.class);
        //
        //
        //
        PageInfo<AfcSettleTaskMo> result = PageHelper.startPage(to.getPageNum(), to.getPageSize(), true, false, null).doSelectPageInfo(() -> _mapper.selectSelective(qo));
        _log.info("查询的结果为: {}", String.valueOf(result));
        return result.getList();
    }
}
