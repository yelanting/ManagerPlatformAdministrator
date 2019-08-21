/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月2日 下午2:54:03
 * @see:
 */
package com.administrator.platform.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.JenkinsDefine;
import com.administrator.platform.constdefine.YunXiaoDefine;
import com.administrator.platform.model.GlobalParam;
import com.administrator.platform.service.GlobalParamService;

@Service
public class GlobalParamDataInit {

	@Autowired
	private GlobalParamService globalParamService;

	@PostConstruct
	public void initJenkinsData() {

		checkAndInitGlobalParam(
		        JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_URL,
		        new GlobalParam(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_URL,
		                "http://192.168.110.11:8080/jenkins/",
		                "jenkins对接地址，不可更改"));

		checkAndInitGlobalParam(
		        JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_USERNAME,
		        new GlobalParam(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_USERNAME,
		                "sunliuping", "jenkins登陆用户，不可更改"));
		checkAndInitGlobalParam(
		        JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_PASSWORD,
		        new GlobalParam(
		                JenkinsDefine.JENKINS_KEY_OF_GLOBAL_PARAM_JENKINS_LOGIN_PASSWORD,
		                "Admin@1234", "jenkins登陆密码，不可更改"));
		checkAndInitGlobalParam(JenkinsDefine.JENKINS_KEY_OF_SVN_AUTH,
		        new GlobalParam(JenkinsDefine.JENKINS_KEY_OF_SVN_AUTH,
		                "b18caf67-e8ad-495d-bb3a-37da88d04d34",
		                "jenkins中配置的svnid，可从中选择其中一个，但是key值不能改"));
		checkAndInitGlobalParam(YunXiaoDefine.KEY_OF_AUTOTEST_RESULT_CALLBACK,
		        new GlobalParam(YunXiaoDefine.KEY_OF_AUTOTEST_RESULT_CALLBACK,
		                "http://integration.aliyun.com/testTrigger/setTriggerResult",
		                "云效回调的url"));

	}

	/**
	 * 判断一下，如果当前key不存在，就初始化一下
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param paramKey
	 * @param globalParam
	 */
	private void checkAndInitGlobalParam(String paramKey,
	        GlobalParam globalParam) {
		if (null == globalParamService.findGlobalParamByParamKey(paramKey)) {
			globalParamService.addGlobalParam(globalParam);
		}
	}
}
