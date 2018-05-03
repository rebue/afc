package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.dic.RebateResultDic;

/**
 * 返款的返回结果
 */
@ApiModel(value = "返款的返回结果")
@JsonInclude(Include.NON_NULL)
public class RebateRo {
    @ApiModelProperty(value = "返款返回结果的代码", example = "1", required = true)
    private RebateResultDic result;

    public RebateResultDic getResult() {
        return result;
    }

    public void setResult(RebateResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RebateRo [result=" + result + "]";
    }

}
