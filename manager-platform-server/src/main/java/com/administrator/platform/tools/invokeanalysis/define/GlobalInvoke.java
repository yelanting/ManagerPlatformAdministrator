/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 下午3:55:33
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.define;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.administrator.platform.tools.invokeanalysis.vo.MethodInfoVO;

public class GlobalInvoke {
	private GlobalInvoke() {

	}

	public static final Map<String, Map<String, List<MethodInfoVO>>> ALL_INVOKE_RELATION_SHIP = new HashMap<>();
}
