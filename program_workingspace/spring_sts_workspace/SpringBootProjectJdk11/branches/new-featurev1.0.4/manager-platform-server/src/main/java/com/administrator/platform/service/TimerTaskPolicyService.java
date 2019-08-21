/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.TimerTaskPolicy;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface TimerTaskPolicyService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @return
	 */
	List<TimerTaskPolicy> findAllTimerTaskPolicyList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskPolicy
	 * @param timerTaskPolicy
	 * @return
	 */
	TimerTaskPolicy addTimerTaskPolicy(TimerTaskPolicy timerTaskPolicy);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskPolicy
	 * @param timerTaskPolicy
	 * @return
	 */
	TimerTaskPolicy updateTimerTaskPolicy(TimerTaskPolicy timerTaskPolicy);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskPolicy
	 * @param id
	 * @return
	 */
	TimerTaskPolicy getTimerTaskPolicyById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskPolicy
	 * @param timerTaskPolicy
	 * @return
	 */
	TimerTaskPolicy getTimerTaskPolicyByObject(TimerTaskPolicy timerTaskPolicy);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteTimerTaskPolicy(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteTimerTaskPolicy(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param searchContent
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByCname(String searchContent);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param searchContent
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByEname(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param searchContent
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByCnameLike(
	        String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<TimerTaskPolicy>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<TimerTaskPolicy> findTimerTaskPolicyByPage(Integer page, Integer size);

}
