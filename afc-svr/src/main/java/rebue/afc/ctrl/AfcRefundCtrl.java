package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.dic.RefundResultDic;
import rebue.afc.ro.RefundRo;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.to.RefundTo;

/**
 * 退款
 */
@RestController
public class AfcRefundCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcRefundCtrl.class);

    @Resource
    private AfcRefundSvc        svc;

    /**
     * 退款
     */
    @PostMapping("/refund")
    RefundRo refund(@RequestBody RefundTo to) {
        _log.info("退款： {}", to);
        try {
            return svc.refund(to);
        } catch (DuplicateKeyException e) {
            String msg = "重复退款";
            _log.error("{}: {}", msg, to);
            RefundRo ro = new RefundRo();
            ro.setResult(RefundResultDic.ALREADY_ADD);
            ro.setMsg(msg);
            return ro;
        } catch (Exception e) {
            String msg = "退款失败-" + e.getMessage();
            _log.error(msg + ":" + to, e);
            RefundRo ro = new RefundRo();
            ro.setResult(RefundResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }
    }

}
