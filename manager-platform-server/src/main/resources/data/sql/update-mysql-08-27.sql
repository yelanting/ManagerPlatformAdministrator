use autotest_platform_administrator;
DROP TABLE IF EXISTS `tb_java_file_parser`;
CREATE TABLE IF NOT EXISTS `tb_java_file_parser`
(
   `id` bigint (20) NOT NULL AUTO_INCREMENT,
   `project_name` varchar (255) DEFAULT NULL,
   `newer_remote_url` varchar (255) DEFAULT NULL,
   `newer_version` varchar (255) DEFAULT NULL,
   `pinyin_name` varchar (255) DEFAULT NULL,
   `username` varchar (255) DEFAULT NULL,
   `password` varchar (255) DEFAULT NULL,
   `description` varchar (255) DEFAULT NULL,
   `version_control_type` varchar (11) DEFAULT 'SVN',
   `result_url` varchar (255) DEFAULT NULL,
   `create_user` varchar (255) DEFAULT NULL,
   `update_user` varchar (255) DEFAULT NULL,
   `create_date` timestamp DEFAULT CURRENT_TIMESTAMP (),
   `update_date` timestamp DEFAULT CURRENT_TIMESTAMP (),
   PRIMARY KEY (`id`)
)
ENGINE = MyISAM CHARSET = utf8;