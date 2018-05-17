package rebue.afc.platform.svc;

import rebue.afc.mo.AfcPlatformTradeMo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcPlatformTradeSvc extends MybatisBaseSvc<AfcPlatformTradeMo, java.lang.Long> {

    /**
     * 添加一笔平台交易
     */
    void addTrade(AfcPlatformTradeMo tradeMo);

}