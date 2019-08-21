/**
 * @author : 孙留平
 * @since : 2018年9月11日 下午2:58:27
 * @see:
 */
package com.administrator.platform.core.base.util;

/**
 * @author : Administrator
 * @since : 2018年9月11日 下午2:58:27
 * @see :
 */

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.definition.form.GlobalDefination;
import com.administrator.platform.exception.base.BusinessValidationException;

public class Md5Util {
    private static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private Md5Util() {

    }

    /**
     * 创建MD5加密字符串
     * 
     * @see :
     * @param :
     * @return : String
     * @param s
     * @return
     */
    public static String createMd5Str(String s) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        char[] str;
        try {
            byte[] btInput = s.getBytes(GlobalDefination.CHAR_SET_DEFAULT);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = null;
            mdInst = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
        } catch (Exception e) {
            logger.error("md5出错:{}", e.getMessage());
            throw new BusinessValidationException("md5出错");
        }
        return new String(str);
    }

    /**
     * 根据参数和密钥生成签名
     * 
     * @param map
     * @param secretKey
     * @return
     */
    public static String generateSign(Map map, String secretKey) {
        if (map == null) {
            throw new BusinessValidationException(
                    GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }
        try {
            Map<String, Object> sortedMap = new TreeMap<>(
                    new Comparator<String>() {
                        @Override
                        public int compare(String key1, String key2) {
                            return key1.compareTo(key2);
                        }
                    });
            sortedMap.putAll(map);
            StringBuffer signBuffer = new StringBuffer();
            for (Entry<String, Object> entry : sortedMap.entrySet()) {
                if (entry.getValue() != null) {
                    signBuffer.append(
                            entry.getKey() + entry.getValue().toString());
                }
            }
            return createMd5Str(signBuffer.toString() + secretKey);
        } catch (Exception e) {
            throw new BusinessValidationException("生成签名失败");
        }
    }

    public static String generateSign(String body, String secretKey,
            String url) {
        return createMd5Str(url + body + secretKey);
    }

    public static String generateSign(String body, String secretKey) {
        return createMd5Str(body + secretKey);
    }
}
