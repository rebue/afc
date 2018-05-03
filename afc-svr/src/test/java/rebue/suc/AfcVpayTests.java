package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.PayResultDic;
import rebue.afc.ro.PayOrderQueryRo;
import rebue.afc.ro.PayRo;
import rebue.afc.ro.PrepayRo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;

public class AfcVpayTests {

    private String       _hostUrl      = "http://localhost:9300";

    private ObjectMapper _objectMapper = new ObjectMapper();

    private Long         userId        = 466801648262578181L;

    @Test
    public void test01() throws IOException {
        // 预支付
        String url = _hostUrl + "/vpay/prepay";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        String orderId = RandomEx.randomUUID();
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "V支付交易的标题");
        paramsMap.put("tradeDetail", "V支付交易的详情");
        paramsMap.put("tradeAmount", 1.01);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        PrepayRo prepayRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), PrepayRo.class);
        String prepayId = prepayRo.getPrepayId();
        Assert.assertNotNull(prepayId);

        // 支付
        url = _hostUrl + "/vpay/pay";
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("prepayId", prepayId);
        paramsMap.put("payPswd", "");
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        PayRo payRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), PayRo.class);
        Assert.assertNotNull(payRo);
        Assert.assertEquals(PayResultDic.SUCCESS, payRo.getResult());

        // 查询
        url = _hostUrl + "/vpay/queryorder?orderId=" + orderId;
        PayOrderQueryRo vpayQueryOrderRo = _objectMapper.readValue(OkhttpUtils.get(url), PayOrderQueryRo.class);
        Assert.assertNotNull(vpayQueryOrderRo);
        System.out.println(vpayQueryOrderRo);
    }

}
