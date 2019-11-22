/**
 * @author 作者: 孙留平
 * @since 创建时间: 2019年8月27日 下午8:07:16
 * @see:
 */
package com.administrator.platform.tools.javaparser;

public class JavaParserConstDefine {
    /**
     * 默认存放解析内容的文件夹
     */
    public static final String DEFAULT_JAVA_PARSER_FOLDER = "javaParser";
    public static final String DEFAULT_JAVA_PARSER_RESULT_FOLDER = "javaParserResult";

    public static final String DEFAULT_REQUEST_MAPPING_FILE = "requestMapping.txt";
    public static final String DEFAULT_REQUEST_MAPPING_FILE_EXCEL = "requestMapping.xlsx";

    /**
     * 默认的Controller方法级别的注解
     */
    public static final String DEFAULT_CLASS_CONTROLLER_REQUEST_MAPPING = "RequestMapping";

    /**
     * 默认方法级别的注解
     */
    public static final String DEFAULT_METHOD_REQUEST_MAPPING = "Mapping";
    public static final String DEFAULT_METHOD_REQUEST_MAPPING_OF_STRUTS = "Action";
    public static final String DEFAULT_CONTROLLER_CLASS_SIGN = "Controller";
    public static final String DEFAULT_CONTROLLER_ANNOTATION_SIGN = "@Controller";
    public static final String DEFAULT_CONTROLLER_COMPONENT_SIGN = "@Component";
    public static final String DEFAULT_CONTROLLER_REST_CONTROLLER_SIGN = "@RestController";
    public static final String DEFAULT_CONTROLLER_REQUEST_MAPPING_SIGN = "@RequestMapping";
    public static final String DEFAULT_CONTROLLER_REQUEST_MAPPING_SIGN_STRUTS = "@Namespace";

    public static final String DEFAULT_STRUTS_REQUEST_MAPPING_SIGN = "@Action";
    public static final String DEFAULT_METHOD_ANNOTATION_CHAR = "@";

    /**
     * package标识
     */
    public static final String DEFAULT_PACKAGE_SIGN = "package";
    /**
     * 语句末尾标识
     */
    public static final String DEFAULT_PACKAGE_END_SIGN = ";";
    public static final String DEFAULT_MULTI_VALUES_SEP_SIGN = ",";

    /**
     * 默认的分割符
     */
    public static final String DEFAULT_REQUEST_MAPPING_SEP_CHAR = "\"";
    public static final char DEFAULT_REQUEST_MAPPING_START_SEP_CHAR = '/';

    private JavaParserConstDefine() {

    }
}
