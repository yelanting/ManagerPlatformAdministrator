/**
 * Project Name: manager-platform-server
 * File Name: YunXiaoLoginHelper.java
 * Package Name: com.administrator.platform.helper.yunxiao
 * Date: 2019年10月9日 下午4:52:16
 * Copyright (c) 2019, qing121171@gmail.com All Rights Reserved.
 */
package com.administrator.platform.helper.yunxiao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.administrator.platform.constdefine.YunXiaoDefine;
import com.administrator.platform.core.base.util.StringUtil;

/**
 * @author : 孙留平
 * @since : 2019年10月9日 下午4:52:16
 * @see :
 */
public class YunXiaoLoginHelper {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(YunXiaoLoginHelper.class);

	public YunXiaoLoginHelper() {
		this(YunXiaoDefine.DEFAULT_LOGIN_USERNAME,
		        YunXiaoDefine.DEFAULT_LOGIN_PASSWORD);
	}

	public YunXiaoLoginHelper(String username, String password) {
		login(username, password);
	}

	private List<String> getDefaultCookies() {
		List<String> cookiesList = new ArrayList<>();
		cookiesList.add("session=4f7e76d8-1b90-4eed-a51a-25c26933f30c");
		cookiesList.add("ak_region_id=3396a8e5-13ed-4d57-97dc-2417fa444a50");
		cookiesList.add("ak_user_timezone=Asia/Shanghai");
		cookiesList.add("ak_user_locale=zh_CN");
		cookiesList.add("cna=MxYjFjMY/Q4CAXrr8/dC+d7o");
		cookiesList.add(
		        "_pk_ref.1.f1f6=%5B%22%22%2C%22%22%2C1570605573%2C%22http%3A%2F%2Fuc.aliyun.com%2Finternal%2Flogin%3Foauth_callback%3Dhttp%3A%2F%2Fuc.aliyun.com%2Flogin%3Ffrom%3Dhttp%3A%2F%2Fyunxiao.aliyun.com%3A80%26ctx%3D%2Ftorrent%2Fmanagement%22%5D");
		cookiesList.add("a_l=internal");
		cookiesList.add("a_d=.aliyun.com");
		cookiesList.add(
		        "a_u=66E5EADDE27582337C7D71D29D556D4F1899CC0B21CECD66DFD5DB4EE8B0CC4E6B3608F8E29AEAFD7FC6C7E2CA678428D78228EEA9915D8F0DC12D41E7B322F937A49070B2920967FB7D55B21F23A7CD98D2B492F71DB63F1D86DE40432EEB6A9A1DE547D8A14B35C89C6B8CE5C2A9309C862EE783D4138876EA92677608D89FA4FFDD1E7BD7818D555C71ABEA1D25FAB19E3F8792088B88BE35C426AE0703A9315F1246F256B393ECBDF6CC82BB45917014C2216AB375E36C373D02110B565E0595871D965B2FA4167F9A0E7123238509AB302AD6F2BC35541269E82AA409BE2F3FB76AEC0D19C73FE947B5A2E64BC18319173D8AC7017C99C94AC2ABB00250BB58508EFBE35317EEA27A0205E3FED9F2B65DFAA63FB50C31A02719AB68EBEE35C16B045171F3E14AE4F0352BBA5C431314DDE1054FAAC5D5F66E4F1ED80AC481D2302AE2998BE36CC8B8FDB0152CB281B186C51BD0C9BB5E11FB77A78C027567E188409B148D17247991E3FC435AB68467CE64D9910875D6460444B1ACCAA82FF3672B68436EAA127DC50D9D8617E0D7DB115DB597FF29FD5799A6096A7C8A8E3FD3A7197CE2774EA035843EA1514AF75CB43219ACA16FD7E00924AFAF5FA5394F4EA0930E9CE6B349269C102CD8437825668C89685FB4FA7028888914E267736C18A271343A3FD43FB1070EE7676F");
		cookiesList.add(
		        "a_u_t=71E814381A732C7DD2456BB3C4E255DD; aone_user_uuid=2b463bfa-f6e7-4fa3-97da-62f3989e9c5d");
		cookiesList.add(
		        "a_u_k=03E65ABBFA4213CF7597E0BEB9642C0BD5DBE8503A4AC58786915EFD274309AEBC29ED828C0591B8D7FCFB2766F1DA24605D5106450B08C4CD6724B293C568A982BD554C0C280DEFFD6816E77255D202BBD2D1E1C015582DEB737B9CD697EFDF995DC860CDCB32C7BC1E2B47CD3D8E94F853BC3236E72A59FB067664455C0C26D627535D65DB4767A62FC5535F9AB7CE61212AD1A8C0E79777782158ED05D332283CF4561107A290D024137ADE3562EDC625808FC67E1AA14DE40437F1F67EA323955CF8721AA4F391D321011F1BEC4D0F1712F8EB9C7877CB066160617FB17A0A80EAB3F50D4581E2D8AF5AF8CD005870AF30394A7BD027C40C8093F0AF0A2FAF674D7424AAAC16A6877140D6FDC0EED86A53657F6F6BDDDAFE376512FBE41BA884EC16CBCDA2B14C5B861A0B6F4713CF8481FA1219B3F3270A923408614420");
		cookiesList.add(
		        "isg=BPHxr6G2CB3xzqQIa4U_XchQAH2L3mVQ2aby-dMGe7jX-hBMAyyUICAbGI6cMv2I");
		cookiesList.add(
		        "l=cBgFtd1cq4aaibqOBOfaourza779uIRbzsPzaNbMiICPOF5H54AGWZBInSTMCnGVp62MV3SgKvQUBeYBqQe98NtummUYg");
		cookiesList.add(
		        "_pk_id.1.f1f6=745bee08f6803783.1570495916.7.1570606797.1570605573.");
		cookiesList.add("SERVERID=192.168.100.98:10080");
		return cookiesList;
	}

