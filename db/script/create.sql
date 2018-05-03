/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/5/3 10:55:38                            */
/*==============================================================*/


drop table if exists AFC_ACCOUNT;

drop table if exists AFC_FLOW;

drop table if exists AFC_PAY;

drop table if exists AFC_PLATFORM;

drop table if exists AFC_PLATFORM_FLOW;

drop table if exists AFC_TRADE;

drop table if exists AFC_WITHDRAW;

drop table if exists AFC_WITHDRAW_ACCOUNT;

/*==============================================================*/
/* Table: AFC_ACCOUNT                                           */
/*==============================================================*/
create table AFC_ACCOUNT
(
   ID                   bigint not null comment '账户ID(账户ID也就是用户ID)',
   BALANCE              decimal(18,4) not null default 0 comment '余额',
   SETTLE_BALANCE       decimal(18,4) not null default 0 comment '最后结算余额',
   SETTLE_TIME          datetime not null comment '最后结算时间',
   WITHDRAWING          decimal(18,4) not null default 0 comment '提现中总额',
   CASHBACK             decimal(18,4) not null default 0 comment '返现金余额',
   CASHBACKING          decimal(18,4) not null default 0 comment '返现中的金额',
   DEPOSIT              decimal(18,4) not null default 0 comment '进货保证金
            提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额',
   DEPOSIT_USED         decimal(18,4) not null default 0 comment '已占用的进货保证金',
   MODIFIED_TIMESTAMP   bigint not null comment '修改时间戳(添加或更新本条记录时的时间戳)',
   primary key (ID)
);

alter table AFC_ACCOUNT comment '账户信息';

/*==============================================================*/
/* Table: AFC_FLOW                                              */
/*==============================================================*/
create table AFC_FLOW
(
   ID                   bigint not null comment '流水ID(等于交易ID)',
   OLD_MODIFIED_TIMESTAMP bigint not null comment '旧修改时间戳',
   ACCOUNT_ID           bigint not null comment '账户ID(账户ID也就是用户ID)',
   BALANCE              decimal(18,4) not null default 0 comment '余额',
   SETTLE_BALANCE       decimal(18,4) not null default 0 comment '最后结算余额',
   SETTLE_TIME          datetime not null comment '最后结算时间',
   WITHDRAWING          decimal(18,4) not null default 0 comment '提现中总额',
   CASHBACK             decimal(18,4) not null default 0 comment '返现金余额',
   CASHBACKING          decimal(18,4) not null default 0 comment '返现中的金额',
   DEPOSIT              decimal(18,4) not null default 0 comment '进货保证金
            提货时要检查此加盟商的已进货的金额+本次提货的货物成本不能超过此金额',
   DEPOSIT_USED         decimal(18,4) not null default 0 comment '已占用的进货保证金',
   MODIFIED_TIMESTAMP   bigint not null comment '修改时间戳(添加或更新本条记录时的时间戳)',
   primary key (ID),
   unique key AK_ACCOUNT_ID_AND_MODIFIED_TIMESTAMP (ACCOUNT_ID, MODIFIED_TIMESTAMP),
   unique key AK_ACCOUNT_ID_AND_OLD_MODIFIED_TIMESTAMP (ACCOUNT_ID, OLD_MODIFIED_TIMESTAMP)
);

alter table AFC_FLOW comment '账户流水(将账户每一次变更作个记录)';

/*==============================================================*/
/* Table: AFC_PAY                                               */
/*==============================================================*/
create table AFC_PAY
(
   ID                   bigint not null comment '支付ID',
   ACCOUNT_ID           bigint not null comment '账户ID(账户ID也就是用户ID)',
   ORDER_ID             varchar(150) not null comment '订单ID(业务订单ID)',
   PAY_TYPE_ID          tinyint not null comment '支付类型ID(1.V支付;2.微信支付;3.支付宝;4.银联)',
   PAY_ACCOUNT_ID       varchar(150) not null comment '支付账户ID(例如微信ID，支付宝ID，V支付的账户ID也就是本系统的用户ID)',
   PAY_ORDER_ID         varchar(150) not null comment '支付订单ID(V支付订单ID就是交易ID或流水ID)',
   PAY_AMOUNT           decimal(18,4) not null comment '支付金额',
   PAY_TIME             datetime not null comment '支付时间',
   primary key (ID),
   unique key AK_PAY_ORDER_ID (PAY_ORDER_ID),
   unique key AK_PAY_TYPE_AND_ORDER_ID (ORDER_ID, PAY_TYPE_ID)
);

alter table AFC_PAY comment '支付信息';

/*==============================================================*/
/* Table: AFC_PLATFORM                                          */
/*==============================================================*/
create table AFC_PLATFORM
(
   ID                   bigint not null comment '平台信息ID',
   BALANCE              decimal(18,4) not null default 0 comment '余额',
   MODIFIED_TIME        timestamp not null comment '修改时间',
   primary key (ID)
);

alter table AFC_PLATFORM comment '平台信息';

/*==============================================================*/
/* Table: AFC_PLATFORM_FLOW                                     */
/*==============================================================*/
create table AFC_PLATFORM_FLOW
(
   ID                   bigint not null comment '平台流水ID',
   BALANCE              decimal(18,4) not null default 0 comment '余额',
   ORDER_ID             bigint not null comment '销售订单ID',
   FLOW_TYPE            tinyint not null comment '流水类型',
   OLD_MODIFIED_TIMESTAMP bigint not null comment '旧修改时间戳',
   SETTLE_BALANCE       decimal(18,4) not null default 0 comment '最后结算余额',
   SETTLE_TIME          datetime not null comment '最后结算时间',
   MODIFIED_TIMESTAMP   bigint not null comment '修改时间戳(添加或更新本条记录时的时间戳)',
   primary key (ID)
);

