package rebue.suc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.mo.AfcWithdrawMo;
import rebue.afc.to.ApplyWithdrawTo;
import rebue.afc.withdraw.dic.WithdrawApplyResultDic;
import rebue.afc.withdraw.dic.WithdrawCancelResultDic;
import rebue.afc.withdraw.dic.WithdrawDealResultDic;
import rebue.afc.withdraw.dic.WithdrawOkResultDic;
import rebue.afc.withdraw.ro.WithdrawApplyRo;
import rebue.afc.withdraw.ro.WithdrawCancelRo;
import rebue.afc.withdraw.ro.WithdrawDealRo;
import rebue.afc.withdraw.ro.WithdrawOkRo;
import rebue.afc.withdraw.to.WithdrawCancelTo;
import rebue.afc.withdraw.to.WithdrawOkTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.IdRo;
import rebue.robotech.ro.Ro;
import rebue.wheel.OkhttpUtils;
import rebue.wheel.RandomEx;

public class AfcWithdrawTests {

    private String       _hostUrl          = "http://localhost:9300";

    private Long         userId            = 466801645968293888L;
    private Long         opId              = 466801646681325569L;
    private Long         withdrawAccountId = 466846436621942784L;

    private ObjectMapper _objectMapper     = new ObjectMapper();

//    @Test
    public void test01() throws IOException {
        // 用户是否已有提现账户
        String url = _hostUrl + "/withdraw/account/exist/byuserid?userId=" + userId;
        Boolean isExist = _objectMapper.readValue(OkhttpUtils.get(url), Boolean.class);
        Assert.assertNotNull(isExist);
        Assert.assertEquals(false, isExist);

        // 添加提现账户
        url = _hostUrl + "/withdraw/account";
        String orderId = "F01A12C6FC9B4D8F80115EEC6ED57510";
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("accountId", userId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("withdrawType", 1);// 提现类型(1-银行卡,2-支付宝)
        paramsMap.put("contactTel", "0771-8736287");
        paramsMap.put("bankAccountNo", "82347823487248239487248732");
        paramsMap.put("bankAccountName", "张三");
        paramsMap.put("openAccountBank", "南宁市高新支行");
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址1");
        paramsMap.put("ip", "192.168.1.1");
        withdrawAccountId = _objectMapper.readValue(OkhttpUtils.postByFormParams(url, paramsMap), Long.class);
        Assert.assertNotNull(withdrawAccountId);
        System.out.println(withdrawAccountId);

        // 用户是否已有提现账户
        url = _hostUrl + "/withdraw/account/exist/byuserid?userId=" + userId;
        isExist = _objectMapper.readValue(OkhttpUtils.get(url), Boolean.class);
        Assert.assertNotNull(isExist);
        Assert.assertEquals(true, isExist);

        // 修改提现账户
        url = _hostUrl + "/withdraw/account";
        paramsMap = new LinkedHashMap<>();
        paramsMap.put("id", withdrawAccountId);
        paramsMap.put("accountId", userId);
        paramsMap.put("orderId", orderId);
        paramsMap.put("withdrawType", 2);// 提现类型(1-银行卡,2-支付宝)
        paramsMap.put("contactTel", "0771-87362871");
        paramsMap.put("bankAccountNo", "A82347823487248239487248732");
        paramsMap.put("bankAccountName", "张三2");
        paramsMap.put("opId", opId);
        paramsMap.put("mac", "MAC地址2");
        paramsMap.put("ip", "192.168.1.2");
        Integer updateRowCount = _objectMapper.readValue(OkhttpUtils.putByFormParams(url, paramsMap), Integer.class);
        Assert.assertNotNull(updateRowCount);
        Assert.assertEquals(1, updateRowCount.intValue());

        // 查询用户的账户信息
        url = _hostUrl + "/withdraw/account?userId=" + userId;
        @SuppressWarnings("unchecked")
        List<AfcWithdrawAccountMo> list = _objectMapper.readValue(OkhttpUtils.get(url), List.class);
        Assert.assertNotNull(list);
        System.out.println(list);
    }

    @Test
    public void test02() throws IOException {
        // 申请提现
        String url = _hostUrl + "/withdraw/apply";
        ApplyWithdrawTo applyWithdrawTo = new ApplyWithdrawTo();
        applyWithdrawTo.setOrderId(RandomEx.randomUUID());
        applyWithdrawTo.setBankAccountName("测试");
        applyWithdrawTo.setBankAccountNo("6212262102017196223");
        applyWithdrawTo.setContactTel("15878752768");
        applyWithdrawTo.setIp("192.168.1.222");
        applyWithdrawTo.setApplicantId(525616558689484801L);
        applyWithdrawTo.setTradeTitle("大卖网络-用户提现");
        applyWithdrawTo.setWithdrawAmount(BigDecimal.valueOf(100));
        applyWithdrawTo.setWithdrawType((byte) 2);
        IdRo applyRo = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, applyWithdrawTo), IdRo.class);
        Assert.assertNotNull(applyRo);
        Assert.assertEquals(ResultDic.SUCCESS, applyRo.getResult());
        Long withdrawId = Long.parseLong(applyRo.getId());

