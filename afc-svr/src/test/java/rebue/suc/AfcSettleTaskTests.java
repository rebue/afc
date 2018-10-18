package rebue.suc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import rebue.afc.dic.TradeTypeDic;
import rebue.afc.to.ControlTaskTo;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.wheel.OkhttpUtils;

public class AfcSettleTaskTests {

    private String orderDetailId = "517220652621496327";

    private String _hostUrl      = "http://localhost:9300";
//    private String _hostUrl = "http://192.168.1.201/afc-svr";

    private ObjectMapper _objectMapper = new ObjectMapper();

    @Test
    public void test01() throws IOException {
        // 暂停任务
        String url = _hostUrl + "/settle/task/suspend";
        ControlTaskTo to = new ControlTaskTo();
        to.setTradeType(TradeTypeDic.SETTLE_COMMISSION);
        to.setOrderDetailId(orderDetailId);

        Ro ro = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, to), Ro.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(ResultDic.SUCCESS, ro.getResult());
        System.out.println(ro);

        // 恢复任务
        url = _hostUrl + "/settle/task/resume";
        ro = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, to), Ro.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(ResultDic.SUCCESS, ro.getResult());
        System.out.println(ro);

        // 取消任务
        url = _hostUrl + "/settle/task/cancel";
        ro = _objectMapper.readValue(OkhttpUtils.postByJsonParams(url, to), Ro.class);
        Assert.assertNotNull(ro);
        Assert.assertEquals(ResultDic.SUCCESS, ro.getResult());
        System.out.println(ro);

    }

}
