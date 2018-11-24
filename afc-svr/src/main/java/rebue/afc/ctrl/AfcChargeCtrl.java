package rebue.afc.ctrl;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.dic.ChargeTypeDic;
import rebue.afc.ro.ChargeRo;
import rebue.afc.svc.AfcChargeSvc;
import rebue.afc.to.ChargeTo;
import rebue.wheel.AgentUtils;
import rebue.wheel.RandomEx;
import rebue.wheel.turing.JwtUtils;

@Api(tags = "余额或返现金充值")
@RestController
public class AfcChargeCtrl {

	private final static Logger _log = LoggerFactory.getLogger(AfcDepositCtrl.class);

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

	@Resource
	private AfcChargeSvc svc;

	@ApiOperation("余额/返现金-充值")
	@PostMapping("/charge")
	ChargeRo charge(@RequestBody final ChargeTo to, final HttpServletRequest req)
			throws NumberFormatException, ParseException {
		_log.info("余额/返现金-充值： {}", to);
		to.setUserId(to.getId());
		to.setId(null);
		to.setOrderId(RandomEx.randomUUID());
		if (to.getTradeType() == ChargeTypeDic.BALANCECHARGE.getCode()) {
			to.setTradeTitle("余额充值");
		} else if (to.getTradeType() == ChargeTypeDic.CASHBACKCHARGE.getCode()) {
			to.setTradeTitle("返现金充值");
		}

		if (!isDebug || to.getOpId() == null) {
			to.setOpId(JwtUtils.getJwtUserIdInCookie(req));
			to.setIp(AgentUtils.getIpAddr(req, passProxy));
		}
		_log.debug("获取当前用户ID: {}", to.getOpId());
		to.setMac("不再获取MAC地址");
		
		return svc.charge(to);
	}

}
