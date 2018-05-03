package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.dic.PayResultDic;

/**
 * 支付的返回结果
 */
@ApiModel(value = "支付的结果", description = "支付的返回结果")
@JsonInclude(Include.NON_NULL)
public class PayRo {
    @ApiModelProperty(value = "支付返回结果的代码", example = "1", required = true)
    private PayResultDic result;

    public PayResultDic getResult() {
        return result;
    }

    public void setResult(PayResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PayRo [result=" + result + "]";
    }

}
