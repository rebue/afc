package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.mo.AfcTradeMo;
import rebue.sbs.feign.FeignConfig;

@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcTradeSvc {

	/**
	 * 添加一笔交易
	 * 
	 * @param mo
	 */
	@PostMapping("/afc/trade/exadd")
	void addTrade(@RequestBody AfcTradeMo mo);
}
