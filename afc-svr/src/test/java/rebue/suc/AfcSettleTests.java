package rebue.suc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.AddSettleTaskResultDic;
import rebue.afc.ro.AddSettleTasksRo;
import rebue.afc.to.AddSettleTasksDetailTo;
import rebue.afc.to.AddSettleTasksTo;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.idworker.IdWorker3;

public class AfcSettleTests {

    private String       _hostUrl          = "http://localhost:9300";

    private Long         buyerAccountId    = 471579798452436998L;                                                                                                                                                                                                                                                                                                                                                                                                                                                     // 买家账户ID
    private Long         sellerAccountId   = 471579798246916101L;                                                                                                                                                                                                                                                                                                                                                                                                                                                 // 卖家账户ID
    private Long         supplierAccountId = 471579797462581252L;                                                                                                                                                                                                                                                                                                                                                                                                                                                 // 供应商账户ID
    private String       orderId           = "471581669858476032";

    @Value("${appid:0}")
    private int          _appid;

    private ObjectMapper _objectMapper     = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        IdWorker3 _idWorker = new IdWorker3();
        Calendar calendar = Calendar.getInstance();
        // 返现时间
        calendar.add(Calendar.SECOND, 10);
        Date cashbackTime = calendar.getTime();
        // 结算供应商的时间
        calendar.add(Calendar.SECOND, 10);
        Date supplierTime = calendar.getTime();
        // 结算卖家的时间
        Date sellerTime = calendar.getTime();

        String url = _hostUrl + "/settle/tasks";
        AddSettleTasksTo to = new AddSettleTasksTo();
        to.setOrderId(orderId);
        to.setBuyerAccountId(buyerAccountId);
        to.setSellerAccountId(sellerAccountId);
        to.setSettleBuyerCashbackTime(cashbackTime);
        to.setSettlePlatformServiceFeeTime(cashbackTime);
        to.setSettleSupplierTime(supplierTime);
        to.setSettleSellerTime(sellerTime);
        to.setSettleDepositUsedTime(sellerTime);
        to.setMac("测试MAC地址");
        to.setIp("测试IP地址");

        List<AddSettleTasksDetailTo> details = new LinkedList<>();
        AddSettleTasksDetailTo detail = new AddSettleTasksDetailTo();
        detail.setOrderDetailId(_idWorker.getIdStr());
        detail.setSupplierAccountId(supplierAccountId);
        detail.setSettleBuyerCashbackAmount(BigDecimal.valueOf(1.01));
        detail.setSettleBuyerCashbackTitle("结算给买家返现金的标题");
        detail.setSettleBuyerCashbackDetail("结算给买家返现金的详情");
        detail.setSettlePlatformServiceFeeAmount(BigDecimal.valueOf(0.30));
        detail.setSettleSupplierAmount(BigDecimal.valueOf(7.00));
        detail.setSettleSupplierTitle("结算供应商的标题");
        detail.setSettleSupplierDetail("结算供应商的详情");
        detail.setSettleSellerAmount(BigDecimal.valueOf(1.21));
        detail.setSettleSellerTitle("结算卖家的标题");
        detail.setSettleSellerDetail("结算卖家的详情");
        detail.setSettleDepositUsedAmount(BigDecimal.valueOf(7.00));
        detail.setSettleDepositUsedTitle("结算已占用保证金的标题");
        detail.setSettleDepositUsedDetail("结算已占用保证金的详情");
        details.add(detail);
        to.setDetails(details);

        AddSettleTasksRo taskRo = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, to), AddSettleTasksRo.class);
        Assert.assertNotNull(taskRo);
        Assert.assertEquals(AddSettleTaskResultDic.SUCCESS, taskRo.getResult());

//
//        // 查询款项情况
//        url = _hostUrl + "/account/funds?userId=" + userId;
//        AccountFundsRo accountFundsRo = _objectMapper.readValue(OkhttpUtils.get(url), AccountFundsRo.class);
//        Assert.assertNotNull(accountFundsRo);
    }

}
