package rebue.afc.returngoods.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.returngoods.dic.ReturnGoodsByBuyerResultDic;

/**
 * 买家退货的返回结果
 */
@ApiModel(value = "买家退货的返回结果")
@JsonInclude(Include.NON_NULL)
public class ReturnGoodsByBuyerRo {
    @ApiModelProperty(value = "买家退货返回结果的代码", example = "1", required = true)
    private ReturnGoodsByBuyerResultDic result;

    public ReturnGoodsByBuyerResultDic getResult() {
        return result;
    }

    public void setResult(ReturnGoodsByBuyerResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ReturnGoodsByBuyerRo [result=" + result + "]";
    }

}
