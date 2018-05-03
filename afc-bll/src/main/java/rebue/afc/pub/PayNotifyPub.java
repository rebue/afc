package rebue.afc.pub;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import rebue.afc.co.PayNotifyCo;
import rebue.afc.ro.PayNotifyRo;
import rebue.sbs.rabbit.RabbitProducer;

/**
 * 支付完成通知的发布者
 */
@Component
public class PayNotifyPub implements ApplicationListener<ApplicationStartedEvent> {
    private static final Logger _log         = LoggerFactory.getLogger(PayNotifyPub.class);

    /**
     * 启动标志，防止多次启动
     */
    private boolean             bStartedFlag = false;

    @Resource
    private RabbitProducer      producer;

//    @PostConstruct
//    void init() throws Exception {
//        // 声明Exchange
//        producer.declareExchange(PayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME);
//    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // 防止多次启动
        if (bStartedFlag)
            return;
        bStartedFlag = true;

        try {
            _log.info("声明支付完成消息的Exchange");
            producer.declareExchange(PayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME);
        } catch (Exception e) {
            String msg = "声明支付完成消息的Exchange失败";
            _log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * 发送支付完成的消息
     */
    public void send(PayNotifyRo ro) {
        _log.info("发送支付完成的消息");
        producer.send(PayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME, ro);
    }

}
