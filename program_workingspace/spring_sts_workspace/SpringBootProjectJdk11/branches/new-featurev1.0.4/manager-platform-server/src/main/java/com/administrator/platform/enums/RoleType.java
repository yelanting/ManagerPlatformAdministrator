/**
 * @author : 孙留平
 * @since : 2019年4月4日 下午3:11:03
 * @see:
 */
package com.administrator.platform.enums;

import com.administrator.platform.core.base.util.StringUtil;

/**
 * @author : Administrator
 * @since : 2019年4月4日 下午3:11:03
 * @see :
 */
public enum RoleType {
	/**
	 * 超管
	 */
	ROLE_ADMIN("超级管理员", RoleTypeEn.ROLE_TYPE_EN_ADMIN),
	/**
	 * 
	 * DBA用户
	 */
	ROLE_DBA("DBA", RoleTypeEn.ROLE_TYPE_EN_DBA),
	/**
	 * 普通用户
	 */
	ROLE_NORMAL("普通用户", RoleTypeEn.ROLE_TYPE_EN_USER),
	/**
	 * 访客
	 */
	ROLE_GUEST("访客", RoleTypeEn.ROLE_TYPE_EN_USER);

	/**
	 * 中文名
	 */

	private String roleTypeCnName;

	private String roleTypeEnName;

	public String getRoleTypeCnName() {
		return roleTypeCnName;
	}

	private RoleType(String roleTypeCnName, String roleTypeEnName) {
		this.roleTypeCnName = roleTypeCnName;
		this.roleTypeEnName = roleTypeEnName;
	}

	public String getRoleTypeEnName() {
		return roleTypeEnName;
	}

	public void setRoleTypeEnName(String roleTypeEnName) {
		this.roleTypeEnName = roleTypeEnName;
	}

	public void setRoleTypeCnName(String roleTypeCnName) {
		this.roleTypeCnName = roleTypeCnName;
	}

	public static RoleType fromRoleTypeCnName(String roleTypeCnName) {

		if (StringUtil.isEmpty(roleTypeCnName)) {
			return null;
		}

		for (RoleType roleType : RoleType.values()) {
			if (roleTypeCnName.equals(roleType.getRoleTypeCnName())) {
				return roleType;
			}
		}

		return null;
	}
}
