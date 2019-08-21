/// **
// * @author 作者: 孙留平
// * @since 创建时间: 2019年8月7日 下午9:08:33
// * @see:
// */
// package com.administrator.platform.config;
//
// import java.io.IOException;
// import java.util.Properties;
//
// import org.quartz.Scheduler;
// import org.quartz.ee.servlet.QuartzInitializerListener;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.config.PropertiesFactoryBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
/// **
// * quartz配置
// */
// @Configuration
// public class SchedulerConfig {
//
// @Autowired
// private SpringJobFactory springJobFactory;
//
// @Bean(name = "schedulerFactoryBean")
// public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
// SchedulerFactoryBean factory = new SchedulerFactoryBean();
// factory.setAutoStartup(true);
// factory.setStartupDelay(5);// 延时5秒启动
// factory.setQuartzProperties(quartzProperties());
// // 注意这里是重点
// factory.setJobFactory(springJobFactory);
// return factory;
// }
//
// @Bean
// public Properties quartzProperties() throws IOException {
// PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
// propertiesFactoryBean
// .setLocation(new ClassPathResource("/quartz.properties"));
// propertiesFactoryBean.afterPropertiesSet();
// return propertiesFactoryBean.getObject();
// }
//
// /*
// * quartz初始化监听器
// */
// @Bean
// public QuartzInitializerListener executorListener() {
// return new QuartzInitializerListener();
// }
//
// /*
// * 通过SchedulerFactoryBean获取Scheduler的实例
// */
// @Bean(name = "scheduler")
// public Scheduler scheduler() throws IOException {
// return schedulerFactoryBean().getScheduler();
//
// }
// }