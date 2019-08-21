package com.administrator.platform.model;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * @author : Administrator
 * @since : 2018年12月27日 16:47:26
 * @see :
 */
public class PageRusult<T> extends PageInfo<T> {
    public PageRusult() {
    }

    public PageRusult(List<T> list) {
        super(list, 8);
    }

    /**
     * layui框架列表模块返回参数中必须包含code状态字段
     * 
     */
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
