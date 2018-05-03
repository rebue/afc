package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.ChargeResultDic;
import rebue.afc.dic.DepositGoodsTransferResultDic;
import rebue.afc.ro.AccountFundsRo;
import rebue.afc.ro.ChargeRo;
import rebue.afc.ro.DepositGoodsTransferRo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;

public class AfcDepositTests {

    private String       _hostUrl      = "http://localhost:9300";

    private Long         userId        = 466801646681325569L;
    private Long         opId          = 466801646861680642L;
    private Long         inUserId      = userId;
    private Long         outUserId     = 466801647524380675L;

    private ObjectMapper _objectMapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        // 充值
        String url = _hostUrl + "/deposit/charge";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        paramsMap.put("orderId", RandomEx.randomUUID());
        paramsMap.put("tradeTitle", "进货保证金-充值交易的标题");
        paramsMap.put("tradeDetail", "进货保证金-充值交易的详情");
        paramsMap.put("tradeAmount", 1.01);
        paramsMap.put("tradeVoucherNo", RandomEx.randomUUID());
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        ChargeRo chargeRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), ChargeRo.class);
        Assert.assertNotNull(chargeRo);
        Assert.assertEquals(ChargeResultDic.SUCCESS, chargeRo.getResult());

        // 查询款项情况
        url = _hostUrl + "/account/funds?userId=" + userId;
        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
        Assert.assertNotNull(accountFundsRo);
    }

    @Test
    public void test02() throws IOException {
        // 进货
        String url = _hostUrl + "/deposit/goods/in";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        String orderId = RandomEx.randomUUID();
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "进货保证金-进货交易的标题");
        paramsMap.put("tradeDetail", "进货保证金-进货交易的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        DepositGoodsTransferRo inRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), DepositGoodsTransferRo.class);
        Assert.assertNotNull(inRo);
        Assert.assertEquals(DepositGoodsTransferResultDic.SUCCESS, inRo.getResult());

        // 出货
        url = _hostUrl + "/deposit/goods/out";
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("userId", userId);
        orderId = RandomEx.randomUUID();
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "进货保证金-出货交易的标题");
        paramsMap.put("tradeDetail", "进货保证金-出货交易的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        DepositGoodsTransferRo outRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), DepositGoodsTransferRo.class);
        Assert.assertNotNull(outRo);
        Assert.assertEquals(DepositGoodsTransferResultDic.SUCCESS, outRo.getResult());

        // 调货
        url = _hostUrl + "/deposit/goods/transfer";
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("inUserId", inUserId);
        paramsMap.put("outUserId", outUserId);
        orderId = RandomEx.randomUUID();
        paramsMap.put("orderId", orderId);
        paramsMap.put("tradeTitle", "进货保证金-调货交易的标题");
        paramsMap.put("tradeDetail", "进货保证金-调货交易的详情");
        paramsMap.put("tradeAmount", 0.01);
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        DepositGoodsTransferRo transferRo = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), DepositGoodsTransferRo.class);
        Assert.assertNotNull(transferRo);
        Assert.assertEquals(DepositGoodsTransferResultDic.SUCCESS, transferRo.getResult());
    }

}
