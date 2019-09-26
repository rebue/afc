package rebue.afc.sub;

import javax.annotation.Resource;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.Mapper;

import lombok.extern.slf4j.Slf4j;
import rebue.afc.dic.PayAndRefundTypeDic;
import rebue.afc.mo.AfcPayMo;
import rebue.afc.msg.PayDoneMsg;
import rebue.afc.pub.PayDonePub;
import rebue.afc.sgjz.co.SgjzExchangeCo;
import rebue.afc.sgjz.msg.SgjzPayDoneMsg;
import rebue.afc.svc.AfcPaySvc;
import rebue.afc.vpay.co.VpayExchangeCo;
import rebue.afc.vpay.msg.VpayPayDoneMsg;
import rebue.sbs.rabbit.RabbitConsumer;
import rebue.wxx.wxpay.co.WxpayExchangeCo;
import rebue.wxx.wxpay.msg.WxpayPayDoneMsg;

/**
 * 支付完成通知的订阅者
 * 包括手工记账、V支付和微信支付完成的通知
 */
@Service
@Slf4j
public class AfcPayDoneSub implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 处理手工记账支付完成通知的队列
     */
    private final static String SGJZ_DONE_QUEUE_NAME  = "rebue.afc.pay.done.sgjz.queue";

    /**
     * 处理V支付完成通知的队列
     */
    private final static String VPAY_DONE_QUEUE_NAME  = "rebue.afc.pay.done.vpay.queue";

    /**
     * 处理微信支付完成通知的队列
     */
    private final static String WXPAY_DONE_QUEUE_NAME = "rebue.afc.pay.done.wxpay.queue";

    @Resource
    private AfcPaySvc           paySvc;

    @Resource
    private RabbitConsumer      consumer;

    @Resource
    private PayDonePub          payDonePub;

    @Resource
    private Mapper              dozerMapper;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        // 防止里面的代码被运行两次
        if (!(event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext)) {
            return;
        }

        log.info("订阅手工记账的支付完成的通知: {} - {}", SgjzExchangeCo.PAY_DONE_EXCHANGE_NAME, SGJZ_DONE_QUEUE_NAME);
        consumer.bind(SgjzExchangeCo.PAY_DONE_EXCHANGE_NAME, SGJZ_DONE_QUEUE_NAME, SgjzPayDoneMsg.class, (msg) -> {
            log.info("手工记账-收到支付完成的通知: {}", msg);
            final PayDoneMsg payDoneMsg = dozerMapper.map(msg, PayDoneMsg.class);
            payDoneMsg.setPayType(PayAndRefundTypeDic.SGJZ);
            payDoneMsg.setPayAccountId(payDoneMsg.getUserId().toString());
            payDoneMsg.setTradeId(payDoneMsg.getOrderId());
            return handlePayNotify(payDoneMsg);
        });
        log.info("订阅V支付的支付完成的通知: {} - {}", VpayExchangeCo.PAY_DONE_EXCHANGE_NAME, VPAY_DONE_QUEUE_NAME);
        consumer.bind(VpayExchangeCo.PAY_DONE_EXCHANGE_NAME, VPAY_DONE_QUEUE_NAME, VpayPayDoneMsg.class, (msg) -> {
            log.info("V支付-收到支付完成的通知: {}", msg);
            final PayDoneMsg payDoneMsg = dozerMapper.map(msg, PayDoneMsg.class);
            payDoneMsg.setPayType(PayAndRefundTypeDic.VPAY);
            return handlePayNotify(payDoneMsg);
        });
        log.info("订阅微信支付的支付完成的通知: {} - {}", WxpayExchangeCo.PAY_DONE_EXCHANGE_NAME, WXPAY_DONE_QUEUE_NAME);
        consumer.bind(WxpayExchangeCo.PAY_DONE_EXCHANGE_NAME, WXPAY_DONE_QUEUE_NAME, WxpayPayDoneMsg.class, (msg) -> {
            log.info("微信支付-收到支付完成的通知: {}", msg);
            final PayDoneMsg payDoneMsg = dozerMapper.map(msg, PayDoneMsg.class);
            payDoneMsg.setPayType(PayAndRefundTypeDic.WXPAY);
            return handlePayNotify(payDoneMsg);
        });
    }

    /**
     * 处理支付完成的通知
     */
    private boolean handlePayNotify(final PayDoneMsg payDoneMsg) {
        try {
            // 记录支付信息
            final AfcPayMo payMo = new AfcPayMo();
            payMo.setAccountId(payDoneMsg.getUserId());
            payMo.setOrderId(payDoneMsg.getOrderId());
            payMo.setPayTypeId((byte) payDoneMsg.getPayType().getCode());
            payMo.setPayAccountId(payDoneMsg.getPayAccountId());
            payMo.setTradeId(payDoneMsg.getTradeId());
            payMo.setPayAmount(payDoneMsg.getPayAmount());
            payMo.setPayAmount1(payDoneMsg.getPayAmount1());
            payMo.setPayAmount2(payDoneMsg.getPayAmount2());
            payMo.setPayTime(payDoneMsg.getPayTime());
            payMo.setSgjzOpId(payDoneMsg.getSgjzOpId());
            paySvc.add(payMo);

            // 发送支付完成的消息
            payDonePub.send(payDoneMsg);

            return true;
        } catch (final DuplicateKeyException e) {
            log.warn("收到重复的消息: " + payDoneMsg, e);
            return true;
        } catch (final Exception e) {
            log.error("处理支付完成通知出现异常", e);
            return false;
        }
    }
}
