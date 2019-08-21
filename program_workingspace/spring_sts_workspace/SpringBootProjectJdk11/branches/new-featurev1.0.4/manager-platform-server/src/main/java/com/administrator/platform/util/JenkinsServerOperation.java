/*** @author:孙留平*@since:2019 年5月10日 下午2:46:58*@see: */
package com.administrator.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.DecompressingEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.YunXiaoBusinessValidationException;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.AutoTestTask;
import com.administrator.platform.tools.jenkins.JenkinsUser;
import com.administrator.platform.vo.JenkinsJobVO;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Artifact;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Computer;
import com.offbytwo.jenkins.model.JobWithDetails;

import net.sf.json.JSONObject;

/***
 * @author : Administrator
 * @since : 2019年5月10日 下午2:46:58
 * @see :
 */
public class JenkinsServerOperation {
	private static final Logger logger = LoggerFactory
	        .getLogger(JenkinsServerOperation.class);
	private JenkinsServer jenkinsServer;
	private JenkinsHttpClient jenkinsHttpClient;
	private String url;
	private String username;
	private String passsword;

	public JenkinsServerOperation(String url, String username,
	        String passsword) {
		super();
		this.url = url;
		this.username = username;
		this.passsword = passsword;
		try {
			this.jenkinsHttpClient = new JenkinsHttpClient(new URI(url),
			        username, passsword);
		} catch (URISyntaxException e) {
			logger.error("初始化jenkins 客户端连接失败:{}", e.getMessage());
			this.jenkinsHttpClient = null;
		}

	}

	public JenkinsServerOperation(JenkinsServer jenkinsServer) {
		super();
		this.jenkinsServer = jenkinsServer;
	}

	public JenkinsServerOperation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see : 初始化一个操作类
	 * @param :
	 * @return : JenkinsServerOperation
	 * @param jenkinsUrl
	 * @param loginUsername
	 * @param password
	 * @return
	 */
	public static JenkinsServerOperation fromUrlAndLoginInfo(String jenkinsUrl,
	        String loginUsername, String password) {

		JenkinsServerOperation jenkinsServerOperation = new JenkinsServerOperation();
		try {
			JenkinsServer jenkinsServer = new JenkinsServer(new URI(jenkinsUrl),
			        loginUsername, password);

			if (jenkinsServer.isRunning()) {
				jenkinsServerOperation.setJenkinsServer(jenkinsServer);
				return jenkinsServerOperation;
			}

			/**
			 * @see 当前jenkins尚未运行
			 */
			throw new YunXiaoBusinessValidationException(
			        "当前jenkins并未运行,请查看jenkins相关配置");

		} catch (URISyntaxException e) {
			logger.error("jenkins操作失败，失败原因:{}", e.getMessage());
			throw new YunXiaoBusinessValidationException("jenkins的地址不正确");
		}
	}

	/**
	 * @see : 获取所有执行机信息
	 * @param :
	 * @return : Map<String,Computer>
	 * @return
	 */

	public Map<String, Computer> getCurrentComputerList() {
		try {
			return this.jenkinsServer.getComputers();
		} catch (HttpResponseException e) {
			logger.error("jenkins信息获取不正确：{}", e.getMessage());
			throw new YunXiaoBusinessValidationException(
			        "查看全局参数jenkins的地址以及用户名密码是否正确");
		} catch (IOException e) {
			logger.error("获取jenkins中的执行机信息失败");
			return new HashMap<>(16);
		}
	}

	/**
	 * 获取任务详情
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 */
	public JobWithDetails getJobDetail(String jobName) {
		logger.info("获取任务:{}的详情", jobName);

		try {
			return this.jenkinsServer.getJob(jobName);
		} catch (IOException e) {
			logger.error("获取任务:{}的详情失败了，失败原因:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("获取任务详情失败");
		}
	}

	/**
	 * 检查job是否存在，不存在，则创建，存在则更新
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param autoTestTask
	 */
	public void checkAndCreateOrUpdateJob(AutoTestTask autoTestTask) {
		logger.info("开始根据自动化任务信息:{}检查job是不是存在，并做相关操作", autoTestTask);
		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);
		checkAndCreateOrUpdateJob(jenkinsJobVO);

	}

