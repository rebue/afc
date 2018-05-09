package rebue.afc.vpay.svc.impl;

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

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.VpayTradeTaskMapper;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.mo.VpayTradeTaskMo;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.svc.impl.AfcRebateSvcImpl;
import rebue.afc.vpay.dic.AddRebateTaskResultDic;
import rebue.afc.vpay.ro.AddRebateTaskRo;
import rebue.afc.vpay.svc.VpayTradeTaskSvc;
import rebue.afc.vpay.to.AddRebateTaskTo;
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
public class VpayTradeTaskSvcImpl extends MybatisBaseSvcImpl<VpayTradeTaskMo, java.lang.Long, VpayTradeTaskMapper> implements VpayTradeTaskSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcRebateSvcImpl.class);

    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(VpayTradeTaskMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 添加返款任务
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AddRebateTaskRo addRebateTast(AddRebateTaskTo to) throws DuplicateKeyException {
        _log.info("添加交易任务: {}", to);
        if (to.getExecutePlanTime() == null || to.getAccountId() == null || to.getTradeType() == null || to.getTradeAmount() == null
                || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            String msg = "参数有误";
            _log.error("{}: {}", msg, to);
            AddRebateTaskRo ro = new AddRebateTaskRo();
            ro.setResult(AddRebateTaskResultDic.PARAM_ERROR);
            ro.setMsg(msg);
            return ro;
        }

        // 检查是否是本任务支持的交易类型
        switch (TradeTypeDic.getItem(to.getTradeType())) {
        case REBATE_BUYER_CASHBACK:
        case REBATE_PROVIDER_BALANCE:
        case REBATE_SELLER_BALANCE:
        case REBATE_SELLER_DEPOSIT_USED:
            break;
        default:
            String msg = "不支持的交易类型";
            _log.error("{}: {}", msg, to);
            AddRebateTaskRo ro = new AddRebateTaskRo();
            ro.setResult(AddRebateTaskResultDic.NOT_SUPPORTED_TRADE_TYPE);
            ro.setMsg(msg);
            return ro;
        }

        if (!userSvc.exist(to.getAccountId())) {
            String msg = "没有此账户";
            _log.error("{}: {}", msg, to.getAccountId());
            AddRebateTaskRo ro = new AddRebateTaskRo();
            ro.setResult(AddRebateTaskResultDic.NOT_FOUND_ACCOUNT);
            ro.setMsg(msg);
            return ro;
        }

        VpayTradeTaskMo mo = dozerMapper.map(to, VpayTradeTaskMo.class);
        mo.setExecuteState((byte) 0);
        _log.info("添加交易任务记录");
        add(mo);        // 如果交易类型+账户ID+销售订单详情ID如果重复，则抛出DuplicateKeyException异常

        // 返回成功
        String msg = "添加返款任务成功";
        _log.info("{}: {}", msg, to);
        AddRebateTaskRo ro = new AddRebateTaskRo();
        ro.setResult(AddRebateTaskResultDic.SUCCESS);
        ro.setMsg(msg);
        return ro;
    }

    /**
     * 获取将要执行的任务列表
     */
    @Override
    public List<VpayTradeTaskMo> getTasksThatShouldExecute() {
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
    public void executeTask(VpayTradeTaskMo taskMo) {
        // 计算当前时间
        Date now = new Date();

        // 判断交易类型
        switch (TradeTypeDic.getItem(taskMo.getTradeType())) {
        // 返款到平台服务费
        case REBATE_PLATFORM_SERVICE_CHANGE:
//            AfcPlatformFlowMo mo=
            break;
        default:
        }

        // 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
        AfcTradeMo tradeMo = dozerMapper.map(taskMo, AfcTradeMo.class);
//        tradeMo.setId(taskMo.getId());       // 交易ID使用任务ID
        tradeMo.setTradeTime(now);
        tradeMo.setOpId(0L);         // 操作人设为0表示系统自动产生的交易
        try {
            tradeSvc.add(tradeMo);
        } catch (DuplicateKeyException e) {
            _log.error("返款到买家的返现金重复提交");
            throw new RuntimeException("返款到买家的返现金重复提交", e);
        }

    }

}
