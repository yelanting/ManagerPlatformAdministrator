/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:48:56
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.administrator.platform.model.AutoTestTask;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:48:56
 * @see :
 */
public interface TaskService {
	/**
	 * 查询当前列表-所有
	 * 
	 * @see : 获取列表
	 * @param :
	 * @return : List<AutoTestTask>
	 * @return
	 */
	List<AutoTestTask> getList();

	/**
	 * 添加全局参数
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask addAutoTestTask(AutoTestTask autoTestTask);

	/**
	 * 修改数据
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask updateAutoTestTask(AutoTestTask autoTestTask);

	/**
	 * 根据搜索内容查询列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<AutoTestTask>
	 * @param searchContent
	 * @return
	 */
	List<AutoTestTask> searchAutoTestTasksBySearchContent(String searchContent);

	/**
	 * 单个删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param id
	 */
	Long deleteAutoTestTask(Long id);

	/**
	 * 批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	Long[] deleteAutoTestTask(Long[] ids);

	/**
	 * 根据主键查询
	 * 
	 * @see : 根据主键查询
	 * @param :
	 * @return : AutoTestTask
	 * @param id
	 * @return
	 */
	AutoTestTask selectByPrimaryKey(Long id);

	/**
	 * 根据对象查询
	 * 
	 * @see : 根据具体对象查询
	 * @param :
	 * @return : AutoTestTask
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask selectByObject(AutoTestTask autoTestTask);

	/**
	 * 执行任务
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask executeTask(AutoTestTask autoTestTask);

	/**
	 * 取消任务
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask cancelTask(AutoTestTask autoTestTask);

	/**
	 * 获取任务执行信息
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask getExecutionProcess(AutoTestTask autoTestTask);

	/**
	 * 下载测试报告
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param autoTestTask
	 * @return
	 */
	AutoTestTask downloadExecutionReport(AutoTestTask autoTestTask);

	/**
	 * 下载测试报告
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param autoTestTask:任务对象
	 * @param httpServletResponse:
	 *            响应对象
	 * @return
	 */
	void downloadExecutionReport(AutoTestTask autoTestTask,
	        HttpServletResponse httpServletResponse);

	/**
	 * 检查任务是否存在
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param taskName
	 * @return
	 */
	boolean checkTaskNameExists(String taskName);

	/**
	 * 检查任务是否正在执行
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param taskName
	 * @return
	 */
	boolean isTaskRunning(String taskName);
}