	public void login(String username, String password) {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("username", username);
		multiValueMap.add("password", password);
		List<String> cookiesList = getDefaultCookies();
		ResponseEntity<String> loginPageResponseEntity = getLoginPage();
		String csrfToken = getCsrfTokenFromLoginPageResponse(
		        loginPageResponseEntity);
		multiValueMap.add("_csrf", csrfToken);
		cookiesList.add("XSRF-TOKEN=" + csrfToken);
		HttpHeaders httpHeaders = getDefaultHttpHeaders();
		httpHeaders.addAll(loginPageResponseEntity.getHeaders());
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.put(HttpHeaders.COOKIE, cookiesList);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(
		        multiValueMap, httpHeaders);
		ResponseEntity<String> responseEntity = RestTemplateFactory
		        .getRestTemplate().exchange(YunXiaoDefine.LOGIN_ACTION_URL,
		                HttpMethod.POST, requestEntity, String.class);
		LOGGER.error("结果值:{}", responseEntity.getBody());

		String internalUsername = getInternalUsername(
		        responseEntity.getHeaders());
		cookiesList.add(YunXiaoDefine.DEFAULT_INTERNAL_USERNAME_KEY + "="
		        + internalUsername);

		ResponseEntity<String> fromPageResponse = getFromPageBeforeLogin(
		        httpHeaders);

		String auCookieValue = getAuValue(fromPageResponse.getHeaders());
		cookiesList.add(YunXiaoDefine.DEFAULT_AU_KEY + "=" + auCookieValue);

		if (null != YunXiaoGlobal.httpHeaders) {
			YunXiaoGlobal.httpHeaders.clear();
		}

		YunXiaoGlobal.httpHeaders = httpHeaders;

	}

	private ResponseEntity<String> getLoginPage() {
		String url = YunXiaoDefine.BASE_LOGIN_URL;
		ResponseEntity<String> loginPageResponseEntity = RestTemplateFactory
		        .getRestTemplate().getForEntity(url, String.class);
		LOGGER.debug("登录首页响应:{}", loginPageResponseEntity.getBody());
		return loginPageResponseEntity;
	}

	private ResponseEntity<String> getFromPageBeforeLogin(
	        HttpHeaders httpHeaders) {
		String url = YunXiaoDefine.DEFAULT_YUNXIAO_FROM_INDEX_PAGE;

		HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);

