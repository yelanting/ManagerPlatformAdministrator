/**
 * @author : 孙留平
 * @since : 2018年11月20日 下午9:29:53
 * @see:
 */
package com.administrator.platform.domain;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年11月20日 下午9:29:53
 * @see :
 */
public class SearchDomain extends BaseDomain {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 页数
     */
    private Integer page = 1;
    /**
     * 每页行数
     */
    private Integer rows = 15;
    /**
     * 排序字段
     */
    private String sidx;
    /**
     * 排序类型
     */
    private String sord;

    private Integer searchType;

    private boolean pageOnly;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public boolean isPageOnly() {
        return pageOnly;
    }

    public void setPageOnly(boolean pageOnly) {
        this.pageOnly = pageOnly;
    }
}
