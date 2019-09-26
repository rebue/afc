package rebue.afc.ctrl;

import java.text.ParseException;
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

import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.ro.AfcWithdrawRo;
import rebue.afc.ro.WithdrawNumberForMonthRo;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.afc.to.ApplyWithdrawTo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.IdRo;
import rebue.robotech.ro.Ro;
import rebue.wheel.AgentUtils;
import rebue.wheel.turing.JwtUtils;

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

    @Value("${debug:false}")
    private Boolean isDebug;

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
    PageInfo<AfcWithdrawRo> list(AfcWithdrawMo mo, @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize) {
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
     * 
     * @param to
     * @return
     */
    @PostMapping("/withdraw/apply")
    IdRo apply(@RequestBody ApplyWithdrawTo to) {
        _log.info("申请提现： {}", to);
        try {
            return svc.apply(to);
        } catch (Exception e) {
            _log.error("添加申请提现出错, {}", e);
            IdRo ro = new IdRo();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(e.getMessage());
            return ro;
        }
    }

    /**
     * 处理提现
     * 
     * @throws ParseException
     * @throws NumberFormatException
     */
    @PutMapping("/withdraw/deal")
    WithdrawDealRo deal(@RequestParam("id") java.lang.Long id, HttpServletRequest req)
            throws NumberFormatException, ParseException {
        WithdrawDealTo to = new WithdrawDealTo();
        to.setId(id);
        // 获取当前登录用户id
        Long loginId = 521912602410876932L;
        if (!isDebug) {
            loginId = JwtUtils.getJwtUserIdInCookie(req);
        }
        to.setIp(AgentUtils.getIpAddr(req, "nginx"));
        to.setMac("不再获取MAC地址");
        to.setOpId(loginId);
        _log.info("处理提现： {}", to);
        return svc.deal(to);
    }

    /**
     * 确认提现成功（手动）
     * 
     * @throws ParseException
     * @throws NumberFormatException
     */
    @PutMapping("/withdraw/ok")
    Ro ok(@RequestBody WithdrawOkTo to) throws NumberFormatException, ParseException {
        _log.info("确认提现成功（手动）： {}", to);
        // 获取当前登录用户id
//        Long loginId = 521912602410876932L;
//        if (!isDebug) {
//            loginId = JwtUtils.getJwtUserIdInCookie(req);
//        }
//        to.setIp(AgentUtils.getIpAddr(req, "nginx"));
//        to.setMac("不再获取MAC地址");
//        to.setOpId(loginId);
        try {
            return svc.ok(to);
        } catch (Exception e) {
            _log.error("确认提现失败，{}", e);
            Ro ro = new Ro();
            ro.setResult(ResultDic.FAIL);
            ro.setMsg(e.getMessage());
            return ro;
        }
    }

    /**
     * 作废提现
     * 
     * @throws ParseException
     * @throws NumberFormatException
     */
    @PutMapping("/withdraw/cancel")
    WithdrawCancelRo cancel(@RequestBody WithdrawCancelTo to, HttpServletRequest req)
            throws NumberFormatException, ParseException {
        _log.info("作废提现： {}", to);
        if (to.getIp() == null) {
            to.setIp(AgentUtils.getIpAddr(req, "nginx"));
        }
        // 获取当前登录用户id
        Long loginId = 521912602410876932L;
        if (!isDebug) {
            loginId = JwtUtils.getJwtUserIdInCookie(req);
        }
        to.setIp(AgentUtils.getIpAddr(req, "nginx"));
        to.setMac("不再获取MAC地址");
        to.setOpId(loginId);
        return svc.cancel(to);
    }

    /**
     * 查询用户提现中的信息
     * 
     * @param accountId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/withdraw/applting")
    PageInfo<AfcWithdrawMo> selectWithdrawApplying(@RequestParam("accountId") Long accountId,
            @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("list accountId:" + accountId + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        PageInfo<AfcWithdrawMo> result = svc.selectWithdrawApplying(accountId, pageNum, pageSize, "APPLY_TIME desc");
        _log.info("result: " + result);
        return result;
    }

    @GetMapping("/withdraw/get-withdraw-number-for-month")
    WithdrawNumberForMonthRo getWithdrawNumberForMonth(@RequestBody AfcWithdrawMo mo) {
        _log.info("get-withdraw-number-for-month-{}", mo);
        return svc.getWithdrawNumberForMonth(mo);
    }

}
