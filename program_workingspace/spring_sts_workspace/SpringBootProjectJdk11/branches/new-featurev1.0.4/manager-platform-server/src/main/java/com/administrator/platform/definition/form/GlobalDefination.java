/**
 * @author : 孙留平
 * @since : 2019年3月13日 下午2:04:20
 * @see:
 */
package com.administrator.platform.definition.form;

import java.nio.charset.Charset;

/**
 * @author : Administrator
 * @since : 2019年3月13日 下午2:04:20
 * @see :
 */
public class GlobalDefination {
    private GlobalDefination() {

    }

    /**
     * utf-8编码
     */
    public static final String CHAR_SET_DEFAULT = Charset.defaultCharset()
            .toString();

    /**
     * gbk编码
     */
    public static final String CHAR_SET_GBK = "gbk";
}
