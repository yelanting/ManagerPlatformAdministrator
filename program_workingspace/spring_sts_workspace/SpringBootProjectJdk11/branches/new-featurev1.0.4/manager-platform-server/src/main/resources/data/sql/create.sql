CREATE DATABASE `autotest_platform_administrator` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `sys_role` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(100) NOT NULL,
    `comments` varchar(200) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

alter table sys_role add column `role_type` varchar(1000) DEFAULT NULL;

CREATE TABLE `sys_permission` (
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
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_login_record_user` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` varchar(8) NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_role_permission` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `role_id` varchar(8) NOT NULL,
    `permission_id` varchar(8) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_sys_role_permission_pm` (`permission_id`),
    KEY `FK_sys_role_permission_role` (`role_id`),
    CONSTRAINT `FK_sys_role_permission_pm` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`),
    CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_user` (
    `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_account` varchar(20) NOT NULL COMMENT '账号',
    `user_password` varchar(32) NOT NULL COMMENT '密码',
    `user_nickname` varchar(20) NOT NULL COMMENT '用户名',
    `mobile_phone` varchar(12) DEFAULT NULL COMMENT '手机号',
    `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
    `user_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `role_id` varchar(8) NOT NULL COMMENT '角色ID',
    `admin` varchar(10) DEFAULT 'false' COMMENT '是否管理员',
    PRIMARY KEY (`id`),
    UNIQUE `user_account` (`user_account`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_user_role` (
    `id` int(12) NOT NULL AUTO_INCREMENT,
    `user_id` int(8) NOT NULL COMMENT '用户ID',
    `role_id` int(8) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

ALTER TABLE sys_user_role
    ADD CONSTRAINT `FK_Reference_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE sys_user_role
    ADD CONSTRAINT `FK_Reference_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE TABLE `sys_login_record` (
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
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_menu` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `icon` varchar(100) DEFAULT NULL,
    `name` varchar(50) DEFAULT NULL,
    `state` int(11) DEFAULT NULL,
    `url` varchar(200) DEFAULT NULL,
    `p_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1705032705 CHARSET = utf8;

CREATE TABLE `tb_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_book` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `book_desc` varchar(255) DEFAULT NULL,
    `book_name` varchar(255) DEFAULT NULL,
    `url` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_env_address` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(255) DEFAULT NULL,
    `server_url` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_dubbo_test` (
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
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_good_type` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `type_desc` varchar(255) DEFAULT NULL,
    `type_name` varchar(255) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`),
    UNIQUE (`type_name`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_good` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `good_desc` varchar(255) DEFAULT NULL,
    `good_name` varchar(255) DEFAULT NULL,
    `good_type_id` bigint(20) DEFAULT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    `update_date` timestamp DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

INSERT INTO `sys_role` (`role_name`, `comments`, `create_date`, `update_date`, `is_delete`)
VALUES ('系统管理员', '系统管理员', '2018-11-20 19:18:53', '2018-11-20 19:19:04', 0);

INSERT INTO `sys_role` (`role_name`, `comments`, `create_date`, `update_date`, `is_delete`)
VALUES ('系统用户', '系统用户', '2018-11-20 19:18:53', '2018-11-20 19:19:04', 0);

INSERT INTO `sys_user` (`user_account`, `user_password`, `user_nickname`, `mobile_phone`, `sex`
    , `user_status`, `create_date`, `update_date`, `role_id`, `admin`)
VALUES ('admin', 'Admin@1234', 'admin', '15869107902', '女'
    , 0, '2018-11-20 19:16:46', NULL, 1, '1');

INSERT INTO `sys_user` (`user_account`, `user_password`, `user_nickname`, `mobile_phone`, `sex`
    , `user_status`, `create_date`, `update_date`, `role_id`, `admin`)
VALUES ('sunliuping', 'Admin@1234', 'sunliuping', '15869107902', '男'
    , 0, '2018-11-20 19:16:46', NULL, 1, '1');

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