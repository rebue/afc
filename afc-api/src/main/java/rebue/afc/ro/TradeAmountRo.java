package rebue.afc.ro;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 交易金额返回对象
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TradeAmountRo {
    /**
     * 交易总额（收入为正数，支出为负数）
     *
     * 数据库字段: AFC_TRADE.TRADE_AMOUNT
     */
    private BigDecimal tradeAmount;

    /**
     * 交易改变金额1，在交易类型是支付和退款时代表返现金改变了多少
     *
     * 数据库字段: AFC_TRADE.CHANGE_AMOUNT1
     */
    private BigDecimal changeAmount1;

    /**
     * 交易改变金额2，在交易类型是支付和退款时代表余额改变了多少
     *
     * 数据库字段: AFC_TRADE.CHANGE_AMOUNT2
     */
    private BigDecimal changeAmount2;
}
