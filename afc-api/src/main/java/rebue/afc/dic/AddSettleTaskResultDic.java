package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import rebue.wheel.baseintf.EnumBase;

/**
 * 添加结算任务返回结果字典
 * 
 * 1: 添加任务成功
 * -1: 参数不正确
 * -2: 不支持的结算类型
 * -3: 没有此用户
 * -4: 重复添加任务(通过交易类型+账户ID+销售订单详情ID判断)
 */
public enum AddSettleTaskResultDic implements EnumBase {
    /**
     * 1: 添加任务成功
     */
    SUCCESS(1),
    /**
     * -1: 参数不正确
     */
    PARAM_ERROR(-1),
    /**
     * -2: 添加任务失败
     */
    FAIL(-2),
    /**
     * -3: 重复添加任务(通过交易类型+账户ID+销售订单详情ID判断)
     */
    ALREADY_ADD(-3);

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
    public static AddSettleTaskResultDic getItem(final int code) {
        final EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (AddSettleTaskResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    AddSettleTaskResultDic(final int code) {
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
