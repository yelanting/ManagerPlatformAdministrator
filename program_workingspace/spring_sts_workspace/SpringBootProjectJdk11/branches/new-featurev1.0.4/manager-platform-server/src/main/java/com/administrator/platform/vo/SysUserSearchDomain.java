/**
 * @author : 孙留平
 * @since : 2018年11月20日 下午9:31:27
 * @see:
 */
package com.administrator.platform.vo;

import com.administrator.platform.domain.SearchDomain;

/**
 * @author : Administrator
 * @since : 2018年11月20日 下午9:31:27
 * @see :
 */
public class SysUserSearchDomain extends SearchDomain {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;

    private String userAccount;
    private String userNickname;
    private String mobilePhone;
    private String sex;
    private Long roleId;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
