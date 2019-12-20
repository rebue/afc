package rebue.afc.platform.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import rebue.wheel.baseintf.EnumBase;

/**
 * 平台交易类型
 * 
 * 1：购买交易收取服务费
 * 2：用户退款退回服务费
 */
public enum PlatformTradeTypeDic implements EnumBase {
    /**
     * 1：收取服务费(购买交易成功)
     */
    CHARGE_SEVICE_FEE(1),
    /**
     * 2：退回服务费(用户退款)
     */
    GETBACK_SEVICE_FEE(2),
    /**
     * 提现手续费
     */
    WITHDRAW_SEVICE_CHARGE(3),
    /**
     * 平台充值
     */
    CHARGE_BALANCE(4),

    /**
     * 平台利润，全返商品实际成交价格的二分之一
     */
    PROFIT_TO_PLATFORM(5),

    /**
     * 平台返现给用户
     */
    PLATFORM_RETURN_TO_USER(6),

    /**
     * 平台返还积分给用户
     */
    PLATFORM_RETURN_POINT_TO_USER(7);

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
     * jackson反序列化时，通过code得到枚举的实例 注意：此方法必须是static的方法，且返回类型必须是本枚举类，而不能是接口EnumBase
     * 否则jackson将调用默认的反序列化方法，而不会调用本方法
     */
    @JsonCreator
    public static PlatformTradeTypeDic getItem(final int code) {
        final EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (PlatformTradeTypeDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    PlatformTradeTypeDic(final int code) {
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
