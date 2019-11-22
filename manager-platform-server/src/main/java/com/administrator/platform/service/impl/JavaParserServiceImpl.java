/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:20
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.ArrayUtil;
import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.JavaParserRecordFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.mapper.JavaParserRecordMapper;
import com.administrator.platform.model.JavaParserRecord;
import com.administrator.platform.service.JavaParserService;
import com.administrator.platform.tools.javaparser.JavaParserConstDefine;
import com.administrator.platform.tools.javaparser.JavaParserUtil;
import com.administrator.platform.tools.javaparser.RequestMappingInfo;
import com.administrator.platform.tools.vcs.common.VCSType;
import com.administrator.platform.tools.vcs.jgit.JgitClient;
import com.administrator.platform.tools.vcs.svn.SvnClientUtil;
import com.administrator.platform.util.JavaParserCommonUtil;
import com.administrator.platform.util.TempFileUtil;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:20
 * @see :
 */
@Service("javaParserService")
public class JavaParserServiceImpl implements JavaParserService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JavaParserServiceImpl.class);

    @Autowired
    private JavaParserRecordMapper javaParserRecordMapper;

    /**
     * @see com.administrator.platform.service.JavaParserRecordService#findAllJavaParserRecordList()
     */
    @Override
    public List<JavaParserRecord> findAllJavaParserRecordList() {
        LOGGER.debug("获取所有java文件解析信息列表");
        List<JavaParserRecord> allJavaParserRecords = javaParserRecordMapper
                .findAll();
        LOGGER.debug("当前列表:{}", allJavaParserRecords);
        return allJavaParserRecords;
    }

    /**
     * 添加代码java文件解析信息实现方法
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#addJavaParserRecord(com.administrator.platform.model.JavaParserRecord)
     */
    @Override
    public JavaParserRecord addJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        ValidationUtil.validateNull(javaParserRecord, null);
        validateInput(javaParserRecord);
        try {
            LOGGER.debug("添加java文件解析信息:{}", javaParserRecord);
            javaParserRecordMapper.insert(javaParserRecord);
            return javaParserRecord;
        } catch (Exception e) {
            LOGGER.error("新增代码java文件解析失败:{},{}", javaParserRecord,
                    e.getMessage());
            throw new BusinessValidationException("新增代码java文件解析失败!");
        }
    }

    /**
     * 根据ID删除的具体实现
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#deleteJavaParserRecord(java.lang.Long)
     */
    @Override
    public int deleteJavaParserRecord(Long id) {
        ValidationUtil.validateNull(id, null);
        try {
            LOGGER.debug("开始删除java文件解析报告,id为:{}", id);
            javaParserRecordMapper.deleteByPrimaryKey(id);
            return 1;
        } catch (Exception e) {
            throw new BusinessValidationException("根据ID删除失败");
        }
    }

    /**
     * 根据ID批量删除
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#deleteJavaParserRecord(java.lang.Long[])
     */
    @Override
    public int deleteJavaParserRecord(Long[] ids) {
        ValidationUtil.validateArrayNullOrEmpty(ids, null);
        try {
            LOGGER.debug("开始批量删除java文件解析报告,id为:{}", ArrayUtil.toString(ids));
            javaParserRecordMapper.deleteByIds(ids);
            return 1;
        } catch (Exception e) {
            LOGGER.error("批量删除失败:{},{}", ids, e.getMessage());
            throw new BusinessValidationException("根据IDS批量删除失败");
        }
    }

    /**
     * 根据名称查询
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<JavaParserRecord> findJavaParserRecordesByProjectName(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        LOGGER.debug("根据名称搜索java文件解析信息数据:{}", searchContent);
        return javaParserRecordMapper
                .findJavaParserRecordesByProjectName(searchContent);
    }

    /**
     * 根据名称模糊查询
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#findEnvAddrrsByProjectName(java.lang.String)
     */
    @Override
    public List<JavaParserRecord> findJavaParserRecordesByProjectNameLike(
            String searchContent) {
        ValidationUtil.validateStringNullOrEmpty(searchContent, null);
        LOGGER.debug("根据名称模糊搜索java文件解析信息数据:{}", searchContent);
        return javaParserRecordMapper
                .findJavaParserRecordesByProjectNameLike(searchContent);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param javaParserRecord
     *            : 待校验的地址对象
     */
    private void validateInput(JavaParserRecord javaParserRecord) {
        validateInputBase(javaParserRecord);
    }

    /**
     * 校验输入内容
     * 
     * @see :
     * @return : void
     * @param javaParserRecord
     *            : 待校验的地址对象
     */
    private void validateInputBase(JavaParserRecord javaParserRecord) {
        // 判空
        ValidationUtil.validateNull(javaParserRecord, null);

        if (!StringUtil.isStringAvaliable(javaParserRecord.getProjectName())) {
            throw new BusinessValidationException("项目名称不能为空");
        }

        ValidationUtil.validateStringNullOrEmpty(
                javaParserRecord.getNewerRemoteUrl(), "新版本代码地址不能为空");

        // 最新代码地址长度
        ValidationUtil.validateStringAndLength(
                javaParserRecord.getNewerRemoteUrl(), null,
                JavaParserRecordFormDefinition.REMOTE_URL_LENGTH, "新版本代码地址");

        ValidationUtil.validateStringAndLength(
                javaParserRecord.getProjectName(), null,
                JavaParserRecordFormDefinition.COMMON_FORM_FIELD_LENGTH,
                "项目名称");

        ValidationUtil.validateStringAndLength(javaParserRecord.getUsername(),
                null, JavaParserRecordFormDefinition.COMMON_FORM_FIELD_LENGTH,
                "版本库访问用户名");

        ValidationUtil.validateStringAndLength(javaParserRecord.getPassword(),
                null, JavaParserRecordFormDefinition.COMMON_FORM_FIELD_LENGTH,
                "版本库访问密码");

        ValidationUtil.validateStringAndLength(
                javaParserRecord.getDescription(), null,
                JavaParserRecordFormDefinition.DESCRIPTION_FIELD_MAX_LENGTH,
                "备注");

    }

    /**
     * 修改代码java文件解析
     * 
     * @param javaParserRecord:地址对象
     * @see com.administrator.platform.service.JavaParserRecordService#updateJavaParserRecord(com.administrator.platform.model.
     *      JavaParserRecord)
     */
    @Override
    public JavaParserRecord updateJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        LOGGER.debug("更新java文件解析信息:{}", javaParserRecord);
        ValidationUtil.validateNull(javaParserRecord, null);
        JavaParserRecord currentJavaParserRecord = getJavaParserRecordByObject(
                javaParserRecord);

        if (null == currentJavaParserRecord) {
            throw new BusinessValidationException("待修改的对象不存在，不能修改");
        }
        javaParserRecord.setId(currentJavaParserRecord.getId());

        // 校验输入内容
        validateInput(javaParserRecord);
        try {
            javaParserRecordMapper.updateByPrimaryKey(javaParserRecord);
            LOGGER.debug("更新java文件解析信息完成:{}", javaParserRecord);
            return javaParserRecord;
        } catch (Exception e) {
            LOGGER.error("更新代码java文件解析失败:{},{}", javaParserRecord,
                    e.getMessage());
            throw new BusinessValidationException("更新代码java文件解析失败!");
        }
    }

    /**
     * 根据ID查询实体
     * 
     * @param Long
     *            id:待查询的id
     * @see com.administrator.platform.service.JavaParserRecordService#getJavaParserRecordById(java.lang.Long)
     */
    @Override
    public JavaParserRecord getJavaParserRecordById(Long id) {
        ValidationUtil.validateNull(id, null);
        LOGGER.debug("根据ID查询详情:{}", id);
        return javaParserRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据实体查询实体是否存在
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#getJavaParserRecordByObject(com.administrator.platform.model.
     *      JavaParserRecord)
     */
    @Override
    public JavaParserRecord getJavaParserRecordByObject(
            JavaParserRecord javaParserRecord) {
        ValidationUtil.validateNull(javaParserRecord, null);
        LOGGER.debug("根据内容查询详情:{}", javaParserRecord);
        return getJavaParserRecordById(javaParserRecord.getId());
    }

    /**
     * 分页查询
     * 
     * @return
     * @see com.administrator.platform.service.JavaParserRecordService#findJavaParserRecordByPage(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public Page<JavaParserRecord> findJavaParserRecordByPage(Integer page,
            Integer size) {
        ValidationUtil.validateNull(page, null);
        ValidationUtil.validateNull(size, null);
        Pageable pageable = PageRequest.of(page - 1, size);

        LOGGER.debug("分页查询java文件解析数据:第{}页,每页{}条", page, size);
        return javaParserRecordMapper.findAll(pageable);
    }

    /**
     * 重置代码java文件解析数据
     * 
     * @see com.administrator.platform.service.JavaParserRecordService#resetJavaParserRecordData(com.administrator.platform.model.JavaParserRecord)
     */
    @Override
    public JavaParserRecord resetJavaParserRecordData(
            JavaParserRecord javaParserRecord) {
        LOGGER.debug("开始重置java文件解析数据，通过更新java文件解析信息的url来操作");
        javaParserRecord.setResultUrl(null);
        deleteRelationFiles(javaParserRecord);
        return updateJavaParserRecord(javaParserRecord);
    }

    /**
     * 预处理代码
     * 
     * @see :
     * @param :
     * @return : void
     * @param javaParserRecord
     */
    private void prepareNewProjectData(JavaParserRecord javaParserRecord) {
        prepareNewProjectDataWithVersionControlSystem(javaParserRecord);
    }

    /**
     * 下载代码
     * 
     * @see :
     * @param :
     * @return : void
     * @param javaParserRecord
     */
    private void prepareNewProjectDataWithVersionControlSystem(
            JavaParserRecord javaParserRecord) {

        /**
         * 下代码
         */
        LOGGER.debug("开始处理代码,因为没有上传源码和class文件，需要自己下载");
        VCSType vcsType = javaParserRecord.getVersionControlType();
        File newerFileFolder = JavaParserCommonUtil
                .getNewerFolderFileFromJavaParserRecord(javaParserRecord);
        SvnClientUtil svnClientUtilNewer = null;
        if (vcsType == VCSType.GIT) {
            JgitClient jgitClient = JgitClient.fromUsernameAndPassword(
                    javaParserRecord.getUsername(),
                    javaParserRecord.getPassword());
            jgitClient.gitCloneAndGitPull(javaParserRecord.getNewerRemoteUrl(),
                    newerFileFolder, javaParserRecord.getNewerVersion());
        } else if (vcsType == VCSType.SVN) {
            svnClientUtilNewer = new SvnClientUtil(
                    javaParserRecord.getNewerRemoteUrl(),
                    javaParserRecord.getUsername(),
                    javaParserRecord.getPassword());
            svnClientUtilNewer.checkoutDefault(
                    javaParserRecord.getNewerRemoteUrl(),
                    newerFileFolder.getAbsolutePath(),
                    javaParserRecord.getNewerVersion());
        } else {
            throw new BusinessValidationException("版本控制系统不在svn和git之中，需要特殊处理哦！");
        }
    }

    /**
     * 创建映射文件内容
     */
    @Override
    public JavaParserRecord createRequestMappingSource(
            HttpServletRequest request, JavaParserRecord javaParserRecord) {
        prepareNewProjectData(javaParserRecord);
        deleteRelationFiles(javaParserRecord);

        File file = JavaParserCommonUtil
                .getNewerFolderFileFromJavaParserRecord(javaParserRecord);

        if (!file.exists()) {
            throw new BusinessValidationException("代码下载失败，找不到下载后的文件夹");
        }

        String resultFolder = JavaParserCommonUtil
                .getNewerFolderResultPathFromJavaParserRecord(javaParserRecord);

        JavaParserUtil javaParserUtil = new JavaParserUtil();
        List<RequestMappingInfo> requestMappingInfos = javaParserUtil
                .parseRequestMappingFromFolder(file.getAbsolutePath());

        File toWriteFile = new File(resultFolder,
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_FILE);

        File toWriteFileExcel = new File(resultFolder,
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_FILE_EXCEL);

        javaParserUtil.writeRequestMappingToExcel(toWriteFileExcel,
                requestMappingInfos);
        List<String> fullMappings = new ArrayList<>();

        for (RequestMappingInfo eachMappingInfo : requestMappingInfos) {
            fullMappings.add(eachMappingInfo.getFullMapping());
        }

        Collections.sort(fullMappings);

        FileUtil.writeListToFile(toWriteFile, fullMappings);
        LOGGER.debug("解析文件结果:{}", toWriteFile);

        String finalUrl = TempFileUtil
                .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
                        request, toWriteFile.getAbsolutePath());
        String finalUrlExcel = TempFileUtil
                .getWebApplicationAccessUrlFromHttpRequestAndLocalAbsolutePath(
                        request, toWriteFileExcel.getAbsolutePath());

        if (!toWriteFile.exists()) {
            LOGGER.error("文件不存在:{}", toWriteFile.getAbsolutePath());
            javaParserRecord.setResultUrl(null);
            updateJavaParserRecord(javaParserRecord);
            throw new BusinessValidationException("文件写入异常，不存在！");
        }

        if (!toWriteFileExcel.exists()) {
            LOGGER.error("文件不存在:{}", toWriteFileExcel.getAbsolutePath());
            javaParserRecord.setResultUrl(null);
            updateJavaParserRecord(javaParserRecord);
            throw new BusinessValidationException("文件写入异常，不存在！");
        }

        LOGGER.debug("可访问的文件路径为:{}", finalUrl);
        LOGGER.debug("可访问Excel的文件路径为:{}", finalUrlExcel);
        javaParserRecord.setResultUrl(finalUrlExcel);
        return updateJavaParserRecord(javaParserRecord);
    }

    /**
     * 删除相关文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param javaParserRecordDTO
     */
    private void deleteRelationFiles(JavaParserRecord javaParserRecord) {
        File file = JavaParserCommonUtil
                .getNewerFolderFileFromJavaParserRecord(javaParserRecord);
        if (!file.exists()) {
            throw new BusinessValidationException("代码下载失败，找不到下载后的文件夹");
        }

        String resultFolder = JavaParserCommonUtil
                .getNewerFolderResultPathFromJavaParserRecord(javaParserRecord);

        File toWriteFile = new File(resultFolder,
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_FILE);

        File toWriteFileExcel = new File(resultFolder,
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_FILE_EXCEL);

        if (toWriteFile.exists()) {
            FileUtil.forceDeleteDirectory(toWriteFile);
        }
        if (toWriteFileExcel.exists()) {
            FileUtil.forceDeleteDirectory(toWriteFileExcel);
        }
    }
}
