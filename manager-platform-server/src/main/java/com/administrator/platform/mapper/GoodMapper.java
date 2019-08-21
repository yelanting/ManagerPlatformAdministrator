package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.Good;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface GoodMapper {
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
	 * 插入记录
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(Good record);

	/**
	 * 部分字段插入数据
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insertSelective(Good record);

	/**
	 * 主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param id
	 * @return
	 */
	Good selectByPrimaryKey(Long id);

	/**
	 * 部分字段修改数据
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Good record);

	/**
	 * 全字段修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Good record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<Good>
	 * @param goodName
	 * @return
	 */
	List<Good> findGoodsByGoodName(String goodName);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<Good>
	 * @param goodName
	 * @return
	 */
	List<Good> findGoodsByGoodNameLike(String goodName);

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
	 * 根据ID查询
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param id
	 * @return
	 */

	Good getById(Long id);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<Good>
	 */
	Page<Good> findAll(Pageable pageable);

	/**
	 * 根据物品种类批量删除
	 * 
	 * @see :
	 * @param id
	 *            :物品种类id
	 * @return : void
	 */
	List<Good> findAll();

	/**
	 * 根据物品种类ID删除其种类下的所有物品
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param id
	 */
	void deleteGoodsByGoodTypeId(Long id);
}
