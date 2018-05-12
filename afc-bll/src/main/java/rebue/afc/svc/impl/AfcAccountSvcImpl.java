package rebue.afc.svc.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.mapper.AfcAccountMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.svc.AfcAccountSvc;
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
public class AfcAccountSvcImpl extends MybatisBaseSvcImpl<AfcAccountMo, java.lang.Long, AfcAccountMapper>
        implements AfcAccountSvc {

    /**
     */
    private final static Logger _log = LoggerFactory.getLogger(AfcAccountSvcImpl.class);

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcAccountMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 产生了一笔交易(影响余额/返现金/返现中金额/货款)，修改相应的字段及记录时间戳
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int trade(HashMap<String, ?> map) {
        _log.info("产生了一笔交易(影响余额/返现金/货款)，修改相应的字段及记录时间戳: {}", map);
        return _mapper.trade(map);
    }

}
