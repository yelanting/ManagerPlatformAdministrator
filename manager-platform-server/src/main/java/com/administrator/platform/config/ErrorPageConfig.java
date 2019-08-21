/**
 * @author : 孙留平
 * @since : 2019年1月15日 下午10:53:57
 * @see:
 */
package com.administrator.platform.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author : Administrator
 * @since : 2019年1月15日 下午10:53:57
 * @see :
 */
@Configuration
public class ErrorPageConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED,
                    "/page/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND,
                    "/page/404.html");
            ErrorPage error500Page = new ErrorPage(
                    HttpStatus.INTERNAL_SERVER_ERROR, "/page/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }
}
