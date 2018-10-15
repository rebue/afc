package rebue.afc.ctrl;

import com.github.pagehelper.PageInfo;

import java.text.ParseException;
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
import rebue.afc.mo.AfcWithdrawAccountBindFlowMo;
import rebue.afc.ro.AfcWithdrawAccountBindFlowRo;
import rebue.afc.svc.AfcWithdrawAccountBindFlowSvc;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.wheel.AgentUtils;
import rebue.wheel.turing.JwtUtils;

/**
 * 提现账户绑定流程
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@RestController
public class AfcWithdrawAccountBindFlowCtrl {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcWithdrawAccountBindFlowCtrl.class);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Resource
    private AfcWithdrawAccountBindFlowSvc svc;

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private String _uniqueFilesName = "某字段内容";

    /**
     * 添加提现账户绑定流程
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/withdrawaccountbindflow")
    Ro add(@RequestBody AfcWithdrawAccountBindFlowMo mo) throws Exception {
        _log.info("add AfcWithdrawAccountBindFlowMo:" + mo);
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
     * 修改提现账户绑定流程
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/withdrawaccountbindflow")
    Ro modify(@RequestBody AfcWithdrawAccountBindFlowMo mo) throws Exception {
        _log.info("modify AfcWithdrawAccountBindFlowMo:" + mo);
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
     * 删除提现账户绑定流程
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/withdrawaccountbindflow")
    Ro del(@RequestParam("id") java.lang.Long id) {
        _log.info("save AfcWithdrawAccountBindFlowMo:" + id);
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
     * 查询提现账户绑定流程
     */
    @GetMapping("/afc/withdrawaccountbindflow")
    PageInfo<AfcWithdrawAccountBindFlowRo> list(AfcWithdrawAccountBindFlowMo mo, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("list AfcWithdrawAccountBindFlowMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        PageInfo<AfcWithdrawAccountBindFlowRo> result = svc.listEx(mo, pageNum, pageSize, "APPLY_TIME desc");
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个提现账户绑定流程
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/withdrawaccountbindflow/getbyapplicantid")
    AfcWithdrawAccountBindFlowMo getByApplicantId(@RequestParam("applicantId") java.lang.Long applicantId) {
        _log.info("get by applicantId: " + applicantId);
        return svc.getNewOneByApplicantId(applicantId);
    }
    
    /**
     * 添加提现账户绑定流程
     * @param mo
     * @return
     */
    @PostMapping("/afc/withdrawaccountbindflow/addex")
    Ro addEx(@RequestBody AfcWithdrawAccountBindFlowMo mo) {
    	_log.info("添加提现账户绑定流程的参数为：{}", mo);
    	return svc.addEx(mo);
    }
    
    /**
     * 审核通过
     * @param id
     * @param req
     * @return
     * @throws NumberFormatException
     * @throws ParseException
     */
    @PutMapping("/afc/withdrawaccountbindflow/review")
    Ro review(@RequestParam("id") java.lang.Long id, HttpServletRequest req) throws NumberFormatException, ParseException {
    	// 获取当前登录用户id
    	// Long loginId = JwtUtils.getJwtUserIdInCookie(req);
    	AfcWithdrawAccountBindFlowMo accountBindFlowMo = new AfcWithdrawAccountBindFlowMo();
    	accountBindFlowMo.setId(id);
    	accountBindFlowMo.setReviewerIp(AgentUtils.getIpAddr(req, "noproxy"));
    	accountBindFlowMo.setReviewerId(193201L);
    	accountBindFlowMo.setReviewTime(new Date());
    	accountBindFlowMo.setModifiedTimestamp(System.currentTimeMillis());
    	try {
			return svc.review(accountBindFlowMo);
		} catch (Exception e) {
			_log.error("提现账号审核通过出错", e);
			Ro ro = new Ro();
			String msg = e.getMessage();
			ro.setResult(ResultDic.FAIL);
			ro.setMsg(msg);
			return ro;
		}
    }
    
    /**
     * 拒绝申请
     * @param id
     * @param rejectReason
     * @param req
     * @return
     */
    @PutMapping("/afc/withdrawaccountbindflow/reject")
    Ro reject(@RequestParam("id") java.lang.Long id, @RequestParam("rejectReason") String rejectReason, HttpServletRequest req) {
    	// 获取当前登录用户id
    	// Long loginId = JwtUtils.getJwtUserIdInCookie(req);
    	AfcWithdrawAccountBindFlowMo mo = new AfcWithdrawAccountBindFlowMo();
    	mo.setId(id);
    	mo.setFlowState((byte) -1);
    	mo.setRejectReason(rejectReason);
    	mo.setReviewerId(193201L);
    	mo.setReviewTime(new Date());
    	mo.setReviewerIp(AgentUtils.getIpAddr(req, "noproxy"));
    	mo.setModifiedTimestamp(System.currentTimeMillis());
    	int result = svc.reject(mo);
    	Ro ro = new Ro();
    	if (result != 1) {
    		_log.error("拒绝申请提现账号时出现错误，申请id为: {}", id);
			ro.setResult(ResultDic.FAIL);
			ro.setMsg("操作失败");
			return ro;
		}
    	_log.info("拒绝申请提现账号成功，申请id为：{}", id);
    	ro.setResult(ResultDic.SUCCESS);
		ro.setMsg("操作成功");
		return ro;
    }
}
