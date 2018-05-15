package rebue.afc.vpay.svc.impl;

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

import rebue.afc.dic.PayResultDic;
import rebue.afc.dic.PayTypeDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.PayOrderQueryRo;
import rebue.afc.ro.PayRo;
import rebue.afc.ro.PrepayRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.vpay.pub.VpayNotifyPub;
import rebue.afc.vpay.ro.VpayNotifyRo;
import rebue.afc.vpay.svc.AfcVpaySvc;
import rebue.afc.vpay.to.AfcVpayPayTo;
import rebue.afc.vpay.to.AfcVpayPrepayTo;
import rebue.sbs.redis.RedisClient;
import rebue.sbs.redis.RedisSetException;
import rebue.suc.ro.PayPswdVerifyRo;
import rebue.suc.svr.feign.SucUserSvc;
import rebue.wheel.RandomEx;
import rebue.wheel.turing.DigestUtils;

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
public class AfcVpaySvcImpl implements AfcVpaySvc {
    private final static Logger _log                          = LoggerFactory.getLogger(AfcVpaySvcImpl.class);

    /**
     * 缓存两个小时内预支付订单的Key的前缀
     * 后面跟订单ID(orderId)拼接成Key
     * Value为AfcVpayPrepayTo类的对象
     */
    private static final String REDIS_KEY_PREPAY_ORDER_PREFIX = "rebue.afc.vpay.svc.prepay.order.";

    @Resource
    private SucUserSvc          userSvc;
    @Resource
    private AfcAccountSvc       accountSvc;
    @Resource
    private AfcTradeSvc         tradeSvc;
    @Resource
    private AfcPaySvc           paySvc;

    @Resource
    private VpayNotifyPub       vpayNotifyPub;

    @Resource
    private RedisClient         redisClient;
    @Resource
    private Mapper              dozerMapper;

