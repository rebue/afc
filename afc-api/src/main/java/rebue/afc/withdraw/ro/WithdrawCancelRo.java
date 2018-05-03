package rebue.afc.withdraw.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.withdraw.dic.WithdrawCancelResultDic;

/**
 * 作废提现的返回结果
 */
@ApiModel(value = "作废提现的返回结果")
@JsonInclude(Include.NON_NULL)
public class WithdrawCancelRo {
    @ApiModelProperty(value = "作废提现返回结果的代码", example = "1", required = true)
    private WithdrawCancelResultDic result;

    public WithdrawCancelResultDic getResult() {
        return result;
    }

    public void setResult(WithdrawCancelResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WithdrawCancelRo [result=" + result + "]";
    }

}
