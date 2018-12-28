package rebue.afc.withdraw.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1: 申请提交成功
 * 0: 参数不正确
 * -1: 没有此申请人
 * -2: 申请人被锁定
 * -3: 没有发现提现的用户
 * -4: 提现的用户已经被锁定
 * -5: 没有发现用户的提现账户
 * -6: 提现用户的余额不足
 */
@ApiModel(value = "申请提现返回结果字典")
public enum WithdrawApplyResultDic implements EnumBase {
    /**
     * 1: 申请提交成功
     */
    @ApiModelProperty(value = "申请提交成功")
    SUCCESS(1),
    /**
     * 0: 参数不正确
     */
    @ApiModelProperty(value = "参数不正确")
    PARAM_ERROR(0),
    /**
     * -1: 没有此申请人
     */
    @ApiModelProperty(value = "没有此申请人")
    NOT_FOUND_OP(-1),
    /**
     * -2: 申请人被锁定
     */
    @ApiModelProperty(value = "申请人被锁定")
    OP_LOCKED(-2),
    /**
     * -3: 没有发现提现的用户
     */
    @ApiModelProperty(value = "没有发现提现的用户")
    NOT_FOUND_WITHDRAW_USER(-3),
    /**
     * -4: 提现的用户已经被锁定
     */
    @ApiModelProperty(value = "提现的用户已经被锁定")
    WITHDRAW_USER_LOCKED(-4),
    /**
     * -5: 没有发现用户的提现账户
     */
    @ApiModelProperty(value = "没有发现用户的提现账户")
    NOT_FOUND_WITHDRAW_ACCOUNT(-5),
    /**
     * -6: 提现用户的余额不足
     */
    @ApiModelProperty(value = "提现用户的余额不足")
    NOT_ENOUGH_MONEY(-6),

    /*
     * 身份证号不正确
     */
    ID_CARD_ERROR(-7),

    ERROR(-8);

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
    public static WithdrawApplyResultDic getItem(final int code) {
        final EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (WithdrawApplyResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    WithdrawApplyResultDic(final int code) {
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
