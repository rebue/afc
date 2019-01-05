package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;
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
import rebue.afc.to.ApplyWithdrawTo;
import rebue.afc.withdraw.dic.WithdrawCancelResultDic;
import rebue.afc.withdraw.dic.WithdrawDealResultDic;
import rebue.afc.withdraw.dic.WithdrawStateDic;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.pnt.dic.PointLogTypeDic;
import rebue.pnt.mo.PntAccountMo;
import rebue.pnt.svr.feign.PntAccountSvc;
import rebue.pnt.svr.feign.PntPointSvc;
import rebue.pnt.to.AddPointTradeTo;
import rebue.rna.mo.RnaRealnameMo;
import rebue.rna.svr.feign.RnaSvc;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.IdRo;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;
import rebue.wheel.IdCardValidator;
import rebue.wheel.RegexUtils;

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
	public int add(final AfcWithdrawMo mo) {
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

	@Resource
	private RnaSvc rnaSvc;

	@Resource
	private PntPointSvc pntPointSvc;

	@Resource
	private PntAccountSvc pntAccountSvc;

	/**
	 * 提现申请（ 余额-，提现中+ ） 流程： 1、判断参数 2、验证身份证号 3、查询申请人用户信息 4、查询申请人是否已被锁定
	 * 5、查询申请人账户余额，并判断是否足以扣减提现金额 + 提现服务费 6、添加提现申请信息 7、添加实名认证信息 8、添加申请人账户申请提现交易信息
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public IdRo apply(final ApplyWithdrawTo to) {
		_log.info("申请提现的参数为：{}", to);
		final IdRo ro = new IdRo();
		if (StringUtils.isAnyBlank(to.getBankAccountName(), to.getBankAccountNo(), to.getContactTel(), to.getIp())
				|| to.getApplicantId() == null || to.getWithdrawAmount() == null || to.getWithdrawType() == null) {
			_log.error("申请提现出现参数错误");
			ro.setResult(ResultDic.PARAM_ERROR);
			ro.setMsg("参数错误");
			return ro;
		}

		if (!RegexUtils.matchMobile(to.getContactTel())) {
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("手机号码格式不正确");
			return ro;
		}

		// 查询申请人基本信息
		_log.info("申请提现查询用户信息的参数为：{}", to.getApplicantId());
		final SucUserMo sucUserMo = sucUserSvc.getById(to.getApplicantId());
		_log.info("申请提现查询用户信息的返回值为：{}", sucUserMo);
		if (sucUserMo == null) {
			_log.error("申请提现查询用户信息时发现该用户不存在，申请人id为：{}", to.getApplicantId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("非法用户，请重新登录后再试。。。");
			return ro;
		}

		if (sucUserMo.getIsLock()) {
			_log.error("申请提现时发现该申请人已被锁定，申请人id为：{}", to.getApplicantId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("您已被锁定，请解锁后再重试。。。");
			return ro;
		}
		
		 AfcAccountMo accountMo=new AfcAccountMo();
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			// 查询申请人账户信息
			_log.info("申请提现查询申请人账户信息的参数为：{}", to.getApplicantId());
			
			 accountMo = accountSvc.getById(to.getApplicantId());
			_log.info("申请提现查询申请人账户信息的返回值为：{}", accountMo);
		}else {
			// 查询申请人账户信息
			_log.info("申请提现查询申请人组织账户信息的参数为：{}", to.getApplicantOrgId());
			
			 accountMo = accountSvc.getById(to.getApplicantOrgId());
			_log.info("申请提现查询申请人组织账户信息的返回值为：{}", accountMo);
		}

		if (accountMo == null) {
			_log.error("申请提现查询申请人账户信息时发现该申请人不存在提现账户，申请人id为：{}", to.getApplicantId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("非法用户，请重新登录后再试。。。");
			return ro;
		}

		// 查询本月已提现次数
		final AfcWithdrawMo afcWithdrawMo = new AfcWithdrawMo();
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			afcWithdrawMo.setAccountId(to.getApplicantId());
		}else {
			afcWithdrawMo.setAccountId(to.getApplicantOrgId());
		}

		_log.info("申请提现查询本月已提现次数的参数为：{}", afcWithdrawMo);
		WithdrawNumberForMonthRo withdrawNumberForMonthRo = afcWithdrawSvc.getWithdrawNumberForMonth(afcWithdrawMo);
		_log.info("申请提现查询本月已提现次数的返回值为：{}", withdrawNumberForMonthRo);
		// 申请人账户余额
		final BigDecimal balance = accountMo.getBalance().setScale(4, BigDecimal.ROUND_HALF_UP);
		if (balance.compareTo(to.getWithdrawAmount()) < 0) {
			_log.info("申请提现申请账户余额对比提现金额+提现服务费时出现账户余额不足以扣减，申请人id为：{}", to.getApplicantId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("您的余额不足");
			return ro;
		}
		//判断是否是供应商提现1是个人2是供应商
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			_log.info("申请提现查询积分账号信息的参数为：{}", to.getApplicantId());
			PntAccountMo pntAccountMo = pntAccountSvc.getById(to.getApplicantId());
			_log.info("申请提现查询积分账号信息的参数为：{}", pntAccountMo);
			if (pntAccountMo == null) {
				_log.error("申请提现查询积分账号时发现没有积分账号信息，申请的参数为：{}", to);
				ro.setResult(ResultDic.FAIL);
				ro.setMsg("没有发现积分账号信息");
				return ro;
			}

			if ((pntAccountMo.getPoint().subtract(BigDecimal.valueOf(withdrawNumberForMonthRo.getSeviceCharge())))
					.compareTo(BigDecimal.ZERO) < 0) {
				_log.error("申请提现时发现该账号的积分不足以抵扣本次提现，申请的信息为：{}", to);
				ro.setResult(ResultDic.FAIL);
				ro.setMsg("积分不足");
				return ro;
			}
		}

		// 添加申请提现信息
		final AfcWithdrawMo withdrawMo = new AfcWithdrawMo();
		withdrawMo.setOrderId(to.getOrderId());
		withdrawMo.setWithdrawState((byte) 1);
		withdrawMo.setTradeTitle(to.getTradeTitle());
		withdrawMo.setTradeDetail(to.getTradeDetail());
		withdrawMo.setAmount(to.getWithdrawAmount());
		withdrawMo.setRealAmount(to.getWithdrawAmount());
		withdrawMo.setSeviceCharge(new BigDecimal("0"));
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			withdrawMo.setAccountId(to.getApplicantId());
		}else {
			withdrawMo.setAccountId(to.getApplicantOrgId());
		}
		withdrawMo.setApplicantId(to.getApplicantId());
		withdrawMo.setApplyTime(new Date());
		withdrawMo.setApplicantMac("不再获取mac地址");
		withdrawMo.setApplicantIp(to.getIp());
		withdrawMo.setWithdrawType(to.getWithdrawType());
		withdrawMo.setContactTel(to.getContactTel());
		withdrawMo.setBankAccountNo(to.getBankAccountNo());
		withdrawMo.setBankAccountName(to.getBankAccountName());
		withdrawMo.setOpenAccountBank(to.getWithdrawType() == 1 ? to.getOpenAccountBank() : "支付宝网银");
		_log.info("申请提现添加提现信息的参数为：{}", withdrawMo);
		final int addResult = afcWithdrawSvc.add(withdrawMo);
		_log.info("申请提现添加提现信息的返回值为：{}", addResult);
		if (addResult != 1) {
			_log.info("申请提现添加提现信息时出现错误，申请人id为：{}", to.getApplicantId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("提现出错");
			return ro;
		}

		// 添加一笔账户交易
		final AfcTradeMo tradeMo = new AfcTradeMo();
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			tradeMo.setAccountId(to.getApplicantId());
		}else {
			tradeMo.setAccountId(to.getApplicantOrgId());
		}
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_APPLY.getCode());
		tradeMo.setTradeTime(new Date());
		tradeMo.setTradeAmount(to.getWithdrawAmount());
		// 交易标题
		tradeMo.setTradeTitle(to.getTradeTitle());
		// 交易详情
		tradeMo.setTradeDetail(to.getTradeDetail());
		// 申请提现的单号
		tradeMo.setOrderId(to.getOrderId());
		tradeMo.setOpId(to.getApplicantId());
		tradeMo.setMac("不再获取mac地址");
		tradeMo.setIp(to.getIp());
		_log.info("确认提现添加提现交易信息的参数为：{}", tradeMo);
		tradeSvc.addTrade(tradeMo);
		//获取实名认证信息
		RnaRealnameMo rnaRealnameMo=new RnaRealnameMo();
		if(to.getAccountType() ==null ||to.getAccountType() !=2) {
			 rnaRealnameMo = rnaSvc.getById(to.getApplicantId());
		}else {
			rnaRealnameMo = rnaSvc.getById(to.getApplicantOrgId());
		}
		if (rnaRealnameMo == null) {
			if (to.getIdCard() == null || to.getIdCard().equals("") || to.getIdCard().equals("null")) {
				throw new RuntimeException("身份证号不能为空");
			}
			// 验证身份证号
			if (!IdCardValidator.validate(to.getIdCard())) {
				_log.error("申请提现验证身份证号时出现身份证号格式不正确，申请人id为：{}", to.getApplicantId());
				throw new RuntimeException("身份证号格式不正确");
			}

			final RnaRealnameMo realnameMo = new RnaRealnameMo();
			if(to.getAccountType() ==null ||to.getAccountType() !=2) {
				realnameMo.setId(to.getApplicantId());
			}else {
				realnameMo.setId(to.getApplicantOrgId());
			}
			realnameMo.setRealName(to.getBankAccountName());
			realnameMo.setIdCard(to.getIdCard());
			realnameMo.setIsCorrect(false);
			_log.info("申请提现添加实名认证信息的参数为：{}", realnameMo);
			final Ro addRnaRealNameRo = rnaSvc.add(realnameMo);
			_log.info("申请提现添加实名认证信息的返回值为：{}", addRnaRealNameRo);
			if (addRnaRealNameRo.getResult().getCode() != 1) {
				_log.error("申请提现添加实名认证信息出错，申请人id为：{}", to.getApplicantId());
				throw new RuntimeException("添加出错");
			}
		}

		_log.info("申请提现成功，申请人id为：{}", to.getApplicantId());
		ro.setId(withdrawMo.getId().toString());
		ro.setResult(ResultDic.SUCCESS);
		ro.setMsg("申请成功");
		return ro;
	}

	/**
	 * 获取用户的默认提现账户
	 */
	@SuppressWarnings("unused")
	private AfcWithdrawAccountMo getDefaultWithdrawAccount(final Long userId) {
		AfcWithdrawAccountMo withdrawAccountMo;
		final List<AfcWithdrawAccountMo> withdrawAccountMos = withdrawAccountSvc.listByUserId(userId);
		withdrawAccountMo = withdrawAccountMos.get(0);
		return withdrawAccountMo;
	}

	/**
	 * 处理提现
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public WithdrawDealRo deal(final WithdrawDealTo to) {
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
			_log.warn("没有填写处理人的用户ID/MAC/IP: {}", to);
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.PARAM_ERROR);
			ro.setMsg("参数错误");
			return ro;
		}
		final SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("处理提现发现没有此处理人: " + to.getOpId());
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.NOT_FOUND_OP);
			ro.setMsg("您未登录");
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("处理提现发现处理人已被锁定: " + opUserMo);
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.OP_LOCKED);
			ro.setMsg("您已被锁定");
			return ro;
		}
		final AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("处理提现发现没有此申请: " + to.getId());
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.NOT_FOUND_APPLICATION);
			ro.setMsg("没有此申请");
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()) {
			_log.error("处理提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.ALREADY_DEALED);
			ro.setMsg("此申请已被处理");
			return ro;
		}
		// 查询提现账户的V支付账户信息
		final AfcAccountMo accountMo = accountSvc.getById(withdrawMo.getAccountId());
		// 判断用户是否被锁定
		if (userSvc.isLocked(accountMo.getId())) {
			_log.error("提现的用户已经被锁定: " + accountMo);
			final WithdrawDealRo ro = new WithdrawDealRo();
			ro.setResult(WithdrawDealResultDic.WITHDRAW_USER_LOCKED);
			ro.setMsg("提现的用户已经被锁定");
			return ro;
		}
		// 处理提现
		final AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.PROCESSING.getCode());
		modify(modifyMo);
		// 返回成功
		_log.info("处理提现提交成功: {}", to);
		final WithdrawDealRo ro = new WithdrawDealRo();
		ro.setResult(WithdrawDealResultDic.SUCCESS);
		ro.setMsg("处理成功");
		return ro;
	}

	/**
	 * 确认提现成功（手动）（ 提现中- ） 流程： 1、判断参数是否正确 2、查询审核人是否存在 3、判断审核人是否已被锁定 4、查询是否有该提现记录
	 * 5、查询该提现申请是否已被处理 6、查询提现次数和服务费（已成功） 7、查询提现用户账户余额是否足够扣减服务费 8、查询是否已有提现账号，如果没有则添加
	 * 9、修改实名认证信息 10、修改提现信息 11、添加提现账号提现交易信息 12、添加提现账号提现服务费交易信息 13、添加平台提现手续费交易信息
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Ro ok(final WithdrawOkTo to) {
		// 当前时间
		final Date now = new Date();
		final Ro ro = new Ro();
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp())) {
			_log.warn("没有填写提现记录的ID/确认人的用户ID/MAC/IP: {}", to);
			ro.setResult(ResultDic.PARAM_ERROR);
			ro.setMsg("参数不正确");
			return ro;
		}
		final SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("确认提现成功（手动）发现没有此确认人: " + to.getOpId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("没有此确认人");
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("确认提现成功（手动）发现确认人已被锁定: " + opUserMo);
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("该确认人已被锁定");
			return ro;
		}
		final AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("确认提现成功（手动）发现没有此申请: " + to.getId());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("没有此申请");
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
			_log.error("确认提现成功（手动）发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("此申请已被处理");
			return ro;
		}
		final AfcWithdrawMo afcWithdrawMo = new AfcWithdrawMo();
		afcWithdrawMo.setAccountId(to.getAccountId());
		afcWithdrawMo.setWithdrawState((byte) 3);
		_log.info("确认提现成功查询提现次数和服务费的参数为：{}", afcWithdrawMo);
		final WithdrawNumberForMonthRo withdrawNumberForMonthRo = afcWithdrawSvc
				.getWithdrawNumberForMonth(afcWithdrawMo);
		_log.info("确认提现成功查询提现次数和服务费的返回值为：{}", withdrawNumberForMonthRo);

		// 修改时间戳
		Long modifiedTimestamp = System.currentTimeMillis();

		AfcWithdrawAccountMo withdrawAccountMo = new AfcWithdrawAccountMo();
		withdrawAccountMo.setAccountId(withdrawMo.getAccountId());
		_log.info("确认提现成功查询提现账号信息的参数为：{}", withdrawAccountMo);
		AfcWithdrawAccountMo afcWithdrawAccountMo = withdrawAccountSvc.getOne(withdrawAccountMo);
		_log.info("确认提现成功查询提现账号信息的返回值为：{}", afcWithdrawAccountMo);
		if (afcWithdrawAccountMo == null) {
			afcWithdrawAccountMo = new AfcWithdrawAccountMo();
			afcWithdrawAccountMo.setIsDef(true);
			afcWithdrawAccountMo.setAccountId(to.getAccountId());
			afcWithdrawAccountMo.setWithdrawType(withdrawMo.getWithdrawType());
			afcWithdrawAccountMo.setContactTel(withdrawMo.getContactTel());
			afcWithdrawAccountMo.setBankAccountNo(withdrawMo.getBankAccountNo());
			afcWithdrawAccountMo.setBankAccountName(withdrawMo.getBankAccountName());
			afcWithdrawAccountMo.setOpenAccountBank(withdrawMo.getOpenAccountBank());
			afcWithdrawAccountMo.setOpId(to.getOpId());
			afcWithdrawAccountMo.setMac("不再获取mac地址");
			afcWithdrawAccountMo.setIp(to.getIp());
			afcWithdrawAccountMo.setModifiedTimestamp(modifiedTimestamp);
			_log.info("确认提现添加提现账户信息的参数为：{}", afcWithdrawAccountMo);
			final int addWithdrawAccountResult = withdrawAccountSvc.add(afcWithdrawAccountMo);
			_log.info("确认提现添加提现账户信息的返回值为：{}", addWithdrawAccountResult);
			if (addWithdrawAccountResult != 1) {
				_log.error("确认提现添加提现账号信息出错，提现用户id为：{}", to.getAccountId());
				ro.setResult(ResultDic.FAIL);
				ro.setMsg("添加提现账户出错");
				return ro;
			}
		} else {
			withdrawAccountMo = new AfcWithdrawAccountMo();
			withdrawAccountMo.setId(afcWithdrawAccountMo.getId());
			withdrawAccountMo.setWithdrawType(withdrawMo.getWithdrawType());
			withdrawAccountMo.setContactTel(withdrawMo.getContactTel());
			withdrawAccountMo.setBankAccountNo(withdrawMo.getBankAccountNo());
			withdrawAccountMo.setBankAccountName(withdrawMo.getBankAccountName());
			withdrawAccountMo.setOpenAccountBank(withdrawMo.getOpenAccountBank());
			withdrawAccountMo.setModifiedTimestamp(modifiedTimestamp);
			_log.info("添加提现账户修改提现账户信息的参数为：{}", withdrawAccountMo);
			final int modifyWithdrawAccountResult = withdrawAccountSvc.modify(withdrawAccountMo);
			_log.info("添加提现账户修改提现账户信息的返回值为：{}", modifyWithdrawAccountResult);
			if (modifyWithdrawAccountResult != 1) {
				_log.error("确认提现修改提现账号信息出错，提现用户id为：{}", to.getAccountId());
				ro.setResult(ResultDic.FAIL);
				ro.setMsg("修改提现账户出错");
				return ro;
			}
		}

		// 交易金额
		final BigDecimal tradeAmount = withdrawMo.getAmount();
		// 确认提现
		final AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.OK.getCode());
		modifyMo.setSeviceCharge(BigDecimal.ZERO);
		modifyMo.setRecieverId(to.getOpId());
		modifyMo.setRecieverTime(now);
		modifyMo.setRecieverIp(to.getIp());
		modifyMo.setRecieverMac(to.getMac());
		modifyMo.setFinisherId(to.getOpId());
		modifyMo.setFinisherTime(now);
		modifyMo.setFinisherIp(to.getIp());
		modifyMo.setFinisherMac(to.getMac());
		_log.info("确认提现成功修改提现信息的参数为：{}", modifyMo);
		final int modifyResult = afcWithdrawSvc.modify(modifyMo);
		_log.info("确认提现成功修改提现信息的返回值为：{}", modifyResult);
		if (modifyResult != 1) {
			_log.error("确认提现成功修改提现信息出错，提现用户id为：{}", to.getAccountId());
			throw new RuntimeException("操作失败");
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

		// 添加一笔交易,提现服务费
		tradeMo = new AfcTradeMo();
		tradeMo.setAccountId(withdrawMo.getAccountId());
		tradeMo.setTradeType((byte) TradeTypeDic.WITHDRAW_SEVICE_CHARGE.getCode());
		tradeMo.setTradeTime(new Date());
		tradeMo.setTradeAmount(BigDecimal.ZERO);
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
		if(tradeMo.getTradeAmount().compareTo(BigDecimal.ZERO) !=0) {
			tradeSvc.addTrade(tradeMo);
		}


		final AfcPlatformTradeMo platformTradeMo = new AfcPlatformTradeMo();
		platformTradeMo.setPlatformTradeType((byte) PlatformTradeTypeDic.WITHDRAW_SEVICE_CHARGE.getCode());
		platformTradeMo.setOrderId(withdrawMo.getOrderId());
		platformTradeMo.setOrderDetailId(withdrawMo.getOrderId());
		platformTradeMo.setTradeAmount(BigDecimal.ZERO);
		platformTradeMo.setModifiedTimestamp(System.currentTimeMillis());
		_log.info("确认提现添加平台账户交易信息的参数为：{}", tradeMo);
		afcPlatformTradeSvc.addTrade(platformTradeMo);

		final RnaRealnameMo realnameMo = new RnaRealnameMo();
		realnameMo.setId(to.getAccountId());
		realnameMo.setIsCorrect(true);
		_log.info("确认提现修改实名认证信息的参数为：{}", realnameMo);
		final Ro modifyRnaResult = rnaSvc.modify(realnameMo);
		_log.info("确认提现修改实名认证信息的返回值为：{}", modifyRnaResult);
		if (modifyRnaResult.getResult().getCode() != 1) {
			throw new RuntimeException("操作出错");
		}

		// 查询提现帐号类型属于是组织还是个人，组织不需要扣除积分
		_log.info("查询提现帐号是组织还是个人的参数为：{}", to.getAccountId());
		AfcAccountMo afcAccountMo = accountSvc.getById(to.getAccountId());
		_log.info("查询提现帐号是组织还是个人的结果为：afcAccountMo-{}",afcAccountMo);
		if (afcAccountMo.getAccountType() == 1) {

			AddPointTradeTo addPointTradeTo = new AddPointTradeTo();
			addPointTradeTo.setAccountId(to.getAccountId());
			addPointTradeTo.setPointLogType((byte) PointLogTypeDic.VPAY_WITHDRAW.getCode());
			addPointTradeTo.setChangedTitile("大卖网络-用户提现");
			addPointTradeTo.setOrderId(to.getId());
			addPointTradeTo.setChangedPoint(BigDecimal.valueOf(withdrawNumberForMonthRo.getSeviceCharge()).negate());
			_log.info("确认提现添加一笔积分交易的参数为：{}", addPointTradeTo);
			Ro addPointTradeRo = pntPointSvc.addPointTrade(addPointTradeTo);
			_log.info("确认提现添加一笔积分交易的返回值为：{}", addPointTradeRo);
			if (addPointTradeRo == null) {
				_log.error("确认提现添加一笔积分交易出现异常，请求的参数为：{}", to);
				ro.setResult(ResultDic.FAIL);
				ro.setMsg("扣减服务费出现异常");
				return ro;
			}

			if (addPointTradeRo.getResult() != ResultDic.SUCCESS) {
				_log.error("确认提现添加一笔积分交易时出现错误，请求的参数为：{}", to);
				return addPointTradeRo;
			}
		}
		// 返回成功
		_log.info("确认提现成功（手动）成功: {}", to);
		ro.setResult(ResultDic.SUCCESS);
		ro.setMsg("确认提现成功");
		return ro;
	}

	/**
	 * 作废提现（ 余额+，提现中- ） 流程： 1、判断参数 2、查询操作人信息 3、查询操作人是否已被锁定 4、查询是否有该申请信息
	 * 5、查询该申请是否已被处理 6、修改提现信息 7、添加申请人账户作废提现交易信息
	 */
	@Override
	public WithdrawCancelRo cancel(final WithdrawCancelTo to) {
		if (to.getId() == null || StringUtils.isAnyBlank(to.getMac(), to.getIp(), to.getReason())) {
			_log.warn("没有填写提现记录的ID/操作人的用户ID/MAC/IP: {}", to);
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.PARAM_ERROR);
			ro.setMsg("参数错误");
			return ro;
		}
		final SucUserMo opUserMo = userSvc.getById(to.getOpId());
		if (opUserMo == null) {
			_log.error("作废提现发现没有此操作人: " + to.getOpId());
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.NOT_FOUND_OP);
			ro.setMsg("没有此操作人");
			return ro;
		}
		if (opUserMo.getIsLock()) {
			_log.error("作废提现发现操作人已被锁定: " + opUserMo);
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.OP_LOCKED);
			ro.setMsg("操作人被锁定");
			return ro;
		}
		final AfcWithdrawMo withdrawMo = _mapper.selectByPrimaryKey(to.getId());
		if (withdrawMo == null) {
			_log.error("作废提现发现没有此申请: " + to.getId());
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.NOT_FOUND_APPLICATION);
			ro.setMsg("没有此申请");
			return ro;
		}
		if (withdrawMo.getWithdrawState() != WithdrawStateDic.APPLY.getCode()
				&& withdrawMo.getWithdrawState() != WithdrawStateDic.PROCESSING.getCode()) {
			_log.error("作废提现发现此申请已被处理: withdrawState-" + withdrawMo.getWithdrawState());
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.ALREADY_DEALED);
			ro.setMsg("此申请已被处理");
			return ro;
		}
		// 计算当前时间
		final Date now = new Date();
		// 得到交易金额
		final BigDecimal tradeAmount = withdrawMo.getAmount();
		// 作废提现
		final AfcWithdrawMo modifyMo = new AfcWithdrawMo();
		modifyMo.setId(withdrawMo.getId());
		modifyMo.setWithdrawState((byte) WithdrawStateDic.CANCEL.getCode());
		// 作废原因
		modifyMo.setCancelReason(to.getReason());
		_log.error("作废提现修改提现信息的参数为：{}", modifyMo);
		final int modifyResult = modify(modifyMo);
		_log.info("作废提现修改提现信息的返回值为：{}", modifyResult);
		if (modifyResult != 1) {
			final WithdrawCancelRo ro = new WithdrawCancelRo();
			ro.setResult(WithdrawCancelResultDic.ERROR);
			ro.setMsg("操作失败");
			return ro;
		}
		// 添加一笔交易
		final AfcTradeMo tradeMo = new AfcTradeMo();
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
		final WithdrawCancelRo ro = new WithdrawCancelRo();
		ro.setMsg("拒绝提现成功");
		ro.setResult(WithdrawCancelResultDic.SUCCESS);
		return ro;
	}

	/**
	 * 获取用户提现次数和手续费
	 */
	@Override
	public WithdrawNumberForMonthRo getWithdrawNumberForMonth(final AfcWithdrawMo mo) {
		final WithdrawNumberForMonthRo numberForMonthRo = new WithdrawNumberForMonthRo();
		_log.info("查询提现次数的参数为：{}", mo);
		final int withdrawCount = _mapper.selectWithdrawNumberForMonth(mo);
		_log.info("查询提现次数的返回值为：{}", withdrawCount);
		if (withdrawCount == 0) {
			numberForMonthRo.setWithdrawNumber(0);
			numberForMonthRo.setSeviceCharge(0);
			return numberForMonthRo;
		}
		Integer serviceCharge = withdrawCount * 5;
		_log.info("根据提现次数计算出的服务费为: {}", serviceCharge);
		numberForMonthRo.setWithdrawNumber(withdrawCount);
		numberForMonthRo.setSeviceCharge(serviceCharge);
		return numberForMonthRo;
	}

	/**
	 * 重写查询提现信息
	 * 
	 * @param mo
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageInfo<AfcWithdrawRo> lisrEx(final AfcWithdrawMo mo, final int pageNum, final int pageSize,
			final String orderBy) {
		PageInfo<AfcWithdrawRo> doSelectPageInfoEx = new PageInfo<>();
		final List<AfcWithdrawRo> listEx = new ArrayList<>();
		final PageInfo<AfcWithdrawMo> doSelectPageInfo = PageHelper.startPage(pageNum, pageSize, orderBy)
				.doSelectPageInfo(() -> _mapper.selectSelective(mo));
		for (final AfcWithdrawMo afcWithdrawMo : doSelectPageInfo.getList()) {
			final AfcWithdrawRo afcWithdrawRo = dozerMapper.map(afcWithdrawMo, AfcWithdrawRo.class);
			_log.info("查询申请提现账号记录查询用户信息的参数为：{}", afcWithdrawMo.getApplicantId());
			final SucUserMo sucUserMo = sucUserSvc.getById(afcWithdrawMo.getApplicantId());
			_log.info("查询申请提现账号记录查询用户信息的返回值为：{}", sucUserMo);
			if(sucUserMo.getWxNickname() !=null) {
				afcWithdrawRo.setApplicantName(sucUserMo.getWxNickname());
			}else if(sucUserMo.getLoginName() !=null) {
				afcWithdrawRo.setApplicantName(sucUserMo.getLoginName());
			}

			listEx.add(afcWithdrawRo);
		}
		doSelectPageInfoEx = dozerMapper.map(doSelectPageInfo, PageInfo.class);
		doSelectPageInfoEx.setList(listEx);
		_log.info("查询申请提现账号记录的返回值为：{}", doSelectPageInfoEx);
		return doSelectPageInfoEx;
	}

	/**
	 * 查询用户提现中的信息
	 * 
	 * @param accountId
	 * @param pageNum
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	@Override
	public PageInfo<AfcWithdrawMo> selectWithdrawApplying(final Long accountId, final int pageNum, final int pageSize,
			final String orderBy) {
		_log.info("list: accountId-{}; pageNum-{}; orderBy-{}; pageSize-{}", accountId, pageNum, pageSize, orderBy);
		return PageHelper.startPage(pageNum, pageSize, orderBy)
				.doSelectPageInfo(() -> _mapper.selectWithdrawApplying(accountId));
	}
}
