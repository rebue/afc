package rebue.afc.ctrl;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rebue.afc.dic.ChargeResultDic;
import rebue.afc.dic.ChargeTypeDic;
import rebue.afc.dic.TradeTypeDic;
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
    @Value("${suc.passProxy:noproxy}")
    private String              passProxy;
    
    @Resource
    private AfcChargeSvc       svc;
    
    @ApiOperation("余额/返现金-充值")
    @PostMapping("/charge")
    ChargeRo charge(@RequestBody ChargeTo to, final HttpServletRequest req) {
        _log.info("余额/返现金-充值： {}", to);
        to.setUserId(to.getId());
        to.setId(null);
        to.setOrderId(RandomEx.randomUUID());
        if(to.getTradeType() == ChargeTypeDic.BALANCECHARGE.getCode()) {
        	to.setTradeTitle("余额充值");
        }else if(to.getTradeType() == ChargeTypeDic.CASHBACKCHARGE.getCode()) {
        	to.setTradeTitle("返现金充值");
        }
        
        if (!isDebug || to.getOpId() == null) {
			Long userId = null;
			String ip = null;
			try {
				userId = JwtUtils.getJwtUserIdInCookie(req);
				ip = AgentUtils.getIpAddr(req, passProxy);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userId == null) {
				ChargeRo ro = new ChargeRo();
				ro.setResult(ChargeResultDic.NOT_FOUND_OP);
				return ro;
			}
			to.setOpId(userId);
			to.setIp(ip);
		}
        to.setMac("不再获取MAC地址");
        return svc.charge(to);
    }

}
