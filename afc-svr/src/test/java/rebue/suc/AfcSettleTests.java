package rebue.suc;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AfcSettleTests {

    private String       _hostUrl      = "http://localhost:9300";

    private Long         userId        = 466801648262578181L;
    private String       orderId       = "9D19A1299B844FEFA529F726278B2A06";
    private String       orderDetailId = "9D19A1299B844FEFA529F726278B2A06";

    private ObjectMapper _objectMapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
      String url = _hostUrl + "/settle/tasks";
      String orderDetailId = "9D19A1299B844FEFA529F726278B2A06";

//        // 返款到供应商的余额
//        String url = _hostUrl + "/rebate/provider/balance";
//        String orderId = "9D19A1299B844FEFA529F726278B2A06";
//        Map<String, Object> paramsMap = new LinkedHashMap<>();
//        paramsMap.put("userId", userId);
//        paramsMap.put("orderId", orderId);
//        paramsMap.put("tradeTitle", "返款到供应商的余额的标题");
//        paramsMap.put("tradeDetail", "返款到供应商的余额的详情");
//        paramsMap.put("tradeAmount", 0.01);
//        paramsMap.put("mac", "MAC地址1");
//        paramsMap.put("ip", "192.168.1.1");
//        RebateRo rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), RebateRo.class);
//        Assert.assertNotNull(rebateRo);
//        Assert.assertEquals(RebateResultDic.SUCCESS, rebateRo.getResult());
//
//        // 返款到买家的返现金
//        url = _hostUrl + "/rebate/buyer/cashback";
//        paramsMap = new LinkedHashMap<>();
//        paramsMap.put("userId", userId);
//        paramsMap.put("orderId", orderId);
//        paramsMap.put("tradeTitle", "返款到买家的返现金的标题");
//        paramsMap.put("tradeDetail", "返款到买家的返现金的详情");
//        paramsMap.put("tradeAmount", 0.01);
//        paramsMap.put("mac", "MAC地址1");
//        paramsMap.put("ip", "192.168.1.1");
//        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), RebateRo.class);
//        Assert.assertNotNull(rebateRo);
//        Assert.assertEquals(RebateResultDic.SUCCESS, rebateRo.getResult());
//
//        // 返款到加盟商的已占用保证金
//        url = _hostUrl + "/rebate/seller/depositused";
//        paramsMap = new LinkedHashMap<>();
//        paramsMap.put("userId", userId);
//        paramsMap.put("orderId", orderId);
//        paramsMap.put("tradeTitle", "返款到加盟商的已占用保证金");
//        paramsMap.put("tradeDetail", "返款到加盟商的已占用保证金");
//        paramsMap.put("tradeAmount", 0.01);
//        paramsMap.put("mac", "MAC地址1");
//        paramsMap.put("ip", "192.168.1.1");
//        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), RebateRo.class);
//        Assert.assertNotNull(rebateRo);
//        Assert.assertEquals(RebateResultDic.SUCCESS, rebateRo.getResult());
//
//        // 返款到加盟商的余额
//        url = _hostUrl + "/rebate/seller/balance";
//        paramsMap = new LinkedHashMap<>();
//        paramsMap.put("userId", userId);
//        paramsMap.put("orderId", orderId);
//        paramsMap.put("tradeTitle", "返款到加盟商的余额的标题");
//        paramsMap.put("tradeDetail", "返款到加盟商的余额的详情");
//        paramsMap.put("tradeAmount", 0.01);
//        paramsMap.put("mac", "MAC地址1");
//        paramsMap.put("ip", "192.168.1.1");
//        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), RebateRo.class);
//        Assert.assertNotNull(rebateRo);
//        Assert.assertEquals(RebateResultDic.SUCCESS, rebateRo.getResult());
//
//        // 查询款项情况
//        url = _hostUrl + "/account/funds?userId=" + userId;
//        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
//        Assert.assertNotNull(accountFundsRo);
    }

}
