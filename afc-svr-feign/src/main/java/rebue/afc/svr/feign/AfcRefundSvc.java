package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.to.RefundGoBackTo;
import rebue.afc.to.RefundTo;
import rebue.robotech.ro.Ro;
import rebue.sbs.feign.FeignConfig;

/**
 * 创建时间：2018年5月7日 下午4:25:10
 * 项目名称：afc-svr-feign
 * 
 * @author daniel
 * @version 1.0
 * @since JDK 1.8
 *        文件名称：AfcReturnGoodsSvr.java
 *        类说明： v支付退货退款内部接口
 */
@FeignClient(name = "afc-svr", configuration = FeignConfig.class)
public interface AfcRefundSvc {

    /**
     * 退款
     */
    @PostMapping("/refund")
    Ro refund(@RequestBody RefundTo to);

    /**
     * 错误支付，直接退款原路返回
     */
    @PostMapping("/refundgoback")
    Ro refundGoBack(@RequestBody RefundGoBackTo to);

}
