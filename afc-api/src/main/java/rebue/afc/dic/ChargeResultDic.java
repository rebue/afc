package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1 充值成功
 * 0 参数不正确
 * -1 没有此操作人
 * -2 操作人被锁定
 * -3 没有此用户
 */
@ApiModel(value = "充值返回结果字典", description = "充值返回结果的字典")
public enum ChargeResultDic implements EnumBase {
    /**
     * 1: 成功
     */
    @ApiModelProperty(value = "成功")
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
     * -3: 没有此用户
     */
    @ApiModelProperty(value = "没有此用户")
    NOT_FOUND_USER(-3),

    @ApiModelProperty(value = "充值失败")
    FAIL(-4);
    /**
     * 枚举的所有项，注意这个变量是静态单例的
     */
    private static Map<Integer, EnumBase> valueMap;
    // 初始化map，保存枚举的所有项到map中以方便通过code查找
    static {
        valueMap = new HashMap<>();
        for (final EnumBase item : values()) {
            valueMap.put(item.getCode(), item);
        }
    }

    /**
     * jackson反序列化时，通过code得到枚举的实例 注意：此方法必须是static的方法，且返回类型必须是本枚举类，而不能是接口EnumBase 否则jackson将调用默认的反序列化方法，而不会调用本方法
     */
    @JsonCreator
    public static ChargeResultDic getItem(final int code) {
        final EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (ChargeResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    ChargeResultDic(final int code) {
        this.code = code;
    }

    /**
     * @return jackson序列化时，输出枚举实例的code
     */
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name();
    }

}
