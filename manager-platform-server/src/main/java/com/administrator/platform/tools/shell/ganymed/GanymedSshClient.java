/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年7月29日 下午9:49:53
 * @see:
 */
package com.administrator.platform.tools.shell.ganymed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.exception.base.BusinessValidationException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class GanymedSshClient {
	private static GanymedSshClient instance;

	private Session session;
	private ServerInfo serverInfo;
	private Connection connection;

	private SCPClient scpClient;
	private SFTPv3Client sftPv3Client;

	private static final String MODE = "0644";
	private static final int MKDIR_POSIX_PERMISSION = 0755;

	private static final long LENGTH = 1024;
	private static final String REMOTE_PATH_SEPARATOR = "/";
	private static final String NO_SUCH_FILE_ERROR_MESSAGE = "No such file (SSH_FX_NO_SUCH_FILE:";

	private final List<SFTPv3DirectoryEntry> childrenList = new ArrayList<>();

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GanymedSshClient.class);

	private GanymedSshClient() {

	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public GanymedSshClient(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public GanymedSshClient(String host, int port, String username,
	        String password) {
		this.serverInfo = new ServerInfo(host, port, username, password);
	}

	public GanymedSshClient(String host, String username, String password) {
		this.serverInfo = new ServerInfo(host, username, password);
	}

	public GanymedSshClient(String host) {
		this.serverInfo = new ServerInfo(host);
	}

	/**
	 * 初始化session
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void initConnection() {
		if (null == this.connection
		        || !this.connection.isAuthenticationComplete()) {
			this.connection = new Connection(this.serverInfo.getHost());
			LOGGER.debug("开始初始化连接");
			try {
				this.connection.connect();
				boolean authed = connection.authenticateWithPassword(
				        this.serverInfo.getUsername(),
				        this.serverInfo.getPassword());

				if (!authed) {
					throw new BusinessValidationException("初始化ssh连接失败，认证异常");
				}

				LOGGER.debug("CONNECTION 初始化完成");
			} catch (IOException e) {
				LOGGER.error("初始化连接失败，失败原因:{}", e.getMessage());
				throw new BusinessValidationException("初始化连接失败");
			}
		}
	}

	private Connection getConnection() {
		initConnection();
		return this.connection;
	}

	/**
	 * 初始化session
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 */
	private void initSession() {
		if (null == this.session) {
			LOGGER.debug("初始化session");
			try {
				this.session = getConnection().openSession();

				if (null != this.session) {
					LOGGER.debug("session初始化完成");
				}
			} catch (IOException e) {
				LOGGER.error("获取会话失败，失败原因:{}", e.getMessage());
			}

		}
	}

	private Session getCurrentSession() {
		initSession();

		return this.session;
	}

	private SCPClient getScpClient() {
		if (null == this.scpClient) {
			this.scpClient = new SCPClient(getConnection());
		}

		return this.scpClient;

	}

	private void close() {
		if (null != this.connection) {
			this.connection.close();
			this.connection = null;
			this.scpClient = null;
		}

		if (null != this.session) {
			this.session.close();
			this.session = null;
		}

		if (null != this.sftPv3Client) {
			this.sftPv3Client.close();
			this.sftPv3Client = null;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param localFile
	 * @param remoteFileName
	 * @param remoteFolder
	 */
	public void uploadFile(String localFile, String remoteFolder) {
		LOGGER.debug("上传文件:{}，到:{}", localFile, remoteFolder);
		try (SCPOutputStream os = getScpOutputStream(localFile, remoteFolder);
		        FileInputStream fis = new FileInputStream(localFile);) {
			byte[] b = new byte[4096];
			int i;
			while ((i = fis.read(b)) != -1) {
				os.write(b, 0, i);
			}
			os.flush();
		} catch (IOException e) {
			LOGGER.error("scp 上传文件失败：{}", e.getMessage());
			throw new BusinessValidationException("上传文件失败");
		} finally {
			close();
		}
	}

	/**
	 * 获取文件输出流失败
	 * 
	 * @see :
	 * @param :
	 * @return : SCPOutputStream
	 * @param localFile
	 * @param remoteFolder
	 * @return
	 */
	private SCPOutputStream getScpOutputStream(String localFile,
	        String remoteFolder) {
		try {

			File file = new File(localFile);
			return getScpClient().put(file.getName(), file.length(),
			        remoteFolder, MODE);
		} catch (IOException e) {
			LOGGER.error("获取文件输出流失败:{}", e.getMessage());
			throw new BusinessValidationException("获取文件输出流失败");
		}
	}

	/**
	 * 批量上传文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param localFiles
	 * @param remoteFolder
	 */
	public void uploadFiles(String[] localFiles, String remoteFolder) {
		for (String localFile : localFiles) {
			File thisFile = new File(localFile);

			if (thisFile.exists() && thisFile.isDirectory()) {
				File[] files = thisFile.listFiles();

				String dirName = remoteFolder + REMOTE_PATH_SEPARATOR
				        + thisFile.getName();
				mkdir(dirName);
				uploadFiles(files, dirName);

			} else if (thisFile.exists() && thisFile.isFile()) {
				uploadFile(localFile, remoteFolder);
			}
		}
	}

	/**
	 * 上传本地文件到远程服务器端，即将本地的文件localFile上传到远程Linux服务器中的remoteTargetDirectory目录下
	 * 
	 * @param localFileList
	 * @param remoteTargetDirectory
	 */
	public void uploadFiles(List<String> localFileList,
	        String remoteTargetDirectory) {
		uploadFiles(localFileList.toArray(new String[] {}),
		        remoteTargetDirectory);
	}

	/**
	 * 上传本地文件到远程服务器端，即将本地的文件localFile上传到远程Linux服务器中的remoteTargetDirectory目录下
	 * 
	 * @param localFileList
	 * @param remoteTargetDirectory
	 */
	public void uploadFiles(File[] localFileList,
	        String remoteTargetDirectory) {

		String[] filePaths = new String[localFileList.length];

		for (int i = 0; i < localFileList.length; i++) {
			filePaths[i] = localFileList[i].getAbsolutePath();
		}
		uploadFiles(filePaths, remoteTargetDirectory);
	}

	/**
	 * 上传文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param localFolder
	 * @param remoteFolder
	 */
	public void uploadFolder(String localFolder, String remoteFolder) {
		File localFileFolder = new File(localFolder);
		File[] files = localFileFolder.listFiles();
		uploadFiles(files, remoteFolder);
	}

	/**
	 * 下载单个文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFile
	 * @param destFile
	 */
	public boolean downloadFile(String remoteFile, String remoteDir,
	        String destFileFolder) {
		File destFile = new File(destFileFolder);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}

		String localFile = remoteFile;

		/**
		 * 2019年8月12日 20:38:22 modified by 孙留平
		 * 
		 * @see: 当远程文件中有$符号的时候，会被转义，因此远程的时候，需要换上传义字符，尤其是java的内部类被编译出来的class都是带有$符号的
		 * 
		 */
		if (remoteFile.contains("$")) {
			remoteFile = remoteFile.replace("$", "\\$");

		}

		try (SCPInputStream scpInputStream = getRemoteFileInputStream(
		        remoteFile, remoteDir);
		        FileOutputStream fos = new FileOutputStream(
		                new File(destFileFolder, localFile));) {
			String message = String.format("下载文件:%s/%s，到:%s", remoteDir,
			        remoteFile, destFileFolder);

			byte[] b = new byte[4096];
			int i;
			while ((i = scpInputStream.read(b)) != -1) {
				fos.write(b, 0, i);
			}
			fos.flush();
			LOGGER.debug("{}成功", message);
			return true;
		} catch (IOException e) {
			LOGGER.error("下载文件失败,原因:{}", e.getMessage());
			return false;
		}
		// finally {
		// close();
		// }
	}

	/**
	 * 获取远程流
	 * 
	 * @see :
	 * @param :
	 * @return : SCPInputStream
	 * @param remoteFile
	 * @param remoteDir
	 * @return
	 */
	private SCPInputStream getRemoteFileInputStream(String remoteFile,
	        String remoteDir) {
		SCPInputStream scpInputStream;
		try {
			scpInputStream = getScpClient()
			        .get(remoteDir + REMOTE_PATH_SEPARATOR + remoteFile);

			return scpInputStream;
		} catch (IOException e) {
			LOGGER.error("获取目录:{}下的文件:{}流失败,失败原因{}", remoteDir, remoteFile,
			        e.getMessage());
			throw new BusinessValidationException("获取输入流失败");
		}
	}

	/**
	 * 下载单个文件
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFiles
	 * @param destFile
	 */
	public boolean downloadFiles(String[] remoteFiles, String remoteFileFolder,
	        String destFileFolder) {
		File destFile = new File(destFileFolder);
		if (destFile.isFile()) {
			destFileFolder = destFile.getParent();
		}

		for (String string : remoteFiles) {
			downloadFile(string, remoteFileFolder, destFileFolder);
		}

		return true;
	}

	/**
	 * 下载目录
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileFolder
	 * @param destFileFolder
	 */
	public void downloadFolder(String remoteFileFolder, String destFileFolder) {
		// listRemoteDir(remoteFileFolder);
		List<SFTPv3DirectoryEntry> descendantsFiles = getChildren(
		        remoteFileFolder);
		File destFolderFile = new File(destFileFolder);
		if (!destFolderFile.exists()) {
			destFolderFile.mkdirs();
		}
		LOGGER.debug("开始下载:从{}到:{}", remoteFileFolder, destFileFolder);
		for (SFTPv3DirectoryEntry sftPv3DirectoryEntry : descendantsFiles) {
			// 如果是文件夹，则下载文件夹
			if (sftPv3DirectoryEntry.attributes.isDirectory()) {
				downloadFolder(
				        remoteFileFolder + REMOTE_PATH_SEPARATOR
				                + sftPv3DirectoryEntry.filename,
				        destFileFolder + File.separator
				                + sftPv3DirectoryEntry.filename);
			} else if (sftPv3DirectoryEntry.attributes.isRegularFile()) {
				downloadFile(sftPv3DirectoryEntry.filename, remoteFileFolder,
				        destFileFolder);
			}
		}
	}

	/**
	 * 下载目录，从远程数组，到本地文件夹，意思把数组中的远程目录都下载到同一个文件夹下，小心文件覆盖
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileFolder
	 * @param destFileFolder
	 */
	public void downloadFolders(String[] remoteFileFolder,
	        String destFileFolder) {
		for (String string : remoteFileFolder) {
			downloadFolder(string, destFileFolder);
		}
	}

	/**
	 * 下载目录，从远程数组、到本地数组，一一对应
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileFolder
	 * @param destFileFolder
	 */
	public void downloadFolders(String[] remoteFileFolder,
	        String[] destFileFolder) {

		if (null == remoteFileFolder || null == destFileFolder) {
			throw new BusinessValidationException("远程文件夹和本地文件夹都不能为null");
		}

		if (remoteFileFolder.length == 0 || destFileFolder.length == 0) {
			throw new BusinessValidationException("远程文件夹和本地文件夹都不能为空");
		}

		if (remoteFileFolder.length != destFileFolder.length) {
			throw new BusinessValidationException("远程文件夹数组长度，和本地文件夹数组长度，必须得一样");
		}
		for (int i = 0; i < destFileFolder.length; i++) {
			downloadFolder(remoteFileFolder[i], destFileFolder[i]);
		}
	}

	/**
	 * 执行命令
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param command
	 */
	public String executeShell(String command) {
		LOGGER.debug("执行命令:{}", command);
		Session currentSession = getCurrentSession();

		try (InputStream stdStream = new StreamGobbler(
		        currentSession.getStdout());
		        InputStream stdErrStream = new StreamGobbler(
		                currentSession.getStderr());

		        BufferedReader bReader = new BufferedReader(
		                new InputStreamReader(stdStream));

		        BufferedReader bufferedErrorReader = new BufferedReader(
		                new InputStreamReader(stdErrStream))) {
			currentSession.execCommand(command);
			StringBuilder outputBuilder = new StringBuilder();
			String line = null;
			while (true) {
				line = bReader.readLine();
				if (null == line) {
					break;
				}
				outputBuilder.append(line).append("\n");
			}

			String errorLine = null;
			while ((errorLine = bufferedErrorReader.readLine()) != null) {
				outputBuilder.append(errorLine).append("\n");
			}

			LOGGER.debug("命令执行结果:\n{}", outputBuilder);
			return outputBuilder.toString();
		} catch (IOException e1) {
			LOGGER.error("执行命令失败:{}", e1.getMessage());
			throw new BusinessValidationException("执行命令失败");
		} finally {
			close();
		}
	}

	/**
	 * 删除远程文件或者目录
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileOrFolder
	 */
	public boolean deleteRemoteFile(String remoteFile) {
		try {
			LOGGER.debug("尝试删除文件:{}", remoteFile);
			getSftpV3Client().rm(remoteFile);
			LOGGER.debug("删除文件成功:{}", remoteFile);

			return true;
		} catch (IOException e) {
			LOGGER.error("删除文件失败:{}", e.getMessage());

			if (e.getMessage().contains(NO_SUCH_FILE_ERROR_MESSAGE)) {
				LOGGER.debug("文件夹不存在，不需要删除");

				return true;
			}

			return false;
		} finally {
			close();
		}
	}

	/**
	 * 删除远程文件或者目录
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileOrFolder
	 */
	public boolean deleteRemoteFile(String[] remoteFiles) {
		try {

			LOGGER.debug("尝试删除一组文件");
			for (String remoteFile : remoteFiles) {
				getSftpV3Client().rm(remoteFile);
				LOGGER.debug("删除文件成功:{}", remoteFile);
			}
			LOGGER.debug("批量删除文件成功");
			return true;
		} catch (IOException e) {
			LOGGER.error("删除文件失败:{}", e.getMessage());
			if (e.getMessage().contains(NO_SUCH_FILE_ERROR_MESSAGE)) {
				LOGGER.debug("文件不存在，不需要删除");

				return true;
			}

			return false;
		} finally {
			close();
		}
	}

	/**
	 * 删除远程目录
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param remoteFileOrFolder
	 */
	public boolean deleteRemoteFileFolder(String remoteFileFolder) {
		try {
			LOGGER.debug("尝试删除文件夹:{}", remoteFileFolder);
			getSftpV3Client().rmdir(remoteFileFolder);
			LOGGER.debug("删除文件夹成功:{}", remoteFileFolder);

			return true;
		} catch (IOException e) {
			LOGGER.error("删除文件夹失败:{}", e.getMessage());

			if (e.getMessage().contains(NO_SUCH_FILE_ERROR_MESSAGE)) {
				LOGGER.debug("文件夹不存在，不需要删除");

				return true;
			}

			if (e.getMessage().contains("Failure (SSH_FX_FAILURE:")) {
				return deleteNoneEmptyRemoteFolder(remoteFileFolder);
			}

			return false;
		} finally {
			close();
		}
	}

	/**
	 * 删除非空文件夹
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param remoteFolder
	 * @return
	 */
	private boolean deleteNoneEmptyRemoteFolder(String remoteFolder) {
		LOGGER.debug("删除非空文件夹:{}", remoteFolder);
		String command = "rm -rf " + remoteFolder;
		String deleteNoneEmptyFolder = executeShell(command);
		LOGGER.debug("删除非空文件夹结果：{}", deleteNoneEmptyFolder);
		return true;

	}

	/**
	 * 在远端linux上创建文件夹
	 * 
	 * @param dirName
	 *            文件夹名称
	 * @param posixPermissions
	 *            目录或者文件夹的权限
	 */
	public boolean mkdir(String dirName, int posixPermissions) {
		try {
			LOGGER.debug("创建文件夹:{}", dirName);
			getSftpV3Client().mkdir(dirName, posixPermissions);
			LOGGER.debug("创建文件夹:{}成功", dirName);
			return true;
		} catch (IOException e) {
			LOGGER.error("创建文件夹失败:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 在远端linux上创建文件夹
	 * 
	 * @param dirName
	 *            文件夹名称
	 */
	public boolean mkdir(String dirName) {
		return mkdir(dirName, MKDIR_POSIX_PERMISSION);
	}

	/**
	 * 在远程Linux服务器端移动文件或者文件夹到新的位置
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public boolean moveFileOrDir(String oldPath, String newPath) {
		try {
			LOGGER.debug("把文件或者文件夹从:{},移动到:{}", oldPath, newPath);
			getSftpV3Client().mv(oldPath, newPath);
			LOGGER.debug("把文件或者文件夹从:{},移动到:{}成功", oldPath, newPath);
			return true;
		} catch (Exception e) {
			LOGGER.error("移动文件失败:从何{}到:{}", oldPath, newPath);
			return false;
		}
	}

	/**
	 * 获取sftpclient
	 * 
	 * @see :
	 * @param :
	 * @return : SFTPv3Client
	 * @return
	 */
	private SFTPv3Client getSftpV3Client() {
		try {
			if (null == this.sftPv3Client) {
				LOGGER.debug("初始化SFTPv3Client");
				this.sftPv3Client = new SFTPv3Client(getConnection());
				LOGGER.debug("初始化SFTPv3Client完成");
			}
			return this.sftPv3Client;
		} catch (IOException e) {
			LOGGER.error("获取SFTPv3Client失败，失败原因:{}", e.getMessage());
			throw new BusinessValidationException("获取SFTPv3Client失败");
		}
	}

	/**
	 * 列举远程目录文件
	 * 
	 * @see :
	 * @param :
	 * @return : List<File>
	 * @param remoteDir
	 * @return
	 */
	private void listRemoteDir(String remoteDir) {
		List<SFTPv3DirectoryEntry> children = null;
		try {
			children = getSftpV3Client().ls(remoteDir);
			if (children.isEmpty()) {
				return;
			}

			Iterator iterator = children.iterator();
			while (iterator.hasNext()) {
				SFTPv3DirectoryEntry thisChild = (SFTPv3DirectoryEntry) iterator
				        .next();

				SFTPv3FileAttributes attributes = thisChild.attributes;
				if (!".".equals(thisChild.filename)
				        && !"..".equals(thisChild.filename)) {
					String childFolder = remoteDir + "/" + thisChild.filename;
					if (attributes.isDirectory()) {
						listRemoteDir(childFolder);
					}
					this.childrenList.add(thisChild);
				}
			}
		} catch (IOException e) {
			LOGGER.error("获取子孙文件或者文件夹失败:{}", e.getMessage());
			throw new BusinessValidationException("获取文件夹下的内容失败");
		}
	}

	/**
	 * 列举远程目录文件
	 * 
	 * @see :
	 * @param :
	 * @return : List<File>
	 * @param remoteDir
	 * @return
	 */
	private List<SFTPv3DirectoryEntry> getChildren(String remoteDir) {
		List<SFTPv3DirectoryEntry> children = null;
		List<SFTPv3DirectoryEntry> finalChildren = new ArrayList<>();
		try {
			children = getSftpV3Client().ls(remoteDir);
			Iterator iterator = children.iterator();
			while (iterator.hasNext()) {
				SFTPv3DirectoryEntry thisChild = (SFTPv3DirectoryEntry) iterator
				        .next();
				if (!".".equals(thisChild.filename)
				        && !"..".equals(thisChild.filename)) {
					finalChildren.add(thisChild);
				}
			}
			return finalChildren;
		} catch (IOException e) {
			LOGGER.error("获取子孙文件或者文件夹失败:{}", e.getMessage());
			throw new BusinessValidationException("获取文件夹下的内容失败");
		}
		// finally {
		// close();
		// }
	}

	/**
	 * 单例模式
	 * 懒汉式
	 * 线程安全
	 * 
	 * @return
	 */
	public static GanymedSshClient getInstance() {
		if (null == instance) {
			synchronized (GanymedSshClient.class) {
				if (null == instance) {
					instance = new GanymedSshClient();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取实例
	 * 
	 * @see :
	 * @param :
	 * @return : GanymedSshClient
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @return
	 */
	public static GanymedSshClient getInstance(String ip, int port, String name,
	        String password) {
		if (null == instance) {
			synchronized (GanymedSshClient.class) {
				if (null == instance) {
					instance = new GanymedSshClient(ip, port, name, password);
				}
			}
		}
		return instance;
	}

	/**
	 * 获取实例
	 * 
	 * @see :
	 * @param :
	 * @return : GanymedSshClient
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @return
	 */
	public static GanymedSshClient getInstance(String ip, String name,
	        String password) {
		if (null == instance) {
			synchronized (GanymedSshClient.class) {
				if (null == instance) {
					instance = new GanymedSshClient(ip, name, password);
				}
			}
		}
		return instance;
	}

	/**
	 * 获取实例
	 * 
	 * @see :
	 * @param :
	 * @return : GanymedSshClient
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @return
	 */
	public static GanymedSshClient getInstance(String ip) {
		if (null == instance) {
			synchronized (GanymedSshClient.class) {
				if (null == instance) {
					instance = new GanymedSshClient(ip);
				}
			}
		}
		return instance;
	}

	/**
	 * 获取实例
	 * 
	 * @see :
	 * @param :
	 * @return : GanymedSshClient
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @return
	 */
	public static GanymedSshClient getInstance(ServerInfo serverInfo) {
		if (null == instance) {
			synchronized (GanymedSshClient.class) {
				if (null == instance) {
					instance = new GanymedSshClient(serverInfo);
				}
			}
		}
		return instance;
	}

	/**
	 * 判断服务器是否可认证
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @return
	 */
	public boolean serverCanBeAuthed() {
		try {
			initConnection();
			return true;
		} catch (Exception e) {
			LOGGER.error("认证授权失败，原因:{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 判断服务器是否可连通
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @return
	 */
	public boolean serverIpCanBeConnected() {
		try {
			return InetAddress.getByName(this.serverInfo.getHost())
			        .isReachable(3000);
		} catch (UnknownHostException e) {
			LOGGER.error("测试连接服务器失败,失败原因:{}", e.getMessage());
			return false;
		} catch (IOException e) {
			LOGGER.error("连接服务器IO异常,失败原因:{}", e.getMessage());
			return false;
		}

	}

	public static void main(String[] args) {
		GanymedSshClient ganymedSshClient = new GanymedSshClient(
		        "192.168.110.31", "admin", "admin");
		String destFolder = "E:\\test3\\tq-datamanagement";
		ganymedSshClient.downloadFolder(
		        "/home/admin/codecoverge/tq-datamanagement", destFolder);
	}
}
