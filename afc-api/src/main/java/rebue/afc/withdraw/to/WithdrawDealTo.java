package rebue.afc.withdraw.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 处理提现的传输对象（参数）
 */
@ApiModel(value = "处理提现的传输对象（参数）")
public class WithdrawDealTo {
    /**
     * 提现记录ID
     */
    @ApiModelProperty(value = "提现记录ID")
    private Long   id;
    /**
     * 处理人的用户ID
     */
    private Long   opId;
    /**
     * 处理人的MAC地址
     */
    private String mac;
    /**
     * 处理人的IP地址
     */
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "WithdrawDealTo [id=" + id + ", opId=" + opId + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
