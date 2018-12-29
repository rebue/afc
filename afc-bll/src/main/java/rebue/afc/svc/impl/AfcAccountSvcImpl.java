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

/**
 * 账户信息
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
public class AfcAccountSvcImpl extends MybatisBaseSvcImpl<AfcAccountMo, java.lang.Long, AfcAccountMapper> implements AfcAccountSvc {

    /**
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(final AfcAccountMo mo) {
        _log.info("添加账户信息");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        mo.setModifiedTimestamp(mo.getId());
        return super.add(mo);
    }

    /**
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcAccountSvcImpl.class);

    /**
     * 修改金额-产生了一笔交易(影响余额/返现金/返现中金额/货款)，修改相应的字段及记录时间戳
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyAmount(final HashMap<String, ?> map) {
        _log.info("修改金额-产生了一笔交易(影响余额/返现金/货款)，修改相应的字段及记录时间戳: {}", map);
        if (_mapper.modifyAmount(map) != 1) {
            final String msg = "修改账户的金额不成功: 出现并发问题";
            _log.error("{}-{}", msg, map);
            throw new RuntimeException(msg);
        }
    }
}
