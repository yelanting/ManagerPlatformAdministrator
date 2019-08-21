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
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.TimerTaskMonitorFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.TimerTaskMonitorMapper;
import com.administrator.platform.model.TimerTaskMonitor;
import com.administrator.platform.service.TimerTaskMonitorService;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:17:27
 * @see :
 */
@Service
@Order(1)
public class TimerTaskMonitorServiceImpl implements TimerTaskMonitorService {
	private static final Logger logger = LoggerFactory
	        .getLogger(TimerTaskMonitorServiceImpl.class);
	@Autowired
	private TimerTaskMonitorMapper timerTaskMonitorMapper;

	/**
	 * @see com.administrator.platform.service.TimerTaskMonitorService#findAllTimerTaskMonitorList()
	 */
	@Override
	public List<TimerTaskMonitor> findAllTimerTaskMonitorList() {
		return timerTaskMonitorMapper.findAllJobTimerTaskMonitorList();
	}

	/**
	 * 
	 * 添加测试环境地址信息实现方法
	 * 
	 * @see
	 *      com.administrator.platform.service.TimerTaskMonitorService#addTimerTaskMonitor(com.administrator.platform.model.TimerTaskMonitor)
	 */
	@Override
	public TimerTaskMonitor addTimerTaskMonitor(
	        TimerTaskMonitor timerTaskMonitor) {
		ValidationUtil.validateNull(timerTaskMonitor, null);
		validateInput(timerTaskMonitor);
		try {
			logger.debug("新增定时任务监控:{}", timerTaskMonitor);
			timerTaskMonitorMapper.insert(timerTaskMonitor);
			logger.debug("新增成功,返回的信息：{}", timerTaskMonitor);
			return timerTaskMonitor;
		} catch (Exception e) {
			logger.error("添加定时任务监控失败:{},{}", timerTaskMonitor, e.getMessage());
			throw new BusinessValidationException("添加定时任务监控失败！！！");
		}
	}

	/**
	 * 根据ID删除的具体实现
	 * 
	 * 
	 * @see com.administrator.platform.service.TimerTaskMonitorService#deleteTimerTaskMonitor(java.lang.Long)
	 */
	@Override
	public int deleteTimerTaskMonitor(Long id) {
		ValidationUtil.validateNull(id, null);
		try {
			timerTaskMonitorMapper.deleteByPrimaryKey(id);
			return 1;
		} catch (Exception e) {
			logger.error("根据定时任务监控id删除定时任务监控失败:{},{}", id, e.getMessage());
			throw new BusinessValidationException(
			        "根据定时任务监控id删除定时任务监控失败：" + e.getMessage());
		}
	}

	/**
	 * 根据ID批量删除
	 * 
	 * @see com.administrator.platform.service.TimerTaskMonitorService#deleteTimerTaskMonitor(java.lang.Long[])
	 */
	@Override
	public int deleteTimerTaskMonitor(Long[] ids) {
		ValidationUtil.validateArrayNullOrEmpty(ids, null);
		try {
			timerTaskMonitorMapper.deleteByIds(ids);
			return 1;
		} catch (Exception e) {
			throw new BusinessValidationException(
			        "根据ids批量删除定时任务监控失败" + e.getMessage());
		}
	}

	/**
	 * 校验输入内容
	 * 
	 * @see :
	 * @return : void
	 * @param timerTaskMonitor
	 *            : 待校验的地址对象
	 */
	private void validateInput(TimerTaskMonitor timerTaskMonitor) {
		// 判空
		ValidationUtil.validateNull(timerTaskMonitor, null);

		if (!StringUtil.isStringAvaliable(timerTaskMonitor.getJobName())) {
			throw new BusinessValidationException("定时任务监控名称不能为空！");
		}

		// 验证定时任务监控名称长度
		ValidationUtil.validateStringAndLength(timerTaskMonitor.getJobName(),
		        null, TimerTaskMonitorFormDefinition.GOOD_NAME_LENGTH, "项目名称");

		// 验证定时任务监控描述

		ValidationUtil.validateStringAndLength(
		        timerTaskMonitor.getDescription(), null,
		        TimerTaskMonitorFormDefinition.GOOD_DESC_FIELD_MAX_LENGTH,
		        "定时任务监控描述");
	}

	/**
	 * 修改测试环境地址
	 * 
	 * @param timerTask:地址对象
	 * @see com.administrator.platform.service.TimerTaskMonitorService#updateTimerTaskMonitor(com.administrator.platform.model.
	 *      TimerTaskMonitor)
	 */
	@Override
	public TimerTaskMonitor updateTimerTaskMonitor(
	        TimerTaskMonitor timerTaskMonitor) {
		ValidationUtil.validateNull(timerTaskMonitor, null);

		TimerTaskMonitor currentTimerTaskMonitor = getTimerTaskMonitorByObject(
		        timerTaskMonitor);

		if (null == currentTimerTaskMonitor) {
			throw new BusinessValidationException("待修改的对象不存在，不能修改");
		}
		timerTaskMonitor.setId(currentTimerTaskMonitor.getId());

		// 校验输入内容
		validateInput(timerTaskMonitor);
		try {
			timerTaskMonitorMapper.updateByPrimaryKey(timerTaskMonitor);
			return timerTaskMonitor;
		} catch (Exception e) {
			logger.error("更新定时任务监控失败：{},{}", timerTaskMonitor, e.getMessage());
			throw new BusinessValidationException("更新定时任务监控信息失败!!!");
		}
	}

	/**
	 * 根据实体查询实体是否存在
	 * 
	 * @see com.administrator.platform.service.TimerTaskMonitorService#getTimerTaskMonitorByObject(com.administrator.platform.model.
	 *      TimerTaskMonitor)
	 */
	@Override
	public TimerTaskMonitor getTimerTaskMonitorByObject(
	        TimerTaskMonitor timerTaskMonitor) {
		ValidationUtil.validateNull(timerTaskMonitor, null);
		return getTimerTaskMonitorById(timerTaskMonitor.getId());
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * 
	 * @see com.administrator.platform.service.TimerTaskMonitorService#findTimerTaskMonitorByPage(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Page<TimerTaskMonitor> findTimerTaskMonitorByPage(Integer page,
	        Integer size) {
		ValidationUtil.validateNull(page, null);
		ValidationUtil.validateNull(size, null);
		Pageable pageable = PageRequest.of(page - 1, size);
		return timerTaskMonitorMapper.findJobTimerTaskMonitorByPage(pageable);
	}

	@Override
	public TimerTaskMonitor getTimerTaskMonitorById(Long id) {
		return timerTaskMonitorMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TimerTaskMonitor> findTimerTaskMonitorsByTimerTaskName(
	        String searchContent) {
		return timerTaskMonitorMapper
		        .findJobTimerTaskMonitorListByName(searchContent);
	}

	@Override
	public long[] deleteAllTimerTaskMonitors() {
		List<TimerTaskMonitor> allTimerTaskMonitors = findAllTimerTaskMonitorList();

		long[] deletedIds = new long[allTimerTaskMonitors.size()];

		for (int i = 0; i < allTimerTaskMonitors.size(); i++) {
			deletedIds[i] = allTimerTaskMonitors.get(i).getId();
		}
		timerTaskMonitorMapper.deleteAllTimerTaskMonitors();
		return deletedIds;
	}

}
