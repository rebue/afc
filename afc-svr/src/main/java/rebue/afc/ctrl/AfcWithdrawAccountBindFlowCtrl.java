package rebue.afc.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
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

import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcWithdrawAccountBindFlowMo;
import rebue.afc.svc.AfcWithdrawAccountBindFlowSvc;
import rebue.afc.ro.AfcWithdrawAccountBindFlowGetRo;

import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;

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
    private final static Logger _log = LoggerFactory.getLogger(AfcWithdrawAccountBindFlowCtrl.class);

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
    Ro del(@RequestParam("id") java.lang.Long id) {        _log.info("save AfcWithdrawAccountBindFlowMo:" + id);
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
     * 
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/withdrawaccountbindflow")
    PageInfo<AfcWithdrawAccountBindFlowMo> list(AfcWithdrawAccountBindFlowMo mo, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("list AfcWithdrawAccountBindFlowMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        PageInfo<AfcWithdrawAccountBindFlowMo> result = svc.list(mo, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个提现账户绑定流程
     * 
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/withdrawaccountbindflow/getbyid")
    AfcWithdrawAccountBindFlowMo getById(@RequestParam("id") java.lang.Long id) {
        _log.info("get AfcWithdrawAccountBindFlowMo by id: " + id);
        return svc.getById(id);
    }

}
