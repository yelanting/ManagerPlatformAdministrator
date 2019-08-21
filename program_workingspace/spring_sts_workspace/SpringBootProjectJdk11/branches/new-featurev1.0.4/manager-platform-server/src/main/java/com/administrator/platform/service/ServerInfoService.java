/**
 * @author : 孙留平
 * @since : 2019年7月31日 下午17:15:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.ServerInfo;
import com.administrator.platform.vo.ShellExecuteResultDTO;

/**
 * @author : Administrator
 * @since : 2019年7月31日 下午17:15:58
 * @see :
 */
public interface ServerInfoService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @return
	 */
	List<ServerInfo> findAllServerInfoList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo
	 * @param serverInfo
	 * @return
	 */
	ServerInfo addServerInfo(ServerInfo serverInfo);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo
	 * @param serverInfo
	 * @return
	 */
	ServerInfo updateServerInfo(ServerInfo serverInfo);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo
	 * @param id
	 * @return
	 */
	ServerInfo getServerInfoById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo
	 * @param serverInfo
	 * @return
	 */
	ServerInfo getServerInfoByObject(ServerInfo serverInfo);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteServerInfo(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteServerInfo(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @param searchContent
	 * @return
	 */
	List<ServerInfo> findServerInfosByServerIp(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @param searchContent
	 * @return
	 */
	List<ServerInfo> findServerInfosByServerIpLike(String searchContent);

	/**
	 * 执行命令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param serverInfo
	 * @param executeShell
	 * @return
	 */
	String executeShell(ServerInfo serverInfo, String executeShell);

	/**
	 * 批量执行命令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param serverInfo
	 * @param executeShell
	 * @return
	 */
	String[] executeShell(ServerInfo[] serverInfo, String executeShell);

	/**
	 * 批量执行命令
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param serverInfo
	 * @param executeShell
	 * @return
	 */
	String[] executeShell(Long[] ids, String executeShell);

	/**
	 * 批量执行shell
	 * 
	 * @see :
	 * @param :
	 * @return : ShellExecuteResultDTO[]
	 * @param ids
	 * @param executeShell
	 * @return
	 */
	ShellExecuteResultDTO[] executeShellInBatchWithResponse(Long[] ids,
	        String executeShell);

	/**
	 * 执行shell
	 * 
	 * @see :
	 * @param :
	 * @return : ShellExecuteResultDTO
	 * @param serverInfo
	 * @param executeShell
	 * @return
	 */
	ShellExecuteResultDTO executeShellWithResponse(ServerInfo serverInfo,
	        String executeShell);

	/**
	 * 检查服务器是否可授权
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param serverInfo
	 * @return
	 */
	ShellExecuteResultDTO[] checkServerCanBeConnected(Long[] ids);
}
