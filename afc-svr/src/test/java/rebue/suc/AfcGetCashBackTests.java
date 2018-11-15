package rebue.suc;

import java.io.IOException;

import org.junit.Test;

import rebue.afc.to.GetCashBackTaskTo;

public class AfcGetCashBackTests {
	private long buyerAcountId = 472560891909373961L;
    private String _hostUrl = "http://localhost:9300/settle/task/cashbacktask";

    @Test
    public void test01() throws IOException {
    	//查询用户待返现任务
    	GetCashBackTaskTo to = new GetCashBackTaskTo();
     	

    }

}
