/**
 * @author : 孙留平
 * @since : 2018年10月15日 上午9:15:23
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
 * @since : 2018年10月15日 上午9:15:23
 * @see :
 */
@Entity
@Table(name = "tb_good_type")
public class GoodType extends BaseDomain {

    /**
     * @Fields serialVersionUID : 物品分类的序列化ID
     */
    private static final long serialVersionUID = -3593120769864750477L;

    private String typeName;
    private String typeDesc;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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
