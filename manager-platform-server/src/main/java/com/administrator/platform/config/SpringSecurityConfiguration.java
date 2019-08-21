package com.administrator.platform.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.administrator.platform.enums.RoleType;
import com.administrator.platform.model.SecurityUser;
import com.administrator.platform.service.SysUserService;

/*****
 * @author : Administrator
 * @since : 2018年12月15日 下午11:55:40
 * @see :
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory
	        .getLogger(SpringSecurityConfiguration.class);
	private static final String LOGIN_URL = "/login";
	private static final String LOGIN_PASSWORD = "Admin@1234";

	@Autowired
	private SysUserService sysUserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().headers().disable();
		http.authorizeRequests()
		        .antMatchers("/static/**", "/layui/**", "/login/**",
		                "/public/**", "/css/**", "/js/**", "/images/**",
		                "/webjars/**", "**/favicon.ico", "/index", "/druid/**")
		        .permitAll().anyRequest().authenticated().and().formLogin()
		        .loginPage(LOGIN_URL).defaultSuccessUrl("/index").permitAll()
		        .and().logout().logoutUrl("/logout").logoutSuccessUrl(LOGIN_URL)
		        .permitAll().invalidateHttpSession(true)
		        .deleteCookies("JSESSIONID")
		        .logoutSuccessHandler(logoutSuccessHandler()).and()
		        .sessionManagement().maximumSessions(10).expiredUrl(LOGIN_URL);
		http.authorizeRequests().antMatchers("/system/**")
		        .hasRole(RoleType.ROLE_ADMIN.getRoleTypeEnName());
		http.authorizeRequests().antMatchers("/user/**")
		        .hasRole(RoleType.ROLE_GUEST.getRoleTypeEnName());
		http.authorizeRequests().antMatchers("/db/**")
		        .hasRole(RoleType.ROLE_DBA.getRoleTypeEnName());
		http.headers().frameOptions().sameOrigin();
		http.rememberMe().rememberMeParameter("remember");
		super.configure(http);
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	/**
	 * // 密码加密
	 * 
	 * @see :
	 * @param :
	 * @return : BCryptPasswordEncoder
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	        throws Exception {
		// PasswordEncoder passwordEncoder = passwordEncoder();
		//
		// InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfigurer = auth
		// .inMemoryAuthentication().passwordEncoder(passwordEncoder);
		//
		// userConfigurer.withUser("sunliuping")
		// .password(passwordEncoder.encode(LOGIN_PASSWORD)).roles("USER");
		// userConfigurer.withUser("superAdmin")
		// .password(passwordEncoder.encode(LOGIN_PASSWORD))
		// .roles("USER", "ADMIN");
		// userConfigurer.withUser("admin")
		// .password(passwordEncoder.encode(LOGIN_PASSWORD))
		// .roles("USER", "ADMIN");
		auth.userDetailsService(sysUserService)
		        .passwordEncoder(passwordEncoder());
		auth.eraseCredentials(false);
	}

	/**
	 * 登出处理
	 * 
	 * @see :
	 * @param :
	 * @return : LogoutSuccessHandler
	 * @return
	 */
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest httpServletRequest,
			        HttpServletResponse httpServletResponse,
			        Authentication authentication)
			        throws IOException, ServletException {
				try {
					SecurityUser user = (SecurityUser) authentication
					        .getPrincipal();
					logger.info("USER : " + user.getUserAccount()
					        + " LOGOUT SUCCESS !  ");
				} catch (Exception e) {
					logger.info("LOGOUT EXCEPTION : {} : ", e.getMessage());
				}
				httpServletResponse.sendRedirect(LOGIN_URL);
			}
		};
	}

	@Bean
	RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();

		String roleHierarchy = "ROLE_DBA > ROLE_ADMIN  ROLE_ADMIN>ROLE_USER";
		roleHierarchyImpl.setHierarchy(roleHierarchy);
		return roleHierarchyImpl;
	}
}
