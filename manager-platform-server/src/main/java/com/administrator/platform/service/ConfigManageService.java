/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.yunxiao.ConfigManage;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface ConfigManageService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<ConfigManage>
	 * @return
	 */
	List<ConfigManage> findAllConfigManageList();

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : ConfigManage
	 * @param id
	 * @return
	 */
	ConfigManage getConfigManageById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : ConfigManage
	 * @param configManage
	 * @return
	 */
	ConfigManage getConfigManageByObject(ConfigManage configManage);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ConfigManage>
	 * @param searchContent
	 * @return
	 */
	List<ConfigManage> findConfigManagesByName(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ConfigManage>
	 * @param searchContent
	 * @return
	 */
	List<ConfigManage> findConfigManagesByNameLike(String searchContent);

	/**
	 * 定时采集，并备份执行数据
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param configManage
	 */
	void timerTaskBuildConfigManage(ConfigManage configManage);

	/**
	 * 配置定时采集任务
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param cronConfig
	 * @param configManageId
	 * @return
	 */
	TimerTask configTimerTaskAndChangeStatus(String cronConfig,
	        Long configManageId, boolean enabled);
}
