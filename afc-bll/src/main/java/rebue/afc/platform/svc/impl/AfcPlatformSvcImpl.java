package rebue.afc.platform.svc.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.mapper.AfcPlatformMapper;
import rebue.afc.mo.AfcPlatformMo;
import rebue.afc.platform.svc.AfcPlatformSvc;
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
public class AfcPlatformSvcImpl extends MybatisBaseSvcImpl<AfcPlatformMo, java.lang.Long, AfcPlatformMapper> implements AfcPlatformSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcPlatformSvcImpl.class);

    /**
     * @mbg.generated
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcPlatformMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 修改平台余额
     * 
     * @param newBalance
     *            修改后的余额
     * @param newModifiedTimestamp
     *            修改的时间戳
     * @param newBalance
     *            修改前的余额(用来防止并发修改)
     * @param oldModifiedTimestamp
     *            旧的修改时间(用来防止并发修改)
     * @param id
     *            要修改的平台的ID
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyBalance(BigDecimal newBalance, Long newModifiedTimestamp, BigDecimal oldBalance, Long oldModifiedTimestamp, Long id) {
        int rowCount = _mapper.modifyBalance(newBalance, newModifiedTimestamp, oldBalance, oldModifiedTimestamp, id);
        if (rowCount != 1) {
            String msg = "修改平台余额不成功: 出现并发问题";
            _log.error("{}-{}", msg, id);
            throw new RuntimeException(msg);
        }
    }

}
