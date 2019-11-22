/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.TimerTask;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface TimerTaskService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @return
	 */
	List<TimerTask> findAllTimerTaskList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param record
	 * @return
	 */
	TimerTask addTimerTask(TimerTask record);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param record
	 * @return
	 */
	TimerTask updateTimerTask(TimerTask record);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param id
	 * @return
	 */
	TimerTask getTimerTaskById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param record
	 * @return
	 */
	TimerTask getTimerTaskByObject(TimerTask record);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteTimerTask(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteTimerTask(Long[] ids);

	/**
	 * 根据ids批量开启
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param ids
	 * @return
	 */
	List<TimerTask> openTimerTaskInBatch(Long[] ids);

	/**
	 * 批量开启所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param ids
	 * @return
	 */
	List<TimerTask> openAllTimerTaskInBatch();

	/**
	 * 根据ids批量关闭
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param ids
	 * @return
	 */
	List<TimerTask> closeTimerTaskInBatch(Long[] ids);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	List<TimerTask> closeAllTimerTaskInBatch();

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param searchContent
	 * @return
	 */
	List<TimerTask> findTimerTasksByTimerTaskName(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param searchContent
	 * @return
	 */
	List<TimerTask> findTimerTasksByTimerTaskNameLike(String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<TimerTask>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<TimerTask> findTimerTaskByPage(Integer page, Integer size);

	/**
	 * 变更任务状态
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @return
	 */
	TimerTask changeTimerTaskStatus(Long id, String operation);
}