/**
 * @author : 孙留平
 * @since : 2019年4月3日 下午7:22:07
 * @see:
 */
package com.administrator.platform.service;

import com.administrator.platform.model.SysUser;

/**
 * @author : Administrator
 * @since : 2019年4月3日 下午7:22:07
 * @see :
 */
public interface LoginService {
    SysUser login(SysUser sysUser);

    void logout(SysUser sysUser);
}
