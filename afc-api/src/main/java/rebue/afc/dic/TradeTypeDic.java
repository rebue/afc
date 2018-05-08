package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rebue.wheel.baseintf.EnumBase;

/**
 * 10: 充值-余额
 * 11: 充值-返现金
 * 20: 支付
 * 30: 申请提现
 * 31: 提现
 * 32: 作废提现
 * 40: 进货保证金-充值
 * 41: 进货保证金-进货
 * 42: 进货保证金-出货
 * 50: 返款-返款到供应商的余额
 * 51: 返款-返款到买家的返现金
 * 52: 返款-返款到加盟商的已占用保证金
 * 53: 返款-返款到加盟商的余额
 * 54: 返款-返款到平台的服务费
 * 60: 退货-买家退货（退到买家的余额和返现金）
 * 61: 退货-买家退货（扣减买家的返现金）
 */
@ApiModel(value = "交易类型")
public enum TradeTypeDic implements EnumBase {
    /**
     * 10: 充值-余额
     */
    @ApiModelProperty(value = "充值-余额")
    CHARGE_BALANCE(10),
    /**
     * 11: 充值-返现金
     */
    @ApiModelProperty(value = "充值-返现金")
    CHARGE_CASHBACK(11),
    /**
     * 20: 支付
     */
    @ApiModelProperty(value = "支付")
    PAY(20),
    /**
     * 30: 申请提现
     */
    @ApiModelProperty(value = "申请提现")
    WITHDRAW_APPLY(30),
    /**
     * 31: 提现
     */
    @ApiModelProperty(value = "提现")
    WITHDRAW_OK(31),
    /**
     * 32: 作废提现
     */
    @ApiModelProperty(value = "作废提现")
    WITHDRAW_CANCEL(32),
    /**
     * 40: 进货保证金-充值
     */
    @ApiModelProperty(value = "进货保证金-充值")
    DEPOSIT_CHARGE(40),
    /**
     * 41: 进货保证金-进货
     */
    @ApiModelProperty(value = "进货保证金-进货")
    DEPOSIT_GOODS_IN(41),
    /**
     * 42: 进货保证金-出货
     */
    @ApiModelProperty(value = "进货保证金-出货")
    DEPOSIT_GOODS_RETURN(42),
    /**
     * 50: 返款到供应商的余额
     */
    @ApiModelProperty(value = "返款到供应商的余额")
    REBATE_PROVIDER_BALANCE(50),
    /**
     * 51: 返款到买家的返现金
     */
    @ApiModelProperty(value = "返款到买家的返现金")
    REBATE_BUYER_CASHBACK(51),
    /**
     * 52: 返款到加盟商的已占用保证金
     */
    @ApiModelProperty(value = "返款到加盟商的已占用保证金")
    REBATE_SELLER_DEPOSIT_USED(52),
    /**
     * 53: 返款到加盟商的余额
     */
    @ApiModelProperty(value = "返款到加盟商的余额")
    REBATE_SELLER_BALANCE(53),
    /**
     * 54: 返款到平台的服务费
     */
    @ApiModelProperty(value = "返款到平台的服务费")
    REBATE_PLATFORM_SERVICE_CHANGE(54),
    /**
     * 60: 退货-买家退货（退到买家的余额和返现金）
     */
    @ApiModelProperty(value = "退货-买家退货（退到买家的余额和返现金）")
    RETURN_GOODS_BY_BUYER(60),
    /**
     * 61: 退货-买家退货
     */
    @ApiModelProperty(value = "退货-买家退货（扣减买家的返现金）")
    RETURN_GOODS_BY_BUYER_SUBTRACT_CASHBACK(61);
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
    public static TradeTypeDic getItem(int code) {
        EnumBase result = valueMap.get(code);
        if (result == null) {
            throw new IllegalArgumentException("输入的code" + code + "不在枚举的取值范围内");
        }
        return (TradeTypeDic) result;
    }

    private int code;

    /**
     * 构造器，传入code
     */
    TradeTypeDic(int code) {
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
