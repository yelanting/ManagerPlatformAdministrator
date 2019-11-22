/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午10:12:49
 * @see:
 */
package com.administrator.platform.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.constdefine.CommonDefine;
import com.administrator.platform.core.base.util.ChinesePinYin;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.model.JavaParserRecord;
import com.administrator.platform.tools.javaparser.JavaParserConstDefine;
import com.administrator.platform.tools.vcs.common.VcsCommonUtil;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午10:12:49
 * @see :
 */
public class JavaParserCommonUtil {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JavaParserCommonUtil.class);

    private JavaParserCommonUtil() {

    }

    /**
     * 解析新版本文件夹
     * 
     * @see :
     * @param :
     * @return : File
     * @param codeCoverage
     * @return
     */
    private static File parseNewProjectFolderFromUrlAndVersion(String remoteUrl,
            String remoteVersion) {
        File newerFolder = getJavaParserFullFolderFileOfSub(
                VcsCommonUtil.parseNewProjectFolderFromUrlAndVersion(remoteUrl,
                        remoteVersion));
        LOGGER.debug("新代码的路径为:{}", newerFolder.getAbsolutePath());
        return newerFolder;
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static File getJavaParserBaseFolder() {
        File newFile = new File(CommonUtil.getDefaultFileSaveFolder(),
                JavaParserConstDefine.DEFAULT_JAVA_PARSER_FOLDER);
        LOGGER.debug("getJavaParserBaseFolder():{}", newFile.getAbsolutePath());
        return newFile;
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static File getJavaParserResultBaseFolder() {
        File newFileResult = new File(CommonUtil.getDefaultFileSaveFolder(),
                JavaParserConstDefine.DEFAULT_JAVA_PARSER_RESULT_FOLDER);

        LOGGER.debug("getJavaParserResultBaseFolder():{}",
                newFileResult.getAbsolutePath());
        return newFileResult;
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static String getJavaParserBaseFolderPath() {
        return getJavaParserBaseFolder().getAbsolutePath();
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static String getJavaParserResultBaseFolderPath() {
        return getJavaParserResultBaseFolder().getAbsolutePath();
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录,相对文件夹
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static String getJavaParserFullFolderPathOfSub(String folderName) {
        return getJavaParserFullFolderFileOfSub(folderName).getAbsolutePath();
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录,相对文件夹
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static File getJavaParserFullFolderFileOfSub(String folderName) {
        File newFile = new File(getJavaParserBaseFolderPath(), folderName);
        LOGGER.debug("getJavaParserFullFolderFileOfSub(String folderName):{}",
                newFile.getAbsolutePath());
        return newFile;

    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录,相对文件夹
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private static File getJavaParserResultFullFolderFileOfSub(
            String folderName) {
        File newFileFullResult = new File(getJavaParserResultBaseFolderPath(),
                folderName);

        if (!newFileFullResult.exists()) {
            newFileFullResult.mkdirs();
        }
        LOGGER.debug(
                "getJavaParserResultFullFolderFileOfSub(String folderName):{}",
                newFileFullResult.getAbsolutePath());
        return newFileFullResult;
    }

    /**
     * 从javaparserRecord实体中解析存放路径
     * 
     * @see :
     * @param :
     * @return : String
     * @param javaParserRecord
     * @return
     */
    public static String getNewerFolderPathFromJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        return getNewerFolderFileFromJavaParserRecord(javaParserRecord)
                .getAbsolutePath();
    }

    /**
     * 从javaparserRecord实体中解析存放路径
     * 
     * @see :
     * @param :
     * @return : String
     * @param javaParserRecord
     * @return
     */
    public static String getNewerFolderResultPathFromJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        return getNewerResultFolderFileFromJavaParserRecord(javaParserRecord)
                .getAbsolutePath();
    }

    /**
     * 从javaparserRecord实体中解析存放路径
     * 
     * @see :
     * @param :
     * @return : String
     * @param javaParserRecord
     * @return
     */
    public static File getNewerFolderFileFromJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        return getJavaParserFullFolderFileOfSub(
                parseNewerFolderFromJavaParserRecord(javaParserRecord));
    }

    /**
     * 从javaparserRecord实体中解析存放路径
     * 
     * @see :
     * @param :
     * @return : String
     * @param javaParserRecord
     * @return
     */
    public static File getNewerResultFolderFileFromJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        return getJavaParserResultFullFolderFileOfSub(
                parseNewerFolderFromJavaParserRecord(javaParserRecord));
    }

    /**
     * 解析文件名
     * 
     * @see :
     * @param :
     * @return : String
     * @param javaParserRecord
     * @return
     */
    private static String parseNewerFolderFromJavaParserRecord(
            JavaParserRecord javaParserRecord) {
        String folderNameFromParser = VcsCommonUtil
                .parseNewProjectFolderFromUrlAndVersion(
                        javaParserRecord.getNewerRemoteUrl(),
                        javaParserRecord.getNewerVersion());

        if (StringUtil.isEmpty(folderNameFromParser)) {
            return "";
        }

        if (folderNameFromParser
                .indexOf(CommonDefine.DEFAULT_NULL_PREFIX) != -1) {
            return folderNameFromParser.replace(
                    CommonDefine.DEFAULT_NULL_PREFIX,
                    ChinesePinYin.getPingYin(javaParserRecord.getProjectName())
                            + CommonDefine.UNDER_LINE_SIGN);
        } else {
            return folderNameFromParser;
        }

    }

    /**
     * 获取requestmapping的值
     * 
     * @see :
     * @param :
     * @return : String
     * @param requestMappingExp
     * @return
     */
    public static String parseRequestMappingValue(String requestMappingExp) {
        if (StringUtil.isEmpty(requestMappingExp) || requestMappingExp.indexOf(
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_SEP_CHAR) == -1) {
            return "";
        }
        /**
         * 先找到第一个引号
         */
        int leftIndex = requestMappingExp.indexOf(
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_SEP_CHAR);

        if (leftIndex == -1) {
            return "";
        }
        /**
         * 再找到第二个引号，截取第一个引号之后的内容，再去取第一个引号
         */
        String restContent = requestMappingExp.substring(leftIndex + 1);

        int rightIndex = restContent.indexOf(
                JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_SEP_CHAR);

        // 在源串中取的时候，需要加上前半部分
        int realRightIndex = leftIndex + rightIndex + 1;

        if (leftIndex == -1 || rightIndex == -1
                || leftIndex == realRightIndex) {
            return "";
        }

        String requestMappingValue = requestMappingExp.substring(leftIndex + 1,
                realRightIndex);

        /**
         * 如果有多个映射
         */

        if (requestMappingValue.indexOf(
                JavaParserConstDefine.DEFAULT_MULTI_VALUES_SEP_SIGN) != -1) {
            requestMappingValue = JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_SEP_CHAR
                    + requestMappingValue
                    + JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_SEP_CHAR;
            LOGGER.debug("这个请求映射是个多value:{}", requestMappingValue);
            return requestMappingValue;
        }
        return dealRequestMappingValue(requestMappingValue);
    }

    /**
     * 获取requestmapping的值
     * 
     * @see :
     * @param :
     * @return : String
     * @param packageValue
     * @return
     */
    public static String parsePackageValue(String packageValue) {
        if (StringUtil.isEmpty(packageValue)) {
            return "";
        }

        int leftIndex = packageValue
                .indexOf(JavaParserConstDefine.DEFAULT_PACKAGE_SIGN)
                + JavaParserConstDefine.DEFAULT_PACKAGE_SIGN.length();
        int rightIndex = packageValue
                .lastIndexOf(JavaParserConstDefine.DEFAULT_PACKAGE_END_SIGN);
        if (leftIndex == -1 || rightIndex == -1 || leftIndex == rightIndex) {
            return "";
        }
        return packageValue.substring(leftIndex + 1, rightIndex);
    }

    /**
     * 获取requestmapping的值
     * 
     * @see :
     * @param :
     * @return : String
     * @param requestMappingValue
     * @return
     */
    public static String dealRequestMappingValue(String requestMappingValue) {
        if (StringUtil.isEmpty(requestMappingValue)) {
            return "";
        }

        if (requestMappingValue.charAt(
                0) != JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_START_SEP_CHAR) {
            requestMappingValue = JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_START_SEP_CHAR
                    + requestMappingValue;
        }
        return requestMappingValue;
    }
}
