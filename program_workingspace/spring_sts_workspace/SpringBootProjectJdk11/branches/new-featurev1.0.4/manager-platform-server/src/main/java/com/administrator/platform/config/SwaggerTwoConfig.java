/**
 * @author : 孙留平
 * @since : 2018年9月9日 上午10:20:50
 * @see:
 */
package com.administrator.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : Administrator
 * @since : 2018年9月9日 上午10:20:50
 * @see : swagger的配置类
 */

@Configuration
@EnableSwagger2
public class SwaggerTwoConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.administrator.platform.controller"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * @see :构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     * @param :
     * @return : ApiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("基于Swagger2的API文档").version("1.0").build();
    }
}
