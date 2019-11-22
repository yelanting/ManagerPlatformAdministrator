/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月22日 下午3:04:43
 * @see:
 */
package com.administrator.platform.tools.invokeanalysis.vo;

public class Actions {
	public static final String PATH = Actions.class.getName().replace(".", "/");

	public static void methodStart(String owner, String name, String desc) {
		// 处理
		System.out.println("方法开始");
	}

	public static void methodEnd(String owner, String name, String desc) {
		// 处理
		System.out.println("方法结束");
	}

}
