package rebue.afc.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import rebue.afc.dic.RefundResultDic;

/**
 * 退款给买家的返回结果
 */
@JsonInclude(Include.NON_NULL)
public class RefundRo {
    /**
     * 退款给买家返回结果的代码
     */
    private RefundResultDic result;

    /**
     * 退款给买家返回结果的信息
     */
    private String          msg;

    public RefundResultDic getResult() {
        return result;
    }

    public void setResult(RefundResultDic result) {
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
        return "RefundRo [result=" + result + ", msg=" + msg + "]";
    }

}
