/**
 * @author : 孙留平
 * @since : 2018年11月23日 上午11:24:34
 * @see:
 */
package com.administrator.platform.tools.javaparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Administrator
 * @since : 2018年11月23日 上午11:24:34
 * @see :
 */
public class JavaParserGlobal {
    private JavaParserGlobal() {

    }

    public static final List<File> ALL_FILES = new ArrayList<>(16);
    public static final String FILE_FOLDER_NAME = "files";
    public static final String ALL_METHOD_NAME_FILE = "all.txt";
    public static final String ALL_PARAMS_ARE_STRING_METHOD_NAME_FILE = "allString.txt";
    public static final String NOT_ALL_PARAMS_ARE_STRING_NAME_FILE = "notAllString.txt";
    public static final List<String> ALL_METHODS_LIST = new ArrayList<>();
    public static final List<String> ALL_STRING_PARAMS_METHODS_LIST = new ArrayList<>();
    public static final List<String> ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST = new ArrayList<>();

    public static final String RECORD_SEPERATOR = ":::";
}
