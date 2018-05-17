package rebue.afc.svc;

import java.util.HashMap;

import rebue.afc.mo.AfcAccountMo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcAccountSvc extends MybatisBaseSvc<AfcAccountMo, java.lang.Long> {

    /**
     * 修改金额-产生了一笔交易(影响余额/返现金/返现中金额/货款)，修改相应的字段及记录时间戳
     */
    void modifyAmount(HashMap<String, ?> map);

}