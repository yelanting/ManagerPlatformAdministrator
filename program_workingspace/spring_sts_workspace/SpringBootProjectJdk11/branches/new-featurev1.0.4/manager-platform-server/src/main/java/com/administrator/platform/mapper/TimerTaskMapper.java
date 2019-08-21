package com.administrator.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.TimerTask;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface TimerTaskMapper {
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
	 * 插入记录
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(TimerTask record);

	/**
	 * 主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param id
	 * @return
	 */
	TimerTask selectByPrimaryKey(Long id);

	/**
	 * 全字段修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(TimerTask record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param goodName
	 * @return
	 */
	List<TimerTask> findTimerTasksByTimerTaskName(String goodName);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param goodName
	 * @return
	 */
	List<TimerTask> findTimerTasksByTimerTaskNameLike(String goodName);

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
	 * 根据ID查询
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param id
	 * @return
	 */

	TimerTask getById(Long id);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<TimerTask>
	 */
	Page<TimerTask> findAll(Pageable pageable);

	/**
	 * 根据物品种类批量删除
	 * 
	 * @see :
	 * @param id
	 *            :物品种类id
	 * @return : void
	 */
	List<TimerTask> findAll();

	/**
	 * 查询非当前id的同名定时任务
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTask>
	 * @param name
	 * @param id
	 * @return
	 */
	List<TimerTask> findTimerTaskWithNameExceptThisId(
	        @Param("taskName") String taskName, @Param("id") Long id);
}
