/**
 * @author : 孙留平
 * @since : 2018年10月14日 上午1:47:40
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年10月14日 上午1:47:40
 * @see :
 */
@Entity
@Table(name = "tb_book")
public class Book extends BaseDomain {
    /**
     * @Fields serialVersionUID : 伪装的Sid
     */
    private static final long serialVersionUID = -3980868089212710523L;
    private String bookName;
    private String bookDesc;
    private String url;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

}
