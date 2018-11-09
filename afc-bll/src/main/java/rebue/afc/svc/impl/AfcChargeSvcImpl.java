package rebue.afc.svc.impl;

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
import rebue.afc.mapper.AfcAccountMapper;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcChargeSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.ChargeTo;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 充值
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcChargeSvcImpl extends MybatisBaseSvcImpl<AfcAccountMo, java.lang.Long, AfcAccountMapper> implements AfcChargeSvc {
	private final static Logger _log = LoggerFactory.getLogger(AfcChargeSvcImpl.class);
	
	@Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcFlowSvc          flowSvc;
    @Resource
    private Mapper              dozerMapper;
	
	@Override
	public ChargeRo chargeBalance(ChargeTo to) {
		if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
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

        SucUserMo chargeUserMo = userSvc.getById(to.getUserId());
        if (chargeUserMo == null) {
            _log.error("充值发现没有此用户: " + to.getUserId());
            ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 添加一笔交易
        AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
        tradeMo.setAccountId(to.getUserId());
        if(to.getTradeTitle().contains("余额")) {
        	tradeMo.setTradeType((byte) TradeTypeDic.CHARGE_BALANCE.getCode());
        }else if(to.getTradeTitle().contains("返现金")) {
        	tradeMo.setTradeType((byte) TradeTypeDic.CHARGE_CASHBACK.getCode());
        }
        tradeMo.setTradeTime(new Date());
        tradeSvc.addTrade(tradeMo);

        // 返回成功
        _log.info("进货保证金充值成功: {}", to);
        ChargeRo ro = new ChargeRo();
        ro.setResult(ChargeResultDic.SUCCESS);
        return ro;
	}

}
