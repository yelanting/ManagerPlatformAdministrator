/**
 * @author : 孙留平
 * @since : 2018年10月13日 下午3:05:25
 * @see:
 */
package com.administrator.platform.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * @author : Administrator
 * @since : 2018年10月13日 下午3:05:25
 * @see : pagehelper的配置信息
 */
@Configuration
public class MyBatisPageHelperConfig {

    /**
     * 返回pagehelper的配置
     * 
     * @see :
     * @param :
     * @return : PageHelper
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