	/**
	 * 检查job是否存在，不存在，则创建，存在则更新
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jenkinsJobVo
	 */
	public void checkAndCreateOrUpdateJob(JenkinsJobVO jenkinsJobVo) {
		logger.info("开始根据自动化任务信息:{}检查job是不是存在，并做相关操作", jenkinsJobVo);
		String jobName = jenkinsJobVo.getJobName();

		ValidationUtil.validateStringNullOrEmpty(jobName, "自动化任务的任务名称不能为空");

		// 检查任务是不是存在

		JobWithDetails jobWithDetails = getJobDetail(jobName);
		// 如果不存在，则创建且运行
		if (null == jobWithDetails) {
			createJob(jenkinsJobVo);
		} else {
			// 如果已经存在
			// 判断是否正在执行
			if (isJobRunning(jobName)) {
				throw new YunXiaoBusinessValidationException("当前任务正在执行，不可操作");
			}

			/**
			 * 2019年5月30日 13:41:22
			 * 
			 * @see: modified 修改，不用更新，如果有任务就直接调起
			 */
			// String newJobXml = JenkinsDataConverter
			// .generateJobXML(jenkinsJobVo);
			// updateJob(jobName, newJobXml);
		}

		// 创建或者更新完毕之后，执行

		jobWithDetails = getJobDetail(jobName);

		if (null == jobWithDetails) {
			throw new YunXiaoBusinessValidationException(
			        "创建或者更新任务之后，依然没有查询到任务");
		}

		runJob(jobName);

		/**
		 * 执行之后检查，是否正在运行; 直到运行成功，或者超时
		 */
		int waitTimes = 60;

		boolean jobRunning = false;

		while ((waitTimes > 0) && (!(jobRunning = isJobRunning(jobName)))) {
			try {
				waitTimes -= 1;
				Thread.sleep(1 * 1000L);
			} catch (InterruptedException e) {
				logger.error("轮询job执行状态的时候，暂停1秒失败了:{}", e.getMessage());
			}

		}

		if (waitTimes <= 0 || !jobRunning) {
			throw new YunXiaoBusinessValidationException("任务启动失败");
		}

		logger.info("任务启动成功");
	}

	/**
	 * 创建任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 * @param autoTestTask
	 */
	public void createJob(AutoTestTask autoTestTask) {
		JenkinsJobVO jenkinsJobVO = JenkinsJobVO.fromAutoTestTask(autoTestTask);
		createJob(jenkinsJobVO);
	}

	/**
	 * 创建任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 * @param jenkinsJobVo
	 */
	public void createJob(JenkinsJobVO jenkinsJobVo) {
		String jobXml = JenkinsDataConverter.generateJobXML(jenkinsJobVo);
		logger.info("根据自动化任务:{},创建job,jobXml为:\n{}", jenkinsJobVo, jobXml);
		try {
			this.jenkinsServer.createJob(jenkinsJobVo.getJobName(), jobXml);
			logger.debug("创建job成功");
		} catch (IOException e) {
			logger.error("根据任务:{}创建jenkins任务失败,失败原因:{}", jenkinsJobVo,
			        e.getMessage());
			throw new YunXiaoBusinessValidationException("创建任务失败");
		}
	}

