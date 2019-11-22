/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月27日 上午10:08:52
 * @see:
 */
package com.administrator.platform.vo;

import com.administrator.platform.model.JavaParserRecord;

public class JavaParserRecordDTO extends JavaParserRecord {

	@Override
	public String toString() {
		return "JavaParserRecordDTO [getProjectName()=" + getProjectName()
		        + ", getPinYinName()=" + getPinYinName() + ", getUsername()="
		        + getUsername() + ", getPassword()=" + getPassword()
		        + ", getVersionControlType()=" + getVersionControlType()
		        + ", getNewerRemoteUrl()=" + getNewerRemoteUrl()
		        + ", getNewerVersion()=" + getNewerVersion() + ", toString()="
		        + super.toString() + ", getResultUrl()=" + getResultUrl()
		        + ", getId()=" + getId() + ", getCreateUser()="
		        + getCreateUser() + ", getUpdateUser()=" + getUpdateUser()
		        + ", getSortField()=" + getSortField() + ", getOrder()="
		        + getOrder() + ", getCreateDate()=" + getCreateDate()
		        + ", getUpdateDate()=" + getUpdateDate() + ", getMobile()="
		        + getMobile() + ", getDescription()=" + getDescription()
		        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
		        + "]";
	}

}
