package rebue.suc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import rebue.wheel.OkhttpUtils;

public class AfcAccountTests {

    private String _hostUrl = "http://localhost:9300";
//    private String       _hostUrl      = "http://120.77.220.106/afc-svr";

    @Test
    public void test01() throws IOException {
        // 查询账户余额
        String url = _hostUrl + "/account/funds?userId=" + 466801647524380675L;
        String ro = OkhttpUtils.get(url);
        Assert.assertNotNull(ro);
        System.out.println(ro);
    }

}
