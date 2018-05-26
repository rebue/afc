package rebue.afc.withdraw.ctrl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.svc.AfcWithdrawSvc;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawApplyTo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawDealTo;
import rebue.afc.withdraw.to.WithdrawOkTo;

@Api(tags = "提现账户")
@RestController
public class AfcWithdrawCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcWithdrawCtrl.class);

    @Resource
    private AfcWithdrawSvc      svc;

    @ApiOperation("获取提现记录")
    @GetMapping("/withdraw")
    PageInfo<AfcWithdrawMo> list(AfcWithdrawMo qo, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        _log.info("获取提现记录: {} pageNum-{} pageSize-{}", qo, pageNum, pageSize);
        if (pageSize > 50) {
            String msg = "pageSize不能大于50";
            _log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        PageInfo<AfcWithdrawMo> result = svc.list(qo, pageNum, pageSize, "ID desc");
        _log.info("result: {}", result);
        return result;
    }

    @ApiOperation("申请提现")
    @PostMapping("/withdraw/apply")
    WithdrawApplyRo apply(WithdrawApplyTo to) {
        _log.info("申请提现： {}", to);
        return svc.apply(to);
    }

    @ApiOperation("处理提现")
    @PutMapping("/withdraw/deal")
    WithdrawDealRo deal(WithdrawDealTo to) {
        _log.info("处理提现： {}", to);
        return svc.deal(to);
    }

    @ApiOperation("确认提现成功（手动）")
    @PutMapping("/withdraw/ok")
    WithdrawOkRo ok(WithdrawOkTo to) {
        _log.info("确认提现成功（手动）： {}", to);
        return svc.ok(to);
    }

    @ApiOperation("作废提现")
    @PutMapping("/withdraw/cancel")
    WithdrawCancelRo cancel(WithdrawCancelTo to) {
        _log.info("作废提现： {}", to);
        return svc.cancel(to);
    }

}
