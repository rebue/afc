package rebue.afc.withdraw.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 确认提现成功（手动）的传输对象（参数）
 */
@ApiModel(value = "确认提现成功（手动）的传输对象（参数）")
public class WithdrawOkTo {
    /**
     * 提现记录ID
     */
    @ApiModelProperty(value = "提现记录ID")
    private Long   id;
    /**
     * 凭证单号
     */
    @ApiModelProperty(value = "凭证单号")
    private String voucherNo;
    /**
     * 确认人的用户ID
     */
    @ApiModelProperty(value = "确认人的用户ID")
    private Long   opId;
    /**
     * 确认人的MAC地址
     */
    @ApiModelProperty(value = "确认人的MAC地址")
    private String mac;
    /**
     * 确认人的IP地址
     */
    @ApiModelProperty(value = "确认人的IP地址")
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
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
        return "WithdrawOkTo [id=" + id + ", voucherNo=" + voucherNo + ", opId=" + opId + ", mac=" + mac + ", ip=" + ip
                + "]";
    }

}
