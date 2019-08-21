/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:17:27
 * @see:
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.constdefine.TimerTaskAndRelations;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.TimerTaskPolicyFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.TimerTaskPolicyMapper;
import com.administrator.platform.model.TimerTaskPolicy;
import com.administrator.platform.service.TimerTaskPolicyService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
public class TimerTaskPolicyServiceImpl implements TimerTaskPolicyService {
	private static final Logger logger = LoggerFactory
	        .getLogger(TimerTaskPolicyServiceImpl.class);
	@Autowired
	private TimerTaskPolicyMapper timerTaskPolicyMapper;

	/**
	 * @see com.administrator.platform.service.GoodService#findAllGoodList()
	 */
	@Override
	public List<TimerTaskPolicy> findAllTimerTaskPolicyList() {
		return timerTaskPolicyMapper.findAll();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see
	 *      com.administrator.platform.service.GoodService#addGood(com.administrator.platform.model.Good)
	 */
	@Override
	public TimerTaskPolicy addTimerTaskPolicy(TimerTaskPolicy timerTaskPolicy) {
		ValidationUtil.validateNull(timerTaskPolicy, null);
		validateInput(timerTaskPolicy);

		if (!findTimerTaskPoliciesByCname(timerTaskPolicy.getCname())
		        .isEmpty()) {
			throw new BusinessValidationException("此中文名策略已经存在");
		}

		if (!findTimerTaskPoliciesByEname(timerTaskPolicy.getEname())
		        .isEmpty()) {
			throw new BusinessValidationException("此英文名策略已经存在");
		}

		try {
			timerTaskPolicyMapper.insert(timerTaskPolicy);
			return timerTaskPolicy;
		} catch (Exception e) {
			logger.error("添加策略失败:{},{}", timerTaskPolicy, e.getMessage());
			throw new BusinessValidationException("添加策略失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long)
	 */
	@Override
	public int deleteTimerTaskPolicy(Long id) {

		ValidationUtil.validateNull(id, null);
		TimerTaskPolicy timerTaskPolicy = timerTaskPolicyMapper
		        .selectByPrimaryKey(id);

		if (null == timerTaskPolicy) {
			throw new BusinessValidationException("id为" + id + "的策略不存在！");
		}

		if (TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_ENAME
		        .equals(timerTaskPolicy.getEname())) {
			throw new BusinessValidationException("这个默认采集覆盖率数据的策略不可删除");
		}
		try {
			timerTaskPolicyMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException("删除策略失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.GoodService#deleteGood(java.lang.Long[])
	 */
	@Override
	public int deleteTimerTaskPolicy(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);

		Long[] realDelteIds = findRecordsThatCanBeDeleted(ids);

		if (realDelteIds.length == 0) {
			throw new BusinessValidationException("选择的列表中除了默认不能删的策略，没有可以删除的");
		}
		try {
			timerTaskPolicyMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException("批量删除策略失败：" + e.getMessage());
		}
	}

	/**
	 * 从要删除的列表中获取可以删除的
	 * 
	 * @see :
	 * @param :
	 * @return : Long[]
	 * @param toDeleteIds
	 * @return
	 */
	private Long[] findRecordsThatCanBeDeleted(Long[] toDeleteIds) {
		List<TimerTaskPolicy> currentList = timerTaskPolicyMapper
		        .selectByIds(toDeleteIds);
		List<TimerTaskPolicy> realDeleteList = new ArrayList<TimerTaskPolicy>();
		Long[] realDeleteIds = null;

		boolean isDefault = false;
		for (TimerTaskPolicy timerTaskPolicy : currentList) {
			isDefault = TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_CNAME
			        .equals(timerTaskPolicy.getCname())
			        && (TimerTaskAndRelations.COLLECTIONG_EXEC_DATA_AND_BACK_UP_POLICY_ENAME
			                .equals(timerTaskPolicy.getEname()));

			if (!isDefault) {
				realDeleteList.add(timerTaskPolicy);
			}
		}

		realDeleteIds = new Long[realDeleteList.size()];
		realDeleteList.toArray(realDeleteIds);
		return realDeleteIds;
	}

	/**
	 * 根据名称查询
	 * 
	 * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
	 */
	@Override
	public List<TimerTaskPolicy> findTimerTaskPoliciesByCname(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return timerTaskPolicyMapper
		        .findTimerTaskPoliciesByCname(searchContent);
	}

	/**
	 * 根据名称模糊查询
	 * 
	 * @see com.administrator.platform.service.GoodService#findEnvAddrrsByProjectName(java.lang.String)
	 */
	@Override
	public List<TimerTaskPolicy> findTimerTaskPoliciesByCnameLike(
	        String searchContent) {
		ValidationUtil.validateStringNullOrEmpty(searchContent, null);
		return timerTaskPolicyMapper
		        .findTimerTaskPoliciesByCnameLike(searchContent);
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param envAddress
	 *            : 待校验的地址对象
	 */
	private void validateInput(TimerTaskPolicy timerTaskPolicy) {
		// 判空
		ValidationUtil.validateNull(timerTaskPolicy, null);

		if (!StringUtil.isStringAvaliable(timerTaskPolicy.getCname())) {
			throw new BusinessValidationException("中文名称不能为空");
		}

		ValidationUtil.validateStringAndLength(timerTaskPolicy.getCname(), null,
		        TimerTaskPolicyFormDefinition.C_NAME_LENGTH, "策略中文名称");

		ValidationUtil.validateStringAndLength(timerTaskPolicy.getEname(), 0,
		        TimerTaskPolicyFormDefinition.E_NAME_LENGTH, "策略英文名称");
		ValidationUtil.validateStringAndLength(timerTaskPolicy.getCode(), 0,
		        TimerTaskPolicyFormDefinition.CODE_DESC_MAX_LENGTH, "策略待执行代码");

		ValidationUtil.validateStringAndLength(timerTaskPolicy.getDescription(),
		        null, TimerTaskPolicyFormDefinition.GOODTYPE_DESC_MAX_LENGTH,
		        "物品类型描述");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param envAddress:地址对象
	 * @see com.administrator.platform.service.GoodService#updateGood(com.administrator.platform.model.
	 *      Good)
	 */
	@Override
	public TimerTaskPolicy updateTimerTaskPolicy(
	        TimerTaskPolicy timerTaskPolicy) {
		ValidationUtil.validateNull(timerTaskPolicy, null);

		TimerTaskPolicy currentTimerTaskPolicy = getTimerTaskPolicyByObject(
		        timerTaskPolicy);

		if (null == currentTimerTaskPolicy) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		timerTaskPolicy.setId(currentTimerTaskPolicy.getId());

		// 校验输入内容
		validateInput(timerTaskPolicy);

		if (findWithEnameExceptThisId(timerTaskPolicy.getEname(),
		        timerTaskPolicy.getId())) {
			throw new BusinessValidationException("此英文名策略已经存在");
		}

		if (findWithCnameExceptThisId(timerTaskPolicy.getCname(),
		        timerTaskPolicy.getId())) {
			throw new BusinessValidationException("此中文名策略已经存在");
		}

		try {
			timerTaskPolicyMapper.updateByPrimaryKey(timerTaskPolicy);
			return timerTaskPolicy;
		} catch (Exception e) {
			logger.error("更新策略失败:{},{}", timerTaskPolicy, e.getMessage());
			throw new BusinessValidationException("更新策略失败！！！");
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
	public TimerTaskPolicy getTimerTaskPolicyById(Long id) {
		ValidationUtil.validateNull(id, null);
		return timerTaskPolicyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.GoodService#getGoodByObject(com.administrator.platform.model.
	 *      Good)
	 */
	@Override
	public TimerTaskPolicy getTimerTaskPolicyByObject(
	        TimerTaskPolicy timerTaskPolicy) {
		ValidationUtil.validateNull(timerTaskPolicy, null);
		return getTimerTaskPolicyById(timerTaskPolicy.getId());
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
	public Page<TimerTaskPolicy> findTimerTaskPolicyByPage(Integer page,
	        Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return timerTaskPolicyMapper.findAll(pageable);
	}

	/**
	 * 查找非当前id的同名记录
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param cname
	 * @param id
	 * @return
	 */
	private boolean findWithEnameExceptThisId(String ename, Long id) {
		List<TimerTaskPolicy> searchList = timerTaskPolicyMapper
		        .findWithEnameExceptThisId(ename, id);
		return null != searchList && searchList.isEmpty();
	}

	/**
	 * 查找非当前id的同名记录
	 * 
	 * @see :
	 * @param :
	 * @return : boolean
	 * @param cname
	 * @param id
	 * @return
	 */
	private boolean findWithCnameExceptThisId(String cname, Long id) {
		List<TimerTaskPolicy> searchList = timerTaskPolicyMapper
		        .findWithCnameExceptThisId(cname, id);
		return null != searchList && searchList.isEmpty();
	}

	@Override
	public List<TimerTaskPolicy> findTimerTaskPoliciesByEname(
	        String searchContent) {
		return timerTaskPolicyMapper
		        .findTimerTaskPoliciesByEname(searchContent);
	}
}