        // 处理提现
        url = _hostUrl + "/withdraw/deal?id=" + withdrawId;
        Ro dealRo = _objectMapper.readValue(OkhttpUtils.put(url), Ro.class);
        Assert.assertNotNull(dealRo);
        Assert.assertEquals(ResultDic.SUCCESS, dealRo.getResult());

        // 确认提现成功（手动）
        url = _hostUrl + "/withdraw/ok";
        WithdrawOkTo withdrawOkTo = new WithdrawOkTo();
        withdrawOkTo.setId(withdrawId);
        withdrawOkTo.setAccountId(525616558689484801L);
        withdrawOkTo.setVoucherNo("1234546745421");
        withdrawOkTo.setOpId(520874560590053376L);
        withdrawOkTo.setMac("不再获取MAC地址");
        withdrawOkTo.setIp("192.168.1.222");
        WithdrawOkRo okRo = _objectMapper.readValue(OkhttpUtils.putByJsonParams(url, withdrawOkTo), WithdrawOkRo.class);
        Assert.assertNotNull(okRo);
        Assert.assertEquals(WithdrawOkResultDic.SUCCESS, okRo.getResult());

        // 申请提现
        url = _hostUrl + "/withdraw/apply";
        applyWithdrawTo = new ApplyWithdrawTo();
        applyWithdrawTo.setOrderId(RandomEx.randomUUID());
        applyWithdrawTo.setBankAccountName("测试");
        applyWithdrawTo.setBankAccountNo("6212262102017196223");
        applyWithdrawTo.setContactTel("15878752768");
        applyWithdrawTo.setIp("192.168.1.222");
        applyWithdrawTo.setApplicantId(525616558689484801L);
        applyWithdrawTo.setTradeTitle("大卖网络-用户提现");
        applyWithdrawTo.setWithdrawAmount(BigDecimal.valueOf(100));
        applyWithdrawTo.setWithdrawType((byte) 1);
        applyWithdrawTo.setOpenAccountBank("工商银行");
        applyRo = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, applyWithdrawTo), IdRo.class);
        Assert.assertNotNull(applyRo);
        Assert.assertEquals(ResultDic.SUCCESS, applyRo.getResult());
        withdrawId = Long.parseLong(applyRo.getId());

        // 处理提现
        url = _hostUrl + "/withdraw/deal?id=" + withdrawId;
        dealRo = _objectMapper.readValue(OkhttpUtils.put(url), Ro.class);
        Assert.assertNotNull(dealRo);
        Assert.assertEquals(ResultDic.SUCCESS, dealRo.getResult());

        // 作废提现
        url = _hostUrl + "/withdraw/cancel";
        WithdrawCancelTo withdrawCancelTo = new WithdrawCancelTo();
        withdrawCancelTo.setId(withdrawId);
        withdrawCancelTo.setReason("测试");
        withdrawCancelTo.setOpId(520874560590053376L);
        withdrawCancelTo.setMac("不再获取MAC地址");
        withdrawCancelTo.setIp("192.168.1.222");
        WithdrawCancelRo cancelRo = _objectMapper.readValue(OkhttpUtils.putByJsonParams(url, withdrawCancelTo), WithdrawCancelRo.class);
        Assert.assertNotNull(cancelRo);
        Assert.assertEquals(WithdrawCancelResultDic.SUCCESS, cancelRo.getResult());

        // 获取提现记录
        url = _hostUrl + "/withdraw?withdrawState=1&pageNum=1&pageSize=20";
        @SuppressWarnings("unchecked")
        PageInfo<AfcWithdrawMo> list = _objectMapper.readValue(OkhttpUtils.get(url), PageInfo.class);
        Assert.assertNotNull(list);
        System.out.println(list);
    }
    
//    @Test
    public void withdrawNumber() throws IOException {
    	String url = _hostUrl + "/afc/withdraw/withdrawnumber?accountId=525616558689484801";
    	String string = OkhttpUtils.get(url);
    	System.out.println(string);
    }
}
