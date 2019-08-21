/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.TimerTaskMonitor;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface TimerTaskMonitorService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskMonitor>
	 * @return
	 */
	List<TimerTaskMonitor> findAllTimerTaskMonitorList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskMonitor
	 * @param record
	 * @return
	 */
	TimerTaskMonitor addTimerTaskMonitor(TimerTaskMonitor record);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskMonitor
	 * @param record
	 * @return
	 */
	TimerTaskMonitor updateTimerTaskMonitor(TimerTaskMonitor record);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskMonitor
	 * @param id
	 * @return
	 */
	TimerTaskMonitor getTimerTaskMonitorById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskMonitor
	 * @param record
	 * @return
	 */
	TimerTaskMonitor getTimerTaskMonitorByObject(TimerTaskMonitor record);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteTimerTaskMonitor(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteTimerTaskMonitor(Long[] ids);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskMonitor>
	 * @param searchContent
	 * @return
	 */
	List<TimerTaskMonitor> findTimerTaskMonitorsByTimerTaskName(
	        String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<TimerTaskMonitor>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<TimerTaskMonitor> findTimerTaskMonitorByPage(Integer page,
	        Integer size);

	/**
	 * 一键清除所有的定时任务监控
	 * 
	 * @see :
	 * @param :
	 * @return : Long[]
	 * @return
	 */
	long[] deleteAllTimerTaskMonitors();
}