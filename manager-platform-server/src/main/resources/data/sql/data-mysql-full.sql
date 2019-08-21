CREATE DATABASE IF NOT EXISTS `autotest_platform_administrator`;

USE autotest_platform_administrator;

CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(100) NOT NULL,
    `role_cn_name` varchar(100) NOT NULL DEFAULT 'GUEST',
    `role_type` varchar(1000) DEFAULT NULL,
    `comments` varchar(200) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` varchar(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sys_permission` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id` varchar(8) DEFAULT NULL COMMENT '上级权限',
    `permission_name` varchar(20) NOT NULL COMMENT '权限名',
    `permission_value` varchar(20) DEFAULT NULL COMMENT '权限值',
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除：0否,1是',
    `permission_icon` varchar(20) DEFAULT NULL COMMENT '图标',
    `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序编号',
    `permission_type` int(11) NOT NULL DEFAULT '0' COMMENT '权限类型：0菜单，1按钮',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sys_login_record_user` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(8) NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sys_role_permission` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `role_id` varchar(8) NOT NULL,
    `permission_id` varchar(8) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_sys_role_permission_pm` (`permission_id`),
    KEY `FK_sys_role_permission_role` (`role_id`),
    CONSTRAINT `FK_sys_role_permission_pm` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`),
    CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_account` varchar(20) NOT NULL COMMENT '账号',
    `user_password` varchar(100) NOT NULL COMMENT '密码',
    `user_nickname` varchar(20) NOT NULL COMMENT '用户名',
    `mobile_phone` varchar(12) DEFAULT NULL COMMENT '手机号',
    `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
    `user_status` varchar(10) NOT NULL DEFAULT 'true' COMMENT '状态，1或true正常，false或0冻结',
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `admin` varchar(10) DEFAULT 'false' COMMENT '是否管理员',
    `deleted` varchar(10) DEFAULT 'false' COMMENT '是否已删除',
    PRIMARY KEY (`id`),
    UNIQUE `user_account` (`user_account`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `id` int(12) NOT NULL AUTO_INCREMENT,
    `user_id` int(8) NOT NULL COMMENT '用户ID',
    `role_id` int(8) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

ALTER TABLE sys_user_role
    ADD CONSTRAINT `FK_Reference_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE sys_user_role
    ADD CONSTRAINT `FK_Reference_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE TABLE IF NOT EXISTS `sys_login_record` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(8) NOT NULL COMMENT '用户id',
    `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
    `device` varchar(200) DEFAULT NULL COMMENT '设备名',
    `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
    `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP() COMMENT '登录时间',
    PRIMARY KEY (`id`),
    KEY `FK_sys_login_record_user` (`user_id`),
    CONSTRAINT `FK_sys_login_record_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_menu` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `icon` varchar(100) DEFAULT NULL,
    `name` varchar(50) DEFAULT NULL,
    `state` int(11) DEFAULT NULL,
    `url` varchar(200) DEFAULT NULL,
    `p_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1705032705 CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_role_menu` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `menu_id` int(11) DEFAULT NULL,
    `role_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 289 CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_book` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `book_desc` varchar(255) DEFAULT NULL,
    `book_name` varchar(255) DEFAULT NULL,
    `url` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_env_address` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) DEFAULT NULL,
    `server_url` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_dubbo_test` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `case_name` varchar(200) NOT NULL,
    `protocol_name` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `group_name` varchar(255) DEFAULT NULL,
    `interface_name` varchar(255) DEFAULT NULL,
    `method_name` varchar(255) DEFAULT NULL,
    `version` varchar(255) DEFAULT NULL,
    `client` varchar(255) DEFAULT NULL,
    `income_params` varchar(5000) DEFAULT NULL,
    `dubbo_context_params` varchar(5000) DEFAULT NULL,
    `dubbo_test_response` varchar(255) DEFAULT NULL,
    `dubbo_test_check` varchar(255) DEFAULT NULL,
    `dubbo_test_result` varchar(255) DEFAULT NULL,
    `case_desc` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_good_type` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `type_desc` varchar(255) DEFAULT NULL,
    `type_name` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`),
    UNIQUE (`type_name`)
) ENGINE = InnoDB CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `tb_good` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `good_desc` varchar(255) DEFAULT NULL,
    `good_name` varchar(255) DEFAULT NULL,
    `good_type_id` bigint(20) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
	`good_code` varchar(50) DEFAULT NULL COMMENT '物品编号',
	`borrowed_times` bigint(20) DEFAULT 0 COMMENT '借用次数',
	`give_back_times` bigint(20) DEFAULT 0 COMMENT '归还次数',
	`good_status` varchar(20) DEFAULT NULL COMMENT '当前状态',
	`current_owner` varchar(100) DEFAULT NULL COMMENT '当前持有人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

INSERT INTO `sys_role` (`role_name`, `comments`, `create_date`, `update_date`, `deleted`)
VALUES ('系统管理员', '系统管理员', '2018-11-20 19:18:53', '2018-11-20 19:19:04', '0');

INSERT INTO `sys_role` (`role_name`, `comments`, `create_date`, `update_date`, `deleted`)
VALUES ('系统用户', '系统用户', '2018-11-20 19:18:53', '2018-11-20 19:19:04', '0');

INSERT INTO `sys_user` (`user_account`, `user_password`, `user_nickname`, `mobile_phone`, `sex`
    , `user_status`, `create_date`, `update_date`, `admin`, `deleted`)
VALUES ('admin', 'Admin@1234', 'admin', '15869107902', '女'
    , '1', '2018-11-20 19:16:46', NULL, 'true', 'false');

INSERT INTO `sys_user` (`user_account`, `user_password`, `user_nickname`, `mobile_phone`, `sex`
    , `user_status`, `create_date`, `update_date`, `admin`, `deleted`)
VALUES ('sunliuping', 'Admin@1234', 'sunliuping', '15869107902', '男'
    , '1', '2018-11-20 19:16:46', NULL, 'true', 'false');

INSERT INTO `tb_good_type` (`create_date`, `type_desc`, `type_name`, `update_date`)
VALUES ('2018-11-20 20:54:54', '手机类型', '手机', NULL);

INSERT INTO `tb_menu` (`id`, `icon`, `name`, `state`, `url`
    , `p_id`)
VALUES (1, 'menu-plugin', '系统菜单', 1, NULL
        , -1),
    (10, '&#xe68e;', '内容管理', 1, NULL
        , 1),
    (60, '&#xe631;', '系统管理', 1, NULL
        , 1),
    (61, '&#xe705;', '新闻资讯', 1, 'http://www.ifeng.com/'
        , 1),
    (1000, 'icon-text', '文章管理', 2, 'https://www.hongxiu.com/'
        , 10),
    (6000, '&#xe631;', '菜单管理', 2, 'admin/menu/tomunemanage'
        , 60),
    (6010, 'icon-icon10', '角色管理', 2, 'admin/role/torolemanage'
        , 60),
    (6020, '&#xe612;', '用户管理', 2, 'admin/user/tousermanage'
        , 60),
    (6030, '&#xe631;', 'sql监控', 2, 'druid/index.html'
        , 60),
    (6040, 'icon-ziliao', '修改密码', 2, 'admin/user/toUpdatePassword'
        , 60),
    (6050, 'icon-tuichu', '安全退出', 2, 'user/logout'
        , 60),
    (6100, 'icon-text', '凤凰网', 2, 'http://www.ifeng.com/'
        , 61),
    (200000, '44', '44', 3, '44'
        , 2000);

INSERT INTO `tb_role_menu` (`id`, `menu_id`, `role_id`)
VALUES (36, 10, 2),
    (42, 1, 2),
    (45, 1, 4),
    (48, 1, 5),
    (55, 1, 9),
    (65, 1, 7),
    (66, 10, 7),
    (126, 60, 15),
    (127, 6010, 15),
    (128, 6020, 15),
    (129, 6030, 15),
    (130, 6040, 15),
    (131, 6050, 15),
    (248, 2000, 1),
    (259, 100000, 1),
    (278, 10, 1),
    (279, 1000, 1),
    (280, 60, 1),
    (281, 6000, 1),
    (282, 6010, 1),
    (283, 6020, 1),
    (284, 6030, 1),
    (285, 6040, 1),
    (286, 6050, 1),
    (287, 61, 1),
    (288, 6100, 1);

DROP TABLE IF EXISTS `tb_xmind_parser`;

CREATE TABLE IF NOT EXISTS `tb_xmind_parser` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `xmind_file_name` varchar(200) NOT NULL,
    `xmind_file_url` varchar(200) NOT NULL,
    `access_url` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

DROP TABLE IF EXISTS `tb_other_address`;

CREATE TABLE IF NOT EXISTS `tb_other_address` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) DEFAULT NULL,
    `url` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

DROP TABLE IF EXISTS `tb_code_coverage`;

CREATE TABLE IF NOT EXISTS `tb_code_coverage` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) DEFAULT NULL,
    `newer_remote_url` varchar(255) DEFAULT NULL,
    `newer_version` varchar(255) DEFAULT NULL,
    `older_remote_url` varchar(255) DEFAULT NULL,
    `older_version` varchar(255) DEFAULT NULL,
    `tcp_server_ip` varchar(255) DEFAULT NULL,
    `tcp_server_port` int(11) NOT NULL,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `version_control_type` varchar(11) DEFAULT 'SVN',
    `build_type` varchar(11) DEFAULT 'MAVEN',
    `need_compile` varchar(11) DEFAULT 'NO',
    `dependency_projects` varchar(500) DEFAULT NULL,
    `whole_code_coverage_data_url` varchar(255) DEFAULT NULL,
    `increment_code_coverage_data_url` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `channel_name` varchar(50) DEFAULT '' COMMENT 'app渠道名称',
    `create_user` varchar(50) DEFAULT 'admin' COMMENT '创建用户',
    `update_user` varchar(50) DEFAULT 'admin' COMMENT '修改用户',
    `jdk_version` varchar(10) DEFAULT '1.8' COMMENT 'jdk版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;

DROP TABLE IF EXISTS `tb_global_param_config`;
CREATE TABLE IF NOT EXISTS `tb_global_param_config` (
        `id` int(8) NOT NULL AUTO_INCREMENT,
        `param_key` varchar(100) NOT NULL,
        `param_value` varchar(100) DEFAULT NULL,
        `param_comment` varchar(100) DEFAULT NULL,
        `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
        `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `deleted` varchar(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
        `comments` varchar(200) DEFAULT NULL COMMENT '备注',
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB CHARSET = utf8;

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


DROP TABLE IF EXISTS `tb_autotest_task`;
CREATE TABLE IF NOT EXISTS `tb_autotest_task` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `task_name` varchar(100) NOT NULL,
    `executor_ip` varchar(100) DEFAULT NULL,
    `server_port` int(8) default 23,
    `to_execute_script_name` varchar(500) NOT NULL COMMENT '待执行脚本',
    `execute_result_url` varchar(1000) DEFAULT NULL COMMENT '执行结果',
    `execute_detail_url` varchar(1000) DEFAULT NULL COMMENT '执行详情URL',
    `login_user_name` varchar(100) NOT NULL COMMENT '登陆用户名',
    `login_password` varchar(100) NOT NULL COMMENT '登陆密码',
    `comments` varchar(200) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` varchar(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;


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
    `keep_period` varchar(100) DEFAULT NULL COMMENT '持有时间',
    `keep_overtime` tinyint(4) DEFAULT NULL COMMENT '是否超期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;


use autotest_platform_administrator;
DROP TABLE IF EXISTS `tb_timer_task_policy`;
CREATE TABLE IF NOT EXISTS `tb_timer_task_policy`
(
   `id` bigint (20) NOT NULL AUTO_INCREMENT,
   `cname` varchar (100) NOT NULL COMMENT '中文名',
   `ename` varchar (100) NOT NULL COMMENT '英文名',
   `code` varchar (1000) NOT NULL COMMENT '执行的代码',
   `description` varchar (100) DEFAULT NULL COMMENT '备注',
   `create_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '创建时间',
   `update_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '更新时间',
   `create_user` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
   `update_user` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
   PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARSET = utf8;

DROP TABLE IF EXISTS `tb_timer_task`;
CREATE TABLE IF NOT EXISTS `tb_timer_task`
(
   `id` bigint (20) NOT NULL AUTO_INCREMENT,
   `policy_id` bigint (20) NOT NULL COMMENT '策略id',
   `task_name` varchar (100) DEFAULT NULL COMMENT '任务名称',
   `task_group` varchar (100) DEFAULT NULL COMMENT '任务组名称',
   `config` varchar (100) DEFAULT NULL COMMENT '定时任务配置',
   `closed` tinyint (4) DEFAULT 0 COMMENT '是否关闭',
   `description` varchar (100) DEFAULT NULL COMMENT '备注',
   `create_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '创建时间',
   `update_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '更新时间',
   `create_user` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
   `update_user` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
   PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARSET = utf8;

DROP TABLE IF EXISTS `tb_timer_task_monitor`;
CREATE TABLE IF NOT EXISTS `tb_timer_task_monitor`
(
   `id` bigint (20) NOT NULL AUTO_INCREMENT,
   `job_name` varchar (100) DEFAULT NULL COMMENT '任务名称',
   `success` tinyint (4) DEFAULT 0 COMMENT '是否成功',
   `description` varchar (100) DEFAULT NULL COMMENT '备注',
   `create_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '创建时间',
   `update_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '更新时间',
   `create_user` VARCHAR (100) DEFAULT NULL COMMENT '创建人',
   `update_user` VARCHAR (100) DEFAULT NULL COMMENT '更新人',
   `start_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '开始时间',
   `end_date` timestamp DEFAULT CURRENT_TIMESTAMP () COMMENT '结束时间',
   PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARSET = utf8;
