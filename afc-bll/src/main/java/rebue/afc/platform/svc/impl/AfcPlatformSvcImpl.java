package rebue.afc.platform.svc.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.ChargeResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcPlatformMapper;
import rebue.afc.mo.AfcPlatformMo;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.platform.dic.PlatformTradeTypeDic;
import rebue.afc.platform.svc.AfcPlatformSvc;
import rebue.afc.ro.ChargeRo;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.ChargeTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
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
public class AfcPlatformSvcImpl extends MybatisBaseSvcImpl<AfcPlatformMo, java.lang.Long, AfcPlatformMapper>
		implements AfcPlatformSvc {
	private final static Logger _log = LoggerFactory.getLogger(AfcPlatformSvcImpl.class);

	@Resource
	private SucUserSvc userSvc;
	
	@Resource
	private AfcPlatformSvc platformSvc;

	@Resource
	private Mapper dozerMapper;

	@Resource
	private AfcPlatformTradeSvcImpl platFormTradeSvc;

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
	public void modifyBalance(BigDecimal newBalance, Long newModifiedTimestamp, BigDecimal oldBalance,
			Long oldModifiedTimestamp, Long id) {
		int rowCount = _mapper.modifyBalance(newBalance, newModifiedTimestamp, oldBalance, oldModifiedTimestamp, id);
		if (rowCount != 1) {
			String msg = "修改平台余额不成功: 出现并发问题";
			_log.error("{}-{}", msg, id);
			throw new RuntimeException(msg);
		}
	}

	@Override
	public ChargeRo chargeBalance(ChargeTo to) {
		if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null
				|| StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
			_log.warn("没有填写充值的用户ID/充值单号/充值交易的标题/充值交易的金额/操作人账号/MAC/IP: {}", to);
			ChargeRo ro = new ChargeRo();
			ro.setResult(ChargeResultDic.PARAM_ERROR);
			return ro;
		}
		SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("充值发现没有此操作人: " + to.getOpId());
			ChargeRo ro = new ChargeRo();
			ro.setResult(ChargeResultDic.NOT_FOUND_OP);
			return ro;
		}

		if (opUserMo.getIsLock()) {
			_log.error("充值发现操作人已被锁定: " + opUserMo);
			ChargeRo ro = new ChargeRo();
			ro.setResult(ChargeResultDic.OP_LOCKED);
			return ro;
		}

		AfcPlatformMo afcPlatFormMo = platformSvc.getById(to.getUserId());
		if (afcPlatFormMo == null) {
			_log.error("充值发现没有此用户: " + to.getUserId());
			ChargeRo ro = new ChargeRo();
			ro.setResult(ChargeResultDic.NOT_FOUND_USER);
			return ro;
		}

		// 添加一笔交易
		AfcPlatformTradeMo afcPlatFormTradMo = dozerMapper.map(to, AfcPlatformTradeMo.class);
		afcPlatFormTradMo.setId(to.getUserId());
		afcPlatFormTradMo.setPlatformTradeType((byte) PlatformTradeTypeDic.CHARGE_BALANCE.getCode());
		afcPlatFormTradMo.setModifiedTimestamp(new Date().getTime());
		platFormTradeSvc.addTrade(afcPlatFormTradMo);

		// 返回成功
		_log.info("平台充值成功: {}", to);
		ChargeRo ro = new ChargeRo();
		ro.setResult(ChargeResultDic.SUCCESS);
		return ro;
	}
}
