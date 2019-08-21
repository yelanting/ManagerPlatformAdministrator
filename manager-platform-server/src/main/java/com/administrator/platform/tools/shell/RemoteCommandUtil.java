/**
 * @author : 孙留平
 * @since : 2019年2月27日 下午5:20:30
 * @see:
 */
package com.administrator.platform.tools.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.definition.form.GlobalDefination;
import com.trilead.ssh2.StreamGobbler;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * @author : Administrator
 * @since : 2019年2月27日 下午5:20:30
 * @see :
 */
public class RemoteCommandUtil {

	private static final Logger logger = LoggerFactory
	        .getLogger(RemoteCommandUtil.class);

	private static final String DEFAULT_CHAR_SET = GlobalDefination.CHAR_SET_DEFAULT;

	/**
	 * 登录主机
	 * 
	 * @return
	 *         登录成功返回true，否则返回false
	 */
	public static Connection login(String ip, String userName, String userPwd) {

		boolean flg = false;
		Connection conn = null;
		try {
			conn = new Connection(ip);
			// 连接
			conn.connect();
			// 认证
			flg = conn.authenticateWithPassword(userName, userPwd);
			if (flg) {
				logger.info("=========登录成功:{}=========", conn);
				return conn;
			}
		} catch (IOException e) {
			logger.error("=========登录失败,失败信息:{}=========", e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 远程执行shll脚本或者命令
	 * 
	 * @param cmd
	 *            即将执行的命令
	 * @return
	 *         命令执行完后返回的结果值
	 */
	public static String execute(Connection conn, String cmd) {
		String result = "";
		try {
			if (conn != null) {
				// 打开一个会话
				Session session = conn.openSession();
				// 执行命令

				if (cmd.indexOf(";") == -1) {
					session.execCommand(cmd);
				} else {
					String[] commands = cmd.split(";");

					for (String command : commands) {
						session.execCommand(command);
					}
				}

				result = processStdout(session.getStdout(), DEFAULT_CHAR_SET);
				// 如果为得到标准输出为空，说明脚本执行出错了
				if (StringUtil.isEmpty(result)) {
					logger.info("得到标准输出为空,连接conn:{},执行的命令：{}", conn, cmd);
					result = processStdout(session.getStderr(),
					        DEFAULT_CHAR_SET);
				} else {
					logger.info("执行命令成功,连接conn: {},执行的命令：{}", conn, cmd);
				}
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			logger.info("执行命令失败,连接conn:{} ,执行的命令：{},错误原因：{}", conn, cmd,
			        e.getMessage());
			e.printStackTrace();
		}

		logger.debug("execute result:{}", result);
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 * 
	 * @param in
	 *            输入流对象
	 * @param charset
	 *            编码
	 * @return
	 *         以纯文本的格式返回
	 */
	private static String processStdout(InputStream in, String charset) {
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(
			        new InputStreamReader(stdout, charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("解析脚本出错：{}", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("解析脚本出错：{}", e.getMessage());
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		String ip = "192.168.110.191";
		String username = "admin";
		String password = "Admin@123";

		Connection connection = login(ip, username, password);
		execute(connection,
		        "mkdir -p /home/admin/manager_platform/jacoco_coverage; cd /home/admin/manager_platform/jacoco_coverage;ls");
	}
}
