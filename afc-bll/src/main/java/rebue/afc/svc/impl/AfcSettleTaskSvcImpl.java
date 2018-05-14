package rebue.afc.svc.impl;

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
import rebue.afc.mo.AfcTradeMo;
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
        if (to.getExecutePlanTime() == null || to.getAccountId() == null || to.getTradeType() == null || to.getTradeAmount() == null || to.getTradeAmount() == 0L
                || StringUtils.isAnyBlank(to.getOrderId(), to.getOrderDetailId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }

        // 检查是否是本任务支持的交易类型
        switch (TradeTypeDic.getItem(to.getTradeType())) {
        case SETTLE_CASHBACKING:
        case SETTLE_COST:
        case SETTLE_SELLER_PROFIT:
        case SETTLE_DEPOSIT_USED:
        case SETTLE_PLATFORM_SERVICE_FEE:
            break;
        default:
            String msg = "不支持的结算类型";
            _log.error("{}: {}", msg, to);
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.NOT_SUPPORTED_SETTLE_TYPE);
            ro.setMsg(msg);
            return ro;
        }

        if (!userSvc.exist(to.getAccountId())) {
            String msg = "没有此账户";
            _log.error("{}: {}", msg, to.getAccountId());
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.NOT_FOUND_ACCOUNT);
            ro.setMsg(msg);
            return ro;
        }

        // 如果是结算返现金，先添加返现中金额
        if (TradeTypeDic.SETTLE_CASHBACK.getCode() == to.getTradeType().intValue()) {
            // 添加一笔交易
            AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
            tradeMo.setTradeType((byte) TradeTypeDic.SETTLE_CASHBACKING.getCode());
            tradeMo.setTradeTime(new Date());
            tradeMo.setOpId(0L);                    // 操作人设为0表示系统自动产生的交易
            tradeSvc.addTrade(tradeMo);
        }

        AfcSettleTaskMo mo = dozerMapper.map(to, AfcSettleTaskMo.class);
        mo.setExecuteState((byte) SettleTaskExecuteStateDic.NONE.getCode());
        _log.info("添加结算任务");
        thisSvc.add(mo);        // 如果交易类型+账户ID+销售订单详情ID重复，则抛出DuplicateKeyException异常

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
            settleSvc.settlePlatformServiceFee(taskMo.getOrderId(), taskMo.getTradeAmount(), now);
            break;
        case SETTLE_CASHBACK:
        case SETTLE_COST:
        case SETTLE_SELLER_PROFIT:
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
