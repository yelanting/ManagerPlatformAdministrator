/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午8:16:58
 * @see:
 */
package com.administrator.platform.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.administrator.platform.model.CodeCoverage;
import com.administrator.platform.model.TimerTask;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午8:16:58
 * @see :
 */
public interface CodeCoverageService {
	/**
	 * 查询地址列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<CodeCoverage>
	 * @return
	 */
	List<CodeCoverage> findAllCodeCoverageList();

	/**
	 * 添加地址
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 * @return
	 */
	CodeCoverage addCodeCoverage(CodeCoverage codeCoverage);

	/**
	 * 修改地址
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 * @return
	 */
	CodeCoverage updateCodeCoverage(CodeCoverage codeCoverage);

	/**
	 * 根据id查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param id
	 * @return
	 */
	CodeCoverage getCodeCoverageById(Long id);

	/**
	 * 根据对象查询对象
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 * @return
	 */
	CodeCoverage getCodeCoverageByObject(CodeCoverage codeCoverage);

	/**
	 * 根据id删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param id
	 * @return
	 */
	int deleteCodeCoverage(Long id);

	/**
	 * 根据id批量删除
	 * 
	 * @see :
	 * @param :
	 * @return : int
	 * @param ids
	 * @return
	 */
	int deleteCodeCoverage(Long[] ids);

	/**
	 * 根据名称查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<CodeCoverage>
	 * @param searchContent
	 * @return
	 */
	List<CodeCoverage> findCodeCoverageesByProjectName(String searchContent);

	/**
	 * 根据名称模糊查询
	 * 
	 * @see :
	 * @param :
	 * @return : List<CodeCoverage>
	 * @param searchContent
	 * @return
	 */
	List<CodeCoverage> findCodeCoverageesByProjectNameLike(
	        String searchContent);

	/**
	 * 分页查询
	 * 
	 * @see :
	 * @param :
	 * @return : Page<CodeCoverage>
	 * @param page
	 * @param size
	 * @return
	 */
	Page<CodeCoverage> findCodeCoverageByPage(Integer page, Integer size);

	/**
	 * 生成覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	CodeCoverage createAllCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request);

	/**
	 * 生成增量覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 */
	CodeCoverage createIncrementCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request);

	/**
	 * 生成覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	CodeCoverage fastCreateAllCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request);

	/**
	 * 生成增量覆盖率信息
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 */
	CodeCoverage fastCreateIncrementCodeCoverageData(CodeCoverage codeCoverage,
	        HttpServletRequest request);

	/**
	 * 重置覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 */
	CodeCoverage resetCodeCoverageData(CodeCoverage codeCoverage);

	/**
	 * 删除备份的覆盖率数据信息
	 * 
	 * @see :
	 * @param :
	 * @return : CodeCoverage
	 * @param codeCoverage
	 */
	CodeCoverage clearBackUpExecData(CodeCoverage codeCoverage);

	/**
	 * 定时采集，并备份执行数据
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param codeCoverage
	 */
	void timerTaskCollectingExecDataAndBackUp(CodeCoverage codeCoverage);

	/**
	 * 配置定时采集任务
	 * 
	 * @see :
	 * @param :
	 * @return : TimerTask
	 * @param cronConfig
	 * @param codeCoverageId
	 * @return
	 */
	TimerTask configTimerTaskAndChangeStatus(String cronConfig,
	        Long codeCoverageId, boolean enabled);
}
