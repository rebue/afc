package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1 成功
 * 0 缓存失败
 * -1 参数不正确(没有填写预支付ID/支付密码/MAC/IP)
 * -2 没有找到预支付信息
 * -3 找不到用户信息
 * -4 密码错误
 * -5 账号被锁定
 * -6 余额或返现金不足
 * -7 订单已经支付
 * -8 用户没有设置支付密码
 */
@ApiModel(value = "支付返回结果字典", description = "支付返回结果的字典")
public enum PayResultDic implements EnumBase {
    /**
     * 1: 成功
     */
    @ApiModelProperty(value = "成功")
    SUCCESS(1),
    /**
     * 0: 缓存失败
     */
    @ApiModelProperty(value = "缓存失败")
    CACHE_FAIL(0),
    /**
     * -1: 参数不正确
     */
    @ApiModelProperty(value = "参数不正确(没有填写预支付ID/支付密码/MAC/IP)")
    PARAM_ERROR(-1),
    /**
     * -2: 没有找到预支付信息
     */
    @ApiModelProperty(value = "没有找到预支付信息")
    NOT_FOUND_PREPAY(-2),
    /**
     * -3: 找不到用户信息
     */
    NOT_FOUND_USER(-3),
    /**
     * -4: 密码错误
     */
    PASSWORD_ERROR(-4),
    /**
     * -5: 账号被锁定
     */
    LOCKED(-5),
    /**
     * -6: 余额或返现金不足
     */
    @ApiModelProperty(value = "余额或返现金不足")
    NO_ENOUGH_MONEY(-6),
    /**
     * -7: 订单已经支付
     */
    @ApiModelProperty(value = "订单已经支付")
    ALREADY_PAID(-7),
    /**
     * -8: 用户没有设置支付密码
     */
    @ApiModelProperty(value = "用户没有设置支付密码")
    NOT_SET_PASSWORD(-8);
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
    public static PayResultDic getItem(int code) {
        EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (PayResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    PayResultDic(int code) {
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
