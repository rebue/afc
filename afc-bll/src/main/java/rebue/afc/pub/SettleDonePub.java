package rebue.afc.pub;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import rebue.afc.co.AfcExchangeCo;
import rebue.afc.msg.SettleDoneMsg;
import rebue.sbs.rabbit.RabbitProducer;

/**
 * 结算完成通知的发布者
 * 
 * @deprecated
 */
@Deprecated
@Component
public class SettleDonePub implements ApplicationListener<ApplicationStartedEvent> {
    private static final Logger _log         = LoggerFactory.getLogger(SettleDonePub.class);

    /**
     * 启动标志，防止多次启动
     */
    private boolean             bStartedFlag = false;

    @Resource
    private RabbitProducer      producer;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {
        // 防止多次启动
        if (bStartedFlag) {
            return;
        }
        bStartedFlag = true;

        try {
            _log.info("声明结算完成消息的Exchange: {}", AfcExchangeCo.SETTLE_DONE_EXCHANGE_NAME);
            producer.declareExchange(AfcExchangeCo.SETTLE_DONE_EXCHANGE_NAME);
        } catch (final Exception e) {
            final String msg = "声明结算完成消息的Exchange失败";
            _log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * 发送结算完成的消息
     */
    public void send(final SettleDoneMsg msg) {
        _log.info("发送结算完成的消息");
        producer.send(AfcExchangeCo.SETTLE_DONE_EXCHANGE_NAME, msg);
    }

}
