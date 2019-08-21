/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:48:56
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.ExecutorAgent;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:48:56
 * @see :
 */
public interface ExecutorAgentService {
	/**
	 * 查询所有列表
	 * 
	 * @see : 获取列表
	 * @param :
	 * @return : List<ExecutorAgent>
	 * @return
	 */
	List<ExecutorAgent> getList();

	/**
	 * 根据搜索内容查询列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<ExecutorAgent>
	 * @param searchContent
	 * @return
	 */
	List<ExecutorAgent> searchExecutorAgentsBySearchContent(
	        String searchContent);
}
