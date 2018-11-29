package rebue.afc.svc.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;

import rebue.afc.dic.ChargeResultDic;
import rebue.afc.dic.DepositGoodsTransferResultDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.ro.DepositGoodsTransferRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcDepositSvc;
import rebue.afc.svc.AfcFlowSvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.ChargeTo;
import rebue.afc.to.DepositGoodsInTo;
import rebue.afc.to.DepositGoodsReturnTo;
import rebue.afc.to.DepositGoodsTransferTo;
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
public class AfcDepositSvcImpl implements AfcDepositSvc {
    private final static Logger _log = LoggerFactory.getLogger(AfcDepositSvcImpl.class);

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

    /**
     * 进货保证金-充值（ 进货保证金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ChargeRo charge(final ChargeTo to) {
        if (to.getUserId() == null || to.getTradeAmount() == null || to.getOpId() == null || StringUtils.isAnyBlank(to.getOrderId(), to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写充值的用户ID/充值单号/充值交易的标题/充值交易的金额/操作人账号/MAC/IP: {}", to);
            final ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.PARAM_ERROR);
            return ro;
        }

        final SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("充值进货保证金发现没有此操作人: " + to.getOpId());
            final ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("充值进货保证金发现操作人已被锁定: " + opUserMo);
            final ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.OP_LOCKED);
            return ro;
        }

        final SucUserMo chargeUserMo = userSvc.getById(to.getUserId());
        if (chargeUserMo == null) {
            _log.error("充值进货保证金发现没有此用户: " + to.getUserId());
            final ChargeRo ro = new ChargeRo();
            ro.setResult(ChargeResultDic.NOT_FOUND_USER);
            return ro;
        }

        // 添加一笔交易
        final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_CHARGE.getCode());
        tradeMo.setTradeTime(new Date());
        tradeSvc.addTrade(tradeMo);

        // 返回成功
        _log.info("进货保证金充值成功: {}", to);
        final ChargeRo ro = new ChargeRo();
        ro.setResult(ChargeResultDic.SUCCESS);
        return ro;
    }

    /**
     * 进货保证金-进货（ 已占用进货保证金+ ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo inGoods(final DepositGoodsInTo to) {
        if (to.getUserId() == null || to.getOrderId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写进货加盟商的用户ID/进货单号/进货交易的标题/进货交易的金额/操作人账号/MAC/IP: {}", to);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.PARAM_ERROR);
            return ro;
        }

        final SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("进货保证金-进货发现没有此操作人: " + to.getOpId());
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("进货保证金-进货发现操作人已被锁定: " + opUserMo);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.OP_LOCKED);
            return ro;
        }

        final SucUserMo userMo = userSvc.getById(to.getUserId());
        if (userMo == null) {
            _log.error("进货保证金-进货发现没有此加盟商: " + to.getUserId());
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_ACCOUNT);
            return ro;
        }
        if (userMo.getIsLock()) {
            _log.error("进货保证金-进货发现加盟商被锁定: " + userMo);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.ACCOUNT_LOCKED);
            return ro;
        }

        // 添加一笔交易
        final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_GOODS_IN.getCode());
        tradeMo.setTradeTime(new Date());
        tradeSvc.addTrade(tradeMo);

        // 返回成功
        _log.info("进货保证金-进货成功: {}", to);
        final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
        ro.setResult(DepositGoodsTransferResultDic.SUCCESS);
        return ro;
    }

    /**
     * 进货保证金-出货（ 已占用进货保证金- ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo outGoods(final DepositGoodsReturnTo to) {
        if (to.getUserId() == null || to.getOrderId() == null || to.getTradeAmount() == null || to.getOpId() == null
                || StringUtils.isAnyBlank(to.getTradeTitle(), to.getMac(), to.getIp())) {
            _log.warn("没有填写出货加盟商的用户ID/出货单号/出货交易的标题/出货交易的金额/操作人账号/MAC/IP: {}", to);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.PARAM_ERROR);
            return ro;
        }

        final SucUserMo opUserMo = userSvc.getById(to.getOpId());
        if (opUserMo == null) {
            _log.error("进货保证金-出货发现没有此操作人: " + to.getOpId());
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_OP);
            return ro;
        }

        if (opUserMo.getIsLock()) {
            _log.error("进货保证金-出货发现操作人已被锁定: " + opUserMo);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.OP_LOCKED);
            return ro;
        }

        final SucUserMo userMo = userSvc.getById(to.getUserId());
        if (userMo == null) {
            _log.error("进货保证金-出货发现没有此加盟商: " + to.getUserId());
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.NOT_FOUND_ACCOUNT);
            return ro;
        }
        if (userMo.getIsLock()) {
            _log.error("进货保证金-出货发现加盟商被锁定: " + userMo);
            final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
            ro.setResult(DepositGoodsTransferResultDic.ACCOUNT_LOCKED);
            return ro;
        }

        // 添加一笔交易
        final AfcTradeMo tradeMo = dozerMapper.map(to, AfcTradeMo.class);
        tradeMo.setAccountId(to.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.DEPOSIT_CHARGE.getCode());
        tradeMo.setTradeTime(new Date());
        tradeSvc.addTrade(tradeMo);

        // 返回成功
        _log.info("进货保证金-出货成功: {}", to);
        final DepositGoodsTransferRo ro = new DepositGoodsTransferRo();
        ro.setResult(DepositGoodsTransferResultDic.SUCCESS);
        return ro;
    }

    /**
     * （ 已占用进货保证金+- ）进货保证金-调货
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DepositGoodsTransferRo transferGoods(final DepositGoodsTransferTo to) {
        final DepositGoodsInTo inTo = dozerMapper.map(to, DepositGoodsInTo.class);
        inTo.setUserId(to.getInUserId());
        final DepositGoodsTransferRo ro = inGoods(inTo);
        if (ro.getResult() != DepositGoodsTransferResultDic.SUCCESS) {
            return ro;
        }
        final DepositGoodsReturnTo outTo = dozerMapper.map(to, DepositGoodsReturnTo.class);
        outTo.setUserId(to.getOutUserId());
        return outGoods(outTo);
    }
}
