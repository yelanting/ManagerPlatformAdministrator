package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.GoodType;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface GoodTypeMapper {
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
	 * 插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(GoodType record);

	/**
	 * 部分字段插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int insertSelective(GoodType record);

	/**
	 * 根据主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : GoodType
	 * @param id
	 * @return
	 */
	GoodType selectByPrimaryKey(Long id);

	/**
	 * 部分字段修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKeySelective(GoodType record);

	/**
	 * 全字段修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKey(GoodType record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<GoodType>
	 * @param typeName
	 * @return
	 */
	List<GoodType> findGoodTypesByTypeName(String typeName);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<GoodType>
	 * @param typeName
	 * @return
	 */
	List<GoodType> findGoodTypesByTypeNameLike(String typeName);

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
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<GoodType>
	 */
	Page<GoodType> findAll(Pageable pageable);

	/**
	 * 查询所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<GoodType>
	 * @return
	 */
	List<GoodType> findAll();
}
