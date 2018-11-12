package rebue.suc;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import rebue.afc.dic.TaskExecuteStateDic;
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
     	

    }

}
