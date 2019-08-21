package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.DubboTest;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface DubboTestMapper {
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
	int insert(DubboTest record);

	/**
	 * 选择字段插入记录
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insertSelective(DubboTest record);

	/**
	 * 更新记录
	 * 
	 * @see :
	 * @param :
	 * @return : DubboTest
	 * @param id
	 * @return
	 */
	DubboTest selectByPrimaryKey(Long id);

	/**
	 * 部分字段更新
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(DubboTest record);

	/**
	 * 根据对象跟新
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */

	int updateByPrimaryKey(DubboTest record);

	/**
	 * 查询所有
	 * 
	 * @see :
	 * @param :
	 * @return : List<DubboTest>
	 * @return
	 */
	List<DubboTest> findAllDubboTests();

	/**
	 * 批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<DubboTest>
	 * @param interfaceName
	 * @return
	 */

	List<DubboTest> findDubboTestesByInterfaceName(String interfaceName);

	/**
	 * 模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<DubboTest>
	 * @param interfaceName
	 * @return
	 */
	List<DubboTest> findDubboTestesByInterfaceNameLike(String interfaceName);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<DubboTest>
	 * @param pageable
	 * @return
	 */
	Page<DubboTest> findAll(Pageable pageable);
}
