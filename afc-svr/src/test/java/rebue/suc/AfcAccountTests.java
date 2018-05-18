package rebue.suc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import rebue.wheel.OkhttpUtils;

public class AfcAccountTests {

//    private String _hostUrl = "http://localhost:9300";
    private String _hostUrl = "http://192.168.1.201/afc-svr";

    @Test
    public void test01() throws IOException {
        // 查询账户余额
        String url = _hostUrl + "/account/funds?userId=" + 472572157856186368L;
        String ro = OkhttpUtils.get(url);
        Assert.assertNotNull(ro);
        System.out.println(ro);
    }

}