	/**
	 * 删除任务
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void deleteJob(String jobName) {
		logger.info("删除job:{}", jobName);
		try {
			JobWithDetails jobWithDetails = getJobDetail(jobName);

			if (null == jobWithDetails) {
				logger.debug("job:{}不存在，不需要删除", jobName);
				return;
			}

			this.jenkinsServer.deleteJob(jobName);
			logger.debug("删除job:{}成功", jobName);
		} catch (IOException e) {
			logger.error("删除任务:{}失败，原因:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("删除任务失败");
		}
	}

	/**
	 * 执行任务失败
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 */
	public void runJob(String jobName) {
		logger.info("执行名称为:{}的job", jobName);
		try {
			JobWithDetails jobWithDetails = this.jenkinsServer.getJob(jobName);
			if (null == jobWithDetails) {
				logger.error("job不存在，无法执行");
				return;
			}

			jobWithDetails.build();
			logger.debug("job执行成功");

		} catch (IOException e) {
			logger.error("任务:{}执行失败,失败原因:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("任务执行失败");
		}
	}

	/**
	 * 取消job
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param jobName
	 * @return
	 */
	public boolean cancelJob(String jobName) {
		logger.info("取消名称为:{}的job", jobName);
		try {
			JobWithDetails jobWithDetails = this.jenkinsServer.getJob(jobName);
			if (null == jobWithDetails) {
				throw new YunXiaoBusinessValidationException("任务不存在，不需要取消");
			}

			if (isJobRunning(jobName)) {
				logger.debug("正在运行，需要取消");
				jobWithDetails.getLastBuild().Stop();
				return true;
			} else {
				logger.debug("尚未运行，不需要取消");
				throw new YunXiaoBusinessValidationException("未运行的任务，不可取消");
			}

		} catch (IOException e) {
			logger.error("任务:{}取消失败,失败原因:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("任务取消失败");
		}
	}

	/**
	 * @see :
	 * @param :
	 * @return : void
	 */
	public void getLastJobBuildDetail(String jobName) {
		try {
			String url = this.jenkinsServer.getJob(jobName).getLastBuild()
			        .details().getUrl();
			logger.debug("执行任务信息:{}", url);
		} catch (IOException e) {
			logger.error("任务:{}的最近执行结果失败:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("获取最后一次执行结果失败");
		}
	}

	/**
	 * @see :
	 * @param :
	 * @return : void
	 * @param jobName
	 */

	public boolean isJobRunning(String jobName) {
		logger.info("判断job：{}是否正在执行", jobName);
		JobWithDetails jobWithDetails = getJobDetail(jobName);
		if (null != jobWithDetails) {
			try {
				boolean result = jobWithDetails.getLastBuild().details()
				        .isBuilding();
				logger.debug("job的构建结果:{}", result);
				return result;
			} catch (IOException e) {
				logger.error("判断job的执行状态失败:失败原因{}", e.getMessage());
				return false;
			}
		}

		return false;
	}

	/**
	 * 获取最后构建的日志
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param jobName
	 * @return
	 */
	public String getJobLastBuildLogOut(String jobName) {
		logger.info("获取任务:{}的最后一次构建日志", jobName);
		JobWithDetails jobWithDetails = getJobDetail(jobName);

		if (null == jobWithDetails) {
			logger.error("没有找到任务为：{}的任务", jobName);
			return null;
		}

		try {
			String outPut = jobWithDetails.getLastBuild().details()
			        .getConsoleOutputText();
			logger.debug("最后一次构建的任务日志输出为:{}", outPut);

			return outPut;
		} catch (IOException e) {
			logger.error("获取job:{}构建日志失败,原因为:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("获取job的最近最近一次构建输出失败");
		}
	}

	/**
	 * @see : 更新job信息
	 * @param :
	 * @return : void
	 * @param jobName
	 *            : 原有的job名称
	 * @param newJobXml
	 *            :新的job信息
	 */
	public void updateJob(String jobName, String newJobXml) {
		logger.info("更新job的信息:{}", jobName);
		try {
			this.jenkinsServer.updateJob(jobName, newJobXml);
			logger.info("更新成功");
		} catch (IOException e) {
			logger.error("更新job失败:{},失败原因:{}", jobName, e.getMessage());
			throw new YunXiaoBusinessValidationException("更新job信息失败");
		}
	}

	/**
	 * 获取构建产物
	 * 
	 * @see :
	 * @param :
	 * @return : List<Artifact>
	 * @param jobName
	 * @return
	 */
	public List<Artifact> getArtifacts(String jobName) {
		logger.info("获取任务:{}的构建产物", jobName);
		Build build = getLastBuild(jobName);
		if (null == build) {
			throw new YunXiaoBusinessValidationException("此任务没有输出产物或者任务不存在");
		}

		try {
			return build.details().getArtifacts();
		} catch (IOException e) {
			logger.error("获取构建产物失败,错误信息：{}", e.getMessage());

			throw new YunXiaoBusinessValidationException("获取构建产物失败");
		}
	}

	/**
	 * 下载构建产物
	 * 
	 * @see :
	 * @param :
	 * @return : InputStream
	 * @param jobName
	 * @param artifact
	 * @return
	 */
	public InputStream downloadArtifact(String jobName, Artifact artifact) {
		Build lastBuild = getLastBuild(jobName);
		if (null == lastBuild) {
			throw new YunXiaoBusinessValidationException("没有最后一次构建，或者没有构建产物");
		}

		try {
			return lastBuild.details().downloadArtifact(artifact);
		} catch (IOException | URISyntaxException e) {
			logger.error("下载构建产物的流信息失败,失败原因:{}", e.getMessage());
			throw new YunXiaoBusinessValidationException("获取构建产物的流信息失败");
		}
	}

	/**
	 * 获取最后一次构建详情
	 * 
	 * @see :
	 * @param :
	 * @return : Build
	 * @param jobName
	 * @return
	 */
	private Build getLastBuild(String jobName) {

		JobWithDetails jobWithDetails = getJobDetail(jobName);

		if (null == jobWithDetails) {
			logger.error("获取任务详情失败");
			return null;
		}
		return jobWithDetails.getLastBuild();
	}

	/**
	 * @return the jenkinsServer
	 */
	public JenkinsServer getJenkinsServer() {
		return jenkinsServer;
	}

	/**
	 * @param jenkinsServer
	 *            the jenkinsServer to set
	 */
	public void setJenkinsServer(JenkinsServer jenkinsServer) {
		this.jenkinsServer = jenkinsServer;
	}

	/**
	 * 获取客户端连接
	 * 
	 * @see :
	 * @param :
	 * @return : JenkinsHttpClient
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static JenkinsHttpClient getJenkinsHttpClientFromUrlAndLoginInfo(
	        String url, String username, String password) {
		try {
			return new JenkinsHttpClient(new URI(url), username, password);
		} catch (URISyntaxException e) {
			throw new BusinessValidationException("初始化jenkins连接失败");
		}
	}

	/**
	 * 创建用户
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param jenkinsUser
	 */
	public boolean createUser(JenkinsUser jenkinsUser) {
		List<NameValuePair> postContent = makeCreateUserPostData(jenkinsUser);
		try {
			HttpResponse httpResponse = this.jenkinsHttpClient
			        .post_form_with_result(
			                "/securityRealm/createAccountByAdmin", postContent,
			                false);

			HttpEntity httpEntity = httpResponse.getEntity();

			if (httpEntity instanceof DecompressingEntity) {
				throw new BusinessValidationException(
				        "用户" + jenkinsUser.getUsername() + "已经存在");
			}
			logger.debug("返回响应:{}", httpResponse.getEntity().toString());
			return true;
		} catch (IOException e) {
			logger.error("创建用户失败");
			return false;
		}
	}

	/**
	 * 创建报文
	 * 
	 * @see :
	 * @param :
	 * @return : List<NameValuePair>
	 * @param jenkinsUser
	 * @return
	 */
	private List<NameValuePair> makeCreateUserPostData(
	        JenkinsUser jenkinsUser) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair = null;

		String charsetString = "gbk";
		try {
			nameValuePair = new BasicNameValuePair("fullname", URLEncoder
			        .encode(jenkinsUser.getFullname(), charsetString));
			// nameValuePair = new BasicNameValuePair("fullname",
			// jenkinsUser.getFullname());
		} catch (UnsupportedEncodingException e) {
			nameValuePair = new BasicNameValuePair("fullname",
			        jenkinsUser.getFullname());
		}
		nameValuePairs.add(nameValuePair);

		nameValuePair = new BasicNameValuePair("email", jenkinsUser.getEmail());
		nameValuePairs.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("username",
		        jenkinsUser.getUsername());
		nameValuePairs.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("password2",
		        jenkinsUser.getPassword2());
		nameValuePairs.add(nameValuePair);
		nameValuePair = new BasicNameValuePair("password1",
		        jenkinsUser.getPassword1());
		nameValuePairs.add(nameValuePair);

		try {
			nameValuePair = new BasicNameValuePair("json",
			        URLEncoder.encode(
			                JSONObject.fromObject(jenkinsUser).toString(),
			                charsetString));
		} catch (UnsupportedEncodingException e) {
			nameValuePair = new BasicNameValuePair("json",

			        JSONObject.fromObject(jenkinsUser).toString());
		}
		nameValuePairs.add(nameValuePair);
		try {
			nameValuePair = new BasicNameValuePair("Submit",
			        URLEncoder.encode("新建用户", charsetString));
		} catch (UnsupportedEncodingException e) {
			nameValuePair = new BasicNameValuePair("Submit", "新建用户");
		}

		nameValuePairs.add(nameValuePair);
		return nameValuePairs;
	}

	public static void main(String[] args) {

		JenkinsUser jenkinsUser = new JenkinsUser();
		jenkinsUser.setUsername("autotest2");
		jenkinsUser.setEmail("autotest@hztianque.com");
		jenkinsUser.setPassword1("Admin@1234");
		jenkinsUser.setPassword2("Admin@1234");
		jenkinsUser.setFullname("自动化测试2");

		String url = "http://192.168.110.11:8080/jenkins/";
		String username = "sunliuping";
		String password = "Admin@1234";

		JenkinsServerOperation jenkinsServerOperation = new JenkinsServerOperation(
		        url, username, password);
		System.out.println(jenkinsServerOperation.createUser(jenkinsUser));
	}
}
