package rebue.afc.withdraw.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import rebue.afc.withdraw.dic.WithdrawOkResultDic;

/**
 * 确认提现成功（手动）的返回结果
 */
@Data
@ApiModel(value = "确认提现成功（手动）的返回结果")
@JsonInclude(Include.NON_NULL)
public class WithdrawOkRo {
    @ApiModelProperty(value = "确认提现成功（手动）返回结果的代码", example = "1", required = true)
    private WithdrawOkResultDic result;

    /**
     * 返回提示u
     */
    private String msg;

}
