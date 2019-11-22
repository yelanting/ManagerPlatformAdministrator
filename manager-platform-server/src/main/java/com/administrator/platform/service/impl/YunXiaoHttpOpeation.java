/**
 * Project Name: manager-platform-server
 * File Name: YunXiaoHttpUtil.java
 * Package Name: com.administrator.platform.helper.yunxiao
 * Date: 2019年10月8日 下午2:14:19
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.administrator.platform.constdefine.YunXiaoDefine;
import com.administrator.platform.core.base.util.JsonUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.helper.yunxiao.RestTemplateFactory;
import com.administrator.platform.helper.yunxiao.YunXiaoGlobal;
import com.administrator.platform.helper.yunxiao.YunXiaoLoginHelper;
import com.administrator.platform.model.yunxiao.ConfigManage;
import com.administrator.platform.model.yunxiao.PipeLine;
import com.administrator.platform.model.yunxiao.YunXiaoPipeLineProject;
import com.administrator.platform.model.yunxiao.YunXiaoPipeLineSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author : 孙留平
 * @since : 2019年10月8日 下午2:14:19
 * @see : 云效http请求操作
 */
@Service("yunXiaoHttpOpeation")
public class YunXiaoHttpOpeation {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(YunXiaoHttpOpeation.class);

	/**
	 * @see :
	 */
	public YunXiaoHttpOpeation() {
		new YunXiaoLoginHelper();
	}

	/**
	 * @see :
	 */
	public YunXiaoHttpOpeation(String username, String password) {
		new YunXiaoLoginHelper(username, password);
	}

	public ResponseEntity<String> post(String url,
	        MultiValueMap<String, String> params) {
		LOGGER.debug("发送post请求，请求url:{},请求内容:{}", url, params);
		return client(url, HttpMethod.POST, params);
	}

	public ResponseEntity<String> get(String url,
	        MultiValueMap<String, String> params) {
		LOGGER.debug("发送get请求，请求url:{},请求内容:{}", url, params);
		return client(url, HttpMethod.GET, params);
	}

	public ResponseEntity<String> get(String url) {
		LOGGER.debug("发送get请求，请求url:{}", url);
		return client(url, HttpMethod.GET, null);
	}

	/**
	 * 获取所有的流水线列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<PipeLine>
	 * @return
	 */
	public List<PipeLine> getPipeLineList() {
		LOGGER.debug("获取pipeLine列表请求");
		List<PipeLine> pileLineList = getAllYunXiaoPipeLines(
		        getAllYunXiaoPipeLineSets(getAllYunXiaoPipeLineProjects()));
		return pileLineList;
	}

	/**
	 * 获取所有的流水线列表
	 * 
	 * @see :
	 * @param :
	 * @return : List<PipeLine>
	 * @return
	 */
	public List<ConfigManage> getConfigManageList() {
		LOGGER.debug("获取ConfigManage列表请求");
		List<ConfigManage> configManageList = getAllYunXiaoConfigManageList();
		return configManageList;
	}

	/**
	 * 先获取所有的云效流水线项目
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineProject>
	 * @return
	 */
	public List<YunXiaoPipeLineProject> getAllYunXiaoPipeLineProjects() {
		LOGGER.debug("获取pipeLine列表请求");
		ResponseEntity<String> responseEntity = client(
		        YunXiaoDefine.YUNXIAO_ACTION_GET_PIPELINE_LIST, HttpMethod.GET,
		        null);

		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());

		JSONArray jsonArray = jsonObject.getJSONArray("data");

		List<YunXiaoPipeLineProject> pileLineList = new ArrayList<YunXiaoPipeLineProject>();

