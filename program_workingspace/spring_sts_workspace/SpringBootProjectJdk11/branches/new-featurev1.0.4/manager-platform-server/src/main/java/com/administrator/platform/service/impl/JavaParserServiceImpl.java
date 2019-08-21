/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:20
 * @see:
 */
package com.administrator.platform.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.definition.form.JavaParserFormDefinition;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.service.JavaParserService;
import com.administrator.platform.tools.javaparser.JavaParserGlobal;
import com.administrator.platform.tools.javaparser.JavaParserResult;
import com.administrator.platform.tools.javaparser.JavaParserUtil;
import com.administrator.platform.util.define.FileSuffix;
import com.administrator.platform.vo.JavaParserDTO;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:20
 * @see :
 */
@Service
public class JavaParserServiceImpl implements JavaParserService {
    private Logger logger = LoggerFactory
            .getLogger(JavaParserServiceImpl.class);

    /**
     * 
     * @see
     *      com.administrator.platform.service.JavaParserService#parseJavaFiles(com.administrator.platform.vo.JavaParserDTO)
     */
    @Override
    public String parseJavaFiles(JavaParserDTO javaParserDTO) {
        /**
         * @see : 校验输入内容
         */
        validateInput(javaParserDTO);
        try {
            parseJavaCode(javaParserDTO);
        } catch (IOException e) {
            logger.error("解析java文件失败:{}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @see : 校验输入
     * @since :2018年11月22日 21:10:05
     */
    private void validateInput(JavaParserDTO javaParserDTO) {
        // 判空
        ValidationUtil.validateNull(javaParserDTO, null);

        String folderPath = javaParserDTO.getFileFolder();
        ValidationUtil.validateStringNullOrEmpty(folderPath, null);

        ValidationUtil.validateStringAndLength(folderPath, null,
                JavaParserFormDefinition.FOLDER_PATH_LENGTH, "文件(或文件夹)路径");

        File file = new File(folderPath);

        if (!file.exists()) {
            throw new BusinessValidationException("文件夹或者文件路径不存在！！！");
        }

        // ValidationUtil.validateStringAndLength(javaParserDTO.getFileToStore(),
        // null, JavaParserFormDefinition.FILE_TO_STORE_PATH, "待存放文件名称");

        // ValidationUtil.validateStringNullOrEmpty(javaParserDTO.getFileToStore(),
        // "待保存的文件名称不能为空");
    }

    /**
     * 解析出结果
     * 
     * @see :
     * @param :
     * @return : JavaParserResult
     * @param javaParserDTO
     * @return
     * @throws IOException
     */
    public JavaParserResult parseJavaCode(JavaParserDTO javaParserDTO)
            throws IOException {
        JavaParserResult javaParserResult = new JavaParserResult();

        checkAndCreateFoldersAndFiles(javaParserResult);

        if (StringUtil.isStringAvaliable(javaParserDTO.getFileToStore())) {
            javaParserResult.setFileToStore(javaParserDTO.getFileToStore());
        }

        String filesFolder = javaParserDTO.getFileFolder();

        List<File> filesUnderFolder = FileUtil.getFilesUnderFolder(filesFolder,
                FileSuffix.JAVA_FILE);

        String fileToStore = javaParserDTO.getFileToStore();

        String fileFolder = javaParserResult.getFileToStoreFolder();
        File fileFolderFile = new File(fileFolder);

        if (!fileFolderFile.exists()) {
            fileFolderFile.mkdirs();
        }

        File fileToStoreFile = null;
        if (null != fileToStore) {
            fileToStoreFile = new File(
                    fileFolder + File.separatorChar + fileToStore);

            if (!fileToStoreFile.exists() || !fileToStoreFile.isFile()) {
                fileToStoreFile.createNewFile();
            }
        }

        JavaParserUtil javaParserUtil = null;

        JavaParserGlobal.ALL_METHODS_LIST.clear();
        JavaParserGlobal.ALL_STRING_PARAMS_METHODS_LIST.clear();
        JavaParserGlobal.ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST.clear();

        for (File file : filesUnderFolder) {
            javaParserUtil = new JavaParserUtil();
            javaParserUtil.setEachFile(
                    createJavaParserFile(javaParserResult, file.getName()));
            javaParserUtil.parseJavaCodeAndLogOut((file.getAbsolutePath()),
                    javaParserDTO);
        }

        File allMethodsFile = Paths.get(JavaParserGlobal.FILE_FOLDER_NAME,
                JavaParserGlobal.ALL_METHOD_NAME_FILE).toFile();

        String listInfo = "总方法数:" + JavaParserGlobal.ALL_METHODS_LIST.size();
        JavaParserGlobal.ALL_METHODS_LIST.add(listInfo);
        FileUtil.writeStringListToFile(allMethodsFile,
                JavaParserGlobal.ALL_METHODS_LIST);

        File allParamsAreStringMethodsFile = Paths
                .get(JavaParserGlobal.FILE_FOLDER_NAME,
                        JavaParserGlobal.ALL_PARAMS_ARE_STRING_METHOD_NAME_FILE)
                .toFile();

        String listAllStringInfo = "总方法数:"
                + JavaParserGlobal.ALL_STRING_PARAMS_METHODS_LIST.size();
        JavaParserGlobal.ALL_STRING_PARAMS_METHODS_LIST.add(listAllStringInfo);
        FileUtil.writeStringListToFile(allParamsAreStringMethodsFile,
                JavaParserGlobal.ALL_STRING_PARAMS_METHODS_LIST);

        File notAllParamsAreStringFile = Paths
                .get(JavaParserGlobal.FILE_FOLDER_NAME,
                        JavaParserGlobal.NOT_ALL_PARAMS_ARE_STRING_NAME_FILE)
                .toFile();

        String listNotAllParamsAreStringInfo = "总方法数:"
                + JavaParserGlobal.ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST
                        .size();
        JavaParserGlobal.ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST
                .add(listNotAllParamsAreStringInfo);
        FileUtil.writeStringListToFile(notAllParamsAreStringFile,
                JavaParserGlobal.ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST);
        return javaParserResult;
    }

    private void checkAndCreateFoldersAndFiles(
            JavaParserResult javaParserResult) {
        File fileFolder = new File(javaParserResult.getFileToStoreFolder());

        if (fileFolder.exists()) {
            // boolean deleteResult = fileFolder.delete();
            try {
                logger.debug("删除该目录:{},", fileFolder);
                FileUtils.deleteDirectory(fileFolder);
                logger.debug("删除该目录:{},删除成功", fileFolder);
            } catch (IOException e) {
                logger.debug("删除该目录:{},删除失败", fileFolder);
                e.printStackTrace();
            }
            // logger.debug("删除该目录:{},删除结果{}", fileFolder, deleteResult);
        }

        if (!fileFolder.exists() || !fileFolder.isDirectory()) {
            boolean addResult = fileFolder.mkdirs();
            logger.debug("新增该目录:{},新增结果{}", fileFolder, addResult);

            java.nio.file.Path allMethodsFile = Paths.get(
                    JavaParserGlobal.FILE_FOLDER_NAME,
                    JavaParserGlobal.ALL_METHOD_NAME_FILE);
            File allMethodsFilePath = allMethodsFile.toFile();

            if (!allMethodsFilePath.exists()) {
                try {
                    allMethodsFilePath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            java.nio.file.Path allStringParamsMethodsFile = Paths.get(
                    JavaParserGlobal.FILE_FOLDER_NAME,
                    JavaParserGlobal.ALL_PARAMS_ARE_STRING_METHOD_NAME_FILE);
            File allStringParamsMethodsFilePath = allStringParamsMethodsFile
                    .toFile();

            if (!allStringParamsMethodsFilePath.exists()) {
                try {
                    allStringParamsMethodsFilePath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            java.nio.file.Path notAllParamsAreStringFile = Paths.get(
                    JavaParserGlobal.FILE_FOLDER_NAME,
                    JavaParserGlobal.ALL_METHOD_NAME_FILE);
            File notAllParamsAreStringFilePath = notAllParamsAreStringFile
                    .toFile();

            if (!notAllParamsAreStringFilePath.exists()) {
                try {
                    notAllParamsAreStringFilePath.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File createJavaParserFile(JavaParserResult javaParserResult,
            String filePath) {
        // checkAndCreateFoldersAndFiles(javaParserResult);

        String filePathFinal = javaParserResult.getFileToStoreFolder()
                + File.separatorChar + filePath.replace(".java", ".txt");

        File fileFinal = new File(filePathFinal);
        if (!fileFinal.exists() || !fileFinal.isFile()) {
            try {
                fileFinal.createNewFile();
            } catch (IOException e) {
                logger.error("创建新文件失败:{}", e.getMessage());
                // e.printStackTrace();
                throw new BusinessValidationException(
                        "创建文件失败:" + fileFinal.getAbsolutePath());
            }
        }
        return fileFinal;
    }

}
