package rebue.afc.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcRefundMo;
import rebue.afc.svc.AfcRefundSvc;
import rebue.afc.to.RefundGoBackTo;
import rebue.afc.to.RefundTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.wheel.AgentUtils;

/**
 * 退款
 */
@RestController
public class AfcRefundCtrl {

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private final String _uniqueFilesName = "某字段内容";

    /**
     * 添加退款日志
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/refund")
    Ro add(@RequestBody final AfcRefundMo mo) throws Exception {
        _log.info("add AfcRefundMo:" + mo);
        final Ro ro = new Ro();
        try {
            final int result = svc.add(mo);
            if (result == 1) {
                final String msg = "添加成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                final String msg = "添加失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (final DuplicateKeyException e) {
            final String msg = "添加失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (final RuntimeException e) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 修改退款日志
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/refund")
    Ro modify(@RequestBody final AfcRefundMo mo) throws Exception {
        _log.info("modify AfcRefundMo:" + mo);
        final Ro ro = new Ro();
        try {
            if (svc.modify(mo) == 1) {
                final String msg = "修改成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                final String msg = "修改失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (final DuplicateKeyException e) {
            final String msg = "修改失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (final RuntimeException e) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 删除退款日志
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/refund")
    Ro del(@RequestParam("id") final java.lang.Long id) {
        _log.info("save AfcRefundMo:" + id);
        final int result = svc.del(id);
        final Ro ro = new Ro();
        if (result == 1) {
            final String msg = "删除成功";
            _log.info("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.SUCCESS);
            return ro;
        } else {
            final String msg = "删除失败，找不到该记录";
            _log.error("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 查询退款日志
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/refund")
    PageInfo<AfcRefundMo> list(final AfcRefundMo mo, @RequestParam("pageNum") final int pageNum, @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcRefundMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcRefundMo> result = svc.list(mo, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个退款日志
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/refund/getbyid")
    AfcRefundMo getById(@RequestParam("id") final java.lang.Long id) {
        _log.info("get AfcRefundMo by id: " + id);
        return svc.getById(id);
    }

    private static final Logger _log = LoggerFactory.getLogger(AfcRefundCtrl.class);

    /**
     * 是否测试模式（测试模式下不用从Cookie中获取用户ID）
     */
    @Value("${debug:false}")
    private Boolean             isDebug;

    /**
     * 前面经过的代理
     */
    @Value("${afc.passProxy:noproxy}")
    private String              passProxy;

    @Resource
    private AfcRefundSvc        svc;

    /**
     * 退款
     */
    @PostMapping("/refund")
    Ro refund(@RequestBody final RefundTo to) {
        _log.info("退款： {}", to);
        try {
            return svc.refund(to);
        } catch (final DuplicateKeyException e) {
            final String msg = "重复退款";
            _log.error("{}: {}", msg, to);
            final Ro ro = new Ro();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        } catch (final Exception e) {
            final String msg = "退款失败-" + e.getMessage();
            _log.error(msg + ":" + to, e);
            final Ro ro = new Ro();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }
    }

    /**
     * 错误支付，直接退款原路返回
     */
    @PostMapping("/refundgoback")
    Ro refundGoBack(@RequestBody final RefundGoBackTo to, final HttpServletRequest req) {
        _log.info("refundGoBack： {}", to);
        try {
            to.setIp(AgentUtils.getIpAddr(req, passProxy));
            return svc.refundGoBack(to);
        } catch (final DuplicateKeyException e) {
            final String msg = "重复退款";
            _log.error("{}: {}", msg, to);
            final Ro ro = new Ro();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        } catch (final Exception e) {
            final String msg = "退款失败-" + e.getMessage();
            _log.error(msg + ":" + to, e);
            final Ro ro = new Ro();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(msg);
            return ro;
        }
    }
}