    /**
     * V支付-预支付
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PrepayRo prepay(AfcVpayPrepayTo to) {
        // 生成预支付的ID(UUID加盐散列)
        String prepayId = DigestUtils.sha512AsHexStr((RandomEx.randomUUID() + RandomEx.random1(6)).getBytes());
        String redisKey = REDIS_KEY_PREPAY_ORDER_PREFIX + prepayId;
        try {
            redisClient.setObj(redisKey, to, 2 * 60 * 60);
        } catch (RedisSetException e) {
            prepayId = null;
        }
        PrepayRo result = new PrepayRo();
        result.setPrepayId(prepayId);
        result.setRequirePayPswd(userSvc.requirePayPswd(to.getUserId(), to.getTradeAmount()));
        return result;
    }

    /**
     * V支付-支付（ -返现金-余额 ）
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PayRo pay(AfcVpayPayTo to) {
        if (StringUtils.isAnyBlank(to.getPrepayId(), to.getMac(), to.getIp())) {
            _log.warn("没有填写预支付ID/MAC/IP: {}", to);
            PayRo ro = new PayRo();
            ro.setResult(PayResultDic.PARAM_ERROR);
            return ro;
        }

        // 取出预支付信息
        String redisKey = REDIS_KEY_PREPAY_ORDER_PREFIX + to.getPrepayId();
        AfcVpayPrepayTo prepay = redisClient.getObj(redisKey, AfcVpayPrepayTo.class);
        if (prepay == null) {
            _log.warn("没有找到预支付信息: {}", to);
            PayRo ro = new PayRo();
            ro.setResult(PayResultDic.NOT_FOUND_PREPAY);
            return ro;
        }

        // 校验支付密码
        PayPswdVerifyRo payPswdVerifyRo = userSvc.verifyPayPswd(prepay.getUserId(), to.getPayPswd(), prepay.getTradeAmount());
        PayRo ro;
        switch (payPswdVerifyRo.getResult()) {
        case SUCCESS:
            break;
        case CACHE_FAIL:
            _log.warn("缓存失败");
            ro = new PayRo();
            ro.setResult(PayResultDic.CACHE_FAIL);
            return ro;
        case PARAM_ERROR:
            _log.warn("参数不正确(没有填写用户ID/支付密码)");
            ro = new PayRo();
            ro.setResult(PayResultDic.PARAM_ERROR);
            return ro;
        case NOT_FOUND_USER:
            _log.warn("找不到用户信息: {}", to);
            ro = new PayRo();
            ro.setResult(PayResultDic.NOT_FOUND_USER);
            return ro;
        case NOT_SET_PASSWORD:
            _log.warn("用户没有设置支付密码: {}", to);
            ro = new PayRo();
            ro.setResult(PayResultDic.NOT_SET_PASSWORD);
            return ro;
        case PASSWORD_ERROR:
            _log.warn("密码错误: {}", to);
            ro = new PayRo();
            ro.setResult(PayResultDic.PASSWORD_ERROR);
            return ro;
        case LOCKED:
            _log.warn("账号被锁定: {}", to);
            ro = new PayRo();
            ro.setResult(PayResultDic.LOCKED);
            return ro;
        default:
            _log.error("校验支付密码返回未设定的类型: " + payPswdVerifyRo);
            throw new RuntimeException("校验支付密码返回未设定的类型: " + payPswdVerifyRo);
        }

        // 查询账户信息
        AfcAccountMo oldAccountMo = accountSvc.getById(prepay.getUserId());
        // 得到交易金额
        BigDecimal tradeAmount = new BigDecimal(prepay.getTradeAmount().toString());

        // 检查是否余额或返现金不足
        if (tradeAmount.compareTo(oldAccountMo.getBalance().add(oldAccountMo.getCashback())) > 0) {
            ro = new PayRo();
            ro.setResult(PayResultDic.NO_ENOUGH_MONEY);
            return ro;
        }

        // 计算当前时间
        Date now = new Date();

        // 添加一笔交易
        AfcTradeMo tradeMo = new AfcTradeMo();
        tradeMo.setAccountId(prepay.getUserId());
        tradeMo.setTradeType((byte) TradeTypeDic.PAY.getCode());
        tradeMo.setTradeAmount(tradeAmount);
        tradeMo.setTradeTitle(prepay.getTradeTitle());
        tradeMo.setTradeDetail(prepay.getTradeDetail());
        tradeMo.setTradeTime(now);
        tradeMo.setOrderId(prepay.getOrderId());
        tradeMo.setOpId(prepay.getUserId());
        tradeMo.setMac(to.getMac());
        tradeMo.setIp(to.getIp());
        tradeSvc.addTrade(tradeMo);

        // 加入支付完成通知的消息队列
        VpayNotifyRo payNotifyRo = new VpayNotifyRo();
        payNotifyRo.setUserId(prepay.getUserId());
        payNotifyRo.setOrderId(prepay.getOrderId());
        payNotifyRo.setPayAccountId(prepay.getUserId().toString());
        payNotifyRo.setPayOrderId(tradeMo.getId().toString());
        payNotifyRo.setPayAmount(tradeAmount);
        payNotifyRo.setPayChangeAmount1(tradeMo.getChangeAmount1());
        payNotifyRo.setPayChangeAmount2(tradeMo.getChangeAmount2());
        payNotifyRo.setPayTime(now);
        vpayNotifyPub.send(payNotifyRo);

        // 删除预支付的缓存
        redisClient.del(redisKey);

        // 返回成功
        ro = new PayRo();
        ro.setResult(PayResultDic.SUCCESS);
        return ro;
    }

    /**
     * V支付-查询订单
     */
    @Override
    public PayOrderQueryRo queryOrder(String orderId) {
        AfcPayMo payMo = paySvc.getByOrderId(PayTypeDic.VPAY, orderId);
        if (payMo != null) {
            PayOrderQueryRo ro = new PayOrderQueryRo();
            ro.setPayOrderId(payMo.getPayOrderId());
            ro.setTradeAmount(payMo.getPayAmount().doubleValue());
            ro.setPayTime(payMo.getPayTime());
            return ro;
        } else {
            return null;
        }
    }

}
