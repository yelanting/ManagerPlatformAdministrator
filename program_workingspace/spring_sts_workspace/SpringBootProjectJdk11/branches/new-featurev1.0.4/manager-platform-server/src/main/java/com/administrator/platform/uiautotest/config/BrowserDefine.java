package com.administrator.platform.uiautotest.config;

/**
 * @author : 孙留平
 * @since : 2018年9月7日 下午2:21:00
 * @see:
 */
public class BrowserDefine {
    /**
     * 项目所需要的driver文件路径
     */
    public static final String CURRENT_PROJECT_NEEDED_SOURCE_FILE_PATH = "/source/drivers/";

    public enum BrowserTypes {
        /**
         * chrome浏览器
         */

        CHROME,
        /**
         * FireForx浏览器
         */
        FIREFOX,

        /**
         * IE浏览器
         */
        IE
    }
}
