/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午7:15:13
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.AutoTestTaskMapper;
import com.administrator.platform.model.AutoTestTask;
import com.administrator.platform.service.JenkinsService;
import com.administrator.platform.service.TaskService;
import com.administrator.platform.vo.JenkinsJobVO;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午7:15:13
 * @see :
 */
@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory
	        .getLogger(TaskServiceImpl.class);

	@Autowired
	private AutoTestTaskMapper autoTestTaskMapper;

	@Autowired
	private JenkinsService jenkinsService;

	/**
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#getList()
	 */
	@Override
	public List<AutoTestTask> getList() {
		return autoTestTaskMapper.findAll();
	}

	/**
	 * @see 添加任务
	 */
	@Override
	public AutoTestTask addAutoTestTask(AutoTestTask autoTestTask) {
		logger.info("添加任务:{}", autoTestTask);
		if (checkTaskNameExists(autoTestTask.getTaskName())) {
			throw new BusinessValidationException("该任务名称已经存在，不可重复添加");
		}

		try {

			int resultKey = autoTestTaskMapper.insert(autoTestTask);
			autoTestTask.setId(Long.valueOf(resultKey));
			return autoTestTask;
		} catch (Exception e) {
			logger.error("添加任务失败,错误信息:{}", e.getMessage());
			throw new BusinessValidationException("添加任务失败");
		}
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.AutoTestTaskService#updateAutoTestTask(com.tianque.yunxiao.connect.server.model.AutoTestTask)
	 */
	@Override
	public AutoTestTask updateAutoTestTask(AutoTestTask autoTestTask) {
		logger.info("修改任务");
		ValidationUtil.validateNull(autoTestTask.getId(), null);

		if (checkTaskNameExists(autoTestTask)) {
			throw new BusinessValidationException("该任务名称已经存在，不可修改为此任务名称");
		}

		AutoTestTask currentAutoTestTask = selectByObject(autoTestTask);

		if (null == currentAutoTestTask) {
			throw new BusinessValidationException("待修改对象不存在，不能修改");
		}

		try {
			this.autoTestTaskMapper.updateByPrimaryKey(autoTestTask);
			return autoTestTask;
		} catch (Exception e) {
			throw new BusinessValidationException("修改失败");
		}

	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.AutoTestTaskService#searchAutoTestTasksBySearchContent(java.lang.String)
	 */
	@Override
	public List<AutoTestTask> searchAutoTestTasksBySearchContent(
	        String searchContent) {
		return autoTestTaskMapper
		        .findAutoTestTasksByTaskNameLike(searchContent);
	}

	/**
	 * @see 单个删除
	 */
	@Override
	public Long deleteAutoTestTask(Long id) {
		ValidationUtil.validateNull(id, null);

		AutoTestTask currentAutoTestTask = selectByPrimaryKey(id);

		if (null == currentAutoTestTask) {
			throw new BusinessValidationException("该id对应的记录不存在，不需要删除");
		}

		try {
			logger.info("开始删除id为:{}的任务", id);

			/**
			 * 附带删除jenkins中的job
			 */

			JenkinsJobVO jenkinsJobVO = JenkinsJobVO
			        .fromAutoTestTask(currentAutoTestTask);
			jenkinsService.deleteJob(jenkinsJobVO);
			autoTestTaskMapper.deleteByPrimaryKey(id);
			return id;
		} catch (Exception e) {
			logger.error("删除id为:{}的任务失败,失败原因:{}", id, e.getMessage());
			throw new BusinessValidationException("删除任务失败");
		}
	}

	/**
	 * 批量删除
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.AutoTestTaskService#deleteAutoTestTask(java.lang.Long[])
	 */
	@Override
	public Long[] deleteAutoTestTask(Long[] ids) {
		ValidationUtil.validateNull(ids, null);
		ValidationUtil.validateArrayNullOrEmpty(ids, null);

		for (Long long1 : ids) {
			AutoTestTask autoTestTask = selectByPrimaryKey(long1);
			if (null != autoTestTask) {
				JenkinsJobVO jenkinsJobVO = JenkinsJobVO
				        .fromAutoTestTask(autoTestTask);
				jenkinsService.deleteJob(jenkinsJobVO);
			}
		}

		autoTestTaskMapper.deleteByIds(ids);
		return ids;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.AutoTestTaskService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public AutoTestTask selectByPrimaryKey(Long id) {
		logger.info("根据主键查询");
		ValidationUtil.validateNull(id, null);
		return autoTestTaskMapper.selectByPrimaryKey(id);
	}

	/**
	 * @see
	 *      com.tianque.yunxiao.connect.server.service.AutoTestTaskService#selectByObject(com.tianque.yunxiao.connect.server.model
	 *      .AutoTestTask)
	 */
	@Override
	public AutoTestTask selectByObject(AutoTestTask autoTestTask) {
		return selectByPrimaryKey(autoTestTask.getId());
	}

	/**
	 * @see :检查
	 * @param :
	 * @return : boolean
	 * @return
	 */
	@Override
	public boolean checkTaskNameExists(String taskName) {
		List<AutoTestTask> currentList = searchAutoTestTasksBySearchContent(
		        taskName);

		return !currentList.isEmpty();
	}

	/**
	 * @see :检查
	 * @param :
	 * @return : boolean
	 * @return
	 */
	private boolean checkTaskNameExists(AutoTestTask autoTestTask) {
		List<AutoTestTask> currentList = autoTestTaskMapper
		        .findOtherAutoTestTasksAccordingToTaskName(autoTestTask);
		return !currentList.isEmpty();
	}

	/**
	 * 执行任务
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#executeTask(com.tianque.yunxiao.connect.server.model.AutoTestTask)
	 */
	@Override
	public AutoTestTask executeTask(AutoTestTask autoTestTask) {
		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);

		if (this.jenkinsService.isJobRunning(jenkinsJobVO.getJobName())) {
			throw new BusinessValidationException("任务正在执行，不可重复执行");
		}

		this.jenkinsService.runJenkinsJob(jenkinsJobVO);
		return autoTestTask;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#getExecutionProcess(com.tianque.yunxiao.connect.server.model.AutoTestTask)
	 */
	@Override
	public AutoTestTask getExecutionProcess(AutoTestTask autoTestTask) {

		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);

		logger.debug("jenkinsVo:{}", jenkinsJobVO);
		autoTestTask.setExecuteDetailOutPut(
		        jenkinsService.getExecutionProcess(jenkinsJobVO));
		return autoTestTask;
	}

	/**
	 * @author Administrator
	 * @since 2019年5月15日 17:05:10
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#downloadExecutionReport(com.tianque.yunxiao.connect.server.model.AutoTestTask)
	 */
	@Override
	public AutoTestTask downloadExecutionReport(AutoTestTask autoTestTask) {
		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);
		logger.debug("jenkinsVo:{}", jenkinsJobVO);
		autoTestTask.setExecuteDetailOutPut(
		        jenkinsService.getExecutionProcess(jenkinsJobVO));
		return autoTestTask;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#downloadExecutionReport(com.tianque.yunxiao.connect.server.model.AutoTestTask,
	 *      org.apache.http.HttpResponse)
	 */
	@Override
	public void downloadExecutionReport(AutoTestTask autoTestTask,
	        HttpServletResponse httpServletResponse) {
		// 读取任务执行的状态

		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);

		boolean taskIsRunning = jenkinsService
		        .isJobRunning(jenkinsJobVO.getJobName());

		if (taskIsRunning) {
			throw new BusinessValidationException("任务正在执行，请稍后再下载");
		}

		InputStream inputStream = jenkinsService
		        .getArtifactDetail(jenkinsJobVO.getJobName());

		if (null == inputStream) {
			throw new BusinessValidationException("获取构建产物流信息失败");
		}

		httpServletResponse.reset();

		httpServletResponse.setHeader("content-type",
		        "application/octet-stream");
		httpServletResponse.setContentType("application/octet-stream");
		httpServletResponse.addHeader("Pargam", "no-cache");
		httpServletResponse.addHeader("Cache-Control", "no-cache");
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.addHeader("Content-Disposition",
		        "attachment;fileName=" + System.currentTimeMillis() + ".html");

		try (OutputStream outputStream = httpServletResponse
		        .getOutputStream();) {

			int written = 0;
			int len = -1;

			byte[] buffer = new byte[1024];

			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
				written += len;
			}

			logger.debug("读取数据个数:{}", written);
			httpServletResponse.addHeader("Content-Length",
			        String.valueOf(written));
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			logger.info("下载完成");
		} catch (IOException e) {
			logger.error("下载测试报告失败");
			throw new BusinessValidationException("下载失败");
		}
	}

	/**
	 * @see 检查任务是否正在执行
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#isTaskRunning(java.lang.String)
	 */
	@Override
	public boolean isTaskRunning(String taskName) {
		return this.jenkinsService.isJobRunning(taskName);
	}

	/**
	 * 取消任务
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.TaskService#cancelTask(com.tianque.yunxiao.connect.server.model.AutoTestTask)
	 */
	@Override
	public AutoTestTask cancelTask(AutoTestTask autoTestTask) {
		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);
		logger.info("开始取消任务:{}", autoTestTask);

		if (this.jenkinsService.cancelJob(jenkinsJobVO.getJobName())) {
			logger.debug("取消任务成功");
			return autoTestTask;
		} else {
			logger.error("取消任务失败");
			throw new BusinessValidationException("取消失败");
		}
	}
}
