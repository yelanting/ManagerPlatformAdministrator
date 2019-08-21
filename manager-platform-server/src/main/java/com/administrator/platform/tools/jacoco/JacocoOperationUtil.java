/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午9:34:33
 * @see:
 */
package com.administrator.platform.tools.jacoco;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.core.base.util.Util;
import com.administrator.platform.util.define.OperationSystemEnum;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午9:34:33
 * @see :
 */
public class JacocoOperationUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(JacocoOperationUtil.class);

    private JacocoOperationUtil() {

    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    public static String getJacocoCoverageBaseFolder() {
        OperationSystemEnum operationSystemEnum = Util
                .getCurrentOperationSystem();
        // logger.debug("操作系统为:{}", operationSystemEnum);
        if (operationSystemEnum == OperationSystemEnum.LINUX) {
            return JacocoDefine.LINUX_BASE_FOLDER;
        }
        // else if (operationSystemEnum == OperationSystemEnum.WINDOWS) {
        return JacocoDefine.WINDOWS_BASE_FOLDER;
        // }
        // return null;
    }

    /**
     * 获取当前操作系统自定义的jacoco操作目录
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    public static String getJacocoCoverageExecBackupFolder() {
        String jacocoCoverageBaseFolder = getJacocoCoverageBaseFolder();
        if (StringUtil.isEmpty(jacocoCoverageBaseFolder)) {
            return null;
        }

        return jacocoCoverageBaseFolder + File.separator
                + JacocoDefine.DEFAULT_JACOCO_EXEC_BACKUP_FOLDER;
    }

    /**
     * 编译代码
     * 
     * @see :
     * @param :
     * @return : void
     * @param projectFolder
     */
    public static void compileSourceCode(String projectFolder) {
    }

    /**
     * 获取当前的文件保存文件目录
     * 
     * @see :
     * @param :
     * @return : File
     * @param filePath
     * @return
     */
    public static File getCurrentSystemFileStorePath(String filePath) {
        return new File(getJacocoCoverageBaseFolder(), filePath);
    }

    /**
     * 把exec从备份文件夹中拷贝到各自的模块目录
     * 
     * @see :
     * @param :
     * @return : void
     * @param sourceFolder
     * @param destFolder
     */
    public static void copyExecFileToFolder(String sourceFolder,
            String destFolder) {
        String fileName = sourceFolder + File.separator
                + JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT;
        String destFileName = destFolder + File.separator
                + JacocoDefine.JACOCO_EXEC_FILE_NAME_DEFAULT;
        try {
            FileUtil.copyFile(fileName, destFileName);
        } catch (IOException e) {
            logger.error("拷贝jacocoexec备份文件失败：{}->{}", sourceFolder, destFolder);
        }
    }
}
