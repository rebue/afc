package rebue.suc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.idworker.IdWorker3;

public class AfcSettleTests {

    private String       _hostUrl          = "http://localhost:9300";

    private Long         buyerAccountId    = 471579798452436998L;                                                                                                                                                                                                                                                                                                                             // 买家账户ID
    private Long         sellerAccountId   = 471579798246916101L;                                                                                                                                                                                                                                                                                                                             // 卖家账户ID
    private Long         supplierAccountId = 471579797462581252L;                                                                                                                                                                                                                                                                                                                             // 供应商账户ID
    private String       orderId           = "471581601956888576";

    @Value("${appid:0}")
    private int          _appid;

    private ObjectMapper _objectMapper     = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        IdWorker3 _idWorker = new IdWorker3();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // 返现时间
        calendar.add(Calendar.SECOND, 10);
        String cashbackTime = sdf.format(calendar.getTime());
        // 结算供应商的时间
        calendar.add(Calendar.SECOND, 10);
        String supplierTime = sdf.format(calendar.getTime());
        // 结算卖家的时间
        String sellerTime = sdf.format(calendar.getTime());

        String url = _hostUrl + "/settle/tasks";
        String orderDetailId = _idWorker.getIdStr();
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("buyerAccountId", buyerAccountId);
        paramsMap.put("sellerAccountId", sellerAccountId);
        paramsMap.put("supplierAccountId", supplierAccountId);
        paramsMap.put("settleBuyerCashbackAmount", 1.01);
        paramsMap.put("settleBuyerCashbackTitle", "结算给买家返现金的标题");
        paramsMap.put("settleBuyerCashbackDetail", "结算给买家返现金的详情");
        paramsMap.put("settleBuyerCashbackTime", cashbackTime);
        paramsMap.put("settlePlatformServiceFeeAmount", 0.30);
        paramsMap.put("settlePlatformServiceFeeTime", cashbackTime);
        paramsMap.put("settleSupplierAmount", 7.00);
        paramsMap.put("settleSupplierTitle", "结算供应商的标题");
        paramsMap.put("settleSupplierDetail", "结算供应商的详情");
        paramsMap.put("settleSupplierTime", supplierTime);
        paramsMap.put("settleSellerAmount", 1.21);
        paramsMap.put("settleSellerTitle", "结算卖家的标题");
        paramsMap.put("settleSellerDetail", "结算卖家的详情");
        paramsMap.put("settleSellerTime", sellerTime);
        paramsMap.put("settleDepositUsedAmount", 7.00);
        paramsMap.put("settleDepositUsedTitle", "结算已占用保证金的标题");
        paramsMap.put("settleDepositUsedDetail", "结算已占用保证金的详情");
        paramsMap.put("settleDepositUsedTime", sellerTime);
        paramsMap.put("orderId", orderId);
        paramsMap.put("orderDetailId", orderDetailId);
        paramsMap.put("mac", "测试MAC地址");
        paramsMap.put("ip", "测试IP地址");
        AddSettleTasksRo taskRo = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, paramsMap), AddSettleTasksRo.class);
        Assert.assertNotNull(taskRo);
        Assert.assertEquals(AddSettleTaskResultDic.SUCCESS, taskRo.getResult());

//
//        // 查询款项情况
//        url = _hostUrl + "/account/funds?userId=" + userId;
//        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
//        Assert.assertNotNull(accountFundsRo);
    }

}
