package rebue.afc.withdraw.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import rebue.afc.withdraw.dic.WithdrawApplyResultDic;

/**
 * 申请提现的返回结果
 */
@Data
@ApiModel(value = "申请提现的返回结果")
@JsonInclude(Include.NON_NULL)
public class WithdrawApplyRo {
    @ApiModelProperty(value = "申请提现返回结果的代码", example = "1", required = true)
    private WithdrawApplyResultDic result;
    
    private String msg;

    @ApiModelProperty(value = "提现记录ID")
    private Long                   id;
}
