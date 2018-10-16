package rebue.afc.withdraw.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 确认提现成功（手动）的传输对象（参数）
 */
@Data
@ApiModel(value = "确认提现成功（手动）的传输对象（参数）")
public class WithdrawOkTo {
    /**
     * 提现记录ID
     */
    @ApiModelProperty(value = "提现记录ID")
    private Long   id;
    /**
     * 提现账户id
     */
    private Long accountId;
    /**
     * 凭证单号
     */
    @ApiModelProperty(value = "凭证单号")
    private String voucherNo;
    /**
     * 确认人的用户ID
     */
    @ApiModelProperty(value = "确认人的用户ID")
    private Long   opId;
    /**
     * 确认人的MAC地址
     */
    @ApiModelProperty(value = "确认人的MAC地址")
    private String mac;
    /**
     * 确认人的IP地址
     */
    @ApiModelProperty(value = "确认人的IP地址")
    private String ip;

}
