package rebue.afc.svr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import rebue.afc.to.RefundApprovedTo;
import rebue.afc.to.RefundImmediateTo;
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
     * 经过审核的退款(买家申请，卖家同意后进行的退款)
     */
    @PostMapping("/refund/approved")
    Ro refundApproved(@RequestBody RefundApprovedTo to);

    /**
     * 直接退款(因错误支付、卖家取消发货等原因，直接退款原路返回)
     */
    @PostMapping("/refund/immediate")
    Ro refundImmediate(@RequestBody RefundImmediateTo to);

}