alter table AFC_PLATFORM_FLOW comment '平台流水';

/*==============================================================*/
/* Table: AFC_TRADE                                             */
/*==============================================================*/
create table AFC_TRADE
(
   ID                   bigint not null comment '交易ID(等于流水ID)',
   ACCOUNT_ID           bigint not null comment '账户ID(账户ID也就是用户ID)',
   TRADE_TYPE           tinyint not null comment '交易类型',
   TRADE_AMOUNT         decimal(18,4) not null comment '交易金额（收入为正数，支出为负数）',
   CHANGE_AMOUNT1       decimal(18,4) comment '交易改变金额1，在交易类型是支付时代表返现金改变了多少',
   CHANGE_AMOUNT2       decimal(18,4) comment '交易改变金额2，在交易类型是支付时代表余额改变了多少',
   TRADE_TITLE          varchar(50) not null comment '交易标题',
   TRADE_DETAIL         varchar(150) comment '交易详情',
   TRADE_TIME           datetime not null comment '交易时间',
   ORDER_ID             varchar(150) not null comment '订单ID(业务订单ID)',
   TRADE_VOUCHER_NO     varchar(150) comment '交易凭证号',
   OP_ID                bigint not null comment '操作人ID',
   MAC                  varchar(30) not null comment 'MAC地址',
   IP                   varchar(150) not null comment 'IP地址',
   primary key (ID),
   unique key AK_TRADE_TYPE_AND_ACCOUNT_AND_ORDER_ID (TRADE_TYPE, ACCOUNT_ID, ORDER_ID),
   unique key AK_TRADE_TYPE_AND_TRADE_VOUCHE_NO (TRADE_TYPE, TRADE_VOUCHER_NO)
);

alter table AFC_TRADE comment '账户交易(账户交易流水)';

/*==============================================================*/
/* Table: AFC_WITHDRAW                                          */
/*==============================================================*/
create table AFC_WITHDRAW
(
   ID                   bigint not null comment '提现记录',
   ACCOUNT_ID           bigint not null comment '申请时记录-账户ID（方便查询）',
   ORDER_ID             varchar(150) not null comment '申请单号（唯一约束，以防重复申请）',
   WITHDRAW_STATE       tinyint not null comment '提现状态(-1-作废；1-申请；2-处理中；3-已提现)',
   TRADE_TITLE          varchar(50) not null comment '交易标题',
   TRADE_DETAIL         varchar(150) comment '交易详情',
   AMOUNT               decimal(18,4) not null comment '提现金额(提现金额=实际到账金额+提现服务费)',
   REAL_AMOUNT          decimal(18,4) not null comment '实际到账金额',
   SEVICE_CHARGE        decimal(18,4) not null comment '提现服务费',
   APPLICANT_ID         bigint not null comment '申请时记录-申请人ID',
   APPLY_TIME           datetime not null comment '申请时记录-申请时间',
   APPLICANT_MAC        varchar(30) not null comment '申请时记录-申请人MAC地址',
   APPLICANT_IP         varchar(150) not null comment '申请时记录-申请人IP地址',
   RECIEVER_ID          bigint comment '受理时记录-受理人ID',
   RECIEVER_TIME        datetime comment '受理时记录-受理时间',
   RECIEVER_MAC         varchar(30) comment '受理时记录-受理人MAC地址',
   RECIEVER_IP          varchar(150) comment '受理时记录-受理人IP地址',
   FINISHER_ID          bigint comment '提现成功或作废时记录-结束人ID',
   FINISHER_TIME        datetime comment '提现成功或作废时记录-结束时间',
   FINISHER_MAC         varchar(30) comment '提现成功或作废时记录-结束人MAC地址',
   FINISHER_IP          varchar(150) comment '提现成功或作废时记录-结束人IP地址',
   WITHDRAW_TYPE        tinyint not null comment '提现类型(1-银行卡,2-支付宝)',
   CONTACT_TEL          varchar(50) not null comment '提现联系电话',
   BANK_ACCOUNT_NO      varchar(100) not null comment '提现银行账号',
   BANK_ACCOUNT_NAME    varchar(150) not null comment '提现银行账户名称',
   OPEN_ACCOUNT_BANK    varchar(150) not null comment '提现开户银行',
   CANCEL_REASON        varchar(100) comment '作废提现时记录-作废原因',
   primary key (ID),
   unique key AK_ORDER_ID (ORDER_ID)
);

alter table AFC_WITHDRAW comment '提现信息';

/*==============================================================*/
/* Table: AFC_WITHDRAW_ACCOUNT                                  */
/*==============================================================*/
create table AFC_WITHDRAW_ACCOUNT
(
   ID                   bigint not null comment '提现账户ID',
   IS_DEF               bool not null comment '是否默认提现账户',
   ACCOUNT_ID           bigint not null comment '账户ID',
   WITHDRAW_TYPE        tinyint not null comment '提现类型(1-银行卡,2-支付宝)',
   CONTACT_TEL          varchar(50) comment '联系电话',
   BANK_ACCOUNT_NO      varchar(100) not null comment '银行账号',
   BANK_ACCOUNT_NAME    varchar(150) not null comment '银行账户名称',
   OPEN_ACCOUNT_BANK    varchar(150) not null comment '开户银行',
   OP_ID                bigint not null comment '操作人ID',
   MAC                  varchar(30) not null comment 'MAC地址',
   IP                   varchar(150) not null comment 'IP地址',
   MODIFIED_TIMESTAMP   bigint not null comment '修改时间戳(添加或更新本条记录时的时间戳)',
   primary key (ID)
);

alter table AFC_WITHDRAW_ACCOUNT comment '提现账户';

