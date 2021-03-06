package rebue.afc.withdraw.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1: 确认成功
 * 0: 参数不正确
 * -1: 没有此确认人
 * -2: 确认人被锁定
 * -3: 没有此申请
 * -4: 此申请已被处理
 * -5: 扣减服务费失败
 * -6: 操作失败
 */
@ApiModel(value = "确认提现成功（手动）返回结果字典")
public enum WithdrawOkResultDic implements EnumBase {
    /**
     * 1: 处理提交成功
     */
    @ApiModelProperty(value = "处理提交成功")
    SUCCESS(1),
    /**
     * 0: 参数不正确
     */
    @ApiModelProperty(value = "参数不正确")
    PARAM_ERROR(0),
    /**
     * -1: 没有此确认人
     */
    @ApiModelProperty(value = "没有此确认人")
    NOT_FOUND_OP(-1),
    /**
     * -2: 确认人被锁定
     */
    @ApiModelProperty(value = "确认人被锁定")
    OP_LOCKED(-2),
    /**
     * -3: 没有此申请
     */
    @ApiModelProperty(value = "没有此申请")
    NOT_FOUND_APPLICATION(-3),
    /**
     * -4: 此申请已被处理
     */
    @ApiModelProperty(value = "此申请已被处理")
    ALREADY_DEALED(-4),
    /**
     * 扣减服务费失败
     */
    SUBTRACT_SERVICE_CHARGE_ERROR(-5),

    /**
     * 添加提现账户出错
     */
    ADD_WITHDRAW_ACCOUNT_ERROR(-6),

    /**
     * 操作失败
     */
    ERROR(-7);

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
    public static WithdrawOkResultDic getItem(final int code) {
        final EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (WithdrawOkResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    WithdrawOkResultDic(final int code) {
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
