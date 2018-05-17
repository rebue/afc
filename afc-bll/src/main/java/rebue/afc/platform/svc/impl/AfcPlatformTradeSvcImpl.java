package rebue.afc.platform.svc.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.mapper.AfcPlatformTradeMapper;
import rebue.afc.mo.AfcPlatformMo;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.afc.platform.dic.PlatformTradeTypeDic;
import rebue.afc.platform.svc.AfcPlatformSvc;
import rebue.afc.platform.svc.AfcPlatformTradeSvc;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;

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
public class AfcPlatformTradeSvcImpl extends MybatisBaseSvcImpl<AfcPlatformTradeMo, java.lang.Long, AfcPlatformTradeMapper> implements AfcPlatformTradeSvc {

    private final static Logger _log = LoggerFactory.getLogger(AfcPlatformTradeSvcImpl.class);

    @Resource
    private AfcPlatformTradeSvc thisSvc;
    @Resource
    private AfcPlatformSvc      platformSvc;

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcPlatformTradeMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 添加一笔平台交易
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addTrade(AfcPlatformTradeMo tradeMo) {
        _log.info("添加一笔平台交易记录");

        // 交易金额
        BigDecimal tradeAmount = tradeMo.getTradeAmount();
        _log.info("交易的金额: {}", tradeAmount);

        _log.info("查询平台信息");
        AfcPlatformMo oldPlatfromMo = platformSvc.getById(0L);
        _log.info("当前平台信息: {}", oldPlatfromMo);
        BigDecimal oldBalance = oldPlatfromMo.getBalance();
        _log.info("改变前的余额: {}", oldBalance);

        BigDecimal newBalance;

        // 判断交易类型
        switch (PlatformTradeTypeDic.getItem(tradeMo.getPlatformTradeType())) {
        // XXX AFC : 交易 : （ 余额+ ）结算-结算平台服务费(将交易结算的服务费打到平台的余额)
        case CHARGE_SEVICE_FEE:
            newBalance = oldBalance.add(tradeAmount);
            break;
        // XXX AFC : 交易 : （ 余额- ）退款-收回平台服务费(结算给平台的服务费的金额)
        case GETBACK_SEVICE_FEE:
            newBalance = oldBalance.subtract(tradeAmount);
            break;
        default:
            String msg = "不支持此平台交易类型";
            _log.error("{}: {}", msg, tradeMo.getPlatformTradeType());
            throw new RuntimeException(msg);
        }
        _log.info("改变后的余额: {}", newBalance);

        // 添加一笔平台交易
        tradeMo.setOldBalance(oldBalance);                                                         // 交易后的余额
        tradeMo.setNewBalance(newBalance);                                                         // 交易后的余额
        thisSvc.add(tradeMo);      // 如果重复提交，会抛出DuplicateKeyException运行时异常

        // 修改平台余额
        platformSvc.modifyBalance(newBalance, tradeMo.getModifiedTimestamp(), oldPlatfromMo.getBalance(), oldPlatfromMo.getModifiedTimestamp(), oldPlatfromMo.getId());

    }
}
