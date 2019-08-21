/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:05:01
 * @see:
 */
package com.administrator.platform.service;

import com.administrator.platform.vo.JavaParserDTO;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:05:01
 * @see :
 */
public interface JavaParserService {
    /**
     * 解析java源文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param javaParserDTO
     */
    String parseJavaFiles(JavaParserDTO javaParserDTO);
}
