/**
 * @author : 孙留平
 * @since : 2019年5月14日 下午2:14:55
 * @see:
 */
package com.administrator.platform.vo;

import java.util.Arrays;

import com.administrator.platform.model.AutoTestTask;

/**
 * @author : Administrator
 * @since : 2019年5月14日 下午2:14:55
 * @see :
 */
public class JenkinsJobVO {
	/**
	 * job名称
	 */
	private String jobName;

	/**
	 * job描述
	 */
	private String jobDesc;

	/**
	 * 待执行的命令
	 */
	private String command;

	/**
	 * 收件人
	 */
	private String[] mailToGroup;

	/**
	 * 发件人
	 */
	private String mailFromTitle;

	/**
	 * 邮件内容
	 */

	private String mailContent;

	/**
	 * svn认证id
	 */
	private String svnAuthId;

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobDesc
	 */
	public String getJobDesc() {
		return jobDesc;
	}

	/**
	 * @param jobDesc
	 *            the jobDesc to set
	 */
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command
	 *            the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the mailToGroup
	 */
	public String[] getMailToGroup() {
		return mailToGroup;
	}

	/**
	 * @param mailToGroup
	 *            the mailToGroup to set
	 */
	public void setMailToGroup(String[] mailToGroup) {
		this.mailToGroup = mailToGroup;
	}

	/**
	 * @return the mailFromTitle
	 */
	public String getMailFromTitle() {
		return mailFromTitle;
	}

	/**
	 * @param mailFromTitle
	 *            the mailFromTitle to set
	 */
	public void setMailFromTitle(String mailFromTitle) {
		this.mailFromTitle = mailFromTitle;
	}

	/**
	 * @return the mailContent
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailContent
	 *            the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	/**
	 * 从自动化的任务中，转换为jenkins的job对象
	 * 
	 * @see :
	 * @param :
	 * @return : JenkinsJobVO
	 * @param autoTestTask
	 * @return
	 */
	public static JenkinsJobVO fromAutoTestTask(AutoTestTask autoTestTask) {
		JenkinsJobVO jenkinsJobVO = new JenkinsJobVO();
		jenkinsJobVO.setJobName(autoTestTask.getTaskName());

		String scriptName = autoTestTask.getToExecuteScriptName();
		jenkinsJobVO
		        .setJobDesc("为"
		                + ((null == scriptName) ? "自动化测试"
		                        : autoTestTask.getToExecuteScriptName())
		                + "自动创建的任务");
		jenkinsJobVO.setCommand("c:/python37/python.exe \"%WORKSPACE%/Web_Test/"
		        + autoTestTask.getToExecuteScriptName() + "\"");
		// jenkinsJobVO.setCommand("tasklist");
		return jenkinsJobVO;
	}

	/**
	 * @return the svnAuthId
	 */
	public String getSvnAuthId() {
		return svnAuthId;
	}

	/**
	 * @param svnAuthId
	 *            the svnAuthId to set
	 */
	public void setSvnAuthId(String svnAuthId) {
		this.svnAuthId = svnAuthId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JenkinsJobVO [jobName=" + jobName + ", jobDesc=" + jobDesc
		        + ", command=" + command + ", mailToGroup="
		        + Arrays.toString(mailToGroup) + ", mailFromTitle="
		        + mailFromTitle + ", mailContent=" + mailContent
		        + ", svnAuthId=" + svnAuthId + "]";
	}

}
