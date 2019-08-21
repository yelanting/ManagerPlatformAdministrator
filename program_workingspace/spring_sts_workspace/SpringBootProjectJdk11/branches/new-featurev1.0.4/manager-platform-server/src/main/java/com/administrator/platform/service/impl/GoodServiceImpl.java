/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.GoodFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.GoodMapper;
import com.administrator.platform.model.Good;
import com.administrator.platform.model.GoodType;
import com.administrator.platform.service.GoodService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class GoodServiceImpl implements GoodService {
	private static final Logger logger = LoggerFactory
	        .getLogger(GoodServiceImpl.class);
	@Autowired
	private GoodMapper goodMapper;

	@Autowired
	private GoodTypeServiceImpl goodTypeService;

	/**
	 * @see com.administrator.platform.service.GoodService#findAllGoodList()
	 */
	@Override
	public List<Good> findAllGoodList() {
		return goodMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see
	 *      com.administrator.platform.service.GoodService#addGood(com.administrator.platform.model.Good)
	 */
	@Override
	public Good addGood(Good good) {
		ValidationUtil.validateNull(good, null);
		validateInput(good);

		GoodType goodType = goodTypeService
		        .getGoodTypeById(good.getGoodTypeId());

		if (null == goodType) {
			throw new BusinessValidationException("物品关联的物品类型不存在！");
		}

		try {
			goodMapper.insert(good);
			return good;
		} catch (Exception e) {
			logger.error("添加物品失败:{},{}", good, e.getMessage());
			throw new BusinessValidationException("添加物品失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long)
	 */
	@Override
	public int deleteGood(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			goodMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			logger.error("根据物品id删除物品失败:{},{}", id, e.getMessage());
			throw new BusinessValidationException(
			        "根据物品id删除物品失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long[])
	 */
	@Override
	public int deleteGood(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			goodMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据ids批量删除物品失败" + e.getMessage());
		}
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.GoodService#findEnvAddrrsByGoodName(java.lang.String)
	 */
	@Override
	public List<Good> findGoodsByGoodName(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return goodMapper.findGoodsByGoodName(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.GoodService#findEnvAddrrsByGoodName(java.lang.String)
	 */
	@Override
	public List<Good> findGoodsByGoodNameLike(String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return goodMapper.findGoodsByGoodNameLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param good
	 *            : 待校验的地址对象
	 */
	private void validateInput(Good good) {
		// 判空
		ValidationUtil.validateNull(good, null);

		if (!StringUtil.isStringAvaliable(good.getGoodName())) {
			throw new BusinessValidationException("物品名称不能为空！");
		}

		// 验证物品名称长度
		ValidationUtil.validateStringAndLength(good.getGoodName(), null,
		        GoodFormDefinition.GOOD_NAME_LENGTH, "项目名称");
		// 验证物品类型
		if (null == good.getGoodTypeId()) {
			throw new BusinessValidationException("物品类型不能为空！");
		}

		// 验证物品描述

		ValidationUtil.validateStringAndLength(good.getGoodDesc(), null,
		        GoodFormDefinition.GOOD_DESC_FIELD_MAX_LENGTH, "物品描述");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param good:地址对象
	 * @see com.administrator.platform.service.GoodService#updateGood(com.administrator.platform.model.
	 *      Good)
	 */
	@Override
	public Good updateGood(Good good) {
		ValidationUtil.validateNull(good, null);

		Good currentGood = getGoodByObject(good);

		if (null == currentGood) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		good.setId(currentGood.getId());

		// 校验输入内容
		validateInput(good);
		try {
			goodMapper.updateByPrimaryKey(good);
			return good;
		} catch (Exception e) {
			logger.error("更新物品失败：{},{}", good, e.getMessage());
			throw new BusinessValidationException("更新物品信息失败!!!");
		}
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param Long
	 *            id:待查询的id
	 * @see com.administrator.platform.service.GoodService#getGoodById(java.lang.Long)
	 */
	@Override
	public Good getGoodById(Long id) {
		logger.debug("根据ID查询物品详情:{}", id);
		ValidationUtil.validateNull(id, null);
		Good good = goodMapper.selectByPrimaryKey(id);
		logger.debug("详情:{}", good);
		return good;
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.GoodService#getGoodByObject(com.administrator.platform.model.
	 *      Good)
	 */
	@Override
	public Good getGoodByObject(Good good) {
		ValidationUtil.validateNull(good, null);
		return getGoodById(good.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.GoodService#findGoodByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<Good> findGoodByPage(Integer page, Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return goodMapper.findAll(pageable);
	}

	/**
	 * 根据物品种类删除物品
	 * (non-Javadoc)
	 * 
	 * @see com.administrator.platform.service.GoodService#deleteGoodsByGoodTypeId(java.lang.Long)
	 */
	@Override
	public int deleteGoodsByGoodTypeId(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			goodMapper.deleteGoodsByGoodTypeId(id);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据物品种类删除物品失败：" + e.getMessage());
		}
	}
}
