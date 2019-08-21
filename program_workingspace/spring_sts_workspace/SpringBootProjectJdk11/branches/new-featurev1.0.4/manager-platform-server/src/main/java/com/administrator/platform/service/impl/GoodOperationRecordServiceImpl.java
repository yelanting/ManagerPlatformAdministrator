/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.TimeUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.GoodOperationRecordFormm;
import com.administrator.platform.enums.GoodStatus;
import com.administrator.platform.enums.OperationType;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.GoodOperationRecordMapper;
import com.administrator.platform.model.Good;
import com.administrator.platform.model.GoodOperationRecord;
import com.administrator.platform.service.GoodOperationRecordService;
import com.administrator.platform.service.GoodService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class GoodOperationRecordServiceImpl
        implements GoodOperationRecordService {
	private static final Logger logger = LoggerFactory
	        .getLogger(GoodOperationRecordServiceImpl.class);
	@Autowired
	private GoodOperationRecordMapper goodOperationRecordMapper;

	@Autowired
	private GoodService goodService;

	/**
	 * 验证物品操作记录是否存在
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param goodId
	 */
	private void validateGood(Long goodId) {

		if (null == goodId || null == goodService.getGoodById(goodId)) {
			throw new BusinessValidationException("物品操作记录信息不存在，不可添加操作记录");
		}
	}

	/**
	 * @see com.administrator.platform.service.GoodOperationRecordService#findAllGoodOperationRecordList()
	 */
	@Override
	public List<GoodOperationRecord> findAllGoodOperationRecordList() {
		return goodOperationRecordMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see
	 *      com.administrator.platform.service.GoodOperationRecordService#addGoodOperationRecord(com.administrator.platform.model.GoodOperationRecord)
	 */
	@Override
	public GoodOperationRecord addGoodOperationRecord(
	        GoodOperationRecord goodOperationRecord) {
		ValidationUtil.validateNull(goodOperationRecord, null);
		validateInput(goodOperationRecord);

		validateGood(goodOperationRecord.getGoodId());

		Good good = goodService.getGoodById(goodOperationRecord.getGoodId());

		logger.debug("准备插入一条操作记录:{}", goodOperationRecord);
		/**
		 * 借出后变为 已借出
		 */
		if (OperationType.BORROW.toString()
		        .equalsIgnoreCase(goodOperationRecord.getOperationType())) {
			logger.debug("当前执行借出操作");
			if (GoodStatus.BORROWED == good.getGoodStatus()) {
				throw new BusinessValidationException("借出状态。不能再借");
			}

			good.setBorrowedTimes(good.getBorrowedTimes() + 1);
			good.setGoodStatus(GoodStatus.BORROWED);

			// 当前持有人变成借用人
			good.setCurrentOwner(goodOperationRecord.getBorrowUser());
		} else {
			logger.debug("当前执行归还操作");
			if (GoodStatus.NORMAL == good.getGoodStatus()) {
				throw new BusinessValidationException("正常状态，不能归还");
			}

			// 归还后 变为正常
			good.setGiveBackTimes(good.getGiveBackTimes() + 1);
			good.setGoodStatus(GoodStatus.NORMAL);

			// 当前持有人变成经办人
			good.setCurrentOwner(goodOperationRecord.getDealPerson());
			GoodOperationRecord lastBorrowGoodOperationRecord = goodOperationRecordMapper
			        .findLastBorrowGoodOperationRecordByGoodId(
			                goodOperationRecord.getGoodId());

			/**
			 * 获取持有时间，并判断是否超期
			 */
			Date nowDate = new Date();
			boolean overtime = TimeUtil.toDateAfterFromDate(
			        lastBorrowGoodOperationRecord.getExpectedGiveBackDate(),
			        nowDate);
			goodOperationRecord.setKeepOvertime(overtime);
			goodOperationRecord.setRealisticGiveBackDate(nowDate);
			goodOperationRecord.setKeepPeriod(TimeUtil.getPeriodTimeOfDate(
			        lastBorrowGoodOperationRecord.getBorrowDate(), nowDate));

			/**
			 * 把借用时间，设置为上次借用时的时间
			 */

			goodOperationRecord.setExpectedGiveBackDate(
			        lastBorrowGoodOperationRecord.getExpectedGiveBackDate());
			/**
			 * 借用人，设置为上次借用的人
			 */
			goodOperationRecord.setBorrowUser(
			        lastBorrowGoodOperationRecord.getBorrowUser());

		}

		logger.debug("添加操作记录:{}", goodOperationRecord);
		try {
			goodOperationRecord.setGoodId(good.getId());
			goodOperationRecordMapper.insert(goodOperationRecord);
			goodService.updateGood(good);
			return goodOperationRecord;
		} catch (Exception e) {
			logger.error("添加物品操作记录失败:{},{}", goodOperationRecord,
			        e.getMessage());
			throw new BusinessValidationException("添加物品操作记录失败！！！");
		}
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param goodOperationRecord
	 *            : 待校验的地址对象
	 */
	private void validateInput(GoodOperationRecord goodOperationRecord) {
		// 判空
		ValidationUtil.validateNull(goodOperationRecord, null);

		if (OperationType.BORROW.toString()
		        .equalsIgnoreCase(goodOperationRecord.getOperationType())) {
			if (!StringUtil
			        .isStringAvaliable(goodOperationRecord.getBorrowUser())) {
				throw new BusinessValidationException("物品借出人不能为空");
			}
		}

		if (OperationType.GIVE_BACK.toString()
		        .equalsIgnoreCase(goodOperationRecord.getOperationType())) {
			if (!StringUtil
			        .isStringAvaliable(goodOperationRecord.getGiveBackUser())) {
				throw new BusinessValidationException("物品归还人不能为空");
			}
		}

		ValidationUtil.validateStringAndLength(
		        goodOperationRecord.getGiveBackUser(), null,
		        GoodOperationRecordFormm.COMMON_FORM_FIELD_LENGTH, "物品归还人");
		ValidationUtil.validateStringAndLength(
		        goodOperationRecord.getDealPerson(), 1,
		        GoodOperationRecordFormm.COMMON_FORM_FIELD_LENGTH, "经手人");

		ValidationUtil.validateStringAndLength(
		        goodOperationRecord.getGiveBackUser(), null,
		        GoodOperationRecordFormm.COMMON_FORM_FIELD_LENGTH, "归还人");

	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.GoodOperationRecordService#getGoodOperationRecordById(java.lang.Long)
	 */
	@Override
	public GoodOperationRecord getGoodOperationRecordById(Long id) {
		ValidationUtil.validateNull(id, null);
		return goodOperationRecordMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.GoodOperationRecordService#getGoodOperationRecordByObject(com.administrator.platform.model.
	 *      GoodOperationRecord)
	 */
	@Override
	public GoodOperationRecord getGoodOperationRecordByObject(
	        GoodOperationRecord goodOperationRecord) {
		ValidationUtil.validateNull(goodOperationRecord, null);
		return getGoodOperationRecordById(goodOperationRecord.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.GoodOperationRecordService#findGoodOperationRecordByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<GoodOperationRecord> findGoodOperationRecordByPage(Integer page,
	        Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return goodOperationRecordMapper.findAll(pageable);
	}

	@Override
	public List<GoodOperationRecord> findGoodOperationRecordsByGoodId(
	        Long goodId) {
		logger.debug("根据物品id查询操作记录：{}", goodId);
		List<GoodOperationRecord> goodOperationRecords = goodOperationRecordMapper
		        .findGoodOperationRecordsByGoodId(goodId);
		logger.error("查询操作记录结果:{}", goodOperationRecords);
		return goodOperationRecords;
	}
}
