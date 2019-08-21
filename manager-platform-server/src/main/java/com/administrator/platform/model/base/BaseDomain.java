package com.administrator.platform.model.base;
/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午1:52:31
 * @see:
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.administrator.platform.core.base.util.CalendarUtil;

/**
 * @author : Administrator
 * @since : 2018年9月7日 下午1:52:31
 * @see : 基础domain类
 */
public class BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String createUser;

	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date createDate;

	private String updateUser;
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date updateDate;

	private String sortField;

	private String order;

	private String mobile;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