		for (int i = 0; i < jsonArray.size(); i++) {
			pileLineList.add((YunXiaoPipeLineProject) JSONObject.toBean(
			        jsonArray.getJSONObject(i), YunXiaoPipeLineProject.class));
		}
		return pileLineList;
	}

	/**
	 * 获取流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param pipeLineProjects
	 * @return
	 */
	public List<YunXiaoPipeLineSet> getAllYunXiaoPipeLineSets(
	        List<YunXiaoPipeLineProject> pipeLineProjects) {

		List<YunXiaoPipeLineSet> pipeLineSets = new ArrayList<>();
		for (YunXiaoPipeLineProject yunXiaoPipeLineProject : pipeLineProjects) {
			YunXiaoPipeLineSet yunXiaoPipeLineSet = getYunXiaoPipeLineSetByProject(
			        yunXiaoPipeLineProject);

			if (null != yunXiaoPipeLineSet) {
				pipeLineSets.add(yunXiaoPipeLineSet);
			}
		}

		return pipeLineSets;
	}

	/**
	 * 根据项目id获取该项目下的流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param projectId
	 * @return
	 */
	public YunXiaoPipeLineSet getYunXiaoPipeLineSetByProjectId(Long projectId) {
		if (null == projectId) {
			return null;
		}

		String url = YunXiaoDefine.YUNXIAO_ACTION_GET_PIPELINE_SETS + projectId;
		ResponseEntity<String> responseEntity = get(url);

		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());
		JSONObject pipeSetObject = jsonObject.getJSONObject("data");

		if (null == pipeSetObject
		        || "null".equalsIgnoreCase(pipeSetObject.toString())) {
			LOGGER.debug("项目:{}没有流水线组", projectId);
			return null;
		}

		YunXiaoPipeLineSet pipeLineSet = (YunXiaoPipeLineSet) JSONObject
		        .toBean(pipeSetObject, YunXiaoPipeLineSet.class);

		LOGGER.debug("获取到项目:{}下的流水线组为:{}", projectId, pipeLineSet);
		return pipeLineSet;
	}

	/**
	 * 根据项目id获取该项目下的流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param projectId
	 * @return
	 */
	public YunXiaoPipeLineSet getYunXiaoPipeLineSetByProject(
	        YunXiaoPipeLineProject yunXiaoPipeLineProject) {
		if (null == yunXiaoPipeLineProject) {
			return null;
		}

		YunXiaoPipeLineSet yunXiaoPipeLineSet = getYunXiaoPipeLineSetByProjectId(
		        yunXiaoPipeLineProject.getId());

		if (null == yunXiaoPipeLineSet) {
			return null;
		}

		if (StringUtil.isEmpty(yunXiaoPipeLineSet.getProjectName())) {
			yunXiaoPipeLineSet.setProjectName(yunXiaoPipeLineProject.getName());
		}

		return yunXiaoPipeLineSet;
	}

	/**
	 * 根据项目id获取该项目下的流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param projectId
	 * @return
	 */
	public List<PipeLine> getYunXiaoPipeLinesByPipeLineSetId(
	        Long pipeLineSetId) {
		if (null == pipeLineSetId) {
			return new ArrayList<>();
		}

		String url = YunXiaoDefine.YUNXIAO_ACTION_GET_PIPELINES_BY_SET_ID
		        + pipeLineSetId;
		ResponseEntity<String> responseEntity = get(url);

		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());
		JSONArray pipeLineArray = jsonObject.getJSONArray("data");

		List<PipeLine> pipeLinesUnderPipeLineSet = new ArrayList<>();
		if (pipeLineArray.isEmpty()) {
			LOGGER.debug("流水线组:{}下没有流水线", pipeLineSetId);
			return pipeLinesUnderPipeLineSet;
		}

		for (int i = 0; i < pipeLineArray.size(); i++) {
			PipeLine eachPipeLine = JsonUtil.jsonToObject(
			        pipeLineArray.getJSONObject(i).toString(), PipeLine.class);
			pipeLinesUnderPipeLineSet.add(eachPipeLine);
		}

		LOGGER.debug("获取到流水线组:{}下的流水线信息为:{}", pipeLineSetId,
		        pipeLinesUnderPipeLineSet);
		return pipeLinesUnderPipeLineSet;
	}

	/**
	 * 根据项目id获取该项目下的流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param projectId
	 * @return
	 */
	public List<PipeLine> getYunXiaoPipeLinesByPipeLineSet(
	        YunXiaoPipeLineSet pipeLineSet) {
		if (null == pipeLineSet) {
			return new ArrayList<>();
		}

		List<PipeLine> pipeLinesUnderSet = getYunXiaoPipeLinesByPipeLineSetId(
		        pipeLineSet.getId());

		for (PipeLine pipeLine : pipeLinesUnderSet) {
			if (StringUtil.isEmpty(pipeLine.getPipelineSetName())) {
				pipeLine.setPipelineSetName(pipeLineSet.getPipelineSetName());
			}

			if (StringUtil.isEmpty(pipeLine.getProjectName())) {
				pipeLine.setProjectName(pipeLineSet.getProjectName());
			}
		}

		return pipeLinesUnderSet;
	}

	/**
	 * 获取流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param pipeLineSets
	 * @return
	 */
	public List<PipeLine> getAllYunXiaoPipeLines(
	        List<YunXiaoPipeLineSet> pipeLineSets) {
		List<PipeLine> pipeLines = new ArrayList<>();

		for (YunXiaoPipeLineSet yunXiaoPipeLineSet : pipeLineSets) {
			List<PipeLine> yunXiaoPipeLinesUnderSet = getYunXiaoPipeLinesByPipeLineSet(
			        yunXiaoPipeLineSet);
			if (!yunXiaoPipeLinesUnderSet.isEmpty()) {
				pipeLines.addAll(yunXiaoPipeLinesUnderSet);
			}
		}
		return pipeLines;
	}

	/**
	 * 获取流水线组
	 * 
	 * @see :
	 * @param :
	 * @return : List<YunXiaoPipeLineSet>
	 * @param pipeLineSets
	 * @return
	 */
	public List<ConfigManage> getAllYunXiaoConfigManageList() {

		MultiValueMap<String, String> getListParaMap = new LinkedMultiValueMap<>();
		getListParaMap.add("onlySelf", "true");
		getListParaMap.add("projectName", null);
		getListParaMap.add("projectNotStatus", "true");
		getListParaMap.add("projectStatus", "all");
		getListParaMap.add("projectType", "all");
		ResponseEntity<String> getConfigManageListEntity = client(
		        YunXiaoDefine.YUNXIAO_ACTION_CONFIG_MANAGE_GET_LIST,
		        HttpMethod.GET, getListParaMap);

		JSONArray configManageList = JSONArray
		        .fromObject(getConfigManageListEntity.getBody());

		LOGGER.debug("configManageList:{}", configManageList);

		List<ConfigManage> configManageListFinaList = new ArrayList<>();
		for (int i = 0; i < configManageList.size(); i++) {
			ConfigManage configManage = JsonUtil.jsonToObject(
			        configManageList.getJSONObject(i).toString(),
			        ConfigManage.class);

			if (null != configManage) {
				configManageListFinaList.add(configManage);
			}
		}
		return configManageListFinaList;
	}

	/**
	 * 构建流水线
	 * 
	 * @see :
	 * @param :
	 * @return : PipeLine
	 * @param pipeLine
	 * @return
	 */
	public PipeLine buildPipeLine(PipeLine pipeLine) {
		LOGGER.debug("获取pipeLine列表请求");

		ResponseEntity<String> responseEntity = client(
		        YunXiaoDefine.YUNXIAO_ACTION_RUN_PIPELINE_BY_ID
		                + pipeLine.getId(),
		        HttpMethod.POST, null);

		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());

		if ("success".equalsIgnoreCase(jsonObject.getString("message"))) {

			return pipeLine;
		}
		throw new BusinessValidationException("构建流水线失败！！！");
	}

	/**
	 * 构建流水线
	 * 
	 * @see :
	 * @param :
	 * @return : PipeLine
	 * @param pipeLine
	 * @return
	 */
	public ConfigManage buildConfigManage(ConfigManage configManage) {
		LOGGER.debug("构建configManage:{}", configManage);
		ResponseEntity<String> responseEntity = client(
		        YunXiaoDefine.YUNXIAO_ACTION_RUN_CONFIG_MANAGE_PIPELINE_BY_ID
		                + configManage.getId(),
		        HttpMethod.POST, null);

		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());

		if ("success".equalsIgnoreCase(jsonObject.getString("message"))) {
			return configManage;
		}

		throw new BusinessValidationException("构建配置管理流水线失败！！！");
	}

	/**
	 * 
	 * @see : 处理Url
	 * @param :
	 * @return : String
	 * @param pathUrl
	 * @return
	 */
	private String dealWithUrl(String pathUrl) {
		if (StringUtil.isEmpty(pathUrl)) {
			return YunXiaoDefine.YUNXIAO_BASE_HTTP_URL;
		}

		if (pathUrl.startsWith("/")) {
			pathUrl = pathUrl.substring(1);
		}

		return YunXiaoDefine.YUNXIAO_BASE_HTTP_URL + pathUrl;
	}

	private ResponseEntity<String> client(String url, HttpMethod method,
	        MultiValueMap<String, String> params) {
		LOGGER.debug("发送请求，请求url:{},请求方式:{},请求内容:{}", url, method, params);

		YunXiaoGlobal.httpHeaders
		        .setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
		        params, YunXiaoGlobal.httpHeaders);

		System.out.println(YunXiaoGlobal.httpHeaders.get(HttpHeaders.COOKIE));

		String finalUrl = dealWithUrl(url);
		LOGGER.debug("请求URL：{}", finalUrl);
		LOGGER.debug("请求参数:{},requestEntity:{}", params, requestEntity);
		ResponseEntity<String> responseEntity = RestTemplateFactory
		        .getRestTemplate()
		        .exchange(finalUrl, method, requestEntity, String.class);
		LOGGER.debug("请求结果:{}", responseEntity.getBody());
		return responseEntity;
	}

	public static void main(String[] args) {
		// String url = "/api-torrent/api/v1/aggregate/list/PROJECT?related=true&unClosed=true";
		String url = "/api-torrent/api/v1/pipelineSets/project/1000097";
		new YunXiaoHttpOpeation().get(url, null);
	}
}
