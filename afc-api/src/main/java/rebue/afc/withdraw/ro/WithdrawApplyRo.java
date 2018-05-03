package rebue.afc.withdraw.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.afc.withdraw.dic.WithdrawApplyResultDic;

/**
 * 申请提现的返回结果
 */
@ApiModel(value = "申请提现的返回结果")
@JsonInclude(Include.NON_NULL)
public class WithdrawApplyRo {
    @ApiModelProperty(value = "申请提现返回结果的代码", example = "1", required = true)
    private WithdrawApplyResultDic result;

    @ApiModelProperty(value = "提现记录ID")
    private Long                   id;

    public WithdrawApplyResultDic getResult() {
        return result;
    }

    public void setResult(WithdrawApplyResultDic result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WithdrawApplyRo [result=" + result + ", id=" + id + "]";
    }

}
