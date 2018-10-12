-- 2018年10月12日18:53:18 
-- 删除提现账号绑定流程表（AFC_WITHDRAW_ACCOUNT_BIND_FLOW）字段ip地址（IP）
alter table onl.ONL_ONLINE_SPEC drop column IP;
-- 2018-09-19 更新到大卖网线上
-- 添加返佣金的字段
alter table AFC_ACCOUNT add COMMISSION_TOTAL Decimal(18,4) default 0 not null comment '已返佣金总额';
alter table AFC_ACCOUNT add COMMISSIONING Decimal(18,4) default 0 not null comment '待返佣金';
alter table AFC_FLOW add COMMISSION_TOTAL Decimal(18,4) not null comment '已返佣金总额';
alter table AFC_FLOW add COMMISSIONING Decimal(18,4) not null comment '待返佣金';
create table AFC_WITHDRAW_ACCOUNT_BIND_FLOW
(
   ID                   bigint not null comment '流程ID',
   FLOW_STATE           tinyint not null comment '流程状态(-1：已拒绝；1：待审核； 2：已审核)
            ',
   REJECT_REASON        varchar(100) comment '拒绝原因',
   APPLICANT_ID         bigint not null comment '申请人ID',
   APPLY_TIME           datetime not null comment '申请时间',
   APPLICANT_IP         varchar(150) not null comment '申请人IP地址',
   REVIEWER_ID          bigint comment '审核人ID',
   REVIEW_TIME          datetime comment '审核时间',
   REVIEWER_IP          varchar(150) comment '审核人IP地址',
   ACCOUNT_ID           bigint not null comment '账户ID（绑定账户的ID）',
   WITHDRAW_TYPE        tinyint not null comment '提现类型(1-银行卡,2-支付宝)',
   CONTACT_TEL          varchar(50) not null comment '联系电话',
   BANK_ACCOUNT_NO      varchar(100) not null comment '银行账号',
   BANK_ACCOUNT_NAME    varchar(150) not null comment '银行账户名称',
   OPEN_ACCOUNT_BANK    varchar(150) not null comment '开户银行',
   IP                   varchar(150) not null comment 'IP地址',
   MODIFIED_TIMESTAMP   bigint not null comment '修改时间戳(添加或更新本条记录时的时间戳)',
   primary key (ID)
);

alter table AFC_WITHDRAW_ACCOUNT_BIND_FLOW comment '提现账户绑定流程';

