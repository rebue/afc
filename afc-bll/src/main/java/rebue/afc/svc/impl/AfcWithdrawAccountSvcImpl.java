package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.mapper.AfcWithdrawAccountMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.ro.AfcWithdrawAccountInfoRo;
import rebue.afc.ro.AfcWithdrawAccountRo;
import rebue.afc.ro.WithdrawNumberForMonthRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcWithdrawAccountSvc;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 提现账户
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
public class AfcWithdrawAccountSvcImpl extends MybatisBaseSvcImpl<AfcWithdrawAccountMo, java.lang.Long, AfcWithdrawAccountMapper> implements AfcWithdrawAccountSvc {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcWithdrawAccountSvcImpl.class);
    
    @Resource
    private AfcWithdrawAccountSvc afcWithdrawAccountSvc;
    
    @Resource
    private AfcAccountSvc afcAccountSvc;
    
    @Resource
    private AfcWithdrawSvc afcWithdrawSvc;
    
    @Resource
    private Mapper              dozerMapper;
    
    @Resource
    private SucUserSvc sucUserSvc;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(AfcWithdrawAccountMo mo) {
        _log.info("添加提现账户信息");
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
    
    /**
     * 获取提现账户信息
     */
    @Override
    public AfcWithdrawAccountInfoRo getWithdrawAccountInfo(Long userId) {
    	_log.info("根据用户id查询用户提现账户信息的参数为：{}", userId);
    	AfcWithdrawAccountInfoRo withdrawAccountInfoRo = new AfcWithdrawAccountInfoRo();
    	AfcWithdrawAccountMo withdrawAccountMo = new AfcWithdrawAccountMo();
    	_log.info("根据用户id查询用户提现账户信息查询用户提现账户信息的参数为：{}", userId);
    	withdrawAccountMo.setAccountId(userId);
    	AfcWithdrawAccountMo afcWithdrawAccountMo = afcWithdrawAccountSvc.getOne(withdrawAccountMo);
    	_log.info("根据用户id查询用户提现账户信息查询用户提现账户信息的返回值为：{}", afcWithdrawAccountMo);
    	if (afcWithdrawAccountMo == null) {
    		_log.error("根据用户id查询用户提现账户信息查询用户提现账户信息为空，用户id为：{}", userId);
			return withdrawAccountInfoRo;
		}
    	_log.info("根据用户id查询用户提现账户信息查询用户账户信息的参数为：{}", userId);
    	AfcAccountMo afcAccountMo = afcAccountSvc.getById(userId);
    	_log.info("根据用户id查询用户提现账户信息查询用户账户信息的返回值为：{}", afcAccountMo);
    	
    	AfcWithdrawMo afcWithdrawMo = new AfcWithdrawMo();
    	afcWithdrawMo.setAccountId(userId);
    	_log.info("根据用户id查询用户提现账户信息查询用户提现信息的参数为：{}", afcWithdrawMo);
    	WithdrawNumberForMonthRo withdrawNumberForMonth = afcWithdrawSvc.getWithdrawNumberForMonth(afcWithdrawMo);
    	_log.info("根据用户id查询用户提现账户信息查询用户提现信息的返回值为：{}", withdrawNumberForMonth);
    	
    	withdrawAccountInfoRo = dozerMapper.map(afcWithdrawAccountMo, AfcWithdrawAccountInfoRo.class);
    	withdrawAccountInfoRo.setBalance(afcAccountMo.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	withdrawAccountInfoRo.setWithdrawNumber(withdrawNumberForMonth.getWithdrawNumber());
    	withdrawAccountInfoRo.setSeviceCharge(withdrawNumberForMonth.getSeviceCharge());
    	_log.info("根据用户id查询用户提现账户信息的返回值为：{}", withdrawAccountInfoRo);
    	return withdrawAccountInfoRo;
    }
    
    /**
     * 重写查询用户提现账号信息
     */
    @SuppressWarnings("unchecked")
	@Override
    public PageInfo<AfcWithdrawAccountRo> listEx(AfcWithdrawAccountMo mo, int pageNum, int pageSize, String orderBy) {
    	PageInfo<AfcWithdrawAccountRo> doSelectPageInfoEx = new PageInfo<AfcWithdrawAccountRo>();
    	List<AfcWithdrawAccountRo> listEx = new ArrayList<AfcWithdrawAccountRo>();
    	PageInfo<AfcWithdrawAccountMo> doSelectPageInfo = PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectSelective(mo));
    	for (AfcWithdrawAccountMo afcWithdrawAccountMo : doSelectPageInfo.getList()) {
    		AfcWithdrawAccountRo afcWithdrawAccountRo = dozerMapper.map(afcWithdrawAccountMo, AfcWithdrawAccountRo.class);
    		_log.info("查询申请提现账号记录查询用户信息的参数为：{}", afcWithdrawAccountMo.getAccountId());
    		SucUserMo sucUserMo = sucUserSvc.getById(afcWithdrawAccountMo.getAccountId());
    		_log.info("查询申请提现账号记录查询用户信息的返回值为：{}", sucUserMo);
    		afcWithdrawAccountRo.setUserName(sucUserMo.getWxNickname());
    		listEx.add(afcWithdrawAccountRo);
		}
    	doSelectPageInfoEx = dozerMapper.map(doSelectPageInfo, PageInfo.class);
    	doSelectPageInfoEx.setList(listEx);
    	_log.info("查询申请提现账号记录的返回值为：{}", doSelectPageInfoEx);
    	return doSelectPageInfoEx;
    }
}
