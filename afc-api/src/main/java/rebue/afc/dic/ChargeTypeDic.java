package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1 余额充值
 * 2 返现金充值
 */
@ApiModel(value = "充值类型", description = "充值类型的字典(1.余额充值;2.返现金充值;)")
public enum ChargeTypeDic implements EnumBase {
	/**
     * 1: V支付
     */
    @ApiModelProperty(value = "余额充值")
    BALANCECHARGE(1),
    /**
     * 2: 微信支付
     */
    @ApiModelProperty(value = "返现金充值")
    CASHBACKCHARGE(2);
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
     * jackson反序列化时，通过code得到枚举的实例
     * 注意：此方法必须是static的方法，且返回类型必须是本枚举类，而不能是接口EnumBase
     * 否则jackson将调用默认的反序列化方法，而不会调用本方法
     */
    @JsonCreator
    public static PayAndRefundTypeDic getItem(int code) {
        EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (PayAndRefundTypeDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    ChargeTypeDic(int code) {
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
