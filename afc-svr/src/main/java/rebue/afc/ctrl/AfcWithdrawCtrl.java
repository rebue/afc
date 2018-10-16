package rebue.afc.ctrl;

import com.github.pagehelper.PageInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.ro.AfcWithdrawRo;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawApplyTo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.wheel.AgentUtils;

/**
 * 提现信息
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@RestController
public class AfcWithdrawCtrl {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcWithdrawCtrl.class);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Resource
    private AfcWithdrawSvc svc;

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String _uniqueFilesName = "某字段内容";

    /**
     * 添加提现信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/withdraw")
    Ro add(@RequestBody AfcWithdrawMo mo) throws Exception {
        _log.info("add AfcWithdrawMo:" + mo);
        Ro ro = new Ro();
        try {
            int result = svc.add(mo);
            if (result == 1) {
                String msg = "添加成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                String msg = "添加失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (DuplicateKeyException e) {
            String msg = "添加失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (RuntimeException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 修改提现信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/withdraw")
    Ro modify(@RequestBody AfcWithdrawMo mo) throws Exception {
        _log.info("modify AfcWithdrawMo:" + mo);
        Ro ro = new Ro();
        try {
            if (svc.modify(mo) == 1) {
                String msg = "修改成功";
                _log.info("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.SUCCESS);
                return ro;
            } else {
                String msg = "修改失败";
                _log.error("{}: mo-{}", msg, mo);
                ro.setMsg(msg);
                ro.setResult(ResultDic.FAIL);
                return ro;
            }
        } catch (DuplicateKeyException e) {
            String msg = "修改失败，" + _uniqueFilesName + "已存在，不允许出现重复";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        } catch (RuntimeException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String msg = "修改失败，出现运行时异常(" + sdf.format(new Date()) + ")";
            _log.error("{}: mo-{}", msg, mo);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 删除提现信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/withdraw")
    Ro del(@RequestParam("id") java.lang.Long id) {
        _log.info("save AfcWithdrawMo:" + id);
        int result = svc.del(id);
        Ro ro = new Ro();
        if (result == 1) {
            String msg = "删除成功";
            _log.info("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.SUCCESS);
            return ro;
        } else {
            String msg = "删除失败，找不到该记录";
            _log.error("{}: id-{}", msg, id);
            ro.setMsg(msg);
            ro.setResult(ResultDic.FAIL);
            return ro;
        }
    }

    /**
     * 获取单个提现信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/withdraw/getbyid")
    AfcWithdrawMo getById(@RequestParam("id") java.lang.Long id) {
        _log.info("get AfcWithdrawMo by id: " + id);
        return svc.getById(id);
    }

    /**
     * 查询提现信息
     */
    @GetMapping("/afc/withdraw")
    PageInfo<AfcWithdrawRo> list(AfcWithdrawMo mo, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("list AfcWithdrawMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        PageInfo<AfcWithdrawRo> result = svc.lisrEx(mo, pageNum, pageSize, "ID desc");
        _log.info("result: " + result);
        return result;
    }

    /**
     * 申请提现
     */
    @PostMapping("/withdraw/apply")
    WithdrawApplyRo apply(WithdrawApplyTo to) {
        _log.info("申请提现： {}", to);
        return svc.apply(to);
    }

    /**
     * 处理提现
     */
    @PutMapping("/withdraw/deal")
    WithdrawDealRo deal(WithdrawDealTo to) {
        _log.info("处理提现： {}", to);
        return svc.deal(to);
    }

    /**
     * 确认提现成功（手动）
     */
    @PutMapping("/withdraw/ok")
    WithdrawOkRo ok(WithdrawOkTo to, HttpServletRequest req) {
        _log.info("确认提现成功（手动）： {}", to);
        // 获取当前登录用户id
    	// Long loginId = JwtUtils.getJwtUserIdInCookie(req);
        to.setIp(AgentUtils.getIpAddr(req, "noproxy"));
        to.setMac("不再获取MAC地址");
        to.setOpId(521494277558239235L);
        return svc.ok(to);
    }

    /**
     * 作废提现
     */
    @PutMapping("/withdraw/cancel")
    WithdrawCancelRo cancel(WithdrawCancelTo to, HttpServletRequest req) {
        _log.info("作废提现： {}", to);
     // 获取当前登录用户id
    	// Long loginId = JwtUtils.getJwtUserIdInCookie(req);
        to.setIp(AgentUtils.getIpAddr(req, "noproxy"));
        to.setMac("不再获取MAC地址");
        to.setOpId(521494277558239235L);
        return svc.cancel(to);
    }
}
