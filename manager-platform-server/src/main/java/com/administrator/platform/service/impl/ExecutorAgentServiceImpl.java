/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午7:15:13
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.model.ExecutorAgent;
import com.administrator.platform.service.ExecutorAgentService;
import com.administrator.platform.service.JenkinsService;
import com.offbytwo.jenkins.model.Computer;
import com.offbytwo.jenkins.model.ComputerWithDetails;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午7:15:13
 * @see :
 */
@Service
public class ExecutorAgentServiceImpl implements ExecutorAgentService {
	private static final Logger logger = LoggerFactory
	        .getLogger(ExecutorAgentServiceImpl.class);

	@Autowired
	private JenkinsService jenkinsService;

	/**
	 * @see 获取列表
	 */
	@Override
	public List<ExecutorAgent> getList() {
		logger.info("获取所有的执行机信息");
		List<Computer> computers = jenkinsService.getComputerList();
		List<ExecutorAgent> executorAgents = new ArrayList<>();

		for (Computer computer : computers) {
			try {
				ComputerWithDetails computerWithDetails = computer.details();
				boolean ifOnline = !computerWithDetails.getOffline();
				if (!ifOnline) {
					continue;
				}

				ExecutorAgent executorAgent = new ExecutorAgent();
				executorAgent.setOffline(computerWithDetails.getOffline());
				executorAgent.setIdle(computerWithDetails.getIdle());
				executorAgent.setExecutorName(computer.getDisplayName());
				executorAgent.setExecutorIp(computer.getDisplayName());
				logger.debug("{}", executorAgent);
				executorAgents.add(executorAgent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return executorAgents;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.ExecutorAgentService#searchExecutorAgentsBySearchContent(java.lang.String)
	 */
	@Override
	public List<ExecutorAgent> searchExecutorAgentsBySearchContent(
	        String searchContent) {

		List<ExecutorAgent> currentExecutorAgents = getList();

		List<ExecutorAgent> finalExecutorAgents = new ArrayList<>();

		for (ExecutorAgent executorAgent : currentExecutorAgents) {
			boolean nameContains = executorAgent.getExecutorName()
			        .contains(searchContent);
			boolean ipContains = executorAgent.getExecutorIp()
			        .contains(searchContent);

			if (nameContains || ipContains) {
				finalExecutorAgents.add(executorAgent);
			}
		}
		return finalExecutorAgents;
	}
}
