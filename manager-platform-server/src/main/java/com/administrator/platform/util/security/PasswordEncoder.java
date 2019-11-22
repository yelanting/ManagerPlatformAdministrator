package com.administrator.platform.util.security;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码加密
 * 
 * @author Louis
 * @date Jan 13, 2019
 */
public class PasswordEncoder {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PasswordEncoder.class);

	private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
	        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static final String MD5 = "MD5";
	private static final String SHA = "SHA";

	private Object salt;
	private String algorithm;

	public PasswordEncoder(Object salt) {
		this(salt, MD5);
	}

	public PasswordEncoder() {
		this(null, MD5);
	}

	public PasswordEncoder(Object salt, String algorithm) {
		this.salt = salt;
		this.algorithm = algorithm;
	}

	/**
	 * 密码加密
	 * 
	 * @param rawPass
	 * @return
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(
			        md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
		} catch (Exception ex) {
			LOGGER.error("加密失败:{}", ex.getMessage());
		}
		return result;
	}

	/**
	 * 密码匹配验证
	 * 
	 * @param encPass
	 *            密文
	 * @param rawPass
	 *            明文
	 * @return
	 */
	public boolean matches(String encPass, String rawPass) {
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);

		return pass1.equals(pass2) || encPass.equals(rawPass);
	}

	private String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	private String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节转换为16进制
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}
}