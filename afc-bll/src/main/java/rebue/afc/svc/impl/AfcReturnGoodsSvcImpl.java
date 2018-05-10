package rebue.afc.svc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcFlowMo;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.returngoods.dic.ReturnGoodsByBuyerResultDic;
import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.afc.returngoods.to.ReturnGoodsByBuyerTo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcReturnGoodsSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;
import rebue.wheel.idworker.IdWorker3;

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
public class AfcReturnGoodsSvcImpl implements AfcReturnGoodsSvc {
	private final static Logger _log = LoggerFactory.getLogger(AfcReturnGoodsSvcImpl.class);

	@Resource
	private SucUserSvc userSvc;
	@Resource
	private AfcAccountSvc accountSvc;
	@Resource
	private AfcTradeSvc tradeSvc;
	@Resource
	private AfcFlowSvc flowSvc;
	@Resource
	private AfcPaySvc paySvc;

	@Resource
	private Mapper dozerMapper;

	@Value("${appid:0}")
	private int _appid;

	protected IdWorker3 _idWorker;

	@PostConstruct
	public void init() {
		_idWorker = new IdWorker3(_appid);
	}

	/**
	 * XXX AFC : 交易 : 退货-买家退货（ 余额+，返现金+ ）
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ReturnGoodsByBuyerRo returnGoodsByBuyer(ReturnGoodsByBuyerTo to) {
		// 用户编号
		Long userId = to.getUserId();
		// 操作人编号
		Long opId = to.getOpId();
		// 退到余额金额
		Double balanceAmount = to.getBalanceAmount();
		// 退到返现金金额
		Double cashbackAmount = to.getCashbackAmount();
		// 退货单号
		String returnGoodsOrderId = to.getReturnGoodsOrderId();
		// 销售单号
		String saleOrderId = to.getSaleOrderId();

		if (userId == null || balanceAmount == null || cashbackAmount == null || opId == null || StringUtils
				.isAnyBlank(returnGoodsOrderId, saleOrderId, to.getTradeTitle(), to.getMac(), to.getIp())) {
			_log.warn("没有填写买家的用户ID/退货单ID/销售单ID/退货的标题/退货到余额的金额/退货到返现金的金额/操作人账号/MAC/IP: {}", to);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.PARAM_ERROR);
			return ro;
		}

		_log.info("用户退货退款查询操作人信息的参数为：{}", opId);
		SucUserMo opUserMo = userSvc.getById(opId);
		_log.info("用户退货退款查询操作人信息的返回值为：{}", opUserMo.toString());
		if (opUserMo.getId() == null) {
			_log.error("用户退货退款查询用户信息时发现没有此操作人，操作人编号为：{}", opId);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_OP);
			return ro;
		}

		if (opUserMo.getIsLock()) {
			_log.error("用户退货退款时发现操作人已被锁定，操作人编号为：", opUserMo);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.OP_LOCKED);
			return ro;
		}

		_log.info("用户退货退款查询用户信息的参数为：{}", userId);
		SucUserMo userMo = userSvc.getById(userId);
		_log.info("用户退货退款查询用户信息的返回值为：{}", userMo.toString());
		if (userMo.getId() == null) {
			_log.error("用户退货退款时发现没有此用户，用户编号为：{}", userId);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_USER);
			return ro;
		}
		if (userMo.getIsLock()) {
			_log.error("用户退货退款时发现用户已被锁定，用户编号为：{}" + userMo);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.USER_LOCKED);
			return ro;
		}

		// 检查账户有没有支付过此销售单
		AfcPayMo condition = new AfcPayMo();
		condition.setAccountId(userId);
		condition.setOrderId(saleOrderId);
		_log.info("用户退货退款查询支付订单信息的参数为：{}", condition.toString());
		List<AfcPayMo> pays = paySvc.list(condition);
		_log.info("用户退货退款查询支付订单信息的返回值为：{}", String.valueOf(pays));
		if (pays.isEmpty()) {
			_log.error("用户退货退款时发现用户未支付此销售单，销售单号为：{}", saleOrderId);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.NOT_FOUND_ORDERID);
			return ro;
		}

		// 得到交易金额
		BigDecimal tradeAmount = new BigDecimal(balanceAmount)
				.add(new BigDecimal(to.getCashbackAmount().toString()));

		// 检查退货金额不能超过支付金额
		// 计算支付总额
		BigDecimal payTotal = new BigDecimal(0);
		for (AfcPayMo afcPayMo : pays) {
			payTotal = payTotal.add(afcPayMo.getPayAmount());
		}
		_log.info("用户退货退款查询退货总额的参数为：{}", saleOrderId);
		// 计算曾经退货总额
		BigDecimal returnGoodsTotal = tradeSvc.getReturnGoodsTotalAmountByOrder(saleOrderId);
		_log.info("用户退货退款查询退货总额的返回值为：{}", returnGoodsTotal);
		if (returnGoodsTotal == null)
			returnGoodsTotal = new BigDecimal(0);
		// 比较大小
		if (payTotal.compareTo(returnGoodsTotal.add(tradeAmount)) < 0) {
			_log.error("退货金额不能超过销售金额: " + to);
			ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
			ro.setResult(ReturnGoodsByBuyerResultDic.NOT_ENOUGH_MONEY);
			return ro;
		}

		// 计算当前时间
		Date now = new Date();
		// 生成本次交易的ID
		Long tradeId = _idWorker.getId();

		_log.info("用户退货退款查询用户账号信息的参数为：{}", userId);
		// 查询旧账户信息
		AfcAccountMo oldAccountMo = accountSvc.getById(userId);
		_log.info("用户退货退款查询用户账号信息的返回值为：{}", oldAccountMo.toString());

		// 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)
		AfcTradeMo tradeMo = new AfcTradeMo();
		tradeMo.setId(tradeId);
		tradeMo.setAccountId(userId);
		tradeMo.setTradeType((byte) TradeTypeDic.RETURN_GOODS_BY_BUYER.getCode());
		tradeMo.setTradeAmount(tradeAmount);
		tradeMo.setTradeTitle(to.getTradeTitle());
		tradeMo.setTradeDetail(to.getTradeDetail());
		tradeMo.setTradeTime(now);
		// XXX AFC : 交易订单号 : 退货-买家退货 : 订单ID=销售单ID||退货单ID
		tradeMo.setOrderId(saleOrderId + "||" + returnGoodsOrderId);
		tradeMo.setOpId(opId);
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		try {
			_log.info("用户退货退款添加账户交易信息的参数为：{}", tradeMo.toString());
			tradeSvc.add(tradeMo);
		} catch (DuplicateKeyException e) {
			_log.error("用户退货退款添加账户交易信息时出错，用户编号为：{}", userId);
			throw new RuntimeException("添加账户交易信息出错", e);
		}

		// 余额+, 返现金+
		AfcAccountMo newAccountMo = new AfcAccountMo();
		newAccountMo.setId(to.getUserId());
		newAccountMo.setBalance(oldAccountMo.getBalance().add(new BigDecimal(to.getBalanceAmount().toString())));
		newAccountMo.setCashback(oldAccountMo.getCashback().add(new BigDecimal(to.getCashbackAmount().toString())));
		newAccountMo.setModifiedTimestamp(now.getTime());
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", userId);
		map.put("balance", newAccountMo.getBalance());
		map.put("oldBalance", oldAccountMo.getBalance());
		map.put("cashback", newAccountMo.getCashback());
		map.put("oldCashback", oldAccountMo.getCashback());
		map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
		map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
		_log.info("用户退货退款修改账户信息的参数为：{}", String.valueOf(map));
		if (accountSvc.trade(map) != 1) {
			_log.error("用户退货退款修改账户信息时出现错误，用户编号为：{}", userId);
			throw new RuntimeException("修改账户信息失败");
		}

		// 添加账户流水
		AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
		dozerMapper.map(newAccountMo, flowMo);
		flowMo.setAccountId(userId);
		flowMo.setId(tradeId);
		flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
		_log.info("用户退货退款添加账户流水的参数为：{}", flowMo.toString());
		flowSvc.add(flowMo);

		// 返回成功
		_log.info("退货-买家退货成功: {}", to);
		ReturnGoodsByBuyerRo ro = new ReturnGoodsByBuyerRo();
		ro.setResult(ReturnGoodsByBuyerResultDic.SUCCESS);
		return ro;
	}

	/**
	 * 用户退货退款并扣减返现金额 Title: returnRefundAndSubtractCashback Description:
	 * 
	 * @param to
	 * @return
	 * @date 2018年5月8日 下午2:04:06
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Map<String, Object> returnRefundAndSubtractCashback(ReturnGoodsByBuyerTo to) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 操作人编号
		Long opId = to.getOpId();
		// 用户编号
		Long userId = to.getUserId();
		// 退到余额的金额
		Double balanceAmount = to.getBalanceAmount();
		// 退到返现金的金额
		Double cashbackAmount = to.getCashbackAmount();
		// 扣减返现金的金额
		Double subtractCashback = to.getSubtractCashback();
		// 销售单ID
		String saleOrderId = to.getSaleOrderId();
		// 退货单ID
		String returnGoodsOrderId = to.getReturnGoodsOrderId();

		if (userId == null || balanceAmount == null || cashbackAmount == null || opId == null
				|| StringUtils.isAnyBlank(returnGoodsOrderId, saleOrderId, to.getTradeTitle(), to.getMac(), to.getIp())
				|| subtractCashback == null) {
			_log.error("用户退货退款并扣减返现金额时发现参数不全，传过来的参数为：{}", to);
			resultMap.put("result", -1);
			resultMap.put("msg", "参数不正确");
			return resultMap;
		}

		_log.info("用户退货退款并扣减返现金额根据操作人ID获取操作人信息的参数为：{}", opId);
		// 根据用户ID获取操作人信息
		SucUserMo opUserMo = new SucUserMo();
		SucUserMo userMo = new SucUserMo();
		try {
			opUserMo = userSvc.getById(opId);
			_log.info("用户退货退款并扣减返现金额根据操作人ID获取操作人信息的返回值为：{}", opUserMo.toString());
			
			_log.info("用户退货退款并扣减返现金额根据用户编号获取用户信息的参数为：{}", userId);
			userMo = userSvc.getById(userId);
			_log.info("用户退货退款并扣减返现金额根据用户编号获取用户信息的返回值为：{}", userMo.toString());
			if (userMo.getId() == null) {
				_log.error("用户退货退款并扣减返现金额时发现没有此用户: " + userId);
				resultMap.put("result", -2);
				resultMap.put("msg", "买家不存在");
				return resultMap;
			}
		} catch (Exception e) {
			_log.error("用户退货退款并扣减返现金额发现没有此操作人: " + opId);
			resultMap.put("result", -3);
			resultMap.put("msg", "没有此操作人");
			e.printStackTrace();
			return resultMap;
		}

		if (opUserMo.getIsLock()) {
			_log.error("用户退货退款并扣减返现金额发现操作人已被锁定，操作人编号为：{}", opId);
			resultMap.put("result", -4);
			resultMap.put("msg", "操作人被锁定");
			return resultMap;
		}

		if (userMo.getIsLock()) {
			_log.error("用户退货退款并扣减返现金额时发现用户被锁定: " + userMo);
			resultMap.put("result", -5);
			resultMap.put("msg", "买家被锁定");
			return resultMap;
		}

		// 检查账户有没有支付过此销售单
		AfcPayMo condition = new AfcPayMo();
		condition.setAccountId(userId);
		condition.setOrderId(saleOrderId);
		List<AfcPayMo> pays = paySvc.list(condition);
		if (pays.isEmpty()) {
			_log.error("用户退货退款并扣减返现金额时发现账户没有支付过此销售单，订单编号为：{}", saleOrderId);
			resultMap.put("result", -6);
			resultMap.put("msg", "账户没有支付过此销售单");
			return resultMap;
		}

		// 得到交易金额
		BigDecimal tradeAmount = new BigDecimal(balanceAmount.toString())
				.add(new BigDecimal(cashbackAmount.toString()));

		// 检查退货金额不能超过支付金额
		// 计算支付总额
		BigDecimal payTotal = new BigDecimal(0);
		for (AfcPayMo afcPayMo : pays) {
			payTotal = payTotal.add(afcPayMo.getPayAmount());
		}
		// 计算曾经退货总额
		BigDecimal returnGoodsTotal = tradeSvc.getReturnGoodsTotalAmountByOrder(saleOrderId);
		if (returnGoodsTotal == null)
			returnGoodsTotal = new BigDecimal(0);
		// 比较大小
		if (payTotal.compareTo(returnGoodsTotal.add(tradeAmount)) < 0) {
			_log.error("退货金额不能超过销售金额: " + to);
			resultMap.put("result", -7);
			resultMap.put("msg", "退货金额不能超过销售金额");
			return resultMap;
		}

		_log.info("用户退货退款并扣减返现金额查询用户支付账号信息的参数为：{}", userId);
		// 查询用户支付账号信息
		AfcAccountMo afcAccountMo = accountSvc.getById(userId);
		_log.info("用户退货退款并扣减返现金额查询用户支付账号信息的返回值为：{}", afcAccountMo.toString());
		if (afcAccountMo.getId() == null || afcAccountMo.getId() == 0) {
			_log.error("用户退货退款并扣减返现金额查询用户支付账号信息时发现没有该用户支付账号信息，用户编号为：{}", userId);
			resultMap.put("result", -8);
			resultMap.put("msg", "没有该用户支付账号");
			return resultMap;
		}

		BigDecimal bd = new BigDecimal("0");
		// 扣减之后的退到返现金额的金额
		BigDecimal newCashbackAmount = new BigDecimal(cashbackAmount);
		// 扣减之后的余额
		BigDecimal newBalance = afcAccountMo.getBalance();
		// 扣减之后退到余额的金额
		BigDecimal newBalanceAmount = new BigDecimal(balanceAmount);
		// 扣减之后的返现金额
		BigDecimal newCashback = new BigDecimal(
				String.valueOf(afcAccountMo.getCashback().subtract(new BigDecimal(subtractCashback)).doubleValue()));
		_log.info("用户退货退款并扣减返现金额扣减之后的返现金额为：{}", newCashback);
		if (newCashback.compareTo(bd) == -1) {
			// 扣减之后的退到返现金额的金额
			newCashbackAmount = new BigDecimal(String.valueOf(newCashbackAmount.subtract(newCashback).doubleValue()));
			_log.info("用户退货退款并扣减返现金额扣减之后的退到返现金额的金额为：{}", newCashbackAmount);
			if (newCashbackAmount.compareTo(bd) == -1) {
				// 扣减之后的余额
				newBalance = new BigDecimal(String.valueOf(newBalance.subtract(newCashbackAmount).doubleValue()));
				_log.info("用户退货退款并扣减返现金额扣减之后的余额为：{}", newBalance);
				if (newBalance.compareTo(bd) == -1) {
					// 扣减之后退到余额的金额
					newBalanceAmount = new BigDecimal(
							String.valueOf(newBalanceAmount.subtract(newBalance).doubleValue()));
					_log.info("用户退货退款并扣减返现金额扣减之后退到余额的金额为：{}", newBalanceAmount);
					if (newBalanceAmount.compareTo(bd) == -1) {
						_log.error("用户退货退款并扣减返现金额扣减返现金额时出现扣减返现金额大于账号所有金额的总和，用户编号为：{}", userId);
						resultMap.put("result", -9);
						resultMap.put("msg", "扣减返现金额失败");
						return resultMap;
					}
				}
			}
		}

		// 计算当前时间
		Date now = new Date();
		// 生成本次交易的ID
		Long tradeId = _idWorker.getId();
		// 生成本次交易的ID
		Long tradeId1 = _idWorker.getId();

		// 查询旧账户信息
		AfcAccountMo oldAccountMo = accountSvc.getById(userId);

		// 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)(退款)
		AfcTradeMo tradeMo = new AfcTradeMo();
		tradeMo.setId(tradeId);
		tradeMo.setAccountId(userId);
		tradeMo.setTradeType((byte) TradeTypeDic.RETURN_GOODS_BY_BUYER.getCode());
		tradeMo.setTradeAmount(tradeAmount);
		tradeMo.setTradeTitle(to.getTradeTitle());
		tradeMo.setTradeDetail(to.getTradeDetail());
		tradeMo.setTradeTime(now);
		// XXX AFC : 交易订单号 : 退货-买家退货 : 订单ID=销售单ID||退货单ID
		tradeMo.setOrderId(saleOrderId + "||" + returnGoodsOrderId);
		tradeMo.setOpId(opId);
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		try {
			_log.info("退货退款并扣减返现金额添加账户交易信息（退款）的参数为：{}", tradeMo.toString());
			tradeSvc.add(tradeMo);
		} catch (DuplicateKeyException e) {
			_log.error("退货退款并扣减返现金额添加账户交易信息时出错，退货编号为：{}", returnGoodsOrderId);
			throw new RuntimeException("添加账户交易信息失败", e);
		}

		// 先添加账户交易，以更早终止并发产生的重复数据(同一交易类型的业务订单不允许重复)（扣减返现金）
		tradeMo = new AfcTradeMo();
		tradeMo.setId(tradeId1);
		tradeMo.setAccountId(userId);
		tradeMo.setTradeType((byte) TradeTypeDic.RETURN_GOODS_BY_BUYER_SUBTRACT_CASHBACK.getCode());
		tradeMo.setTradeAmount(new BigDecimal(String.valueOf(subtractCashback)).setScale(4));
		tradeMo.setTradeTitle("大卖网络-用户退货退款");
		tradeMo.setTradeDetail("用户结算后退货退款扣减返现金");
		tradeMo.setTradeTime(now);
		// XXX AFC : 交易订单号 : 退货-买家退货 : 订单ID=销售单ID||退货单ID
		tradeMo.setOrderId(saleOrderId + "||" + returnGoodsOrderId);
		tradeMo.setOpId(opId);
		tradeMo.setMac(to.getMac());
		tradeMo.setIp(to.getIp());
		_log.info("退货退款并扣减返现金额添加账户交易信息（扣减返现金）的参数为：{}", tradeMo.toString());
		try {
			tradeSvc.add(tradeMo);
		} catch (DuplicateKeyException e) {
			_log.error("退货退款并扣减返现金额添加账户交易信息时出错，退货编号为："+returnGoodsOrderId, e);
			throw new RuntimeException("添加账户交易信息失败", e);
		}

		AfcAccountMo newAccountMo = new AfcAccountMo();
		// 修改账户余额和返现金（扣减返现金）
		newAccountMo.setId(userId);
		newAccountMo.setBalance(newBalance.setScale(4, BigDecimal.ROUND_HALF_UP));
		newAccountMo.setCashback(newCashback.setScale(4, BigDecimal.ROUND_HALF_UP));
		newAccountMo.setModifiedTimestamp(now.getTime());
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", to.getUserId());
		map.put("balance", newAccountMo.getBalance().setScale(4, BigDecimal.ROUND_HALF_UP));
		map.put("oldBalance", oldAccountMo.getBalance().setScale(4, BigDecimal.ROUND_HALF_UP));
		map.put("cashback", newAccountMo.getCashback().setScale(4, BigDecimal.ROUND_HALF_UP));
		map.put("oldCashback", oldAccountMo.getCashback().setScale(4, BigDecimal.ROUND_HALF_UP));
		map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
		map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
		_log.info("退货退款并扣减返现金额修改账户余额和返现金额（扣减返现金额）的参数为：{}", String.valueOf(map));
		if (accountSvc.trade(map) != 1) {
			_log.error("退货退款并扣减返现金额修改账户余额和返现金额（扣减返现金额）时出错，退货编号为：{}", returnGoodsOrderId);
			throw new RuntimeException("修改账户信息出错");
		}

		// 添加账户流水
		AfcFlowMo flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
		dozerMapper.map(newAccountMo, flowMo);
		flowMo.setAccountId(userId);
		flowMo.setId(tradeId);
		flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
		_log.info("退货退款并扣减返现金额添加账户流水的参数为：{}", flowMo.toString());
		try {
			flowSvc.add(flowMo);
			
			oldAccountMo = accountSvc.getById(userId);
			BigDecimal newbalance = new BigDecimal(oldAccountMo.getBalance().add(new BigDecimal(newBalanceAmount.toString())).toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
			BigDecimal newCashBack = new BigDecimal(oldAccountMo.getCashback().add(new BigDecimal(newCashbackAmount.toString())).toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
			// 修改账户余额和返现金额（余额+, 返现金+）
			newAccountMo = new AfcAccountMo();
			newAccountMo.setId(userId);
			newAccountMo.setBalance(newbalance);
			newAccountMo.setCashback(newCashBack);
			newAccountMo.setModifiedTimestamp(now.getTime());
			map = new HashMap<>();
			map.put("id", to.getUserId());
			map.put("balance", newAccountMo.getBalance());
			map.put("oldBalance", oldAccountMo.getBalance().setScale(4, BigDecimal.ROUND_HALF_UP));
			map.put("cashback", newAccountMo.getCashback());
			map.put("oldCashback", oldAccountMo.getCashback().setScale(4, BigDecimal.ROUND_HALF_UP));
			map.put("modifiedTimestamp", newAccountMo.getModifiedTimestamp());
			map.put("oldModifiedTimestamp", oldAccountMo.getModifiedTimestamp());
			_log.info("退货退款并扣减返现金额修改账户余额和返现金额（余额+，返现金额+）的参数为：{}", String.valueOf(map));
			if (accountSvc.trade(map) != 1) {
				_log.error("退货退款并扣减返现金额修改账户余额和返现金额（余额+，返现金额+）时出错，退货编号为：{}", returnGoodsOrderId);
				throw new RuntimeException("修改账户信息出错");
			}

			// 添加账户流水
			flowMo = dozerMapper.map(oldAccountMo, AfcFlowMo.class);
			dozerMapper.map(newAccountMo, flowMo);
			flowMo.setAccountId(userId);
			flowMo.setId(tradeId1);
			flowMo.setOldModifiedTimestamp(oldAccountMo.getModifiedTimestamp());
			_log.info("退货退款并扣减返现金额添加账户流水的参数为：{}", flowMo.toString());
			try {
				flowSvc.add(flowMo);
				resultMap.put("result", 1);
				resultMap.put("msg", "退款成功");
				return resultMap;
			} catch (DuplicateKeyException e) {
				_log.error("退货退款并扣减返现金额添加账户流水时出错，退货编号为：{}", returnGoodsOrderId);
				throw new RuntimeException("添加账户流水出错", e);
			}
		} catch (DuplicateKeyException e) {
			_log.error("退货退款并扣减返现金额添加账户流水时出错，退货编号为：{}", returnGoodsOrderId);
			throw new RuntimeException("添加账户流水出错", e);
		}

	}
}
