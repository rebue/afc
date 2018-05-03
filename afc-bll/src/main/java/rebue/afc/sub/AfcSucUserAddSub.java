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

import rebue.afc.mo.AfcAccountMo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.sbs.rabbit.RabbitConsumer;
import rebue.suc.co.SucExchangeCo;
import rebue.suc.msg.SucUserAddMsg;

/**
 * 订阅用户中心-添加用户的通知
 */
@Service
public class AfcSucUserAddSub implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger _log       = LoggerFactory.getLogger(AfcSucUserAddSub.class);

//    private static AtomicInteger count      = new AtomicInteger();

    private final static String QUEUE_NAME = "rebue.afc.sub.sucuser.add";

    @Resource
    private AfcAccountSvc       accountSvc;

    @Resource
    private RabbitConsumer      consumer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止里面的代码被运行两次
        System.out.println(event.getApplicationContext());
        if (!(event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext))
            return;
//        if (count.incrementAndGet() > 1)
//            return;

        _log.info("绑定添加用户消息的队列: {}", QUEUE_NAME);

        consumer.bind(SucExchangeCo.SUC_USER_ADD_EXCHANGE_NAME, QUEUE_NAME, SucUserAddMsg.class, (msg) -> {
            try {
                _log.info("接收到添加用户的消息: {}", msg);
                // 添加用户的账户信息
                AfcAccountMo accountMo = new AfcAccountMo();
                accountMo.setId(msg.getId());
                Date now = new Date();
                accountMo.setSettleTime(now);
                accountMo.setModifiedTimestamp(now.getTime());
                accountSvc.add(accountMo);
                return true;
            } catch (DuplicateKeyException e) {
                _log.warn("收到重复的消息: " + msg, e);
                return true;
            } catch (Exception e) {
                _log.error("添加账户出现异常", e);
                return false;
            }
        });
    }

}
