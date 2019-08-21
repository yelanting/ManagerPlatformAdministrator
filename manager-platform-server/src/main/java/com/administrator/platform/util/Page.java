package com.administrator.platform.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/***
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public class Page<T> implements Serializable {
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页多少条
     */
    private int rows;
    private static final long serialVersionUID = 1L;
    private List<T> records = Collections.emptyList();
    private Map<String, Object> condition;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }
}
