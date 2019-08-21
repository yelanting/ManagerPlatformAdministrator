/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ArrayUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.ServerInfoFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.ServerInfoMapper;
import com.administrator.platform.model.ServerInfo;
import com.administrator.platform.service.ServerInfoService;
import com.administrator.platform.tools.shell.ganymed.GanymedSshClient;
import com.administrator.platform.vo.ShellExecuteResultDTO;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service(value = "serverInfoService")
public class ServerInfoServiceImpl implements ServerInfoService {
	private static final Logger logger = LoggerFactory
	        .getLogger(ServerInfoServiceImpl.class);

	@Autowired
	private ServerInfoMapper serverInfoMapper;

	/**
	 * @see com.administrator.platform.service.ServerInfoService#findAllServerInfoList()
	 */
	@Override
	public List<ServerInfo> findAllServerInfoList() {
		logger.debug("获取所有服务器信息列表");
		List<ServerInfo> allServerInfos = serverInfoMapper.findAll();
		logger.debug("当前列表:{}", allServerInfos);

		return allServerInfos;
	}

	/**
	 * 添加服务器信息实现方法
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#addServerInfo(com.administrator.platform.model.ServerInfo)
	 */
	@Override
	public ServerInfo addServerInfo(ServerInfo serverInfo) {
		ValidationUtil.validateNull(serverInfo, null);
		validateInput(serverInfo);

		if (!findServerInfosByServerIp(serverInfo.getServerIp()).isEmpty()) {
			throw new BusinessValidationException("该ip已经存在");
		}

		try {
			logger.debug("添加服务器信息:{}", serverInfo);
			serverInfoMapper.insert(serverInfo);
			return serverInfo;
		} catch (Exception e) {
			logger.error("新增服务器失败:{},{}", serverInfo, e.getMessage());
			throw new BusinessValidationException("新增服务器失败!");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#deleteServerInfo(java.lang.Long)
	 */
	@Override
	public int deleteServerInfo(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			logger.debug("开始删除服务器报告,id为:{}", id);
			serverInfoMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException("根据ID删除失败");
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#deleteServerInfo(java.lang.Long[])
	 */
	@Override
	public int deleteServerInfo(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			logger.debug("开始批量删除服务器报告,id为:{}", ArrayUtil.toString(ids));
			serverInfoMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			logger.error("批量删除失败:{},{}", ids, e.getMessage());
			throw new BusinessValidationException("根据IDS批量删除失败");
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#findEnvAddrrsByServerIp(java.lang.String)
	 */
	@Override
	public List<ServerInfo> findServerInfosByServerIp(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称搜索服务器信息数据:{}", searchContent);
		return serverInfoMapper.findServerInfosByServerIp(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#findEnvAddrrsByServerIp(java.lang.String)
	 */
	@Override
	public List<ServerInfo> findServerInfosByServerIpLike(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		logger.debug("根据名称模糊搜索服务器信息数据:{}", searchContent);
		return serverInfoMapper.findServerInfosByServerIpLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param serverInfo
	 *            : 待校验的地址对象
	 */
	private void validateInput(ServerInfo serverInfo) {
		validateInputBase(serverInfo);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param serverInfo
	 *            : 待校验的地址对象
	 */
	private void validateInputBase(ServerInfo serverInfo) {
		// 判空
		ValidationUtil.validateNull(serverInfo, null);

		if (!StringUtil.isStringAvaliable(serverInfo.getServerName())) {
			throw new BusinessValidationException("服务器名称不能为空");
		}

		ValidationUtil.validateStringAndLength(serverInfo.getServerIp(), null,
		        ServerInfoFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器ip地址名称");

		ValidationUtil.validateStringAndLength(serverInfo.getUsername(), null,
		        ServerInfoFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器访问用户名");

		ValidationUtil.validateStringAndLength(serverInfo.getPassword(), null,
		        ServerInfoFormDefinition.COMMON_FORM_FIELD_LENGTH, "服务器访问密码");

		ValidationUtil.validateStringAndLength(serverInfo.getDescription(),
		        null, ServerInfoFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH,
		        "备注");
	}

	/**
	 * 修改服务器
	 * 
	 * @param serverInfo:地址对象
	 * @see com.administrator.platform.service.ServerInfoService#updateServerInfo(com.administrator.platform.model.
	 *      ServerInfo)
	 */
	@Override
	public ServerInfo updateServerInfo(ServerInfo serverInfo) {
		logger.debug("更新服务器信息:{}", serverInfo);
		ValidationUtil.validateNull(serverInfo, null);
		ServerInfo currentServerInfo = getServerInfoByObject(serverInfo);

		if (null == currentServerInfo) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		serverInfo.setId(currentServerInfo.getId());

		// 校验输入内容
		validateInput(serverInfo);

		if (!serverInfoMapper.findServerInfoByServerIpExceptThis(serverInfo)
		        .isEmpty()) {
			throw new BusinessValidationException("该ip已经存在，不可重复添加");
		}

		try {
			serverInfoMapper.updateByPrimaryKey(serverInfo);
			logger.debug("更新服务器信息完成:{}", serverInfo);
			return serverInfo;
		} catch (Exception e) {
			logger.error("更新服务器失败:{},{}", serverInfo, e.getMessage());
			throw new BusinessValidationException("更新服务器失败!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.ServerInfoService#getServerInfoById(java.lang.Long)
	 */
	@Override
	public ServerInfo getServerInfoById(Long id) {
		ValidationUtil.validateNull(id, null);
		logger.debug("根据ID查询详情:{}", id);
		return serverInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.ServerInfoService#getServerInfoByObject(com.administrator.platform.model.
	 *      ServerInfo)
	 */
	@Override
	public ServerInfo getServerInfoByObject(ServerInfo serverInfo) {
		ValidationUtil.validateNull(serverInfo, null);
		logger.debug("根据内容查询详情:{}", serverInfo);
		return getServerInfoById(serverInfo.getId());
	}

	/**
	 * 执行命令
	 * 
	 * @see 在一台服务器上执行命令
	 */
	@Override
	public String executeShell(ServerInfo serverInfo, String executeShell) {

		logger.info("在服务器：{},上执行命令：{}", serverInfo, executeShell);
		String result = new GanymedSshClient(serverInfo.getServerIp(),
		        serverInfo.getUsername(), serverInfo.getPassword())
		                .executeShell(executeShell);

		logger.debug("执行完成,结果为:{}", result);
		return result;
	}

	/**
	 * 执行命令
	 * 
	 * @see 在一台服务器上执行命令
	 */
	@Override
	public ShellExecuteResultDTO executeShellWithResponse(ServerInfo serverInfo,
	        String executeShell) {
		String result = executeShell(serverInfo, executeShell);
		ShellExecuteResultDTO shellExecuteResultDTO = new ShellExecuteResultDTO();
		shellExecuteResultDTO.setServerIp(serverInfo.getServerIp());
		shellExecuteResultDTO.setResult(result);
		return shellExecuteResultDTO;
	}

	/**
	 * 批量执行结果
	 * 
	 * @see
	 * @param serverInfos：
	 *            待批量执行的服务器
	 * @param executeShell:
	 *            待批量执行的命令
	 */
	@Override
	public String[] executeShell(ServerInfo[] serverInfos,
	        String executeShell) {

		logger.info("批量执行命令:{}", executeShell);
		String[] results = new String[serverInfos.length];
		for (int i = 0; i < serverInfos.length; i++) {
			ServerInfo serverInfo = serverInfos[i];
			logger.debug("当前服务器:{}", serverInfo);
			if (null == serverInfo) {
				results[i] = "无数据";
			} else {
				results[i] = "***************" + serverInfo.getServerIp()
				        + "***************\n"
				        + new GanymedSshClient(serverInfo.getServerIp(),
				                serverInfo.getUsername(),
				                serverInfo.getPassword())
				                        .executeShell(executeShell);
			}
		}
		logger.info("批量执行命令:{}完成", executeShell);
		return results;
	}

	/**
	 * 批量执行结果
	 * 
	 * @see
	 * @param serverInfos：
	 *            待批量执行的服务器
	 * @param executeShell:
	 *            待批量执行的命令
	 */
	@Override
	public String[] executeShell(Long[] ids, String executeShell) {
		ServerInfo[] serverInfos = new ServerInfo[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ServerInfo serverInfo = getServerInfoById(ids[i]);
			logger.debug("当前服务器:{}", serverInfo);
			serverInfos[i] = serverInfo;
		}
		return executeShell(serverInfos, executeShell);
	}

	@Override
	public ShellExecuteResultDTO[] executeShellInBatchWithResponse(Long[] ids,
	        String executeShell) {
		ShellExecuteResultDTO[] shellExecuteResultDTOs = new ShellExecuteResultDTO[ids.length];
		for (int i = 0; i < shellExecuteResultDTOs.length; i++) {
			ServerInfo serverInfo = getServerInfoById(ids[i]);
			ShellExecuteResultDTO shellExecuteResultDTO = new ShellExecuteResultDTO();
			shellExecuteResultDTO.setServerIp(serverInfo.getServerIp());
			shellExecuteResultDTO
			        .setResult(new GanymedSshClient(serverInfo.getServerIp(),
			                serverInfo.getUsername(), serverInfo.getPassword())
			                        .executeShell(executeShell));

			shellExecuteResultDTOs[i] = shellExecuteResultDTO;
		}

		return shellExecuteResultDTOs;
	}

	/**
	 * 检测是否可达
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param serverInfo
	 * @return
	 */
	public boolean checkServerIsReachable(ServerInfo serverInfo) {
		return new GanymedSshClient(serverInfo.getServerIp(),
		        serverInfo.getUsername(), serverInfo.getPassword())
		                .serverIpCanBeConnected();
	}

	/**
	 * 检测是否可授权
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param serverInfo
	 * @return
	 */
	public boolean checkServerCanAuthed(ServerInfo serverInfo) {
		return new GanymedSshClient(serverInfo.getServerIp(),
		        serverInfo.getUsername(), serverInfo.getPassword())
		                .serverCanBeAuthed();
	}

	@Override
	public ShellExecuteResultDTO[] checkServerCanBeConnected(Long[] ids) {
		ShellExecuteResultDTO[] shellExecuteResultDTOs = new ShellExecuteResultDTO[ids.length];
		ServerInfo[] serverInfos = getServerInfosByIds(ids);

		ShellExecuteResultDTO shellExecuteResultDTO = null;
		String result = null;
		for (int i = 0; i < serverInfos.length; i++) {
			shellExecuteResultDTO = new ShellExecuteResultDTO();
			// 如果可达
			if (!checkServerIsReachable(serverInfos[i])) {
				result = "服务器无法连通";
			} else if (!checkServerCanAuthed(serverInfos[i])) {
				result = "服务器无法通过认证";
			} else {
				result = "服务器可连通且可认证";
			}

			shellExecuteResultDTO.setServerIp(serverInfos[i].getServerIp());
			shellExecuteResultDTO.setResult(result);
			shellExecuteResultDTOs[i] = shellExecuteResultDTO;
		}
		return shellExecuteResultDTOs;
	}

	/**
	 * 根据id查找服务器
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo[]
	 * @param ids
	 * @return
	 */
	private ServerInfo[] getServerInfosByIds(Long[] ids) {
		ServerInfo[] serverInfos = new ServerInfo[ids.length];

		ServerInfo serverInfo = null;
		for (int i = 0; i < ids.length; i++) {
			serverInfo = getServerInfoById(ids[i]);
			serverInfos[i] = serverInfo;
		}
		return serverInfos;
	}
}
