package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 1 调货成功
 * 0 参数不正确
 * -1 没有此操作人
 * -2 操作人被锁定
 * -3 没有此加盟商
 * -4 加盟商被锁定
 * -5 进货可用货款不足（出货时不会返回此项）
 */
@ApiModel(value = "进货/出货/调货时返回结果的字典")
public enum DepositGoodsTransferResultDic implements EnumBase {
    /**
     * 1: 进货/出货/调货成功
     */
    @ApiModelProperty(value = "调货成功")
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
     * -3: 没有此加盟商
     */
    @ApiModelProperty(value = "没有此加盟商")
    NOT_FOUND_ACCOUNT(-3),
    /**
     * -4 加盟商被锁定
     */
    @ApiModelProperty(value = "加盟商被锁定")
    ACCOUNT_LOCKED(-4),
    /**
     * -5 进货可用货款不足（出货时不会返回此项）
     */
    @ApiModelProperty(value = "进货可用货款不足（出货时不会返回此项）")
    NOT_ENOUGH_MONEY(-5);
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
    public static DepositGoodsTransferResultDic getItem(int code) {
        EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (DepositGoodsTransferResultDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    DepositGoodsTransferResultDic(int code) {
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
