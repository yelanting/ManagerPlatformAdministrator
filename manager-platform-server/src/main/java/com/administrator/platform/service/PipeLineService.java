/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.TimerTask;
import com.administrator.platform.model.yunxiao.PipeLine;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface PipeLineService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<PipeLine>
	 * @return
	 */
	List<PipeLine> findAllPipeLineList();

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : PipeLine
	 * @param id
	 * @return
	 */
	PipeLine getPipeLineById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : PipeLine
	 * @param pipeLine
	 * @return
	 */
	PipeLine getPipeLineByObject(PipeLine pipeLine);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<PipeLine>
	 * @param searchContent
	 * @return
	 */
	List<PipeLine> findPipeLinesByName(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<PipeLine>
	 * @param searchContent
	 * @return
	 */
	List<PipeLine> findPipeLinesByNameLike(String searchContent);

	/**
	 * 定时采集，并备份执行数据
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param pipeLine
	 */
	void timerTaskBuildPipeLine(PipeLine pipeLine);

	/**
	 * 配置定时采集任务
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param cronConfig
	 * @param pipeLineId
	 * @return
	 */
	TimerTask configTimerTaskAndChangeStatus(String cronConfig, Long pipeLineId,
	        boolean enabled);
}
