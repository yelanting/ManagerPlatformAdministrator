
/**
 * @see : Project Name:commons-util
 * @see : File Name:HttpClientUtils.java
 * @author : 孙留平
 * @since : 2019年5月20日 上午10:02:46
 * @see:
 */

package com.administrator.platform.util.http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.enums.RequestContentTypeEnum;

import net.sf.json.JSONObject;

/**
 * @author : Administrator
 * @since : 2019年5月20日 上午10:02:46
 * @see :
 */
public class HttpClientUtils {
	/**
	 * 主地址
	 */

	private String hostUrl;

	/**
	 * 登陆
	 */

	private String loginAction;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否开启https
	 */
	private boolean httpsEnabled;

	/**
	 * 代理地址
	 */
	private String proxyUrl;

	private static HttpClient httpClient;

	private static final Logger logger = LoggerFactory
	        .getLogger(HttpClientUtils.class);

	static {
		// httpClient = HttpClients.custom().build();
		httpClient = HttpClientBuilder.create().build();
	}

	/**
	 * 私有构造
	 * 
	 * @see :
	 */
	private HttpClientUtils() {
	}

	/**
	 * 全量字段构造
	 * 
	 * @see :
	 * @param origin
	 */
	private HttpClientUtils(HttpClientUtils origin) {
		this.hostUrl = origin.hostUrl;
		this.loginAction = origin.loginAction;
		this.userName = origin.userName;
		this.password = origin.password;
		this.httpsEnabled = origin.httpsEnabled;
	}

	/**
	 * 静态内部类
	 * 
	 * @author : Administrator
	 * @since : 2019年5月20日 上午10:16:21
	 * @see :
	 */

	public static class Builder {
		private HttpClientUtils httpClientUtils;

		public Builder() {
			httpClientUtils = new HttpClientUtils();
		}

		public Builder hostUrl(String hostUrl) {
			httpClientUtils.hostUrl = hostUrl;
			return this;
		}

		public Builder loginAction(String loginAction) {
			httpClientUtils.loginAction = loginAction;
			return this;
		}

		public Builder userName(String userName) {
			httpClientUtils.userName = userName;
			return this;
		}

		public Builder password(String password) {
			httpClientUtils.password = password;
			return this;
		}

		public Builder httpsEnabled(boolean enabled) {
			httpClientUtils.httpsEnabled = enabled;
			return this;
		}

		public Builder proxyUrl(String proxyUrl) {
			httpClientUtils.proxyUrl = proxyUrl;
			return this;
		}

		public HttpClientUtils build() {
			return new HttpClientUtils(httpClientUtils);
		}
	}

	/**
	 * @see :
	 * @param hostUrl
	 * @param loginAction
	 * @param userName
	 * @param password
	 */
	public HttpClientUtils(String hostUrl, String loginAction, String userName,
	        String password) {
		super();
		this.hostUrl = hostUrl;
		this.loginAction = loginAction;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @see : hostUrl
	 * @return the hostUrl
	 */
	public String getHostUrl() {
		return hostUrl;
	}

	/**
	 * @param hostUrl
	 *            the hostUrl to set
	 */
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	/**
	 * @see : loginAction
	 * @return the loginAction
	 */
	public String getLoginAction() {
		return loginAction;
	}

	/**
	 * @param loginAction
	 *            the loginAction to set
	 */
	public void setLoginAction(String loginAction) {
		this.loginAction = loginAction;
	}

	/**
	 * @see : userName
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @see : password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @see : httpsEnabled
	 * @return the httpsEnabled
	 */
	public boolean isHttpsEnabled() {
		return httpsEnabled;
	}

	/**
	 * @param httpsEnabled
	 *            the httpsEnabled to set
	 */
	public void setHttpsEnabled(boolean httpsEnabled) {
		this.httpsEnabled = httpsEnabled;
	}

	/**
	 * @see : proxyUrl
	 * @return the proxyUrl
	 */
	public String getProxyUrl() {
		return proxyUrl;
	}

	/**
	 * @param proxyUrl
	 *            the proxyUrl to set
	 */
	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}

	/**
	 * 发送get请求
	 * 
	 * @see :
	 * @param :
	 * @return : void
	 * @param getUrl
	 * @param params
	 */
	public static void doGet(String getUrl, Map<String, Object> params) {
		HttpGet httpGet = new HttpGet(getUrl);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			System.out.println(
			        "httpResponse:" + httpResponse.getEntity().getContent());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static StringEntity makeEntity(String postData) {
		return new StringEntity(postData, Consts.UTF_8);
	}

	private static StringEntity makeEntity(List<NameValuePair> nameValuePairs) {
		return new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
	}

	private static HttpEntity makeEntity(List<NameValuePair> nameValuePairs,
	        Map<String, Object> params) {
		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		addTextBodies(builder, nameValuePairs);
		addBinaryBodies(builder, (String) params.get("filePath"),
		        (String) params.get("fileName"));
		HttpEntity entity = builder.build();
		return entity;

	}

	private static MultipartEntityBuilder addTextBodies(
	        MultipartEntityBuilder builder,
	        List<NameValuePair> nameValuePairs) {
		for (NameValuePair nameValuePair : nameValuePairs) {
			builder.addTextBody(nameValuePair.getName(),
			        nameValuePair.getValue());
		}

		return builder;
	}

	private static MultipartEntityBuilder addBinaryBodies(
	        MultipartEntityBuilder builder, String filePath, String paramKey) {
		builder.addBinaryBody(paramKey, new File(filePath),
		        ContentType.APPLICATION_OCTET_STREAM, "file.ext");
		return builder;
	}

	private static String postData(String url, StringEntity stringEntity,
	        String requestType) {

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);

		if (StringUtil.isEmpty(requestType)) {
			requestType = "json";
		}

		if (RequestContentTypeEnum.JSON.toString()
		        .equalsIgnoreCase(requestType)) {
			httpPost.setHeader("Accept", "application/json");
			// httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Content-type",
			        "application/x-www-form-urlencoded");
		}
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			return httpResponse.getEntity().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 提供给外部的post报文发送方法
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param url
	 * @param postParams
	 * @return
	 */
	public static String post(String url, String postParams) {
		StringEntity stringEntity = makeEntity(postParams);

		return postData(url, stringEntity, null);
	}

	/**
	 * 暴露的get请求
	 * 
	 * @see :
	 * @param :
	 * @return : String
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);

			System.out.println("get结果:" + httpResponse.getEntity().toString());
			return httpResponse.getEntity().toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String get(String url, List<NameValuePair> nameValuePairs) {
		return null;
	}

	public static void main(String[] args) {
		// get("http://192.168.110.190:8020/codeCoverage/getList");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 4);
		String postData = "id=4";
		post("http://localhost:8022/task/deleteTask", postData.toString());
	}

}
