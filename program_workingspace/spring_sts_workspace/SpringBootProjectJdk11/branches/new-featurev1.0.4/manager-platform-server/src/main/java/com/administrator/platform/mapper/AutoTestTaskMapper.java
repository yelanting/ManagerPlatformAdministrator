/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:43:30
 * @see:
 */
package com.administrator.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.administrator.platform.model.AutoTestTask;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:43:30
 * @see :
 */
@Mapper
public interface AutoTestTaskMapper {
	/**
	 * 查询所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<AutoTestTask>
	 * @return
	 */
	List<AutoTestTask> findAll();

	/**
	 * 插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(AutoTestTask record);

	/**
	 * 更新
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKey(AutoTestTask record);

	/**
	 * 单个删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */

	int deleteByPrimaryKey(Long id);

	/**
	 * 批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */

	int deleteByIds(Long[] ids);

	/**
	 * 主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param id
	 * @return
	 */
	AutoTestTask selectByPrimaryKey(Long id);

	/**
	 * 对象查询
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param record
	 * @return
	 */
	AutoTestTask selectByObject(AutoTestTask record);

	/**
	 * 模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<AutoTestTask>
	 * @param searchConent
	 * @return
	 */

	List<AutoTestTask> findAutoTestTasksByTaskNameLike(String searchConent);

	/**
	 * 名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : AutoTestTask
	 * @param taskName
	 * @return
	 */

	AutoTestTask findAutoTestTaskByTaskName(String taskName);

	/**
	 * 查询与当前不同的记录
	 * 
	 * @see :
	 * @param :
	 * @return : List<AutoTestTask>
	 * @param autoTestTask
	 * @return
	 */

	List<AutoTestTask> findOtherAutoTestTasksAccordingToTaskName(
	        AutoTestTask autoTestTask);
}