		ResponseEntity<String> loginPageResponseEntity = RestTemplateFactory
		        .getRestTemplate()
		        .exchange(url, HttpMethod.GET, requestEntity, String.class);
		LOGGER.debug("FromPage响应:{}", loginPageResponseEntity.getBody());
		LOGGER.debug("FromPage响应Cookie:{}",
		        loginPageResponseEntity.getHeaders());
		return loginPageResponseEntity;
	}

	private HttpHeaders getDefaultHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("user-agent",
		        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
		httpHeaders.add("host", "uc.aliyun.com");
		// httpHeaders.add("origin", "http://uc.aliyun.com");
		// httpHeaders.add("referer", YunXiaoDefine.BASE_LOGIN_URL);
		return httpHeaders;
	}

	private String getCsrfTokenFromLoginPageResponse(
	        ResponseEntity<String> responseEntity) {

		String responseContext = responseEntity.getBody();

		Pattern pattern = Pattern.compile("_csrf = \"(.*?)\";");

		Matcher matcher = pattern.matcher(responseContext);

		if (matcher.find()) {
			LOGGER.debug("获取到的csrftoken为:{}", matcher.group(1));
			return matcher.group(1);
		}

		return null;
	}

	private List<String> getHeaderValueByHeaderKeyFromHeaders(
	        HttpHeaders httpHeaders, String headerKey) {
		if (null == httpHeaders || StringUtil.isEmpty(headerKey)) {
			return new ArrayList<String>();
		}
		Set<Map.Entry<String, List<String>>> entries = httpHeaders.entrySet();

		Iterator iterator = entries.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> aEntry = (Entry<String, List<String>>) iterator
			        .next();

			if (headerKey.equals(aEntry.getKey())) {
				LOGGER.debug("获取到的:[{}]为:[{}]", headerKey, aEntry.getValue());
				return aEntry.getValue();
			}
		}
		return new ArrayList<>();
	}

	private List<String> getSetCookieValue(HttpHeaders httpHeaders) {
		return getHeaderValueByHeaderKeyFromHeaders(httpHeaders, "Set-Cookie");
	}

	private String getInternalUsername(HttpHeaders httpHeaders) {
		List<String> setCookieValues = getSetCookieValue(httpHeaders);
		return getCookieValueByCookieKeyFromSetCookies(setCookieValues,
		        YunXiaoDefine.DEFAULT_INTERNAL_USERNAME_KEY);
	}

	private String getAuValue(HttpHeaders httpHeaders) {
		List<String> setCookieValues = getSetCookieValue(httpHeaders);
		return getCookieValueByCookieKeyFromSetCookies(setCookieValues,
		        YunXiaoDefine.DEFAULT_AU_KEY);
	}

	private String getCsrfToken(HttpHeaders httpHeaders) {
		List<String> setCookieValues = getSetCookieValue(httpHeaders);
		return getCookieValueByCookieKeyFromSetCookies(setCookieValues,
		        "XSRF-TOKEN");
	}

	private String getCookieValueByCookieKeyFromSetCookies(
	        List<String> setCookies, String cookieKey) {
		String eachSetCookieValue = null;
		for (int i = 0; i < setCookies.size(); i++) {
			eachSetCookieValue = setCookies.get(i);
			LOGGER.debug("eachSetCookie:{}", eachSetCookieValue);
			if (eachSetCookieValue.contains(cookieKey + "=")) {
				int fromIndex = (cookieKey + "=").length();
				int endIndex = eachSetCookieValue.indexOf(";");
				String cookieValue = eachSetCookieValue.substring(fromIndex,
				        endIndex);
				LOGGER.debug("获取到cookie:{},的值为:{}", cookieKey, cookieValue);
				return cookieValue;
			}
		}
		return null;
	}

	private void displayHeaders(HttpHeaders httpHeaders) {
		if (null == httpHeaders) {
			return;
		}
		Set<Map.Entry<String, List<String>>> entries = httpHeaders.entrySet();
		Iterator iterator = entries.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> aEntry = (Entry<String, List<String>>) iterator
			        .next();
			LOGGER.debug("headerKey:[{}]的headerValue为:[{}]", aEntry.getKey(),
			        aEntry.getValue());
		}
	}
}
