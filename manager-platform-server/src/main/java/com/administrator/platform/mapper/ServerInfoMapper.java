package com.administrator.platform.mapper;

import java.util.List;

import com.administrator.platform.model.ServerInfo;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface ServerInfoMapper {
	/**
	 * 根据主键删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 新增记录
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(ServerInfo record);

	/**
	 * 根据主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : ServerInfo
	 * @param id
	 * @return
	 */
	ServerInfo selectByPrimaryKey(Long id);

	/**
	 * 根据主键更新内容
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(ServerInfo record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @param serverIp
	 * @return
	 */
	List<ServerInfo> findServerInfosByServerIp(String serverIp);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @param serverIp
	 * @return
	 */
	List<ServerInfo> findServerInfosByServerIpLike(String serverIp);

	/**
	 * 查询所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @return
	 */
	List<ServerInfo> findAll();

	/**
	 * 根据ID批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	/**
	 * 查询非当前ip的服务器信息
	 * 
	 * @see :
	 * @param :
	 * @return : List<ServerInfo>
	 * @param serverInfo
	 * @return
	 */
	List<ServerInfo> findServerInfoByServerIpExceptThis(ServerInfo serverInfo);
}
