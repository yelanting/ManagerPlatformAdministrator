/**
 * @author : 孙留平
 * @since : 2019年4月3日 下午4:03:10
 * @see:
 */
package com.administrator.platform.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : Administrator
 * @since : 2019年4月3日 下午4:03:10
 * @see :
 */
public class SecurityUser extends SysUser implements UserDetails {
    public SecurityUser(SysUser sysUser) {
        if (null != sysUser) {
            this.setUserAccount(sysUser.getUserAccount());
            this.setUserNickname(sysUser.getUserNickname());
            this.setUserPassword(sysUser.getUserPassword());
            this.setAdmin(sysUser.getAdmin());
            this.setDeleted(sysUser.getDeleted());
            this.setId(sysUser.getId());
            this.setLocked(sysUser.getLocked());
            this.setMobilePhone(sysUser.getMobilePhone());
            this.setSex(sysUser.getSex());
            this.setUserStatus(sysUser.getUserStatus());
        }
    }

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = -8891591951434213836L;

    /**
     * @see
     *      org.springframework.security.core.userdetails.UserDetails#getAuthorities(
     *      )
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String userAccount = this.getUserAccount();
        if (null != userAccount) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                    userAccount);
            authorities.add(authority);
        }
        return authorities;
    }

    /**
     * @see
     *      org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return this.getUserPassword();
    }

    /**
     * @see
     *      org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return this.getUserAccount();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#
     *      isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#
     *      isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#
     *      isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @see
     *      org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
