package rebue.afc.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户财务中心
 *
 */
@RestController
public class AfcCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcCtrl.class);

    /**
     * 支付完成的通知(测试用)
     */
    @PostMapping("/pay/notify")
    void handleNotify(HttpServletRequest req) throws IOException, DocumentException {
        _log.info("收到支付完成通知：{}", req.getParameterMap());
    }

}
