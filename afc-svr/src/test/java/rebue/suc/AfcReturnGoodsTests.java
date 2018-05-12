package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.returngoods.dic.RefundToBuyerResultDic;
import rebue.afc.returngoods.ro.RefundToBuyerRo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;
import rebue.wheel.idworker.IdWorker3;

public class AfcReturnGoodsTests {

    private String       _hostUrl      = "http://localhost:9300";

    private Long         userId        = 469650705142120448L;
    private Long         opId          = 451273803712954379L;

    private ObjectMapper _objectMapper = new ObjectMapper();

   // @Test
    /*public void test01() throws IOException {
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
    }*/

    @Test
    public void test02() throws JsonParseException, JsonMappingException, IOException {
    	IdWorker3 _idWorker = new IdWorker3();
    	// 买家退货并扣减返现金
        String url = _hostUrl + "/returngoods/and/subtractcashback/tobuyer";
        String orderId = "469651052551995392";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        paramsMap.put("returnGoodsOrderId", _idWorker.getId());
        paramsMap.put("saleOrderId", orderId);
        paramsMap.put("tradeTitle", "退货的标题");
        paramsMap.put("tradeDetail", "退货的详情");
        paramsMap.put("balanceAmount", 0.72);
        paramsMap.put("cashbackAmount", 0.02);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        paramsMap.put("subtractCashback", 0.01);
        Map ro = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), Map.class);
        System.out.println(String.valueOf(ro));

       /* paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        paramsMap.put("returnGoodsOrderId", _idWorker.getId());
        paramsMap.put("saleOrderId", orderId);
        paramsMap.put("tradeTitle", "退货的标题");
        paramsMap.put("tradeDetail", "退货的详情");
        paramsMap.put("balanceAmount", 0.50);
        paramsMap.put("cashbackAmount", 0.02);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        paramsMap.put("subtractCashback", 0.01);
        ro = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), Map.class);
        Assert.assertNotNull(ro);
       System.out.println(String.valueOf(ro));*/
    }
}
