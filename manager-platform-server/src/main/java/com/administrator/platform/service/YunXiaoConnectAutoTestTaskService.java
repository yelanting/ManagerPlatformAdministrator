/**
 * @author : 孙留平
 * @since : 2019年5月16日 下午3:40:38
 * @see:
 */
package com.administrator.platform.service;

import javax.servlet.http.HttpServletResponse;

import com.administrator.platform.model.AutoTestTask;
import com.administrator.platform.model.YunXiaoConnectAutoTestTask;

/**
 * @author : Administrator
 * @since : 2019年5月16日 下午3:40:38
 * @see :
 */
public interface YunXiaoConnectAutoTestTaskService {

	/**
	 * 获取执行报告
	 * 
	 * @see :
	 * @param yunXiaoConnectAutoTestTask:
	 *            回调实体
	 * @param httpServletResponse:响应
	 * @return : void
	 */
	void getAutoTestTaskExecutionReport(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask,
	        HttpServletResponse httpServletResponse);

	/**
	 * 执行任务
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param yunXiaoConnectAutoTestTask
	 * @return
	 */
	AutoTestTask executeAutoTestTask(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask);

	/**
	 * 执行任务
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param yunXiaoConnectAutoTestTask
	 * @return
	 */
	AutoTestTask cancelAutoTestTask(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask);

	/**
	 * 查看执行进度
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param yunXiaoConnectAutoTestTask
	 * @return
	 */
	AutoTestTask getAutoTestTaskProcess(
	        YunXiaoConnectAutoTestTask yunXiaoConnectAutoTestTask);
}
