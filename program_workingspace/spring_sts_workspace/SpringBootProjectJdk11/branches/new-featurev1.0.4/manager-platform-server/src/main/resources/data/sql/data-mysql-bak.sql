
CREATE TABLE `sys_role` (
    `id` varchar(8) NOT NULL,
    `role_name` varchar(100) NOT NULL,
    `comments` varchar(200) DEFAULT NULL,
    `create_time` datetime NOT NULL,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_permission` (
    `permission_id` varchar(8) NOT NULL COMMENT '主键id',
    `parent_id` varchar(8) DEFAULT NULL COMMENT '上级权限',
    `permission_name` varchar(20) NOT NULL COMMENT '权限名',
    `permission_value` varchar(20) DEFAULT NULL COMMENT '权限值',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除：0否,1是',
    `permission_icon` varchar(20) DEFAULT NULL COMMENT '图标',
    `order_number` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序编号',
    `permission_type` int(11) NOT NULL DEFAULT '0' COMMENT '权限类型：0菜单，1按钮',
    PRIMARY KEY (`permission_id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_dictionarydata` (
    `dictdata_code` varchar(8) NOT NULL COMMENT '字典项主键',
    `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
    `dictdata_name` varchar(50) NOT NULL COMMENT '字典项值',
    `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `sort_number` int(11) NOT NULL COMMENT '排序',
    `description` varchar(200) DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`dictdata_code`),
    KEY `FK_Reference_36` (`dict_code`),
    CONSTRAINT `FK_Reference_36` FOREIGN KEY (`dict_code`) REFERENCES `sys_dictionary` (`dict_code`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_dictionary` (
    `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
    `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
    `description` varchar(200) DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`dict_code`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '字典';

CREATE TABLE `sys_login_record_user` (
    `id` varchar(8) NOT NULL COMMENT '主键',
    `user_id` varchar(8) NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_login_record` (
    `id` varchar(8) NOT NULL COMMENT '主键',
    `user_id` varchar(8) NOT NULL COMMENT '用户id',
    `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
    `device` varchar(200) DEFAULT NULL COMMENT '设备名',
    `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
    `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
    `create_time` datetime NOT NULL COMMENT '登录时间',
    PRIMARY KEY (`id`),
    KEY `FK_sys_login_record_user` (`user_id`),
    CONSTRAINT `FK_sys_login_record_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_role_permission` (
    `id` varchar(8) NOT NULL,
    `role_id` varchar(8) NOT NULL,
    `permission_id` varchar(8) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_sys_role_permission_pm` (`permission_id`),
    KEY `FK_sys_role_permission_role` (`role_id`),
    CONSTRAINT `FK_sys_role_permission_pm` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`permission_id`),
    CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_user` (
    `id` varchar(8) NOT NULL COMMENT '主键id',
    `user_account` varchar(20) NOT NULL COMMENT '账号',
    `user_password` varchar(32) NOT NULL COMMENT '密码',
    `user_nickname` varchar(20) NOT NULL COMMENT '用户名',
    `mobile_phone` varchar(12) DEFAULT NULL COMMENT '手机号',
    `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
    `user_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
    `create_time` datetime NOT NULL COMMENT '注册时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `role_id` varchar(8) NOT NULL COMMENT '角色ID',
    `admin` varchar(10) DEFAULT 'false' COMMENT '是否管理员',
    PRIMARY KEY (`id`),
    UNIQUE `user_account` (`user_account`),
    KEY `FK_sys_user_role` (`role_id`),
    CONSTRAINT `FK_sys_user_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `sys_user_role` (
    `user_id` varchar(8) NOT NULL COMMENT '用户ID',
    `role_id` varchar(8) NOT NULL COMMENT '角色ID'
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_book` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `book_desc` varchar(255) DEFAULT NULL,
    `book_name` varchar(255) DEFAULT NULL,
    `create_date` varchar(255) DEFAULT NULL,
    `update_date` varchar(255) DEFAULT NULL,
    `url` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_env_address` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_date` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `project_name` varchar(255) DEFAULT NULL,
    `server_url` varchar(255) DEFAULT NULL,
    `update_date` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_dubbo_test` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_date` varchar(255) DEFAULT NULL,
    `update_date` varchar(255) DEFAULT NULL,
    `zk_server` varchar(255) DEFAULT NULL,
    `interface_name` varchar(255) DEFAULT NULL,
    `method_name` varchar(255) DEFAULT NULL,
    `version` varchar(255) DEFAULT NULL,
    `dubbo_test_response` varchar(255) DEFAULT NULL,
    `dubbo_test_check` varchar(255) DEFAULT NULL,
    `dubbo_test_cesult` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_good_type` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_date` varchar(255) DEFAULT NULL,
    `type_desc` varchar(255) DEFAULT NULL,
    `type_name` varchar(255) DEFAULT NULL,
    `update_date` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

CREATE TABLE `tb_good` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_date` varchar(255) DEFAULT NULL,
    `good_desc` varchar(255) DEFAULT NULL,
    `good_name` varchar(255) DEFAULT NULL,
    `good_type_id` bigint(20) DEFAULT NULL,
    `update_date` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM CHARSET = utf8;

INSERT INTO `sys_role` (`role_id`, `role_name`, `comments`, `create_time`, `update_time`
    , `is_delete`)
VALUES (1, '系统管理员', '系统管理员', '2018-11-20 19:18:53', '2018-11-20 19:19:04'
    , 0);

INSERT INTO `sys_user` (`user_id`, `user_account`, `user_password`, `user_nickname`, `mobile_phone`
    , `sex`, `user_status`, `create_time`, `update_time`, `role_id`
    , `token`)
VALUES (0, 'admin', 'Admin@1234', 'admin', '15869107902'
    , '女', 0, '2018-11-20 19:16:46', NULL, 1
    , NULL);

INSERT INTO `sys_user` (`user_id`, `user_account`, `user_password`, `user_nickname`, `mobile_phone`
    , `sex`, `user_status`, `create_time`, `update_time`, `role_id`
    , `token`)
VALUES (1, 'sunliuping', 'Admin@1234', 'sunliuping', '15869107902'
    , '男', 0, '2018-11-20 19:16:46', NULL, 1
    , NULL);

INSERT INTO `tb_good_type` (`id`, `create_date`, `type_desc`, `type_name`, `update_date`)
VALUES (1, '2018-11-20 20:54:54', '手机类型', '手机', NULL);

    

