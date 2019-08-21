/// **
// * @author : 孙留平
// * @since : 2018年12月21日 下午7:54:37
// * @see:
// */
// package com.administrator.platform.config;
//
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
//
// import javax.annotation.Resource;
//
/// **
// * @author : Administrator
// * @since : 2018年12月21日 下午7:54:37
// * @see :
// */
// import org.apache.shiro.SecurityUtils;
// import org.apache.shiro.authc.AuthenticationException;
// import org.apache.shiro.authc.AuthenticationInfo;
// import org.apache.shiro.authc.AuthenticationToken;
// import org.apache.shiro.authc.SimpleAuthenticationInfo;
// import org.apache.shiro.authz.AuthorizationInfo;
// import org.apache.shiro.authz.SimpleAuthorizationInfo;
// import org.apache.shiro.realm.AuthorizingRealm;
// import org.apache.shiro.subject.PrincipalCollection;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import com.administrator.platform.mapper.MenuMapper;
// import com.administrator.platform.mapper.SysRoleMapper;
// import com.administrator.platform.mapper.SysUserMapper;
// import com.administrator.platform.mapper.SysUserRoleMapper;
// import com.administrator.platform.model.Menu;
// import com.administrator.platform.model.SysRole;
// import com.administrator.platform.model.SysUser;
//
/// **
// * 自定义Realm
// *
// * @author zjt
// *
// */
// public class MyRealm extends AuthorizingRealm {
// private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
// @Resource
// private SysUserMapper sysUserMapper;
//
// @Resource
// private SysRoleMapper sysRoleMapper;
//
// @Resource
// private SysUserRoleMapper sysUserRoleMapper;
//
// @Resource
// private MenuMapper menuMapper;
//
// /**
// * 授权
// */
// @Override
// protected AuthorizationInfo doGetAuthorizationInfo(
// PrincipalCollection principals) {
// String userName = (String) SecurityUtils.getSubject().getPrincipal();
//
// List<SysUser> userList = sysUserMapper
// .findSysUsersByUserAccount(userName);
//
// logger.debug("查询出的用户为:{}", userList);
//
// if (userList != null && userList.size() > 0) {
// SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
// SysUser sysUser = userList.get(0);
// List<SysRole> roleList = sysRoleMapper
// .selectRolesByUserId(sysUser.getId());
//
// Set<String> roles = new HashSet<String>();
// if (roleList.size() > 0) {
// for (SysRole role : roleList) {
// roles.add(role.getRoleName());
// // List<Menu>
// // 根据角色id查询所有资源
// List<Menu> menuList = menuMapper
// .selectMenuesByRoleId(role.getId());
// for (Menu menu : menuList) {
// // 添加权限
// info.addStringPermission(menu.getName());
// }
// }
// }
// info.setRoles(roles);
// return info;
// }
//
// return null;
// }
//
// /**
// * 权限认证
// */
// @Override
// protected AuthenticationInfo doGetAuthenticationInfo(
// AuthenticationToken token) throws AuthenticationException {
// String userName = (String) token.getPrincipal();
// List<SysUser> userList = sysUserMapper
// .findSysUsersByUserAccount(userName);
//
// logger.debug("查询出的用户为:{}", userList);
//
// if (userList != null && userList.size() > 0) {
// return new SimpleAuthenticationInfo(
// userList.get(0).getUserAccount(),
// userList.get(0).getUserPassword(), "myRealm");
// }
// return null;
//
// }
// }
