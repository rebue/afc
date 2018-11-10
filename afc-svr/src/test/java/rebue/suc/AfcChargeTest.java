package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.ChargeResultDic;
import rebue.afc.ro.AccountFundsRo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.svc.impl.AfcChargeSvcImpl;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;

public class AfcChargeTest {
	
	private String       _hostUrl      = "http://localhost:9300";
    private Long         userId        = 525610088929427456L;
    private Long         opId          = 525610088929427456L;
    private ObjectMapper _objectMapper = new ObjectMapper();
    private final static Logger _log = LoggerFactory.getLogger(AfcChargeSvcImpl.class);
//    @Test
//    public void test01() throws IOException {
//        // 余额/返现金充值
//        String url = _hostUrl + "/charge";
//        Map<String, Object> paramsMap = new LinkedHashMap<>();
//        paramsMap = new LinkedHashMap<>();
//        paramsMap.put("userId", userId);
//        paramsMap.put("orderId", RandomEx.randomUUID());
//        paramsMap.put("tradeTitle", "返现金充值");
//        paramsMap.put("tradeDetail", "返现金充值的详情");
//        paramsMap.put("tradeAmount", 1.01);
//        paramsMap.put("tradeVoucherNo", RandomEx.randomUUID());
//        paramsMap.put("opId", opId);
//        paramsMap.put("mac", "MAC地址1");
//        paramsMap.put("ip", "192.168.1.1");
//        _log.info("请求参数为："+paramsMap);
//        ChargeRo chargeRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), ChargeRo.class);
//        Assert.assertNotNull(chargeRo);
//        Assert.assertEquals(ChargeResultDic.SUCCESS, chargeRo.getResult());
//        // 查询款项情况
//        url = _hostUrl + "/account/funds?userId=" + userId;
//        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
//        Assert.assertNotNull(accountFundsRo);
//    }
    
    @Test
    public void test02() throws IOException {
        // 平台充值
        String url = _hostUrl + "/afc/platform/charge";
        Long         platFormId        = 0L;
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", platFormId);
        paramsMap.put("orderId", RandomEx.randomUUID());
        paramsMap.put("tradeTitle", "返现金充值");
        paramsMap.put("tradeDetail", "返现金充值的详情");
        paramsMap.put("tradeAmount", 1.01);
        paramsMap.put("tradeVoucherNo", RandomEx.randomUUID());
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        _log.info("请求参数为："+paramsMap);
        ChargeRo chargeRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), ChargeRo.class);
        Assert.assertNotNull(chargeRo);
        Assert.assertEquals(ChargeResultDic.SUCCESS, chargeRo.getResult());
        // 查询款项情况
        url = _hostUrl + "/afc/platform/getbyid?id=" + platFormId;
        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
        Assert.assertNotNull(accountFundsRo);
    }

}
