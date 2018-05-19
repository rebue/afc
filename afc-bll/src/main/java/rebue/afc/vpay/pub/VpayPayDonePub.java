package rebue.afc.vpay.pub;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import rebue.afc.vpay.co.VpayExchangeCo;
import rebue.afc.vpay.msg.VpayPayDoneMsg;
import rebue.sbs.rabbit.RabbitProducer;

/**
 * V支付-支付完成通知的发布者
 */
@Component
public class VpayPayDonePub implements ApplicationListener<ApplicationStartedEvent> {
    private static final Logger _log         = LoggerFactory.getLogger(VpayPayDonePub.class);

    /**
     * 启动标志，防止多次启动
     */
    private boolean             bStartedFlag = false;

    @Resource
    private RabbitProducer      producer;

//    @PostConstruct
//    void init() throws Exception {
//        // 声明Exchange
//        producer.declareExchange(VpayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME);
//    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // 防止多次启动
        if (bStartedFlag)
            return;
        bStartedFlag = true;

        try {
            _log.info("V支付-声明支付完成消息的Exchange");
            producer.declareExchange(VpayExchangeCo.PAY_DONE_EXCHANGE_NAME);
        } catch (Exception e) {
            String msg = "V支付-声明支付完成消息的Exchange失败";
            _log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * V支付-发送支付完成的消息
     */
    public void send(VpayPayDoneMsg msg) {
        _log.info("V支付-发送支付完成的消息: {}", msg);
        producer.send(VpayExchangeCo.PAY_DONE_EXCHANGE_NAME, msg);
    }

}
