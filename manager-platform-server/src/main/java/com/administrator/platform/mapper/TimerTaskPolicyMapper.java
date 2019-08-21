package com.administrator.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.TimerTaskPolicy;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface TimerTaskPolicyMapper {
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
	 * 插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(TimerTaskPolicy record);

	/**
	 * 根据主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTaskPolicy
	 * @param id
	 * @return
	 */
	TimerTaskPolicy selectByPrimaryKey(Long id);

	/**
	 * 全字段修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKey(TimerTaskPolicy record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param cname
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByCname(String cname);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param cname
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByEname(String ename);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param cname
	 * @return
	 */
	List<TimerTaskPolicy> findTimerTaskPoliciesByCnameLike(String cname);

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
	 * 根据ID批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	List<TimerTaskPolicy> selectByIds(Long[] ids);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<TimerTaskPolicy>
	 */
	Page<TimerTaskPolicy> findAll(Pageable pageable);

	/**
	 * 查询所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @return
	 */
	List<TimerTaskPolicy> findAll();

	/**
	 * 查询非当前id的同名
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param cname
	 * @param id
	 * @return
	 */
	List<TimerTaskPolicy> findWithEnameExceptThisId(
	        @Param("ename") String cname, @Param("id") Long id);

	/**
	 * 查询非当前id的同名
	 * 
	 * @see :
	 * @param :
	 * @return : List<TimerTaskPolicy>
	 * @param cname
	 * @param id
	 * @return
	 */
	List<TimerTaskPolicy> findWithCnameExceptThisId(
	        @Param("cname") String ename, @Param("id") Long id);
}
