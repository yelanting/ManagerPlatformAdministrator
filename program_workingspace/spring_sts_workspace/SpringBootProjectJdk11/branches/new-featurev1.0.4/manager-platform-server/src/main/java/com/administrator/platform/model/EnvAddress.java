/**
 * @author : 孙留平
 * @since : 2018年9月3日 下午7:45:53
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.administrator.platform.core.base.util.CalendarUtil;
import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年9月3日 下午7:45:53
 * @see : 环境信息DO
 */
@Entity
@Table(name = "tb_env_address")
/**
 * 
 * @author : Administrator
 * @since : 2018年12月27日 下午4:50:49
 * @see :
 */
public class EnvAddress extends BaseDomain {
    private static final long serialVersionUID = -8519947068338363038L;

    /**
     * @see 主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @see 项目名称
     */
    private String projectName;

    /**
     * 服务器地址
     */
    private String serverUrl;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
    private Date createDate;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
    private Date updateDate;

    /**
     * 描述备注
     */
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
    @DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    @DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
	public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EnvAddress [id=" + id + ", projectName=" + projectName
                + ", serverUrl=" + serverUrl + ", username=" + username
                + ", password=" + password + ", createDate=" + createDate
                + ", updateDate=" + updateDate + ", description=" + description
                + "]";
    }
}
