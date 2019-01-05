package rebue.afc.ro;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;



@JsonInclude(Include.NON_NULL)
@Data
public class OrgWithdrawRo {
	
	/**
	 *  组织已经提现总额
	 */
	private BigDecimal withdrawTotal;

}
