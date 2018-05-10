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
import rebue.afc.ro.AddSettleTaskRo;
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
    private SucUserSvc          userSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcSettleSvc        settleSvc;

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
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AddSettleTaskRo addSettleTask(AddSettleTaskTo to) throws DuplicateKeyException {
        _log.info("添加结算任务: {}", to);
        if (to.getExecutePlanTime() == null || to.getAccountId() == null || to.getTradeType() == null || to.getTradeAmount() == null || to.getTradeAmount() == 0L
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            AddSettleTaskRo ro = new AddSettleTaskRo();
            ro.setResult(AddSettleTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }

        // 检查是否是本任务支持的交易类型
        switch (TradeTypeDic.getItem(to.getTradeType())) {
        case SETTLE_BUYER_CASHBACK:
        case SETTLE_PROVIDER_BALANCE:
        case SETTLE_SELLER_BALANCE:
        case SETTLE_SELLER_DEPOSIT_USED:
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

        AfcSettleTaskMo mo = dozerMapper.map(to, AfcSettleTaskMo.class);
        mo.setExecuteState((byte) 0);
        _log.info("添加结算任务记录");
        add(mo);        // 如果交易类型+账户ID+销售订单详情ID如果重复，则抛出DuplicateKeyException异常

        // 返回成功
        String msg = "添加返款任务成功";
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
    public List<AfcSettleTaskMo> getTasksThatShouldExecute() {
        return _mapper.selectByExecutePlanTimeBeforeNow();
    }

    /**
     * 执行任务
     * 
     * @param taskMo
     *            要执行的任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void executeTask(AfcSettleTaskMo taskMo) {
        // 计算当前时间
        Date now = new Date();

        // 判断交易类型
        switch (TradeTypeDic.getItem(taskMo.getTradeType())) {
        // 结算平台服务费
        case SETTLE_PLATFORM_SERVICE_FEE:
            settleSvc.settlePlatformServiceFee(taskMo.getOrderId(), taskMo.getTradeAmount(), now);
            break;
        case SETTLE_BUYER_CASHBACK:
        case SETTLE_PROVIDER_BALANCE:
        case SETTLE_SELLER_BALANCE:
        case SETTLE_SELLER_DEPOSIT_USED:
            settleSvc.settleAccountFee(taskMo, now);
            break;
        default:
            String msg = "任务执行不支持此结算类型";
            _log.error(msg + ": {}", taskMo.getTradeType());
            throw new RuntimeException(msg);
        }

        // 将任务状态改为已经执行
        int rowCount = _mapper.done(now, taskMo.getId(), (byte) SettleTaskExecuteStateDic.DONE.getCode(), (byte) SettleTaskExecuteStateDic.NONE.getCode());
        if (rowCount != 1) {
            String msg = "执行结算任务失败: 产生并发问题";
            _log.error(msg);
            throw new RuntimeException(msg);
        }
    }

}
