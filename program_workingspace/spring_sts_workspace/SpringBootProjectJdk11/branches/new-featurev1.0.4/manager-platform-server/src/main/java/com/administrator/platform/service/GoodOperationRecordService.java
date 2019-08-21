/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.GoodOperationRecord;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface GoodOperationRecordService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<Good>
	 * @return
	 */
	List<GoodOperationRecord> findAllGoodOperationRecordList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param envAddress
	 * @return
	 */
	GoodOperationRecord addGoodOperationRecord(GoodOperationRecord record);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param envAddress
	 * @return
	 */
	GoodOperationRecord getGoodOperationRecordByObject(
	        GoodOperationRecord record);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param envAddress
	 * @return
	 */
	GoodOperationRecord getGoodOperationRecordById(Long id);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<Good>
	 * @param searchContent
	 * @return
	 */
	List<GoodOperationRecord> findGoodOperationRecordsByGoodId(Long goodId);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<Good>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<GoodOperationRecord> findGoodOperationRecordByPage(Integer page,
	        Integer size);

}
