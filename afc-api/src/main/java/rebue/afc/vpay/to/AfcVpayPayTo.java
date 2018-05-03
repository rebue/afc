package rebue.afc.vpay.to;

import io.swagger.annotations.ApiModel;

/**
 * V支付-支付的传输对象
 */
@ApiModel(value = "V支付-支付", description = "V支付-支付的参数")
public class AfcVpayPayTo {
    /**
     * 预支付ID
     */
    private String prepayId;
    /**
     * 支付密码(通过MD5散列)
     */
    private String payPswd;
    /**
     * 用户的MAC地址
     */
    private String mac;
    /**
     * 用户的IP地址
     */
    private String ip;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPayPswd() {
        return payPswd;
    }

    public void setPayPswd(String payPswd) {
        this.payPswd = payPswd;
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
        return "AfcVpayPayTo [prepayId=" + prepayId + ", payPswd=" + payPswd + ", mac=" + mac + ", ip=" + ip + "]";
    }

}
