package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.returngoods.dic.ReturnGoodsByBuyerResultDic;
import rebue.afc.returngoods.ro.ReturnGoodsByBuyerRo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;

public class AfcReturnGoodsTests {

    private String       _hostUrl      = "http://localhost:9300";

    private Long         userId        = 466801648262578181L;
    private Long         opId          = 466801645968293888L;

    private ObjectMapper _objectMapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        // 买家退货
        String url = _hostUrl + "/returngoods/bybuyer";
        String orderId = "9D19A1299B844FEFA529F726278B2A06";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        paramsMap.put("returnGoodsOrderId", RandomEx.randomUUID());
        paramsMap.put("saleOrderId", orderId);
        paramsMap.put("tradeTitle", "退货的标题");
        paramsMap.put("tradeDetail", "退货的详情");
        paramsMap.put("balanceAmount", 0.72);
        paramsMap.put("cashbackAmount", 0.02);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        ReturnGoodsByBuyerRo ro = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), ReturnGoodsByBuyerRo.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(ReturnGoodsByBuyerResultDic.SUCCESS, ro.getResult());

        paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        paramsMap.put("returnGoodsOrderId", RandomEx.randomUUID());
        paramsMap.put("saleOrderId", orderId);
        paramsMap.put("tradeTitle", "退货的标题");
        paramsMap.put("tradeDetail", "退货的详情");
        paramsMap.put("balanceAmount", 1.01);
        paramsMap.put("cashbackAmount", 0.02);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        ro = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), ReturnGoodsByBuyerRo.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(ReturnGoodsByBuyerResultDic.NOT_ENOUGH_MONEY, ro.getResult());
    }

}
