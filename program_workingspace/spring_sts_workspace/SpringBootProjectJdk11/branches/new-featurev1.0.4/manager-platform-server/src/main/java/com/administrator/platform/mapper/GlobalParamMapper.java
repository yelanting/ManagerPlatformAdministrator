/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午6:43:30
 * @see:
 */
package com.administrator.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.administrator.platform.model.GlobalParam;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午6:43:30
 * @see :
 */
@Mapper
public interface GlobalParamMapper {

	/**
	 * 所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<GlobalParam>
	 * @return
	 */
	List<GlobalParam> findAll();

	/**
	 * 插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(GlobalParam record);

	/**
	 * 根据对象更新
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKey(GlobalParam record);

	/**
	 * 根据主键单个删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 根据主键批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteByIds(Long[] ids);

	/**
	 * 根据主键查询单个实体
	 * 
	 * @see :
	 * @param :
	 * @return : GlobalParam
	 * @param id
	 * @return
	 */
	GlobalParam selectByPrimaryKey(Long id);

	/**
	 * 根据单个实体查询
	 * 
	 * @see :
	 * @param :
	 * @return : GlobalParam
	 * @param record
	 * @return
	 */
	GlobalParam selectByObject(GlobalParam record);

	/**
	 * 根据key查询列表-模糊
	 * 
	 * @see :
	 * @param :
	 * @return : List<GlobalParam>
	 * @param searchContent
	 * @return
	 */
	List<GlobalParam> findGlobalParamsByParamKeyLike(String searchContent);

	/**
	 * 根据主键查询，精确
	 * 
	 * @see :
	 * @param :
	 * @return : GlobalParam
	 * @param paramKey
	 * @return
	 */
	GlobalParam findGlobalParamByParamKey(String paramKey);

	/**
	 * 查询非当前实体列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<GlobalParam>
	 * @param global
	 * @return
	 */
	List<GlobalParam> findOtherGlobalParamsByObject(GlobalParam global);
}
