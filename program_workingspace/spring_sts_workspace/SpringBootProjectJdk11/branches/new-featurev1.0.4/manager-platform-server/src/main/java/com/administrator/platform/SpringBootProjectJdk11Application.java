package com.administrator.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:21:00
 * @see:
 */
@SpringBootApplication
@EntityScan(value = "com.administrator.platform.model")
@MapperScan(basePackages = { "com.administrator.platform.mapper" })
@ServletComponentScan
public class SpringBootProjectJdk11Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectJdk11Application.class, args);
	}
}
