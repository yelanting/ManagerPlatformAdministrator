
/**
 * @see : Project Name:yunxiaoconnect-server
 * @see : File Name:ObjectOperationUtils.java
 * @author : 孙留平
 * @since : 2019年5月21日 上午9:35:17
 * @see:
 */

package com.administrator.platform.util;

import java.lang.reflect.Field;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author : Administrator
 * @since : 2019年5月21日 上午9:35:17
 * @see :
 */
public class ObjectOperationUtils {
	private ObjectOperationUtils() {

	}

	/**
	 * 把对象转换成键值参数
	 * 
	 * @see :
	 * @param :
	 * @return : MultiValueMap<String,Object>
	 * @param object
	 * @return
	 */
	public static MultiValueMap<String, Object> fromObject(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				multiValueMap.add(field.getName(), field.get(object));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return multiValueMap;
	}

}
