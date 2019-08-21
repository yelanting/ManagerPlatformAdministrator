/// **
// * @author : 孙留平
// * @since : 2018年12月21日 下午7:44:00
// * @see:
// */
// package com.administrator.platform.config;
//
// import java.util.LinkedHashMap;
// import java.util.Map;
//
// import org.apache.shiro.mgt.SecurityManager;
// import org.apache.shiro.spring.LifecycleBeanPostProcessor;
// import
/// org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
// import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
// import org.apache.shiro.web.mgt.CookieRememberMeManager;
// import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
// import org.apache.shiro.web.servlet.SimpleCookie;
// import org.springframework.context.annotation.Bean;
//
/// **
// * @author : Administrator
// * @since : 2018年12月21日 下午7:44:00
// * @see :
// */
//// @Configuration
// public class ShiroConfig {
// private static final String LOGIN_URL = "/login/goToLoginPage";
// private static final String ANON = "anon";
//
// @Bean
// public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
// ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//
// shiroFilterFactoryBean.setSecurityManager(securityManager);
// shiroFilterFactoryBean.setLoginUrl(LOGIN_URL);
// shiroFilterFactoryBean.setUnauthorizedUrl(LOGIN_URL);
//
// Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//
// filterChainDefinitionMap.put("/static/**", ANON);
// filterChainDefinitionMap.put(LOGIN_URL, ANON);
// filterChainDefinitionMap.put("/images/**", ANON);
// filterChainDefinitionMap.put("/templates/**", ANON);
// filterChainDefinitionMap.put("/resources/**", ANON);
// filterChainDefinitionMap.put("/public/**", ANON);
// filterChainDefinitionMap.put("/sys/sysUser/logout", "logout");
// filterChainDefinitionMap.put("/**", "authc");
//
// shiroFilterFactoryBean
// .setFilterChainDefinitionMap(filterChainDefinitionMap);
// return shiroFilterFactoryBean;
// }
//
// @Bean
// public DefaultWebSecurityManager securityManager() {
// DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
// // 设置realm.
// securityManager.setRealm(myRealm());
//
// // 注入记住我管理器;
// securityManager.setRememberMeManager(rememberMeManager());
//
// return securityManager;
// }
//
// /**
// * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
// *
// * @return
// */
// @Bean
// public MyRealm myRealm() {
// MyRealm myRealm = new MyRealm();
// return myRealm;
// }
//
// /**
// * Shiro生命周期处理器
// *
// * @return
// */
// @Bean
// public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
// return new LifecycleBeanPostProcessor();
// }
//
// /**
// *
/// 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
// *
/// 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
// * 不要使用 DefaultAdvisorAutoProxyCreator 会出现二次代理的问题，这里不详述
// *
// * @return
// */
// /**
// * @Bean
// *
// * @DependsOn({"lifecycleBeanPostProcessor"})
// * public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
// * DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new
// * DefaultAdvisorAutoProxyCreator();
// * advisorAutoProxyCreator.setProxyTargetClass(true);
// * return advisorAutoProxyCreator;
// * }
// */
// @Bean
// public AuthorizationAttributeSourceAdvisor
/// authorizationAttributeSourceAdvisor() {
// AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new
/// AuthorizationAttributeSourceAdvisor();
// authorizationAttributeSourceAdvisor
// .setSecurityManager(securityManager());
// return authorizationAttributeSourceAdvisor;
// }
//
// /**
// * cookie对象;
// * 记住密码实现起来也是比较简单的，主要看下是如何实现的。
// *
// * @return
// */
// @Bean
// public SimpleCookie rememberMeCookie() {
// System.out.println("ShiroConfiguration.rememberMeCookie()");
// // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
// SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
// // <!-- 记住我cookie生效时间30天 ,单位秒;-->
// simpleCookie.setMaxAge(259200);
// return simpleCookie;
// }
//
// /**
// * cookie管理对象;
// *
// * @return
// */
// @Bean
// public CookieRememberMeManager rememberMeManager() {
// System.out.println("ShiroConfiguration.rememberMeManager()");
// CookieRememberMeManager cookieRememberMeManager = new
/// CookieRememberMeManager();
// cookieRememberMeManager.setCookie(rememberMeCookie());
// return cookieRememberMeManager;
// }
// }
