/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月15日 下午4:15:49
 * @see:
 */
package com.administrator.platform.enums;

public class RoleTypeEn {
	public static final String ROLE_TYPE_EN_ADMIN = "ADMIN";
	public static final String ROLE_TYPE_EN_USER = "USER";
	public static final String ROLE_TYPE_EN_DBA = "DBA";

	public static final String ROLE_TYPE_EN_PREFIX = "ROLE_";

	public static final String ROLE_TYPE_EN_ADMIN_FULL = ROLE_TYPE_EN_PREFIX
	        + ROLE_TYPE_EN_ADMIN;
	public static final String ROLE_TYPE_EN_USER_FULL = ROLE_TYPE_EN_PREFIX
	        + ROLE_TYPE_EN_USER;
	public static final String ROLE_TYPE_EN_DBA_FULL = ROLE_TYPE_EN_PREFIX
	        + ROLE_TYPE_EN_DBA;
}