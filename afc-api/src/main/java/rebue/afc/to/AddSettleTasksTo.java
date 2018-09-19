package rebue.afc.to;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 添加结算任务的传输对象
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class AddSettleTasksTo {
    /**
     * 订单ID
     * (销售订单ID)
     */
    private String                       orderId;
    /**
     * 买家的账户ID
     */
    private Long                         buyerAccountId;
    /**
     * 卖家的账户ID
     */
    private Long                         sellerAccountId;

    /**
     * 结算给买家返现金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleBuyerCashbackTime;
    /**
     * 结算给上家佣金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleUplineCommissionTime;
    /**
     * 结算给平台服务费的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settlePlatformServiceFeeTime;
    /**
     * 结算给供应商的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleSupplierTime;
    /**
     * 结算给卖家的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleSellerTime;
    /**
     * 结算卖家已占用保证金的计划执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date                         settleDepositUsedTime;

    /**
     * MAC地址
     */
    private String                       mac;
    /**
     * IP地址
     */
    private String                       ip;

    /**
     * 添加结算任务的详情
     */
    private List<AddSettleTasksDetailTo> details;

}