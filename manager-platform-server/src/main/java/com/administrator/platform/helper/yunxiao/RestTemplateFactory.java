/**
 * Project Name: manager-platform-server
 * File Name: RestTemplateFactory.java
 * Package Name: com.administrator.platform.helper.yunxiao
 * Date: 2019年10月9日 下午5:00:29
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.helper.yunxiao;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author : 孙留平
 * @since : 2019年10月9日 下午5:00:29
 * @see :
 */
public class RestTemplateFactory {
	private RestTemplateFactory() {

	}

	private static class RestTemplateHolder {
		private static final RestTemplate restTemplate = new RestTemplate();
	}

	public static RestTemplate getRestTemplate() {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10 * 1000);
		requestFactory.setReadTimeout(10 * 1000);

		RestTemplateHolder.restTemplate.setRequestFactory(requestFactory);

		return RestTemplateHolder.restTemplate;
	}

	public static void main(String[] args) {
		RestTemplateFactory.getRestTemplate();
	}
}
