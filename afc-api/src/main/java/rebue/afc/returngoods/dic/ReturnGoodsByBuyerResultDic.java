package rebue.afc.returngoods.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1: 退货成功
 * 0: 参数不正确
 * -1: 没有此操作人
 * -2: 操作人被锁定
 * -3: 没有此买家
 * -4: 买家被锁定
 * -5: 账户没有支付过此销售单
 * -6: 退货金额不能超过销售金额
 */
@ApiModel(value = "买家退货返回结果字典")
public enum ReturnGoodsByBuyerResultDic implements EnumBase {
    /**
     * 1: 退货成功
     */
    @ApiModelProperty(value = "退货成功")
    SUCCESS(1),
    /**
     * 0: 参数不正确
     */
    @ApiModelProperty(value = "参数不正确")
    PARAM_ERROR(0),
    /**
     * -1: 没有此操作人
     */
    @ApiModelProperty(value = "没有此操作人")
    NOT_FOUND_OP(-1),
    /**
     * -2: 操作人被锁定
     */
    @ApiModelProperty(value = "操作人被锁定")
    OP_LOCKED(-2),
    /**
     * -3: 没有此买家
     */
    @ApiModelProperty(value = "没有此买家")
    NOT_FOUND_USER(-3),
    /**
     * -4: 买家被锁定
     */
    @ApiModelProperty(value = "买家被锁定")
    USER_LOCKED(-4),
    /**
     * -5: 账户没有支付过此销售单
     */
    @ApiModelProperty(value = "账户没有支付过此销售单")
    NOT_FOUND_ORDERID(-5),
    /**
     * -6: 退货金额不能超过销售金额
     */
    @ApiModelProperty(value = "退货金额不能超过销售金额")
    NOT_ENOUGH_MONEY(-6);

    /**
     * 枚举的所有项，注意这个变量是静态单例的
     */
    private static Map<Integer, EnumBase> valueMap;
    // 初始化map，保存枚举的所有项到map中以方便通过code查找
    static {
        valueMap = new HashMap<>();
        for (EnumBase item : values()) {
            valueMap.put(item.getCode(), item);
        }
    }

    /**
     * jackson反序列化时，通过code得到枚举的实例 注意：此方法必须是static的方法，且返回类型必须是本枚举类，而不能是接口EnumBase 否则jackson将调用默认的反序列化方法，而不会调用本方法
     */
    @JsonCreator
    public static ReturnGoodsByBuyerResultDic getItem(int code) {
        EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (ReturnGoodsByBuyerResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    ReturnGoodsByBuyerResultDic(int code) {
        this.code = code;
    }

    /**
     * @return jackson序列化时，输出枚举实例的code
     */
    @Override
    public int getCode() {
        return code;
    }
}
