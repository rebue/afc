package rebue.afc.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import rebue.afc.mo.AfcTradeMo;
import rebue.afc.ro.AfcTradeListRo;
import rebue.afc.ro.OrgWithdrawRo;
import rebue.afc.svc.AfcTradeSvc;
import rebue.afc.to.AfcTradeTo;
import rebue.afc.to.GetAfcTradeTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.wheel.AgentUtils;

/**
 * 账户交易(账户交易流水)
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@RestController
public class AfcTradeCtrl {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger _log = LoggerFactory.getLogger(AfcTradeCtrl.class);

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Resource
    private AfcTradeSvc svc;

    /**
     * 有唯一约束的字段名称
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private final String _uniqueFilesName = "某字段内容";

    /**
     * 添加账户交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PostMapping("/afc/trade")
    Ro add(@RequestBody final AfcTradeMo mo) throws Exception {
        _log.info("add AfcTradeMo:" + mo);
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
     * 修改账户交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @PutMapping("/afc/trade")
    Ro modify(@RequestBody final AfcTradeMo mo) throws Exception {
        _log.info("modify AfcTradeMo:" + mo);
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
     * 删除账户交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @DeleteMapping("/afc/trade")
    Ro del(@RequestParam("id") final java.lang.Long id) {
        _log.info("save AfcTradeMo:" + id);
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
     * 查询账户交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/trade")
    PageInfo<AfcTradeMo> list(final AfcTradeMo mo, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeMo> result = svc.list(mo, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 查询组织交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/orgTrade")
    PageInfo<AfcTradeMo> orgTrade(final GetAfcTradeTo to, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeMo:" + to + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeMo> result = svc.getOrgTradeList(to, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 获取单个账户交易
     *
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @GetMapping("/afc/trade/getbyid")
    AfcTradeMo getById(@RequestParam("id") final java.lang.Long id) {
        _log.info("get AfcTradeMo by id: " + id);
        return svc.getById(id);
    }

    /**
     * 是否测试模式（测试模式下不用从Cookie中获取用户ID）
     */
    @Value("${debug:false}")
    private Boolean isDebug;

    /**
     * 前面经过的代理
     */
    @Value("${afc.passProxy:noproxy}")
    private String passProxy;

    /**
     * 查询用户返现金交易信息
     *
     * @param mo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/afc/trade/cashbacklist")
    PageInfo<AfcTradeMo> cashbackTradeList(final AfcTradeMo mo, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeMo> result = svc.listCashbackTrade(mo, pageNum, pageSize, "TRADE_TIME desc");
        _log.info("result: " + result);
        return result;
    }

    /**
     * 查询用户余额交易信息
     *
     * @param mo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/afc/trade/balancelist")
    PageInfo<AfcTradeMo> balanceTradeList(final AfcTradeMo mo, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeMo:" + mo + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeMo> result = svc.listBalanceTrade(mo, pageNum, pageSize, "TRADE_TIME desc");
        _log.info("result: " + result);
        return result;
    }

    /**
     * 查询个人账户交易
     *
     */
    @GetMapping("/afc/personTradeList")
    PageInfo<AfcTradeListRo> personTradeList(final AfcTradeTo to, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeTo:" + to + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeListRo> result = svc.personTradeList(to, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 查询组织账户交易
     *
     */
    @GetMapping("/afc/orgTradeList")
    PageInfo<AfcTradeListRo> orgTradeList(final AfcTradeTo to, @RequestParam("pageNum") final int pageNum,
            @RequestParam("pageSize") final int pageSize) {
        _log.info("list AfcTradeTo:" + to + ", pageNum = " + pageNum + ", pageSize = " + pageSize);
        if (pageSize > 50) {
            final String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        final PageInfo<AfcTradeListRo> result = svc.orgTradeList(to, pageNum, pageSize);
        _log.info("result: " + result);
        return result;
    }

    /**
     * 添加一笔交易
     */
    @PostMapping("/afc/trade/addex")
    public void addTrade(@RequestBody final AfcTradeMo mo, final HttpServletRequest req)
            throws NumberFormatException, ParseException {
        _log.info("addTrade: {}", mo);
        // 暂时不获取当前用户ID和IP地址，由参数传过来
//        if (!isDebug || mo.getOpId() == null) {
//            mo.setOpId(JwtUtils.getJwtUserIdInCookie(req));
//            mo.setIp(AgentUtils.getIpAddr(req, passProxy));
//        }
        mo.setIp(AgentUtils.getIpAddr(req, passProxy));
        _log.debug("获取当前用户ID: {}", mo.getAccountId());
        mo.setMac("不再获取MAC地址");
        try {
            svc.addTrade(mo);
        } catch (final DuplicateKeyException e) {
            final String msg = "重复添加交易";
            _log.error(msg, e);
        }
    }

    /**
     * 获取组织已经提现总额
     * 
     * @param accountId
     * @return
     */
    @GetMapping("/afc/orgWithdrawTotal")
    OrgWithdrawRo getOrgWithdrawTotal(@RequestParam("accountId") Long accountId) {
        _log.info("获取组织已经提现总额参数为accountId: {}", accountId);
        return svc.getOrgWithdrawTotal(accountId);
    }

    /**
     * 不分页查询账户交易
     *
     */
    @GetMapping("/afc/trade/list")
    List<AfcTradeMo> listEx(@RequestBody final AfcTradeMo mo) {
        _log.info("不分页查询 AfcTradeMo:" + mo);
        return svc.list(mo);
    }
}
