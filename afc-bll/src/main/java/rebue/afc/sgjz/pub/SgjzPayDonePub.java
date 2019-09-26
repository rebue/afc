package rebue.afc.sgjz.pub;

import javax.annotation.Resource;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rebue.afc.sgjz.co.SgjzExchangeCo;
import rebue.afc.sgjz.msg.SgjzPayDoneMsg;
import rebue.sbs.rabbit.RabbitProducer;

/**
 * 手工记账-支付完成通知的发布者
 */
@Component
@Slf4j
public class SgjzPayDonePub implements ApplicationListener<ApplicationStartedEvent> {

    /**
     * 启动标志，防止多次启动
     */
    private boolean        bStartedFlag = false;

    @Resource
    private RabbitProducer producer;

//    @PostConstruct
//    void init() throws Exception {
//        // 声明Exchange
//        producer.declareExchange(VpayNotifyCo.PAY_NOTIFY_EXCHANGE_NAME);
//    }

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {
        // 防止多次启动
        if (bStartedFlag) {
            return;
        }
        bStartedFlag = true;

        try {
            log.info("声明手工记账-支付完成消息的Exchange");
            producer.declareExchange(SgjzExchangeCo.PAY_DONE_EXCHANGE_NAME);
        } catch (final Exception e) {
            final String msg = "声明手工记账-支付完成消息的Exchange失败";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * 手工记账-发送支付完成的消息
     */
    public void send(final SgjzPayDoneMsg msg) {
        log.info("手工记账-发送支付完成的消息: {}", msg);
        producer.send(SgjzExchangeCo.PAY_DONE_EXCHANGE_NAME, msg);
    }

}
