package rebue.afc.sub;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import rebue.afc.dic.AccountTypeDic;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.sbs.rabbit.RabbitConsumer;
import rebue.suc.co.SucExchangeCo;
import rebue.suc.msg.SucAddUserDoneMsg;

/**
 * 订阅用户中心-添加用户完成的通知
 */
@Service
public class AfcSucAddUserDoneSub implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger _log                    = LoggerFactory.getLogger(AfcSucAddUserDoneSub.class);

    /**
     * 处理添加用户完成通知的队列
     */
    private final static String SUC_ADD_USER_QUEUE_NAME = "rebue.afc.suc.add.user.done.queue";

    @Resource
    private AfcAccountSvc       accountSvc;

    @Resource
    private RabbitConsumer      consumer;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        // 防止里面的代码被运行两次
        if (!(event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext)) {
            return;
        }

        _log.info("订阅添加用户完成的通知: {} - {}", SucExchangeCo.SUC_ADD_USER_DONE_EXCHANGE_NAME, SUC_ADD_USER_QUEUE_NAME);
        consumer.bind(SucExchangeCo.SUC_ADD_USER_DONE_EXCHANGE_NAME, SUC_ADD_USER_QUEUE_NAME, SucAddUserDoneMsg.class, (msg) -> {
            try {
                _log.info("接收到添加用户的消息: {}", msg);
                // 添加用户的账户信息
                final AfcAccountMo accountMo = new AfcAccountMo();
                accountMo.setId(msg.getId());
                accountMo.setAccountType((byte) AccountTypeDic.USER.getCode());
                final Date now = new Date();
                accountMo.setSettleTime(now);
                accountSvc.add(accountMo);
                return true;
            } catch (final DuplicateKeyException e) {
                _log.warn("收到重复的消息: " + msg, e);
                return true;
            } catch (final Exception e) {
                _log.error("添加账户出现异常", e);
                return false;
            }
        });
    }

}
