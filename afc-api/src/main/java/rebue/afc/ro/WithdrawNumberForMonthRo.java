package rebue.afc.ro;

import lombok.Data;

@Data
public class WithdrawNumberForMonthRo {

	/**
	 * 提现次数
	 */
	private int withdrawNumber;
	
	/**
     *    提现服务费
     *
     *    数据库字段: AFC_WITHDRAW.SEVICE_CHARGE
     *
     *    @mbg.generated 自动生成，如需修改，请删除本行
     */
    private Integer seviceCharge;
}
