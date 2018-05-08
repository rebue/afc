package rebue.afc.vpay.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import rebue.afc.vpay.dic.AddRebateTaskResultDic;

/**
 * 添加交易任务的返回结果
 */
@JsonInclude(Include.NON_NULL)
public class AddRebateTaskRo {
    /**
     * 添加交易任务返回结果的代码
     */
    private AddRebateTaskResultDic result;

    /**
     * 返回的提示信息
     */
    private String                 msg;

    public AddRebateTaskResultDic getResult() {
        return result;
    }

    public void setResult(AddRebateTaskResultDic result) {
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
        return "AddRebateTaskRo [result=" + result + ", msg=" + msg + "]";
    }

}
