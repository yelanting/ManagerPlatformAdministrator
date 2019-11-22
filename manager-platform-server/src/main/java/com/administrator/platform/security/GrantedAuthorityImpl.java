package com.administrator.platform.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 * 
 * @author 孙留平
 * @date 2019年8月26日 13:45:54
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String authority;

	public GrantedAuthorityImpl(String authority) {
		this.authority = authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}