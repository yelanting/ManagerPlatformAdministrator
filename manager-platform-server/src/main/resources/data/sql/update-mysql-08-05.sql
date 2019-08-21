use autotest_platform_administrator;

ALTER TABLE `tb_good`
ADD COLUMN `good_code` varchar(50) DEFAULT NULL COMMENT '物品编号',
ADD COLUMN `borrowed_times` bigint(20) DEFAULT 0 COMMENT '借用次数',
ADD COLUMN `give_back_times` bigint(20) DEFAULT 0 COMMENT '归还次数',
ADD COLUMN `good_status` varchar(20) DEFAULT NULL COMMENT '当前状态';

CREATE TABLE IF NOT EXISTS `tb_good_operation_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `good_id` bigint(20) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `borrow_date` timestamp DEFAULT CURRENT_TIMESTAMP() COMMENT '借用时间',
    `expected_give_back_date` timestamp DEFAULT CURRENT_TIMESTAMP() COMMENT '预计归还时间',
    `borrow_user` varchar(100) DEFAULT NULL COMMENT '借用人',
    `give_back_user` varchar(100)  DEFAULT NULL COMMENT '归还人',
    `realistic_give_back_date` timestamp DEFAULT CURRENT_TIMESTAMP() NULL COMMENT '实际归还时间',
    `operation_type` varchar(100) DEFAULT NULL COMMENT '操作类型：归还，还是借',
    `deal_person` varchar(100) DEFAULT NULL COMMENT '经手人',
    `description` varchar(100) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;
