use autotest_platform_administrator;
CREATE TABLE IF NOT EXISTS `tb_server_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `server_name` varchar(255) DEFAULT NULL,
    `server_ip` varchar(255) DEFAULT NULL,
    `server_type` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `can_be_connected` varchar(11) DEFAULT 'NO',
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
    `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

ALTER TABLE `tb_code_coverage`
ADD COLUMN `server_id` bigint(20) DEFAULT NULL COMMENT '代码所在服务器IP的id',
ADD COLUMN `source_code_path` varchar(200) DEFAULT NULL COMMENT '代码所在服务器的路径',
ADD COLUMN `new_source_type` varchar(200) DEFAULT NULL COMMENT '新代码的获取类型';