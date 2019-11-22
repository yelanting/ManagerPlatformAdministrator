/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:01
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.JavaParserRecord;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:01
 * @see :
 */
public interface JavaParserService {

	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @return
	 */
	List<JavaParserRecord> findAllJavaParserRecordList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param javaParserRecord
	 * @return
	 */
	JavaParserRecord addJavaParserRecord(JavaParserRecord javaParserRecord);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param javaParserRecord
	 * @return
	 */
	JavaParserRecord updateJavaParserRecord(JavaParserRecord javaParserRecord);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param id
	 * @return
	 */
	JavaParserRecord getJavaParserRecordById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param javaParserRecord
	 * @return
	 */
	JavaParserRecord getJavaParserRecordByObject(
	        JavaParserRecord javaParserRecord);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteJavaParserRecord(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteJavaParserRecord(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @param searchContent
	 * @return
	 */
	List<JavaParserRecord> findJavaParserRecordesByProjectName(
	        String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<JavaParserRecord>
	 * @param searchContent
	 * @return
	 */
	List<JavaParserRecord> findJavaParserRecordesByProjectNameLike(
	        String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<JavaParserRecord>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<JavaParserRecord> findJavaParserRecordByPage(Integer page,
	        Integer size);

	/**
	 * 重置覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : JavaParserRecord
	 * @param javaParserRecord
	 */
	JavaParserRecord resetJavaParserRecordData(
	        JavaParserRecord javaParserRecord);

	/**
	 * 解析请求
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param JavaParserRecord
	 * @return
	 */
	JavaParserRecord createRequestMappingSource(HttpServletRequest request,
	        JavaParserRecord javaParserRecord);
}
