package com.administrator.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.administrator.platform.util.TempFileUtil;

/**
 * 
 * @author : Administrator
 * @since : 2019年3月8日 上午9:55:17
 * @see :
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

	/**
	 * 以前要访问一个页面需要先创建个Controller控制类，在写方法跳转到页面
	 * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.html页面了
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("welcome");
		registry.addViewController("/tologin").setViewName("login");
		registry.addViewController("/welcome").setViewName("welcome");
		registry.addViewController("/error/errordeal")
		        .setViewName("error/errordealogin");
		registry.addViewController("/error/unauthorized")
		        .setViewName("error/unauthorized");
		registry.addViewController("/main").setViewName("main");
	}

	/**
	 * 添加资源映射，可以静态访问文件
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(
		        "classpath:/META-INF/resources/", "classpath:/resources/",
		        "classpath:/static/", "classpath:/public/",
		        "classpath:/templates/", "classpath:/",
		        "file:" + TempFileUtil.getDefaultTempBaseFolder() + "/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
		        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
		        .maxAge(168000).allowedHeaders("*").allowCredentials(true);
	}
}
