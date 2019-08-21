/**
 * @author : 孙留平
 * @since : 2019年4月1日 下午8:21:04
 * @see:
 */
package com.administrator.platform.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.administrator.platform.util.TempFileUtil;

/**
 * @author : Administrator
 * @since : 2019年4月1日 下午8:21:04
 * @see :
 */
@Configuration
public class ServletMultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();

        multipartConfigFactory
                .setLocation(TempFileUtil.getDefaultTempBaseFolder());

        return multipartConfigFactory.createMultipartConfig();
    }
}
