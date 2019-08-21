/**
 * @author : 孙留平
 * @since : 2019年5月14日 下午2:15:53
 * @see:
 */
package com.administrator.platform.util;

import java.io.File;

import com.administrator.platform.constdefine.JenkinsDefine;
import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.vo.JenkinsJobVO;

/**
 * @author : Administrator
 * @since : 2019年5月14日 下午2:15:53
 * @see :
 */
public class JenkinsDataConverter {
	private JenkinsDataConverter() {

	}

	/**
	 * 把jenkins的相关job对象，转换为job的对应xml
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param jenkinsJobVO
	 * @return
	 */
	public static String generateJobXML(JenkinsJobVO jenkinsJobVO) {

		File defaultJobXml = ClasspathFilesUtil
		        .getDefaultJenkinsConfigXmlFile();

		if (null == defaultJobXml) {
			return null;
		}

		/**
		 * 读取默认内容
		 */
		String content = FileUtil.readFile(defaultJobXml.getAbsolutePath());

		// 如果为空，则默认返回模版
		if (null == jenkinsJobVO) {
			return content;
		}

		// 替换描述
		String jobDesc = jenkinsJobVO.getJobDesc();

		if (StringUtil.isEmpty(jobDesc)) {
			jobDesc = "自动创建的自动化测试任务";
		}
		content = content.replaceAll(
		        "<description>defaultDescription</description>",
		        "<description>" + jobDesc + "</description>");

		// 替换收件人
		if (null != jenkinsJobVO.getMailToGroup()
		        && jenkinsJobVO.getMailToGroup().length != 0) {
			content = content.replaceAll(
			        "<recipientList>$DEFAULT_RECIPIENTS</recipientList>",
			        "<recipientList>"
			                + generateMailToGroup(jenkinsJobVO.getMailToGroup())
			                + "</recipientList>");

		}

		// 替换执行命令
		if (StringUtil.isStringAvaliable(jenkinsJobVO.getCommand())) {
			content = content.replaceAll("<command>defaultCommand</command>",
			        "<command>" + jenkinsJobVO.getCommand() + "</command>");
		}

		// 替换发件人
		if (StringUtil.isStringAvaliable(jenkinsJobVO.getMailFromTitle())) {
			content = content.replaceAll("<from>defaultFrom</from>",
			        "<from>" + jenkinsJobVO.getMailFromTitle() + "</from>");
		} else {
			content = content.replaceAll("<from>defaultFrom</from>",
			        "<from>" + "DefaultAutoTestTask" + "</from>");
		}

		// 替换邮件内容
		if (StringUtil.isStringAvaliable(jenkinsJobVO.getMailContent())) {
			content = content.replaceAll(
			        "<defaultContent>$DEFAULT_CONTENT</defaultContent>",
			        "<defaultContent>" + jenkinsJobVO.getMailContent()
			                + "</defaultContent>");

		}

		// 替换认证id
		if (StringUtil.isStringAvaliable(jenkinsJobVO.getSvnAuthId())) {
			content = content.replaceAll(
			        "<credentialsId>defaultCredentialsId</credentialsId>",
			        "<credentialsId>" + jenkinsJobVO.getSvnAuthId()
			                + "</credentialsId>");
		} else {
			content = content.replaceAll(
			        "<credentialsId>defaultCredentialsId</credentialsId>",
			        "<credentialsId>" + JenkinsDefine.DEFAULT_SVN_AUTH_ID
			                + "</credentialsId>");
		}

		return content;
	}

	/**
	 * 创建收件人
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param mailToGroup
	 * @return
	 */
	private static String generateMailToGroup(String[] mailToGroup) {
		if (null == mailToGroup || mailToGroup.length == 0) {
			return null;
		}

		StringBuilder mailToGroupTransfered = new StringBuilder(
		        "$DEFAULT_RECIPIENTS");

		for (String eachMailTo : mailToGroup) {
			mailToGroupTransfered.append(", " + eachMailTo);
		}

		return mailToGroupTransfered.toString();
	}
}
