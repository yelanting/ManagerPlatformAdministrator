/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午8:05:54
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.util.ArrayList;
import java.util.List;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.model.CodeCoverage;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午8:05:54
 * @see :
 */
public class JacocoAgentTcpServer {
	private String jacocoAgentIp;
	private int jacocoAgentPort;
	private String jacocoExecFileName = JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT;

	public JacocoAgentTcpServer() {

	}

	public JacocoAgentTcpServer(String jacocoAgentIp, int jacocoAgentPort) {
		super();
		this.jacocoAgentIp = jacocoAgentIp;
		this.jacocoAgentPort = jacocoAgentPort;
	}

	public String getJacocoAgentIp() {
		return jacocoAgentIp;
	}

	public void setJacocoAgentIp(String jacocoAgentIp) {
		this.jacocoAgentIp = jacocoAgentIp;
	}

	public int getJacocoAgentPort() {
		return jacocoAgentPort;
	}

	public void setJacocoAgentPort(int jacocoAgentPort) {
		this.jacocoAgentPort = jacocoAgentPort;
	}

	public String getJacocoExecFileName() {
		return jacocoExecFileName;
	}

	public void setJacocoExecFileName(String jacocoExecFileName) {
		this.jacocoExecFileName = jacocoExecFileName;
	}

	@Override
	public String toString() {
		return "JacocoAgentTcpServer [jacocoAgentIp=" + jacocoAgentIp
		        + ", jacocoAgentPort=" + jacocoAgentPort
		        + ", jacocoExecFileName=" + jacocoExecFileName + "]";
	}

	/**
	 * 解析地址
	 * 
	 * @see :
	 * @param :
	 * @return : JacocoAgentTcpServer
	 * @param codeCoverage
	 * @return
	 */
	public static JacocoAgentTcpServer parseJacocoAgentTcpServer(
	        CodeCoverage codeCoverage) {
		JacocoAgentTcpServer jacocoAgentTcpServer = new JacocoAgentTcpServer();
		jacocoAgentTcpServer.setJacocoAgentIp(codeCoverage.getTcpServerIp());
		jacocoAgentTcpServer
		        .setJacocoAgentPort(codeCoverage.getTcpServerPort());
		return jacocoAgentTcpServer;
	}

	/**
	 * 解析列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<JacocoAgentTcpServer>
	 * @param codeCoverage
	 * @return
	 */
	public static List<JacocoAgentTcpServer> parseJacocoAgentTcpServersFromCodeCoverage(
	        CodeCoverage codeCoverage) {
		String tcpServceIps = codeCoverage.getTcpServerIp();

		/*
		 * 如果是多节点
		 */
		List<JacocoAgentTcpServer> jacocoAgentTcpServers = new ArrayList<>();

		String tcpServerIpAndPortGroupsSep = ";";
		String tcpServerIpAndPortSep = ":";
		// 如果是单节点
		if (tcpServceIps.indexOf(tcpServerIpAndPortSep) == -1
		        && tcpServceIps.indexOf(tcpServerIpAndPortGroupsSep) == -1) {

			jacocoAgentTcpServers
			        .add(new JacocoAgentTcpServer(codeCoverage.getTcpServerIp(),
			                codeCoverage.getTcpServerPort()));

			if (codeCoverage.getTcpServerPort() == -1) {
				throw new BusinessValidationException(
				        "单节点形式下tcpServerPort不能是默认值-1(代表您没有给定端口号)，请选择有效的端口号");
			}
			return jacocoAgentTcpServers;
		}

		/**
		 * 如果是多节点
		 */
		String[] tcpServerIpAndPortGroups = tcpServceIps
		        .split(tcpServerIpAndPortGroupsSep);

		for (String eachTcpServerIpAndPortGroup : tcpServerIpAndPortGroups) {
			String[] eachTcpServerIpAndPortGroupArr = eachTcpServerIpAndPortGroup
			        .split(tcpServerIpAndPortSep);

			if (eachTcpServerIpAndPortGroupArr.length != 2) {
				throw new BusinessValidationException(
				        "您填写的多节点jacoco服务地址不正确，需以ip:port形式以英文分号(;)分隔");
			}

			JacocoAgentTcpServer jacocoAgentTcpServer = new JacocoAgentTcpServer(
			        eachTcpServerIpAndPortGroupArr[0],
			        Integer.parseInt(eachTcpServerIpAndPortGroupArr[1]));
			jacocoAgentTcpServers.add(jacocoAgentTcpServer);
		}

		return jacocoAgentTcpServers;
	}
}
