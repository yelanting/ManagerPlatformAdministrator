package com.administrator.platform.model;

import java.util.Date;

import com.administrator.platform.enums.RoleType;
import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public class SysRole extends BaseDomain {
	private Long id;

	private String roleName;

	private String comments;

	private Date createDate;

	private Date updateDate;

	private Boolean deleted;

	private String roleCnName;

	private RoleType roleType;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRoleCnName() {
		return roleCnName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setRoleCnName(String roleCnName) {
		this.roleCnName = roleCnName;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", roleName=" + roleName + ", comments="
		        + comments + ", createDate=" + createDate + ", updateDate="
		        + updateDate + ", deleted=" + deleted + ", roleCnName="
		        + roleCnName + ", roleType=" + roleType + "]";
	}
}
