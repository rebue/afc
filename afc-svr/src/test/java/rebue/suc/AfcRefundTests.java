package rebue.suc;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.RefundResultDic;
import rebue.afc.ro.RefundRo;
import rebue.afc.to.RefundTo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.idworker.IdWorker3;

public class AfcRefundTests {

//    private String       _hostUrl      = "http://localhost:9300";
//    private String       _hostUrl          = "http://192.168.1.201/afc-svr";
    private String       _hostUrl          = "http://www.duamai.com/afc-svr";

    private Long         buyerAccountId    = 474407031743184904L;
    private Long         sellerAccountId   = 474407031193731079L;
    private Long         supplierAccountId = 474407028786200582L;
    private Long         opId              = 474407028299661317L;
    private String       orderId           = "474408021036892160";

    @Value("${appid:0}")
    private int          _appid;

    private ObjectMapper _objectMapper     = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        IdWorker3 idWorker = new IdWorker3();

        // 买家退款
        String url = _hostUrl + "/refund";
        RefundTo to = new RefundTo();
        to.setOrderId(orderId);
        to.setOrderDetailId(idWorker.getIdStr());
        to.setBuyerAccountId(buyerAccountId);
        to.setSellerAccountId(sellerAccountId);
        to.setSupplierAccountId(supplierAccountId);
        to.setTradeTitle("退款的标题");
        to.setTradeDetail("退款的详情");
        to.setReturnBalanceToBuyer(BigDecimal.valueOf(0.71));
        to.setReturnCashbackToBuyer(BigDecimal.valueOf(0.02));
        to.setGetbackServiceFeeFromPlatform(BigDecimal.valueOf(0.06));
        to.setGetbackCostFromSupplier(BigDecimal.valueOf(1.01));
        to.setGetbackProfitFromSeller(BigDecimal.valueOf(0.53));
        to.setGetbackDepositUsedFromSeller(BigDecimal.valueOf(4.07));
        to.setOpId(opId);
        to.setMac("测试MAC地址1");
        to.setIp("测试IP地址1");

        RefundRo ro = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, to), RefundRo.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(RefundResultDic.SUCCESS, ro.getResult());
    }

}
