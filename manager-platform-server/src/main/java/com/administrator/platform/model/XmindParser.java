/**
 * @author : 孙留平
 * @since : 2018年10月15日 上午9:15:23
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年10月15日 上午9:15:23
 * @see :
 */
public class XmindParser extends BaseDomain {

    /**
     * @Fields serialVersionUID : 物品分类的序列化ID
     */
    private static final long serialVersionUID = -3593120769864750477L;

    private String xmindFileName;
    private String description;
    private String accessUrl;
    private String xmindFileUrl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * @see 想要以jpa的方式自动初始化数据库表，则需要显式集成方法
     */
    @Override
    public Date getCreateDate() {
        return super.getCreateDate();
    }

    /**
     * @see 想要以jpa的方式自动初始化数据库表，则需要显式集成方法
     */
    @Override
    public Date getUpdateDate() {
        return super.getUpdateDate();
    }

    public String getXmindFileName() {
        return xmindFileName;
    }

    public void setXmindFileName(String xmindFileName) {
        this.xmindFileName = xmindFileName;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public String getXmindFileUrl() {
        return xmindFileUrl;
    }

    public void setXmindFileUrl(String xmindFileUrl) {
        this.xmindFileUrl = xmindFileUrl;
    }

    @Override
    public String toString() {
        return "XmindParser [xmindFileName=" + xmindFileName + ", description="
                + description + ", accessUrl=" + accessUrl + ", xmindFileUrl="
                + xmindFileUrl + "]";
    }
}
