package com.administrator.platform.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 从上下文中对bean操作的工具类
 * 
 * @author : Administrator
 * @since : 2019年8月13日 下午3:40:04
 * @see :
 */
@Component("springBeanUtil")
public class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	        throws BeansException {
		SpringBeanUtil.applicationContext = applicationContext;
	}

	/**
	 * 通过名称在spring容器中获取对象
	 * 
	 * @param beanName
	 *            beanName
	 * @return Bean Object
	 */
	public static Object getBeanFromSpringByBeanName(String beanName) {
		return applicationContext.getBean(beanName);
	}

	/**
	 * 通过名称在spring容器中获取对象,给插件用的方法
	 * 
	 * @param beanName
	 *            beanName
	 * @return Bean Object
	 */
	public static Object getBeanFromSpringByBeanNameForFacade(String beanName) {
		return applicationContext.getBean(beanName);
	}

}
