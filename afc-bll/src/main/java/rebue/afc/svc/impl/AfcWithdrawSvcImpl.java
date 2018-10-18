package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mapper.AfcWithdrawMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.platform.dic.PlatformTradeTypeDic;
import rebue.afc.platform.svc.AfcPlatformTradeSvc;
import rebue.afc.ro.AfcWithdrawRo;
import rebue.afc.ro.WithdrawNumberForMonthRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.svc.AfcWithdrawAccountSvc;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.afc.withdraw.dic.WithdrawApplyResultDic;
import rebue.afc.withdraw.dic.WithdrawCancelResultDic;
import rebue.afc.withdraw.dic.WithdrawDealResultDic;
import rebue.afc.withdraw.dic.WithdrawOkResultDic;
import rebue.afc.withdraw.dic.WithdrawStateDic;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawApplyTo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 提现信息
 *
 * 在单独使用不带任何参数的 @Transactional 注释时， propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED， 而且事务不会针对受控异常（checked exception）回滚。
 *
 * 注意： 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcWithdrawSvcImpl extends MybatisBaseSvcImpl<AfcWithdrawMo, java.lang.Long, AfcWithdrawMapper>
		implements AfcWithdrawSvc {

	/**
	 * @mbg.generated 自动生成，如需修改，请删除本行
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int add(AfcWithdrawMo mo) {
		_log.info("添加提现信息");
		// 如果id为空那么自动生成分布式id
		if (mo.getId() == null || mo.getId() == 0) {
			mo.setId(_idWorker.getId());
		}
		return super.add(mo);
	}

	private static final Logger _log = LoggerFactory.getLogger(AfcWithdrawSvcImpl.class);

	@Resource
	private SucUserSvc userSvc;

	@Resource
	private AfcAccountSvc accountSvc;

	@Resource
	private AfcWithdrawAccountSvc withdrawAccountSvc;

	@Resource
	private AfcTradeSvc tradeSvc;

	@Resource
	private AfcFlowSvc flowSvc;

	@Resource
	private Mapper dozerMapper;
	
	@Resource
	private AfcWithdrawSvc afcWithdrawSvc;
	
	@Resource
	private AfcPlatformTradeSvc afcPlatformTradeSvc;
	
	@Resource
    private SucUserSvc sucUserSvc;

	/**
	 * 提现申请（ 余额-，提现中+ ）
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public WithdrawApplyRo apply(WithdrawApplyTo to) {
		if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null
				|| StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
			_log.warn("没有填写申请提现账户的用户ID/申请提现的金额/申请人的用户ID/MAC/IP: {}", to);
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.PARAM_ERROR);
			return ro;
		}
		SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("提现申请发现没有此申请人: " + to.getOpId());
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.NOT_FOUND_OP);
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("提现申请发现申请人已被锁定: " + opUserMo);
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.OP_LOCKED);
			return ro;
		}
		SucUserMo withdrawUserMo = userSvc.getById(to.getUserId());
		if (withdrawUserMo == null) {
			_log.error("没有发现提现的用户: " + to.getUserId());
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.NOT_FOUND_WITHDRAW_USER);
			return ro;
		}
		if (withdrawUserMo.getIsLock()) {
			_log.error("提现的用户已经被锁定: " + withdrawUserMo);
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.WITHDRAW_USER_LOCKED);
			return ro;
		}
		_log.info("申请提现查询用户提现次数和手续费的参数为：{}", to.getUserId());
		AfcWithdrawMo afcWithdrawMo = new AfcWithdrawMo();
		afcWithdrawMo.setAccountId(to.getUserId());
		// 查询用户提现次数和手续费
		WithdrawNumberForMonthRo withdrawNumberForMonthRo = afcWithdrawSvc.getWithdrawNumberForMonth(afcWithdrawMo);
		_log.info("申请提现查询用户提现次数和手续费的返回值为：{}", withdrawNumberForMonthRo);
		// 用户的提现账户
		AfcWithdrawAccountMo withdrawAccountMo;
		// 如果没有传withdrawAccountId(提现账户参数)，获取用户的默认提现账户
		if (to.getWithdrawAccountId() == null) {
			withdrawAccountMo = getDefaultWithdrawAccount(to.getUserId());
		} else {
			withdrawAccountMo = withdrawAccountSvc.getById(to.getWithdrawAccountId());
		}
		if (withdrawAccountMo == null) {
			_log.error("没有发现用户的提现账户: userId-" + to.getUserId() + ", withdrawAccountId-" + to.getWithdrawAccountId());
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.NOT_FOUND_WITHDRAW_ACCOUNT);
			return ro;
		}
		// 查询旧账户信息
		AfcAccountMo oldAccountMo = accountSvc.getById(to.getUserId());
		int compareTo = oldAccountMo.getBalance().compareTo(withdrawNumberForMonthRo.getSeviceCharge().add(new BigDecimal(to.getTradeAmount()), new MathContext(4)));
		_log.info("申请提现账号余额与提现金额+服务费对比的结果为：{}", compareTo);
		// 判断提现账户的余额是否足够（小于提现金额）
		if (compareTo < 0) {
			_log.error("提现用户的余额不足: " + oldAccountMo);
			WithdrawApplyRo ro = new WithdrawApplyRo();
			ro.setResult(WithdrawApplyResultDic.NOT_ENOUGH_MONEY);
			return ro;
		}
		Date now = new Date();
		// 得到交易金额
		BigDecimal tradeAmount = new BigDecimal(to.getTradeAmount().toString());
		// 添加提现申请
		AfcWithdrawMo withdrawMo = new AfcWithdrawMo();
		withdrawMo.setAccountId(oldAccountMo.getId());
		withdrawMo.setWithdrawState((byte) WithdrawStateDic.PROCESSING.getCode());
		// 申请提现的单号
		withdrawMo.setOrderId(to.getOrderId());
		withdrawMo.setTradeTitle(to.getTradeTitle());
		withdrawMo.setTradeDetail(to.getTradeDetail());
		// 提现金额
		withdrawMo.setAmount(tradeAmount);
		// 提现服务费 TODO 添加查询提服务费的接口
		withdrawMo.setSeviceCharge(BigDecimal.ZERO);
		// 实际到账金额(提现金额-提现服务费)
		withdrawMo.setRealAmount(tradeAmount.subtract(withdrawMo.getSeviceCharge()));
		withdrawMo.setApplicantId(to.getOpId());
		withdrawMo.setApplyTime(now);
		withdrawMo.setApplicantMac(to.getMac());
		withdrawMo.setApplicantIp(to.getIp());
		// 记录当前提现账户详细信息
		withdrawMo.setWithdrawType(withdrawAccountMo.getWithdrawType());
		withdrawMo.setContactTel(withdrawAccountMo.getContactTel());
		withdrawMo.setBankAccountNo(withdrawAccountMo.getBankAccountNo());
		withdrawMo.setBankAccountName(withdrawAccountMo.getBankAccountName());
		withdrawMo.setOpenAccountBank(withdrawAccountMo.getOpenAccountBank());
		add(withdrawMo);
		// 添加一笔交易
		AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
		tradeMo.setAccountId(to.getUserId());
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_APPLY.getCode());
		tradeMo.setTradeTime(now);
		tradeSvc.addTrade(tradeMo);
		// 返回成功
		_log.info("申请提现提交成功: {}", to);
		WithdrawApplyRo ro = new WithdrawApplyRo();
		ro.setResult(WithdrawApplyResultDic.SUCCESS);
		ro.setId(withdrawMo.getId());
		return ro;
	}

	/**
	 * 获取用户的默认提现账户
	 */
	private AfcWithdrawAccountMo getDefaultWithdrawAccount(Long userId) {
		AfcWithdrawAccountMo withdrawAccountMo;
		List<AfcWithdrawAccountMo> withdrawAccountMos = withdrawAccountSvc.listByUserId(userId);
		withdrawAccountMo = withdrawAccountMos.get(0);
		return withdrawAccountMo;
	}

	/**
	 * 处理提现
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public WithdrawDealRo deal(WithdrawDealTo to) {
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
			_log.warn("没有填写处理人的用户ID/MAC/IP: {}", to);
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.PARAM_ERROR);
			return ro;
		}
		SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("处理提现发现没有此处理人: " + to.getOpId());
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.NOT_FOUND_OP);
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("处理提现发现处理人已被锁定: " + opUserMo);
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.OP_LOCKED);
			return ro;
		}
		AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("处理提现发现没有此申请: " + to.getId());
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.NOT_FOUND_APPLICATION);
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()) {
			_log.error("处理提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.ALREADY_DEALED);
			return ro;
		}
		// 查询提现账户的V支付账户信息
		AfcAccountMo accountMo = accountSvc.getById(withdrawMo.getAccountId());
		// 判断用户是否被锁定
		if (userSvc.isLocked(accountMo.getId())) {
			_log.error("提现的用户已经被锁定: " + accountMo);
			WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.WITHDRAW_USER_LOCKED);
			return ro;
		}
		// 处理提现
		AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.PROCESSING.getCode());
		modify(modifyMo);
		// 返回成功
		_log.info("处理提现提交成功: {}", to);
		WithdrawDealRo ro = new WithdrawDealRo();
		ro.setResult(WithdrawDealResultDic.SUCCESS);
		return ro;
	}

	/**
	 * 确认提现成功（手动）（ 提现中- ）
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public WithdrawOkRo ok(WithdrawOkTo to) {
		// 当前时间
		Date now = new Date();
		WithdrawOkRo ro = new WithdrawOkRo();
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
			_log.warn("没有填写提现记录的ID/确认人的用户ID/MAC/IP: {}", to);
			ro.setResult(WithdrawOkResultDic.PARAM_ERROR);
			ro.setMsg("参数不正确");
			return ro;
		}
		SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("确认提现成功（手动）发现没有此确认人: " + to.getOpId());
			ro.setResult(WithdrawOkResultDic.NOT_FOUND_OP);
			ro.setMsg("没有此确认人");
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("确认提现成功（手动）发现确认人已被锁定: " + opUserMo);
			ro.setResult(WithdrawOkResultDic.OP_LOCKED);
			ro.setMsg("该确认人已被锁定");
			return ro;
		}
		AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("确认提现成功（手动）发现没有此申请: " + to.getId());
			ro.setResult(WithdrawOkResultDic.NOT_FOUND_APPLICATION);
			ro.setMsg("没有此申请");
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
			_log.error("确认提现成功（手动）发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			ro.setResult(WithdrawOkResultDic.ALREADY_DEALED);
			ro.setMsg("此申请已被处理");
			return ro;
		}
		AfcWithdrawMo afcWithdrawMo = new AfcWithdrawMo();
		afcWithdrawMo.setAccountId(to.getAccountId());
		afcWithdrawMo.setWithdrawState((byte)3);
		_log.info("确认提现成功查询提现次数和服务费的参数为：{}", afcWithdrawMo);
		WithdrawNumberForMonthRo withdrawNumberForMonthRo = afcWithdrawSvc.getWithdrawNumberForMonth(afcWithdrawMo);
		_log.info("确认提现成功查询提现次数和服务费的返回值为：{}", withdrawNumberForMonthRo);
		
		_log.info("确认提现成功查询账户信息的参数为：{}", to.getAccountId());
		AfcAccountMo accountMo = accountSvc.getById(to.getAccountId());
		_log.info("确认提现成功查询账户信息的返回值为：{}", accountMo);
		if (accountMo.getBalance().compareTo(withdrawNumberForMonthRo.getSeviceCharge()) < 0) {
			ro.setResult(WithdrawOkResultDic.SUBTRACT_SERVICE_CHARGE_ERROR);
			ro.setMsg("扣减服务费失败");
			return ro;
		}
		
		// 交易金额
		BigDecimal tradeAmount = withdrawMo.getAmount();
		// 确认提现
		AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.OK.getCode());
		modifyMo.setSeviceCharge(withdrawNumberForMonthRo.getSeviceCharge());
		modifyMo.setRecieverId(to.getOpId());
		modifyMo.setRecieverTime(now);
		modifyMo.setRecieverIp(to.getIp());
		modifyMo.setRecieverMac(to.getMac());
		modifyMo.setFinisherId(to.getOpId());
		modifyMo.setFinisherTime(now);
		modifyMo.setFinisherIp(to.getIp());
		modifyMo.setFinisherMac(to.getMac());
		_log.info("确认提现成功修改提现信息的参数为：{}", modifyMo);
		int modifyResult = afcWithdrawSvc.modify(modifyMo);
		_log.info("确认提现成功修改提现信息的返回值为：{}", modifyResult);
		if (modifyResult != 1) {
			ro.setResult(WithdrawOkResultDic.ERROR);
			ro.setMsg("操作失败");
			return ro;
		}
		// 添加一笔交易
		AfcTradeMo tradeMo = new AfcTradeMo();
		tradeMo.setAccountId(withdrawMo.getAccountId());
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_OK.getCode());
		tradeMo.setTradeTime(now);
		tradeMo.setTradeAmount(tradeAmount);
		// 交易标题
		tradeMo.setTradeTitle(withdrawMo.getTradeTitle());
		// 交易详情
		tradeMo.setTradeDetail(withdrawMo.getTradeDetail());
		// 申请提现的单号
		tradeMo.setOrderId(withdrawMo.getOrderId());
		tradeMo.setOpId(to.getOpId());
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		_log.info("确认提现添加提现账户交易信息的参数为：{}", tradeMo);
		tradeSvc.addTrade(tradeMo);
		
		// 添加一笔交易
		tradeMo = new AfcTradeMo();
		tradeMo.setAccountId(withdrawMo.getAccountId());
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_SEVICE_CHARGE.getCode());
		tradeMo.setTradeTime(new Date());
		tradeMo.setTradeAmount(withdrawNumberForMonthRo.getSeviceCharge());
		// 交易标题
		tradeMo.setTradeTitle(withdrawMo.getTradeTitle());
		// 交易详情
		tradeMo.setTradeDetail(withdrawMo.getTradeDetail());
		// 申请提现的单号
		tradeMo.setOrderId(withdrawMo.getOrderId());
		tradeMo.setOpId(to.getOpId());
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		_log.info("确认提现添加提现服务费账户交易信息的参数为：{}", tradeMo);
		tradeSvc.addTrade(tradeMo);
		
		AfcPlatformTradeMo platformTradeMo = new AfcPlatformTradeMo();
		platformTradeMo.setPlatformTradeType((byte) PlatformTradeTypeDic.WITHDRAW_SEVICE_CHARGE.getCode());
		platformTradeMo.setOrderId(withdrawMo.getOrderId());
		platformTradeMo.setOrderDetailId(withdrawMo.getOrderId());
		platformTradeMo.setTradeAmount(withdrawNumberForMonthRo.getSeviceCharge());
		platformTradeMo.setModifiedTimestamp(System.currentTimeMillis());
		_log.info("确认提现添加平台账户交易信息的参数为：{}", tradeMo);
		afcPlatformTradeSvc.addTrade(platformTradeMo);
		// 返回成功
		_log.info("确认提现成功（手动）成功: {}", to);
		ro.setResult(WithdrawOkResultDic.SUCCESS);
		ro.setMsg("确认提现成功");
		return ro;
	}

	/**
	 * 作废提现（ 余额+，提现中- ）
	 */
	@Override
	public WithdrawCancelRo cancel(WithdrawCancelTo to) {
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp(), to.getReason())) {
			_log.warn("没有填写提现记录的ID/操作人的用户ID/MAC/IP: {}", to);
			WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.PARAM_ERROR);
			ro.setMsg("参数错误");
			return ro;
		}
		SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("作废提现发现没有此操作人: " + to.getOpId());
			WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.NOT_FOUND_OP);
			ro.setMsg("没有此操作人");
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("作废提现发现操作人已被锁定: " + opUserMo);
			WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.OP_LOCKED);
			ro.setMsg("操作人被锁定");
			return ro;
		}
		AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("作废提现发现没有此申请: " + to.getId());
			WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.NOT_FOUND_APPLICATION);
			ro.setMsg("没有此申请");
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()
				&& withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
			_log.error("作废提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.ALREADY_DEALED);
			ro.setMsg("此申请已被处理");
			return ro;
		}
		// 计算当前时间
		Date now = new Date();
		// 得到交易金额
		BigDecimal tradeAmount = withdrawMo.getAmount();
		// 作废提现
		AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.CANCEL.getCode());
		// 作废原因
		modifyMo.setCancelReason(to.getReason());
		modify(modifyMo);
		// 添加一笔交易
		AfcTradeMo tradeMo = new AfcTradeMo();
		tradeMo.setAccountId(withdrawMo.getAccountId());
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_CANCEL.getCode());
		tradeMo.setTradeTime(now);
		tradeMo.setTradeAmount(tradeAmount);
		// 交易标题
		tradeMo.setTradeTitle("作废提现");
		// 交易详情
		tradeMo.setTradeDetail("作废提现");
		// 申请提现的单号
		tradeMo.setOrderId(withdrawMo.getOrderId());
		tradeMo.setOpId(to.getOpId());
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		tradeSvc.addTrade(tradeMo);
		// 返回成功
		_log.info("作废提现成功: {}", to);
		WithdrawCancelRo ro = new WithdrawCancelRo();
		ro.setMsg("拒绝提现成功");
		ro.setResult(WithdrawCancelResultDic.SUCCESS);
		return ro;
	}

	/**
	 * 获取用户提现次数和手续费
	 */
	@Override
	public WithdrawNumberForMonthRo getWithdrawNumberForMonth(AfcWithdrawMo mo) {
		WithdrawNumberForMonthRo numberForMonthRo = new WithdrawNumberForMonthRo();
		_log.info("查询提现次数的参数为：{}", mo);
		int withdrawCount = _mapper.selectWithdrawNumberForMonth(mo);
		_log.info("查询提现次数的返回值为：{}", withdrawCount);
		if (withdrawCount == 0) {
			numberForMonthRo.setWithdrawNumber(0);
			numberForMonthRo.setSeviceCharge(new BigDecimal("0"));
			return numberForMonthRo;
		}
		double pow = Math.pow(2, withdrawCount);
		_log.info("根据提现次数计算出的服务费为: {}", pow);
		numberForMonthRo.setWithdrawNumber(withdrawCount);
		numberForMonthRo.setSeviceCharge(new BigDecimal(pow));
		return numberForMonthRo;
	}
	
	/**
	 * 重写查询提现信息
	 * @param mo
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageInfo<AfcWithdrawRo> lisrEx(AfcWithdrawMo mo, int pageNum, int pageSize, String orderBy) {
		PageInfo<AfcWithdrawRo> doSelectPageInfoEx = new PageInfo<AfcWithdrawRo>();
    	List<AfcWithdrawRo> listEx = new ArrayList<AfcWithdrawRo>();
    	PageInfo<AfcWithdrawMo> doSelectPageInfo = PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectSelective(mo));
    	for (AfcWithdrawMo afcWithdrawMo : doSelectPageInfo.getList()) {
    		AfcWithdrawRo afcWithdrawRo = dozerMapper.map(afcWithdrawMo, AfcWithdrawRo.class);
    		_log.info("查询申请提现账号记录查询用户信息的参数为：{}", afcWithdrawMo.getApplicantId());
    		SucUserMo sucUserMo = sucUserSvc.getById(afcWithdrawMo.getApplicantId());
    		_log.info("查询申请提现账号记录查询用户信息的返回值为：{}", sucUserMo);
    		afcWithdrawRo.setApplicantName(sucUserMo.getWxNickname());
    		listEx.add(afcWithdrawRo);
		}
    	doSelectPageInfoEx = dozerMapper.map(doSelectPageInfo, PageInfo.class);
    	doSelectPageInfoEx.setList(listEx);
    	_log.info("查询申请提现账号记录的返回值为：{}", doSelectPageInfoEx);
    	return doSelectPageInfoEx;
	}
}
