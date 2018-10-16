package rebue.afc.dic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import rebue.wheel.baseintf.EnumBase;

/**
 * 交易类型
 * 10: 充值-余额
 * 11: 充值-返现金
 * 20: 支付
 * 30: 申请提现
 * 31: 提现
 * 32: 作废提现
 * 33: 提现服务费
 * 40: 进货保证金-充值
 * 41: 进货保证金-进货
 * 42: 进货保证金-出货
 * 50: 结算-结算供应商(将成本打到供应商的余额)
 * 51: 结算-结算返现金(将返现金打到买家的返现金)
 * 52: 结算-结算已占用保证金(释放卖家的已占用保证金相应金额)
 * 53: 结算-结算卖家(将利润打到卖家的余额)
 * 54: 结算-结算返现中金额(打到买家的返现中金额)
 * 55: 结算-结算平台服务费
 * 56: 结算-结算返佣金(将返佣中的金额移到余额，注意是上家在本次交易中应获得的返佣金金额，而不是上家的全部返佣中的金额)
 * 57: 结算-结算返佣中金额(打到上家的返佣中金额)
 * 60: 退货-买家退货（退到买家的余额和返现金）
 * 61: 退货-买家退货（扣减买家的返现金）
 * 63: 退款-收回卖家款项(结算给卖家利润的金额)
 * 64: 收回已占用保证金(结算给卖家释放已占用保证金的金额)
 * 70: 补偿取消的任务-补偿扣除返佣中金额
 */
public enum TradeTypeDic implements EnumBase {
    /**
     * 10: 充值-余额
     */
    CHARGE_BALANCE(10),
    /**
     * 11: 充值-返现金
     */
    CHARGE_CASHBACK(11),
    /**
     * 20: 支付
     */
    PAY(20),
    /**
     * 30: 申请提现
     */
    WITHDRAW_APPLY(30),
    /**
     * 31: 提现
     */
    WITHDRAW_OK(31),
    /**
     * 32: 作废提现
     */
    WITHDRAW_CANCEL(32),
    /**
     * 33: 提现服务费
     */
    WITHDRAW_SEVICE_CHARGE(33),
    /**
     * 40: 进货保证金-充值
     */
    DEPOSIT_CHARGE(40),
    /**
     * 41: 进货保证金-进货
     */
    DEPOSIT_GOODS_IN(41),
    /**
     * 42: 进货保证金-退货
     */
    DEPOSIT_GOODS_RETURN(42),
    /**
     * 50: 结算-结算供应商(将成本打到供应商的余额)
     */
    SETTLE_SUPPLIER(50),
    /**
     * 51: 结算-结算返现金(将返现中的金额移到返现金，注意是买家在本次交易中应获得的返现金金额，而不是买家的全部返现中的返现金)
     */
    SETTLE_CASHBACK(51),
    /**
     * 52: 结算-结算已占用保证金(释放卖家的已占用保证金相应金额)
     */
    SETTLE_DEPOSIT_USED(52),
    /**
     * 53: 结算-结算卖家(将利润打到卖家的余额)
     */
    SETTLE_SELLER(53),
    /**
     * 54: 结算-结算返现中金额(打到买家的返现中金额)
     */
    SETTLE_CASHBACKING(54),
    /**
     * 55: 结算-结算平台服务费
     */
    SETTLE_PLATFORM_SERVICE_FEE(55),
    /**
     * 56: 结算-结算返佣金(将返佣中的金额移到余额，注意是上家在本次交易中应获得的返佣金金额，而不是上家的全部返佣中的金额)
     */
    SETTLE_COMMISSION(56),
    /**
     * 57: 结算-结算返佣中金额(打到上家的返佣中金额)
     */
    SETTLE_COMMISSIONING(57),
    /**
     * 60: 退款-退款给买家（退到买家的余额和返现金）
     */
    REFUND_TO_BUYER(60),
    /**
     * 61: 退款-收回平台服务费(结算给平台的服务费的金额)
     */
    GETBACK_PLATFORM_SERVICE_FEE(61),
    /**
     * 62: 退款-收回供应商款项(结算给供应商的金额)
     */
    GETBACK_SUPPLIER(62),
    /**
     * 63: 退款-收回卖家款项(结算给卖家利润的金额)
     */
    GETBACK_SELLER(63),
    /**
     * 64: 收回已占用保证金(结算给卖家释放已占用保证金的金额)
     */
    GETBACK_DEPOSIT_USED(64),
    /**
     * 70: 补偿取消的任务-补偿扣除返佣中金额
     */
    COMPENDSATE_SUBTRACT_COMMISSIONING(70);

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
