package rebue.afc.svr.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.ro.RefundToBuyerRo;
import rebue.afc.to.RefundToBuyerTo;
import rebue.sbs.feign.FeignConfig;

/**  
* 创建时间：2018年5月7日 下午4:25:10  
* 项目名称：afc-svr-feign  
* @author daniel  
* @version 1.0   
* @since JDK 1.8  
* 文件名称：AfcReturnGoodsSvr.java  
* 类说明：  v支付退货退款内部接口
*/
@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcReturnGoodsSvc {

	/**
	 * 买家退货
	 * Title: returnGoodsByBuyer
	 * Description: 
	 * @param to
	 * @return
	 * @date 2018年5月7日 下午4:26:28
	 */
	@PostMapping("/returngoods/bybuyer")
    RefundToBuyerRo returnGoodsByBuyer(@RequestBody RefundToBuyerTo to);
	
	/**
     * 用户退货退款并扣减返现金额
     * Title: returnRefundAndSubtractCashback
     * Description: 
     * @param to
     * @return
     * @date 2018年5月9日 上午9:32:48
     */
    @PostMapping("/returngoods/refund/bybuyer")
    Map<String, Object> returnRefundAndSubtractCashback(@RequestBody RefundToBuyerTo to);
}
  

