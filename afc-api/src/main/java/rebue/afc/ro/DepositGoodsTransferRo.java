package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.dic.DepositGoodsTransferResultDic;

/**
 * 进货保证金-进货/出货/调货的返回结果
 */
@ApiModel(value = "进货保证金-调货/进货/出货的结果")
@JsonInclude(Include.NON_NULL)
public class DepositGoodsTransferRo {
    @ApiModelProperty(value = "进货保证金-进货/出货/调货返回结果的代码", example = "1", required = true)
    private DepositGoodsTransferResultDic result;

    public DepositGoodsTransferResultDic getResult() {
        return result;
    }

    public void setResult(DepositGoodsTransferResultDic result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DepositTransferRo [result=" + result + "]";
    }

}
