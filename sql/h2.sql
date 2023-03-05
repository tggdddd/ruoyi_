create table `ruoyi-vue-pro`.c_performance_report
(
    id              bigint unsigned auto_increment comment '主键(自增策略)'
        primary key,
    contract_id     bigint                                not null comment '关联的合同表ID',
    form_ids        varchar(255)                          not null comment '业绩提交表单ids',
    start_time      datetime                              not null comment '业绩表单提交开始开始时间',
    end_time        datetime                              null comment '业绩表单提交终止时间',
    notify_time     datetime                              null comment '业绩表单提交通知时间',
    notify_duration datetime                              null comment '业绩表单提交通知频率',
    creator         varchar(64) default ''                null comment '创建者',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64) default ''                null comment '更新者',
    update_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit         default b'0'              not null comment '是否删除',
    tenant_id       bigint      default 0                 not null comment '租户编号'
)
    comment '业绩定义表' collate = utf8mb4_unicode_ci;

