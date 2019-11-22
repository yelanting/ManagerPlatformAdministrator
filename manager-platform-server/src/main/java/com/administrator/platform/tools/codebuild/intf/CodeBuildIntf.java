/**
 * @author : 孙留平
 * @since : 2019年3月26日 上午10:56:58
 * @see:
 */
package com.administrator.platform.tools.codebuild.intf;

import java.io.File;
import java.util.List;

import com.administrator.platform.tools.jacoco.CodeCoverageFilesAndFoldersDTO;

/**
 * @author : Administrator
 * @since : 2019年3月26日 上午10:56:58
 * @see :
 */
public interface CodeBuildIntf {
    /**
     * 获取子模块列表
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param projectPath
     * @return
     */
    List<String> getSubModules(String projectPath);

    /**
     * 获取所有源码目录
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param projectPath
     * @return
     */

    List<String> getSubSourceFileFolders(String projectPath);

    /**
     * 获取所有class文件目录
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param projectPath
     * @return
     */
    List<String> getSubClassesFileFolders(String projectPath);

    /**
     * 获取所有exec文件
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param projectPath
     * @return
     */
    List<String> getExecutionDataFiles(String projectPath);

    /**
     * 从当前文件夹下面解析出需要的覆盖率文件夹信息
     * 
     * @see :
     * @param :
     * @return : List<CodeCoverageFilesAndFoldersDTO>
     * @param projectPath
     * @return
     */
    List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFolders(
            String projectPath);

    /**
     * 从当前文件夹下面解析出需要的覆盖率文件夹信息
     * 
     * @see :
     * @param :
     * @return : List<CodeCoverageFilesAndFoldersDTO>
     * @param projectPath
     * @return
     */
    List<CodeCoverageFilesAndFoldersDTO> getCodeCoverageFilesAndFolders(
            File projectPath);

    /**
     * 检查是否此种类的文件夹
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param projectPath
     * @return
     */

    boolean isThusKindOfProject(String projectPath);

    /**
     * 检查当前文件夹是否多模块
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param projectPath
     * @return
     */
    boolean isThusProjectMulti(String projectPath);

    /**
     * 以一个文件夹初始化为一个MAVEN工程资源
     * 
     * @see :
     * @param :
     * @return : CodeCoverageFilesAndFoldersDTO
     * @param projectDir
     * @return
     */
    CodeCoverageFilesAndFoldersDTO initDefaultCodeCoverageFilesAndFoldersDTO(
            String projectDir);

    /**
     * 清理工程
     * 
     * @see :
     * @param :
     * @return : void
     * @param projectFolder
     */
    void cleanProject(String projectFolder);

    /**
     * 拷贝classes，
     * 
     * @see :
     * @param :
     * @return : void
     * @param fromClassFolder
     * @param projectFolder
     */
    void copyClasses(String fromClassFolder, String projectFolder);
}
