/**
 * @author : 孙留平
 * @since : 2019年2月18日 下午7:45:53
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
 * @since : 2019年2月18日 下午7:45:53
 * @see : 其他环境信息DO
 */
@Entity
@Table(name = "tb_other_address")
/**
 * 
 * @author : Administrator
 * @since : 2018年12月27日 下午4:50:49
 * @see :
 */
public class OtherAddress extends BaseDomain {
    private static final long serialVersionUID = 2114562515713267560L;

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
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "OtherAddress [id=" + id + ", projectName=" + projectName
                + ", url=" + url + ", createDate=" + createDate
                + ", updateDate=" + updateDate + ", description=" + description
                + "]";
    }
}
