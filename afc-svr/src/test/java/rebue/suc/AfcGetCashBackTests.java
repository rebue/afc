package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import rebue.afc.dic.SettleTaskExecuteStateDic;
import rebue.afc.dic.TradeTypeDic;
import rebue.afc.to.GetCashBackTaskTo;
import rebue.wheel.OkhttpUtils;

public class AfcGetCashBackTests {
	private long buyerAcountId = 472560891909373961L;
    private String _hostUrl = "http://localhost:9300/settle/task/cashbacktask";

    @Test
    public void test01() throws IOException {
    	//查询用户待返现任务
    	GetCashBackTaskTo to = new GetCashBackTaskTo();
    	to.setAccountId(buyerAcountId);
    	to.setExecuteState((byte)SettleTaskExecuteStateDic.NONE.getCode());
    	to.setTradType((byte)TradeTypeDic.SETTLE_CASHBACK.getCode());
    	to.setStart((byte)0);
    	to.setLimit((byte)10);    	
//		String cashBackTask = OkhttpUtils.(_hostUrl, to);
//        Assert.assertNotNull(cashBackTask);
//        System.out.println(cashBackTask);
    }

}
