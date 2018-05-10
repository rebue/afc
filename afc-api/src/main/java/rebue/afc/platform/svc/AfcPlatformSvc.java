package rebue.afc.platform.svc;

import java.math.BigDecimal;

import rebue.afc.mo.AfcPlatformMo;
import rebue.robotech.svc.MybatisBaseSvc;

public interface AfcPlatformSvc extends MybatisBaseSvc<AfcPlatformMo, java.lang.Long> {

    /**
     * 修改平台余额
     * 
     * @param newBalance
     *            修改后的余额
     * @param newModifiedTimestamp
     *            修改的时间戳
     * @param newBalance
     *            修改前的余额(用来防止并发修改)
     * @param oldModifiedTimestamp
     *            旧的修改时间(用来防止并发修改)
     * @param id
     *            要修改的平台的ID
     */
    void modifyBalance(BigDecimal newBalance, Long newModifiedTimestamp, BigDecimal oldBalance, Long oldModifiedTimestamp, Long id);

}