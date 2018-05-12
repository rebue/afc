package rebue.afc.returngoods.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import rebue.afc.returngoods.dic.RefundToBuyerResultDic;

/**
 * 退款给买家的返回结果
 */
@JsonInclude(Include.NON_NULL)
public class RefundToBuyerRo {
    /**
     * 退款给买家返回结果的代码
     */
    private RefundToBuyerResultDic result;

    /**
     * 退款给买家返回结果的信息
     */
    private String                 msg;

    public RefundToBuyerResultDic getResult() {
        return result;
    }

    public void setResult(RefundToBuyerResultDic result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RefundToBuyerRo [result=" + result + ", msg=" + msg + "]";
    }

}
