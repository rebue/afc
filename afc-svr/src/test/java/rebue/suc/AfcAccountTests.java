package rebue.suc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import rebue.wheel.OkhttpUtils;

public class AfcAccountTests {

    private Long   userId   = 515489475074326530L;

    private String _hostUrl = "http://localhost:9300";
//    private String _hostUrl = "http://192.168.1.201/afc-svr";

    @Test
    public void test01() throws IOException {
        // 查询账户余额
        String url = _hostUrl + "/account/funds?userId=" + userId;
        String ro = OkhttpUtils.get(url);
        Assert.assertNotNull(ro);
        System.out.println(ro);
    }

}
