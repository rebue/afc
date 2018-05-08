package rebue.suc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.vpay.dic.AddRebateTaskResultDic;
import rebue.afc.vpay.ro.AddRebateTaskRo;
import rebue.wheel.OkhttpUtils;

public class VpayScheduleTests {

    private String       _hostUrl      = "http://localhost:9300";

    private Long         accountId     = 467599399665664002L;
    private String       orderId       = "4475BD1FC04641EB9B61DA2AD7A0F381";

    private ObjectMapper _objectMapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);

        String url = _hostUrl + "/task/rebate";
        // 返款到供应商的余额
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("tradeType", TradeTypeDic.REBATE_PROVIDER_BALANCE.getCode());
        paramsMap.put("executePlanTime", sdf.format(calendar.getTime()));
        paramsMap.put("accountId", accountId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "返款到供应商的余额的标题");
        paramsMap.put("tradeDetail", "返款到供应商的余额的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        AddRebateTaskRo rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), AddRebateTaskRo.class);
        Assert.assertNotNull(rebateRo);
        Assert.assertEquals(AddRebateTaskResultDic.SUCCESS, rebateRo.getResult());

        // 返款到买家的返现金
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("tradeType", TradeTypeDic.REBATE_BUYER_CASHBACK.getCode());
        paramsMap.put("executePlanTime", sdf.format(calendar.getTime()));
        paramsMap.put("accountId", accountId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "返款到买家的返现金的标题");
        paramsMap.put("tradeDetail", "返款到买家的返现金的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), AddRebateTaskRo.class);
        Assert.assertNotNull(rebateRo);
        Assert.assertEquals(AddRebateTaskResultDic.SUCCESS, rebateRo.getResult());

        // 返款到加盟商的已占用保证金
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("tradeType", TradeTypeDic.REBATE_SELLER_DEPOSIT_USED.getCode());
        paramsMap.put("executePlanTime", sdf.format(calendar.getTime()));
        paramsMap.put("accountId", accountId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "返款到加盟商的已占用保证金");
        paramsMap.put("tradeDetail", "返款到加盟商的已占用保证金");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), AddRebateTaskRo.class);
        Assert.assertNotNull(rebateRo);
        Assert.assertEquals(AddRebateTaskResultDic.SUCCESS, rebateRo.getResult());

        // 返款到加盟商的余额
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("tradeType", TradeTypeDic.REBATE_SELLER_BALANCE.getCode());
        paramsMap.put("executePlanTime", sdf.format(calendar.getTime()));
        paramsMap.put("accountId", accountId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "返款到加盟商的余额的标题");
        paramsMap.put("tradeDetail", "返款到加盟商的余额的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        rebateRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), AddRebateTaskRo.class);
        Assert.assertNotNull(rebateRo);
        Assert.assertEquals(AddRebateTaskResultDic.SUCCESS, rebateRo.getResult());

    }

}
