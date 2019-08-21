/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:48:56
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import com.administrator.platform.model.GlobalParam;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:48:56
 * @see :
 */
public interface GlobalParamService {
	/**
	 * 查询列表
	 * 
	 * @see : 获取列表
	 * @param :
	 * @return : List<GlobalParam>
	 * @return
	 */
	List<GlobalParam> getList();

	/**
	 * 添加全局参数
	 * 
	 * @see :
	 * @param :
	 * @return : GlobalParam
	 * @param globalParam
	 * @return
	 */
	GlobalParam addGlobalParam(GlobalParam globalParam);

	/**
	 * 修改数据
	 * 
	 * @see :
	 * @param :
	 * @return : GlobalParam
	 * @param globalParam
	 * @return
	 */
	GlobalParam updateGlobalParam(GlobalParam globalParam);

	/**
	 * 根据搜索内容查询列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<GlobalParam>
	 * @param searchContent
	 * @return
	 */
	List<GlobalParam> searchGlobalParamsBySearchContent(String searchContent);

	// /**
	// * 根据paramKey查询
	// *
	// * @see :
	// * @param :
	// * @return : List<GlobalParam>
	// * @param searchContent
	// * @return
	// */
	// List<GlobalParam> searchGlobalParamsByParamKey(String searchContent);

	/**
	 * 根据paramKey查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<GlobalParam>
	 * @param paramKey
	 * @return
	 */
	GlobalParam findGlobalParamByParamKey(String paramKey);

	/**
	 * 单个删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param id
	 */
	Long deleteGlobalParam(Long id);

	/**
	 * 批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	Long[] deleteGlobalParam(Long[] ids);

	/**
	 * 根据主键查询
	 * 
	 * @see : 根据主键查询
	 * @param :
	 * @return : GlobalParam
	 * @param id
	 * @return
	 */
	GlobalParam selectByPrimaryKey(Long id);

	/**
	 * 根据对象查询
	 * 
	 * @see : 根据具体对象查询
	 * @param :
	 * @return : GlobalParam
	 * @param globalParam
	 * @return
	 */
	GlobalParam selectByObject(GlobalParam globalParam);
}
