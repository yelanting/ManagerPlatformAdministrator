use autotest_platform_administrator;
DROP TABLE IF EXISTS `tb_autotest_task`;
CREATE TABLE IF NOT EXISTS `tb_autotest_task`
(
   `id` int (8) NOT NULL AUTO_INCREMENT,
   `task_name` varchar (100) NOT NULL,
   `executor_ip` varchar (100) DEFAULT NULL,
   `server_port` int (8) default 23,
   `to_execute_script_name` varchar (500) NOT NULL COMMENT '待执行脚本',
   `execute_result_url` varchar (1000) DEFAULT NULL COMMENT '执行结果',
   `execute_detail_url` varchar (1000) DEFAULT NULL COMMENT '执行详情URL',
   `login_user_name` varchar (100) NOT NULL COMMENT '登陆用户名',
   `login_password` varchar (100) NOT NULL COMMENT '登陆密码',
   `comments` varchar (200) DEFAULT NULL,
   `create_date` timestamp DEFAULT CURRENT_TIMESTAMP (),
   `update_date` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `deleted` varchar (11) NOT NULL DEFAULT '0' COMMENT '是否删除',
   PRIMARY KEY (`id`)
)ENGINE = MyISAM CHARSET = utf8;