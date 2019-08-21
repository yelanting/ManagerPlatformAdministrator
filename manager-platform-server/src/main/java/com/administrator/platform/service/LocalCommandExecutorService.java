/**
 * @author : 孙留平
 * @since : 2019年3月13日 上午11:27:46
 * @see:
 */
package com.administrator.platform.service;

import com.administrator.platform.model.ExecuteCommandResult;
import com.administrator.platform.tools.codebuild.entity.BuildType;

/**
 * @author : Administrator
 * @since : 2019年3月13日 上午11:27:46
 * @see :
 */
public interface LocalCommandExecutorService {
	/**
	 * 编译代码
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param projectFolder
	 * @return
	 */
	ExecuteCommandResult compileSourceCode(String projectFolder);

	/**
	 * install代码
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param projectFolder
	 * @return
	 */
	ExecuteCommandResult installSourceCode(String projectFolder);

	/**
	 * 编译代码
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param projectFolder
	 * @return
	 */
	ExecuteCommandResult compileSourceCode(String projectFolder,
			BuildType buildType);

	/**
	 * install代码
	 * 
	 * @see :
	 * @param :
	 * @return : ExecuteCommandResult
	 * @param projectFolder
	 * @return
	 */
	ExecuteCommandResult installSourceCode(String projectFolder,
			BuildType buildType);
}
