/// **
// * @author 作者: 孙留平
// * @since 创建时间: 2019年8月7日 下午9:07:53
// * @see:
// */
// package com.administrator.platform.config;
//
// import org.quartz.spi.TriggerFiredBundle;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
// import org.springframework.scheduling.quartz.AdaptableJobFactory;
// import org.springframework.stereotype.Component;
//
/// **
// * 解决spring bean注入Job的问题
// */
// @Component
// public class SpringJobFactory extends AdaptableJobFactory {
// @Autowired
// private AutowireCapableBeanFactory capableBeanFactory;
//
// @Override
// protected Object createJobInstance(TriggerFiredBundle bundle)
// throws Exception {
// // 调用父类的方法
// Object jobInstance = super.createJobInstance(bundle);
// // 进行注入
// capableBeanFactory.autowireBean(jobInstance);
// return jobInstance;
// }
// }
