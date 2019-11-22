package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.JavaParserRecord;

/**
 * @author : Administrator
 * @since : 2019年8月27日 16:47:26
 * @see :
 */
public interface JavaParserRecordMapper {
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
	int insert(JavaParserRecord record);

	/**
	 * 根据部分属性插入
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insertSelective(JavaParserRecord record);

	/**
	 * 根据主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param id
	 * @return
	 */
	JavaParserRecord selectByPrimaryKey(Long id);

	/**
	 * 根据部分属性修改
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(JavaParserRecord record);

	/**
	 * 根据主键更新内容
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(JavaParserRecord record);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @param projectName
	 * @return
	 */
	List<JavaParserRecord> findJavaParserRecordesByProjectName(
	        String projectName);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @param projectName
	 * @return
	 */
	List<JavaParserRecord> findJavaParserRecordesByProjectNameLike(
	        String projectName);

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
	 * @return : Page<JavaParserRecord>
	 */
	Page<JavaParserRecord> findAll(Pageable pageable);

	/**
	 * 查询所有数据
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @return
	 */
	List<JavaParserRecord> findAll();
}
