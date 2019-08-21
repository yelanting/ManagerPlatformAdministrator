use autotest_platform_administrator;

ALTER TABLE `tb_good`
ADD COLUMN `current_owner` varchar(100) DEFAULT NULL COMMENT '当前持有人';


ALTER TABLE `tb_good_operation_record`
ADD COLUMN `keep_period` varchar(100) DEFAULT NULL COMMENT '持有时间',
ADD COLUMN `keep_overtime` tinyint(4) DEFAULT NULL COMMENT '是否超期';