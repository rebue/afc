package rebue.afc.svc.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.mapper.AfcWithdrawAccountMapper;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.svc.AfcWithdrawAccountSvc;
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
public class AfcWithdrawAccountSvcImpl
        extends MybatisBaseSvcImpl<AfcWithdrawAccountMo, java.lang.Long, AfcWithdrawAccountMapper>
        implements AfcWithdrawAccountSvc {

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcWithdrawAccountMo mo) {
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        if (mo.getIsDef() == null)
            mo.setIsDef(true);
        // 修改时间戳
        mo.setModifiedTimestamp(System.currentTimeMillis());
        return super.add(mo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int modify(AfcWithdrawAccountMo mo) {
        // 修改时间戳
        mo.setModifiedTimestamp(System.currentTimeMillis());
        return super.modify(mo);
    }

    /**
     * 是否存在指定的用户
     */
    @Override
    public Boolean existByUserId(Long userId) {
        AfcWithdrawAccountMo mo = new AfcWithdrawAccountMo();
        mo.setAccountId(userId);
        return _mapper.existSelective(mo);
    }

    /**
     * 查询用户的账户信息
     */
    @Override
    public List<AfcWithdrawAccountMo> listByUserId(Long userId) {
        AfcWithdrawAccountMo mo = new AfcWithdrawAccountMo();
        mo.setAccountId(userId);
        return list(mo);
    }

}
