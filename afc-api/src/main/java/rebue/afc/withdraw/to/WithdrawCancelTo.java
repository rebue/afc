package rebue.afc.withdraw.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作废提现的传输对象（参数）
 */
@ApiModel(value = "作废提现的传输对象（参数）")
public class WithdrawCancelTo {
    /**
     * 提现记录ID
     */
    @ApiModelProperty(value = "提现记录ID")
    private Long   id;
    /**
     * 作废原因
     */
    @ApiModelProperty(value = "作废原因")
    private String reason;
    /**
     * 操作人的用户ID
     */
    @ApiModelProperty(value = "操作人的用户ID")
    private Long   opId;
    /**
     * 操作人的MAC地址
     */
    @ApiModelProperty(value = "操作人的MAC地址")
    private String mac;
    /**
     * 操作人的IP地址
     */
    @ApiModelProperty(value = "操作人的IP地址")
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        return "WithdrawCancelTo [id=" + id + ", reason=" + reason + ", opId=" + opId + ", mac=" + mac + ", ip=" + ip
                + "]";
    }

}
