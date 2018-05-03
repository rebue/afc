package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 预支付的返回结果
 */
@ApiModel(value = "预支付的结果", description = "预支付的返回结果")
@JsonInclude(Include.NON_NULL)
public class PrepayRo {
    @ApiModelProperty(value = "预支付的ID", required = true)
    private String  prepayId;

    @ApiModelProperty(value = "是否需要支付密码", required = false)
    private Boolean requirePayPswd;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public Boolean getRequirePayPswd() {
        return requirePayPswd;
    }

    public void setRequirePayPswd(Boolean requirePayPswd) {
        this.requirePayPswd = requirePayPswd;
    }

    @Override
    public String toString() {
        return "PrepayRo [prepayId=" + prepayId + ", requirePayPswd=" + requirePayPswd + "]";
    }

}
