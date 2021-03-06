package rebue.afc.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

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

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import rebue.afc.mo.AfcAccountMo;
import rebue.afc.ro.AccountFundsRo;
import rebue.afc.svc.AfcAccountSvc;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;

/**
 * 账户信息
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@RestController
public class AfcAccountCtrl {

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private final String _uniqueFilesName = "某字段内容";

    /**
     * 添加账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/account")
    Ro add(@RequestBody final AfcAccountMo mo) throws Exception {
        _log.info("add AfcAccountMo:" + mo);
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
     * 修改账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/account")
    Ro modify(@RequestBody final AfcAccountMo mo) throws Exception {
        _log.info("modify AfcAccountMo:" + mo);
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
     * 删除账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/account")
    Ro del(@RequestParam("id") final java.lang.Long id) {
        _log.info("save AfcAccountMo:" + id);
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
     * 查询账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/account")
    PageInfo<AfcAccountMo> list(final AfcAccountMo mo, @RequestParam("pageNum") final int pageNum, @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcAccountMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcAccountMo> result = svc.list(mo, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个账户信息
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/account/getbyid")
    AfcAccountMo getById(@RequestParam("id") final java.lang.Long id) {
        _log.info("get AfcAccountMo by id: " + id);
        return svc.getById(id);
    }

    private static final Logger _log = LoggerFactory.getLogger(AfcAccountCtrl.class);

    @Resource
    private AfcAccountSvc       svc;

    @Resource
    private Mapper              dozerMapper;

    @ApiOperation("查询账户款项(余额/返现金/提现中)")
    @GetMapping("/account/funds")
    AccountFundsRo getFunds(@RequestParam("userId") final Long accountId) {
        _log.info("查询账户款项(余额/返现金/提现中): {}", accountId);
        final AfcAccountMo accountMo = svc.getById(accountId);
        if (accountMo == null) {
            return null;
        }
        final AccountFundsRo ro = dozerMapper.map(accountMo, AccountFundsRo.class);
        _log.info("账户款项信息: {}", ro);
        return ro;
    }
}
