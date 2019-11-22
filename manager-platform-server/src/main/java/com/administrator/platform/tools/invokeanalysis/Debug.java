/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 上午9:16:14
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.invokeanalysis.define.GlobalInvoke;
import com.administrator.platform.tools.invokeanalysis.visitor.ClassFileReader;

import net.sf.json.JSONObject;

public class Debug {
	private static final Logger LOGGER = LoggerFactory.getLogger(Debug.class);

	public static void main(String[] args) {
		// String classFileFolder =
		// "D:\\TianQue\\tq-product\\tq-robot\\branches\\tq-robot-1.0.0\\tq-robot-server\\target\\classes\\com";
		//
		// ClassFileDealRunner.analysisClassFolder(classFileFolder);

		String fileString = "D:\\testworkspace\\class\\OrganizationController.class";
		new ClassFileReader(fileString).visit();
		// String sSString = "com/tianque/robot/service/impl/OrganizationServiceImpl"
		// .replace("/", ".");
		//
		// System.out.println(sSString.substring(sSString.lastIndexOf('.') + 1));
		// System.out.println(sSString.substring(0, sSString.lastIndexOf('.')));

		// Set<Entry<String, Map<String, List<MethodInfoVO>>>> invokeEntry = GlobalInvoke.ALL_INVOKE_RELATION_SHIP
		// .entrySet();
		//
		// System.out.println(
		// "================================================================");
		// for (Entry<String, Map<String, List<MethodInfoVO>>> entry : invokeEntry) {
		//
		// System.out.println(entry.getKey() + "===>" + entry.getValue());
		// }

		LOGGER.debug(JSONObject
		        .fromObject(GlobalInvoke.ALL_INVOKE_RELATION_SHIP).toString());
	}
}
