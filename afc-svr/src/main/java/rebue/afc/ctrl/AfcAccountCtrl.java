package rebue.afc.ctrl;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rebue.afc.mo.AfcAccountMo;
import rebue.afc.ro.AccountFundsRo;
import rebue.afc.svc.AfcAccountSvc;

/**
 * 账户财务中心
 */
@RestController
public class AfcAccountCtrl {
    private final static Logger _log = LoggerFactory.getLogger(AfcAccountCtrl.class);

    @Resource
    private AfcAccountSvc       svc;

    @Resource
    private Mapper              dozerMapper;

    /**
     * 查询账户款项(余额/返现金/提现中)
     */
    @GetMapping("/account/funds")
    AccountFundsRo getFunds(@RequestParam("userId") Long accountId) {
        _log.info("查询账户款项(余额/返现金/提现中): {}", accountId);
        AfcAccountMo accountMo = svc.getById(accountId);
        if (accountMo == null)
            return null;
        AccountFundsRo ro = dozerMapper.map(accountMo, AccountFundsRo.class);
        _log.info("账户款项信息: {}", ro);
        return ro;
    }
}
