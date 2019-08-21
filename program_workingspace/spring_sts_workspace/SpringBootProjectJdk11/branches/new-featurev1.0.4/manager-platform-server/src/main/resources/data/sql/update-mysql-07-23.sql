use autotest_platform_administrator;

/*添加渠道名称字段，用于控制android端构建任务,只在构建类型是GRADLE的时候生效*/
ALTER TABLE `tb_code_coverage`
ADD COLUMN `create_user` varchar(50) DEFAULT 'admin' COMMENT '创建用户',
ADD COLUMN `update_user` varchar(50) DEFAULT 'admin' COMMENT '修改用户';