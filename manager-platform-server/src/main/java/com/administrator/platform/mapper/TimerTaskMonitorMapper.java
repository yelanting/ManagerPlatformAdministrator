package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.TimerTaskMonitor;

/**
 * 定时任务监控的mapper
 * 
 * @author : Administrator
 * @since : 2019年8月13日 下午3:38:50
 * @see :
 */
public interface TimerTaskMonitorMapper {

	Long insert(TimerTaskMonitor jobMonitor);

	void updateByPrimaryKey(TimerTaskMonitor jobMonitor);

	List<TimerTaskMonitor> findJobTimerTaskMonitorListByName(String taskName);

	List<TimerTaskMonitor> findAllJobTimerTaskMonitorList();

	Page<TimerTaskMonitor> findJobTimerTaskMonitorByPage(Pageable pageable);

	/**
	 * 根据主键删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param id
	 * @return
	 */
	TimerTaskMonitor selectByPrimaryKey(Long id);

	/**
	 * 根据ID批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	/**
	 * 一键清除所有监控
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	void deleteAllTimerTaskMonitors();
}
