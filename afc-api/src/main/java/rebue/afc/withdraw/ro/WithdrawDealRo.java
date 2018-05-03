package rebue.afc.withdraw.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.withdraw.dic.WithdrawDealResultDic;

/**
 * 处理提现的返回结果
 */
@ApiModel(value = "处理提现的返回结果")
@JsonInclude(Include.NON_NULL)
public class WithdrawDealRo {
    @ApiModelProperty(value = "处理提现返回结果的代码", example = "1", required = true)
    private WithdrawDealResultDic result;

    public WithdrawDealResultDic getResult() {
        return result;
    }

    public void setResult(WithdrawDealResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WithdrawDealRo [result=" + result + "]";
    }

}
