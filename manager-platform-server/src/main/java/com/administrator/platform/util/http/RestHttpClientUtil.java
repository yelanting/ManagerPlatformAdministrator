
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:RestHttpClientUtil.java
 * @author : 孙留平
 * @since : 2019年5月20日 下午3:57:32
 * @see:
 */

package com.administrator.platform.util.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.administrator.platform.vo.ResponseData;

/**
 * @author : Administrator
 * @since : 2019年5月20日 下午3:57:32
 * @see :
 */
public class RestHttpClientUtil {
	private RestTemplate restTemplate;

	private void init() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = getDefaultRequestFactory();
		restTemplate = new RestTemplate(clientHttpRequestFactory);
	}

	private SimpleClientHttpRequestFactory getDefaultRequestFactory() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(10 * 1000);
		requestFactory.setReadTimeout(10 * 1000);
		return requestFactory;
	}

	private HttpHeaders getDefaultHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	private HttpEntity<MultiValueMap<String, Object>> createHttpEntity(
	        MultiValueMap<String, Object> multiValueMap) {
		return new HttpEntity<>(multiValueMap, getDefaultHeaders());
	}

	public ResponseData execute(String url, HttpMethod httpMethod,
	        HttpEntity<MultiValueMap<String, Object>> httpEntity) {
		init();
		ResponseEntity<ResponseData> responseEntity = restTemplate.exchange(url,
		        httpMethod, httpEntity, ResponseData.class);
		return responseEntity.getBody();
	}

	public ResponseData post(String url,
	        MultiValueMap<String, Object> multiValueMap) {

		HttpEntity<MultiValueMap<String, Object>> httpEntity = createHttpEntity(
		        multiValueMap);
		return execute(url, HttpMethod.POST, httpEntity);
	}

	public ResponseData get(String url,
	        MultiValueMap<String, Object> multiValueMap) {
		HttpEntity<MultiValueMap<String, Object>> httpEntity = createHttpEntity(
		        multiValueMap);
		return execute(url, HttpMethod.GET, httpEntity);
	}
}
