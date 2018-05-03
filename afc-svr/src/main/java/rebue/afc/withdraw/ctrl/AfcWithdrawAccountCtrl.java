package rebue.afc.withdraw.ctrl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.svc.AfcWithdrawAccountSvc;

@Api(tags = "提现账户")
@RestController
public class AfcWithdrawAccountCtrl {
    private final static Logger   _log = LoggerFactory.getLogger(AfcWithdrawAccountCtrl.class);

    @Resource
    private AfcWithdrawAccountSvc svc;

    @ApiOperation("添加提现账户")
    @PostMapping("/withdraw/account")
    Long add(AfcWithdrawAccountMo mo) {
        _log.info("添加提现账户： {}", mo);
        svc.add(mo);
        return mo.getId();
    }

    @ApiOperation("修改提现账户")
    @PutMapping("/withdraw/account")
    Integer modify(AfcWithdrawAccountMo mo) {
        _log.info("修改提现账户： {}", mo);
        return svc.modify(mo);
    }

    @ApiOperation("用户是否已有提现账户")
    @GetMapping("/withdraw/account/exist/byuserid")
    Boolean existByUserId(@RequestParam("userId") Long userId) {
        _log.info("用户是否已有提现账户： {}", userId);
        return svc.existByUserId(userId);
    }

    @ApiOperation("查询用户的账户信息")
    @GetMapping("/withdraw/account")
    List<AfcWithdrawAccountMo> listByUserId(@RequestParam("userId") Long userId) {
        _log.info("查询用户的账户信息: {}", userId);
        return svc.listByUserId(userId);
    }
}
