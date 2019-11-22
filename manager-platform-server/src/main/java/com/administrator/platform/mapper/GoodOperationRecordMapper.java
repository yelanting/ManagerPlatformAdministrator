package com.administrator.platform.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.administrator.platform.model.GoodOperationRecord;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public interface GoodOperationRecordMapper {
	/**
	 * 插入记录
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param record
	 * @return
	 */
	int insert(GoodOperationRecord record);

	/**
	 * 主键查询
	 * 
	 * @see :
	 * @param :
	 * @return : Good
	 * @param id
	 * @return
	 */
	GoodOperationRecord selectByPrimaryKey(Long id);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param pageable:分页信息
	 * @return : Page<Good>
	 */
	Page<GoodOperationRecord> findAll(Pageable pageable);

	/**
	 * 根据物品种类批量删除
	 * 
	 * @see :
	 * @param id
	 *            :物品种类id
	 * @return : void
	 */
	List<GoodOperationRecord> findAll();

	/**
	 * 根据物品种类ID删除其种类下的所有物品
	 * 
	 * @see :根据物品种类ID删除其种类下的所有物品
	 * @param goodId:物品id
	 * @return : List<GoodOperationRecord>
	 */
	List<GoodOperationRecord> findGoodOperationRecordsByGoodId(Long goodId);

	/**
	 * 查询记录的最新一次借的记录
	 * 
	 * @see :
	 * @param :
	 * @return : GoodOperationRecord
	 * @param goodId
	 * @return
	 */
	GoodOperationRecord findLastBorrowGoodOperationRecordByGoodId(Long goodId);
}
