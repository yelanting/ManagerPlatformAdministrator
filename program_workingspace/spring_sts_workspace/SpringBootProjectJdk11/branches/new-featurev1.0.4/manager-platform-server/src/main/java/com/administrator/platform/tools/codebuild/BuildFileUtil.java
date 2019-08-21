/**
 * @author : 孙留平
 * @since : 2019年3月26日 下午1:55:00
 * @see:
 */
package com.administrator.platform.tools.codebuild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.tools.jacoco.JacocoDefine;

/**
 * @author : Administrator
 * @since : 2019年3月26日 下午1:55:00
 * @see :
 */
public class BuildFileUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(BuildFileUtil.class);

    private BuildFileUtil() {

    }

    /**
     * 加载build.xml文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param buildXmlFilePath
     */
    public static List<Element> loadBuildFile(String buildXmlFilePath) {
        File buildXmlFile = new File(buildXmlFilePath);

        if (!buildXmlFile.exists()) {
            return new ArrayList<>();
        }

        Document document;
        try {
            document = new SAXReader().read(buildXmlFile);
            Element rootElement = document.getRootElement();
            List<Element> childrenElements = rootElement.elements();
            return childrenElements;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * 获取源代码路径
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param childrenElements
     * @return
     */
    public static List<String> getSourceCodeDirs(List<Element> targetElements) {
        List<String> sourceCodeDirs = new ArrayList<>();
        for (Element element : targetElements) {
            List<Element> childrenElementsUnderThisElement = element.elements();
            for (Element eachChildOfTarget : childrenElementsUnderThisElement) {
                if (CodeBuildDefaultDefines.CODE_BUILD_COMPILE_COMMAND
                        .equals(eachChildOfTarget.getName())) {
                    String srcDir = eachChildOfTarget.attributeValue(
                            CodeBuildDefaultDefines.CODE_BUILD_COMPILE_SRCDIR);
                    System.out.println(srcDir);
                    sourceCodeDirs.addAll(Arrays.asList(srcDir
                            .split(CodeBuildDefaultDefines.DEFAULT_DELIMETER)));

                    return sourceCodeDirs;
                }
            }
        }

        return sourceCodeDirs;
    }

    /**
     * 获取源代码路径
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param childrenElements
     * @return
     */
    public static List<String> getClassesCodeDirs(
            List<Element> targetElements) {
        List<String> classesCodeDirs = new ArrayList<>();
        for (Element element : targetElements) {
            System.out.println(element.getName());
            List<Element> childrenElementsUnderThisElement = element.elements();
            for (Element eachChildOfTarget : childrenElementsUnderThisElement) {
                System.out.println(
                        "eachChildOfTarget:" + eachChildOfTarget.getName());
                if (CodeBuildDefaultDefines.CODE_BUILD_COMPILE_COMMAND
                        .equals(eachChildOfTarget.getName())) {

                    System.out.println(eachChildOfTarget.getName() + "命中javac");

                    List<Attribute> attributes = eachChildOfTarget.attributes();
                    for (Attribute attribute : attributes) {
                        System.out.println(attribute.getName());
                    }
                    String destDir = eachChildOfTarget.attributeValue(
                            CodeBuildDefaultDefines.CODE_BUILD_COMPILE_DESTDIR);
                    System.out.println(destDir);
                    classesCodeDirs.addAll(Arrays.asList(destDir
                            .split(CodeBuildDefaultDefines.DEFAULT_DELIMETER)));
                    return classesCodeDirs;
                }
            }
        }

        return classesCodeDirs;
    }

    /**
     * 获取源代码路径
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param childrenElements
     * @return
     */
    public static List<Element> getAllTargetElements(
            List<Element> childrenElements) {
        List<Element> targetElements = new ArrayList<>();
        for (Element element : childrenElements) {
            if (CodeBuildDefaultDefines.CODE_BUILD_TARGET
                    .equals(element.getName())) {
                targetElements.add(element);
            }
        }

        return targetElements;
    }

    public static List<String> getAllPropertiesFiles(
            List<Element> childrenElements) {

        List<String> propertiesFileNames = new ArrayList<>();
        for (Element element : childrenElements) {
            if (CodeBuildDefaultDefines.CODE_BUILD_PROPERTY
                    .equals(element.getName())) {
                propertiesFileNames.add(element.attributeValue(
                        CodeBuildDefaultDefines.CODE_BUILD_PROPERTY_ATTRIBUTE));
            }
        }

        System.out.println(propertiesFileNames);
        return propertiesFileNames;
    }

    /**
     * @see :
     * @param :
     * @return : Map<String,String>
     * @param propertiesFileNames
     * @return
     */
    public static Map<String, String> getAllPropertyesFormPropertyFiles(
            List<String> propertiesFileNames) {
        Map<String, String> propertiesKeyAndValueEntry = new HashMap<>(16);
        for (String string : propertiesFileNames) {
            Properties properties = new Properties();
            try {
                properties.load(new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File(string)))));

                Enumeration ens = properties.propertyNames();
                while (ens.hasMoreElements()) {
                    String key = (String) ens.nextElement();
                    propertiesKeyAndValueEntry.put(key,
                            properties.getProperty(key));

                }
                return propertiesKeyAndValueEntry;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>(16);
    }

    /**
     * 通过build.xml文件获取所有的配置文件中配置的属性
     * 
     * @see :
     * @param :
     * @return : Map<String,String>
     * @param projectOrFilePath
     * @return
     */

    public static Map<String, String> getAllPropertiesKeyAndValueFromPathOrFile(
            String projectOrFilePath) {
        File file = new File(projectOrFilePath);

        File finalFile = null;

        if (!file.exists()) {
            return new HashMap<>(16);
        }

        if (file.isDirectory()) {
            finalFile = new File(file.getAbsolutePath(),
                    JacocoDefine.DEFAULT_PRODUCT_BUILD_FILE_NAME);
        } else {
            finalFile = file;
        }

        // 先获取所有的配置文件

        List<String> propertiesFiles = getAllPropertiesFiles(
                loadBuildFile(finalFile.getAbsolutePath()));

        List<String> propertiesFileWithPath = new ArrayList<>();

        for (String eachPropertieFileName : propertiesFiles) {
            propertiesFileWithPath.add(finalFile.getParent() + File.separator
                    + eachPropertieFileName);
        }

        Map<String, String> allProperties = getAllPropertyesFormPropertyFiles(
                propertiesFileWithPath);

        logger.debug("所有的配置文件属性:{}", allProperties);
        return allProperties;
    }

    /**
     * 从所有的配置文件键值对中，。初始化当前的键值对
     * 
     * @see :
     * @param :
     * @return : Map<String,String>
     * @param currentMapEntry
     * @param wholeMapEntry
     * @return
     */
    public static Map<String, String> initCurrentPropertiesMapWithWholePropertiesEntry(
            Map<String, String> currentMapEntry,
            Map<String, String> wholeMapEntry) {
        Set<String> currentKeyEntries = currentMapEntry.keySet();
        Set<String> wholeKeyEntries = wholeMapEntry.keySet();

        for (String currentEachKey : currentKeyEntries) {
            if (!wholeKeyEntries.contains(currentEachKey)) {
                // 如果目标集中没有当前key，则置null
                currentMapEntry.put(currentEachKey, null);
            } else {
                // 如果目标集中有当前key，则直接替换
                currentMapEntry.put(currentEachKey,
                        wholeMapEntry.get(currentEachKey));

            }
        }

        logger.debug("置换后的属性集群:{}", currentMapEntry);
        return currentMapEntry;
    }

    public static void main(String[] args) {
        String filePath = "D:\\TianQue\\hlsystem\\production.build.xml";
        // List<Element> childrenElements = loadBuildFile(filePath);
        //
        // getClassesCodeDirs(getAllTargetElements(childrenElements));
        //
        // getAllPropertiesFiles(childrenElements);

        getAllPropertiesKeyAndValueFromPathOrFile(filePath);

    }
}
