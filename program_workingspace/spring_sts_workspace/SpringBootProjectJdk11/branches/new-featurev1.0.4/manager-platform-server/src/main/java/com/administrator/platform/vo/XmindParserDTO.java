/**
 * @author : 孙留平
 * @since : 2019年1月19日 下午2:42:27
 * @see:
 */
package com.administrator.platform.vo;

import com.administrator.platform.model.XmindParser;

/**
 * @author : Administrator
 * @since : 2019年1月19日 下午2:42:27
 * @see :
 */
public class XmindParserDTO extends XmindParser {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 8015954825957437253L;

    @Override
    public String toString() {
        return "XmindParserDTO [getId()=" + getId() + ", getCreateDate()="
                + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
                + ", getXmindFileName()=" + getXmindFileName()
                + ", getDescription()=" + getDescription() + ", getAccessUrl()="
                + getAccessUrl() + ", getXmindFileUrl()=" + getXmindFileUrl()
                + ", toString()=" + super.toString() + ", getCreateUser()="
                + getCreateUser() + ", getUpdateUser()=" + getUpdateUser()
                + ", getSortField()=" + getSortField() + ", getOrder()="
                + getOrder() + ", getMobile()=" + getMobile() + ", getClass()="
                + getClass() + ", hashCode()=" + hashCode() + "]";
    }

}
