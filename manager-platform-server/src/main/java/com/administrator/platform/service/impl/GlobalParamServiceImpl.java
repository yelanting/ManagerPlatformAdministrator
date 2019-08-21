/**
 * @author : 孙留平
 * @since : 2019年5月7日 下午7:15:13
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.GlobalParamMapper;
import com.administrator.platform.model.GlobalParam;
import com.administrator.platform.service.GlobalParamService;

/**
 * @author : Administrator
 * @since : 2019年5月7日 下午7:15:13
 * @see :
 */
@Service
public class GlobalParamServiceImpl implements GlobalParamService {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalParamServiceImpl.class);

	@Autowired
	private GlobalParamMapper globalParamMapper;

	/**
	 * @see 获取列表
	 */
	@Override
	public List<GlobalParam> getList() {
		return globalParamMapper.findAll();
	}

	/**
	 * @see 添加全局参数
	 */
	@Override
	public GlobalParam addGlobalParam(GlobalParam globalParam) {
		logger.info("添加全局参数:{}", globalParam);
		if (checkParamKeyExists(globalParam.getParamKey())) {
			throw new BusinessValidationException("该key已经存在，不可重复添加");
		}

		try {

			int resultKey = globalParamMapper.insert(globalParam);
			globalParam.setId(Long.valueOf(resultKey));
			return globalParam;
		} catch (Exception e) {
			logger.error("添加全局参数失败,错误信息:{}", e.getMessage());
			throw new BusinessValidationException("添加全局参数失败");
		}
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#updateGlobalParam(com.tianque.yunxiao.connect.server.model.GlobalParam)
	 */
	@Override
	public GlobalParam updateGlobalParam(GlobalParam globalParam) {
		logger.info("修改全局参数");
		ValidationUtil.validateNull(globalParam.getId(), null);

		if (checkParamKeyExists(globalParam)) {
			throw new BusinessValidationException("该key已经存在，不可修改为此key");
		}

		GlobalParam currentGlobalParam = selectByObject(globalParam);

		if (null == currentGlobalParam) {
			throw new BusinessValidationException("待修改对象不存在，不能修改");
		}

		try {
			this.globalParamMapper.updateByPrimaryKey(globalParam);
			return globalParam;
		} catch (Exception e) {
			throw new BusinessValidationException("修改失败");
		}

	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#searchGlobalParamsBySearchContent(java.lang.String)
	 */
	@Override
	public List<GlobalParam> searchGlobalParamsBySearchContent(
			String searchContent) {
		return globalParamMapper.findGlobalParamsByParamKeyLike(searchContent);
	}

	/**
	 * @see 单个删除
	 */
	@Override
	public Long deleteGlobalParam(Long id) {
		ValidationUtil.validateNull(id, null);
		globalParamMapper.deleteByPrimaryKey(id);
		return id;
	}

	/**
	 * 批量删除
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#deleteGlobalParam(java.lang.Long[])
	 */
	@Override
	public Long[] deleteGlobalParam(Long[] ids) {
		ValidationUtil.validateNull(ids, null);
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		globalParamMapper.deleteByIds(ids);
		return ids;
	}

	/**
	 * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public GlobalParam selectByPrimaryKey(Long id) {
		logger.info("根据主键查询");
		ValidationUtil.validateNull(id, null);
		return globalParamMapper.selectByPrimaryKey(id);
	}

	/**
	 * @see
	 *      com.tianque.yunxiao.connect.server.service.GlobalParamService#selectByObject(com.tianque.yunxiao.connect.server.model
	 *      .GlobalParam)
	 */
	@Override
	public GlobalParam selectByObject(GlobalParam globalParam) {
		return selectByPrimaryKey(globalParam.getId());
	}

	/**
	 * @see :检查
	 * @param :
	 * @return : boolean
	 * @return
	 */
	private boolean checkParamKeyExists(String paramKey) {
		List<GlobalParam> currentList = searchGlobalParamsBySearchContent(
				paramKey);

		return !currentList.isEmpty();
	}

	/**
	 * @see :检查
	 * @param :
	 * @return : boolean
	 * @return
	 */
	private boolean checkParamKeyExists(GlobalParam globalParam) {
		List<GlobalParam> currentList = null;
		if (null != globalParam.getId()) {
			currentList = globalParamMapper
					.findOtherGlobalParamsByObject(globalParam);
		} else {
			currentList = searchGlobalParamsBySearchContent(
					globalParam.getParamKey());
		}
		return !currentList.isEmpty();
	}

	// /**
	// * 根据paramKey模糊查询
	// *
	// * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#
	// * searchGlobalParamsByParamKey(java.lang.String)
	// */
	// @Override
	// public List<GlobalParam> searchGlobalParamsByParamKey(
	// String searchContent) {
	// logger.info("根据ParamKey查询记录 {}", searchContent);
	// return globalParamMapper.findGlobalParamsByParamKey(searchContent);
	// }

	/**
	 * 根据paramKey唯一查询
	 * 
	 * @see com.tianque.yunxiao.connect.server.service.GlobalParamService#
	 *      findGlobalParamByParamKey(java.lang.String)
	 */
	@Override
	public GlobalParam findGlobalParamByParamKey(String paramKey) {
		return globalParamMapper.findGlobalParamByParamKey(paramKey);
	}
}
