package rebue.afc.sub;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import rebue.afc.dic.PayTypeDic;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.pub.PayNotifyPub;
import rebue.afc.ro.PayNotifyRo;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.vpay.co.VpayNotifyCo;
import rebue.afc.vpay.ro.VpayNotifyRo;
import rebue.sbs.rabbit.RabbitConsumer;
import rebue.wheel.OkhttpUtils;
import rebue.wxx.wxpay.co.WxpayNotifyCo;
import rebue.wxx.wxpay.ro.WxpayNotifyRo;

/**
 * 订阅支付完成的通知
 */
@Service
public class AfcPayNotifySub implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger _log = LoggerFactory.getLogger(AfcPayNotifySub.class);

    /**
     * 支付完成通知的URL @FIXME 当不用再支持微薄利商超老版本时删除
     */
    @Value("${afc.pay.pay-notify-url}")
    private String              payNotifyUrl;

    @Resource
    private AfcPaySvc           paySvc;

    @Resource
    private RabbitConsumer      consumer;

    @Resource
    private PayNotifyPub        payNotifyPub;

    @Resource
    private Mapper              dozerMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止里面的代码被运行两次
        System.out.println(event.getApplicationContext());
        if (!(event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext))
            return;

        _log.info("订阅V支付的支付完成的通知");
        consumer.bind(VpayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME, VpayNotifyCo.PAY_NOTIFY_QUEUE_NAME, VpayNotifyRo.class, (ro) -> {
            _log.info("V支付-收到支付完成的通知: {}", ro);
            PayNotifyRo msg = dozerMapper.map(ro, PayNotifyRo.class);
            msg.setPayType(PayTypeDic.VPAY);
            return handlePayNotify(msg);
        });
        _log.info("订阅微信支付的支付完成的通知");
        consumer.bind(WxpayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME, WxpayNotifyCo.PAY_NOTIFY_WXPAY_QUEUE_NAME, WxpayNotifyRo.class, (ro) -> {
            _log.info("微信支付-收到支付完成的通知: {}", ro);
            PayNotifyRo msg = dozerMapper.map(ro, PayNotifyRo.class);
            msg.setPayType(PayTypeDic.WXPAY);
            return handlePayNotify(msg);
        });
    }

    /**
     * 处理支付完成的通知
     */
    private boolean handlePayNotify(PayNotifyRo msg) {
        try {
            // 记录支付信息
            AfcPayMo payMo = new AfcPayMo();
            payMo.setAccountId(msg.getUserId());
            payMo.setOrderId(msg.getOrderId());
            payMo.setPayTypeId((byte) msg.getPayType().getCode());
            payMo.setPayAccountId(msg.getPayAccountId());
            payMo.setPayOrderId(msg.getPayOrderId());
            BigDecimal payAmount = new BigDecimal(msg.getPayAmount().toString());
            payMo.setPayAmount(payAmount);
            payMo.setPayTime(msg.getPayTime());
            paySvc.add(payMo);

            // 发送支付完成的消息
            payNotifyPub.send(msg);

            // 通知业务相关URL回调 @FIXME 当不用再支持微薄利商超老版本时删除
            if (!StringUtils.isBlank(payNotifyUrl)) {
                try {
                    _log.info("通知业务相关URL回调");
                    Map<String, Object> requestParams = new LinkedHashMap<>();
                    requestParams.put("orderId", msg.getOrderId());
                    requestParams.put("payAmount", msg.getPayAmount());
                    requestParams.put("payOrderId", msg.getPayOrderId());
                    OkhttpUtils.postByFormParams(payNotifyUrl, requestParams);
                } catch (Exception e) {
                    _log.error("处理支付完成通知回调业务出现异常", e);
                    return true;
                }
            }
            return true;
        } catch (DuplicateKeyException e) {
            _log.warn("收到重复的消息: " + msg, e);
            return true;
        } catch (Exception e) {
            _log.error("处理支付完成通知出现异常", e);
            return false;
        }
    }
}
