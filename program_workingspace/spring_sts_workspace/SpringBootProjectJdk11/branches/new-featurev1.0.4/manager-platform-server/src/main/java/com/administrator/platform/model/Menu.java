/**
 * @author : 孙留平
 * @since : 2018年12月21日 20:26:01
 * @see:
 */
package com.administrator.platform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年12月21日 20:26:01
 * @see : 菜单
 */
@Entity
@Table(name = "tb_menu")
public class Menu extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * @see 主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @see 图标名称
     */
    private String icon;

    /**
     * @see 名称
     */
    private String name;

    /**
     * @see 状态
     */
    private Integer state;

    /**
     * @see url
     * 
     */
    private String url;

    /**
     * @see
     */
    private Long pId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", icon=" + icon + ", name=" + name
                + ", state=" + state + ", url=" + url + ", pId=" + pId + "]";
    }
}
