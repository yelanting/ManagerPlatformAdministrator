/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:48:56
 * @see:
 */
package com.administrator.platform.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.administrator.platform.vo.JenkinsJobVO;
import com.offbytwo.jenkins.model.Computer;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:48:56
 * @see :
 */
public interface JenkinsService {
	/**
	 * 获取注册上来的节点信息
	 * 
	 * @see :
	 * @param :
	 * @return : List<Computer>
	 * @return
	 */
	List<Computer> getComputerList();

	/**
	 * 执行jenkinsjob
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jenkinsJobVO
	 */
	void runJenkinsJob(JenkinsJobVO jenkinsJobVO);

	/**
	 * 获取任务的执行日志
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param jenkinsJobVO
	 * @return
	 */
	String getExecutionProcess(JenkinsJobVO jenkinsJobVO);

	/**
	 * 判断任务是否正在执行
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param jobName
	 * @return
	 */
	boolean isJobRunning(String jobName);

	/**
	 * 获取构建产物的输入流
	 * 
	 * @see :
	 * @param :
	 * @return : InputStream
	 * @param jobName
	 * @return
	 */
	InputStream getArtifactDetail(String jobName);

	/**
	 * 当有多个构建产物的时候，获取多个输入流，方便写入zip文件
	 * 
	 * @see :
	 * @param :
	 * @return : Map<String,InputStream>
	 * @param jobName
	 * @return
	 */
	Map<String, InputStream> getArtifactsDetail(String jobName);

	/**
	 * 取消任务执行
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 */
	boolean cancelJob(String jobName);

	/**
	 * 删除任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jenkinsJobVO
	 */
	void deleteJob(JenkinsJobVO jenkinsJobVO);
}
