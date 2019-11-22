/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月8日 下午5:04:20
 * @see:
 */
package com.administrator.platform.constdefine;

public class TimerTaskAndRelations {
	public static final String COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CNAME = "定时采集覆盖率数据策略";
	public static final String COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_ENAME = "timer_collecting_exec_data";
	public static final String COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_DESC = "这个策略是定时采集覆盖率数据的，不可删除";
	public static final String COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CODE = "codeCoverageService.timerTaskCollectingExecDataAndBackUp";

	public static final String PIPELINE_CONFIG_POLICY_CNAME = "定时构建流水线策略";
	public static final String PIPELINE_CONFIG_POLICY_ENAME = "timer_build_pipeline";
	public static final String PIPELINE_CONFIG_POLICY_DESC = "这个策略是定时构建流水线的，不可删除";
	public static final String PIPELINE_CONFIG_POLICY_CODE = "pipeLineService.timerTaskBuildPipeLine";

	public static final String CONFIG_MANAGE_POLICY_CNAME = "定时构建配置管理策略";
	public static final String CONFIG_MANAGE_POLICY_ENAME = "timer_build_config_manage";
	public static final String CONFIG_MANAGE_POLICY_DESC = "这个策略是定时构建配置管理流水线的，不可删除";
	public static final String CONFIG_MANAGE_POLICY_CODE = "configManageService.timerTaskBuildConfigManage";
}
