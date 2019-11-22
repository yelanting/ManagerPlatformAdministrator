/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午3:30:46
 * @see:
 */
package com.administrator.platform.tools.javaparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.core.base.util.ExcelUtil;
import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.util.JavaParserCommonUtil;
import com.administrator.platform.util.define.FileSuffix;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sf.json.JSONArray;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午3:30:46
 * @see :
 */
@SuppressWarnings(value = { "unchecked" })
public class JavaParserUtil extends VoidVisitorAdapter {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JavaParserUtil.class);
    private Set<MethodDeclaration> allEmement = new HashSet<>();

    private Set<MethodDeclaration> getAllMethod(CompilationUnit cu) {
        this.visit(cu, null);
        return allEmement;
    }

    @Override
    public void visit(MethodDeclaration method, Object arg) {
        LOGGER.debug("访问到方法:{}", method.getDeclarationAsString());
        allEmement.add(method);
    }

    /**
     * 校验文件
     * 
     * @see :
     * @param :
     * @return : void
     * @param filePath
     */
    private void validateFile(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            throw new BusinessValidationException(
                    GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }
    }

    /**
     * 
     * @see :
     * @param :
     * @return : CompilationUnit
     * @param filePath
     * @return
     */
    private CompilationUnit getCompilationUnit(String filePath) {
        File fileInput = new File(filePath);
        try (FileInputStream in = new FileInputStream(fileInput);) {
            CompilationUnit cu = JavaParser.parse(in);
            return cu;
        } catch (IOException e) {
            LOGGER.error("获取java文件:{}的编译单元失败:{}", filePath, e.getMessage());
        }

        return null;
    }

    /**
     * 获取所有节点
     * 
     * @see :
     * @param :
     * @return : List<Node>
     * @param compilationUnit
     * @return
     */

    private List<Node> getAllChildNodes(CompilationUnit compilationUnit) {
        return compilationUnit.getChildNodes();
    }

    /**
     * 获取所有的class节点
     * 
     * @see :
     * @param :
     * @return : List<Node>
     * @param compilationUnit
     * @return
     */
    private List<Node> getAllClassNode(CompilationUnit compilationUnit) {
        List<Node> allNodes = getAllChildNodes(compilationUnit);
        return getNodeOfCertainClass(allNodes,
                ClassOrInterfaceDeclaration.class);
    }

    /**
     * 获取所有的class节点
     * 
     * @see :
     * @param :
     * @return : List<Node>
     * @param compilationUnit
     * @return
     */
    private List<Node> getNodeOfCertainClass(List<Node> allNodes,
            Class thisClass) {
        List<Node> classNodes = new ArrayList<>();
        for (Node node : allNodes) {
            if (thisClass.isInstance(node)) {
                classNodes.add(node);
            }
        }
        return classNodes;
    }

    /**
     * 获取所有的class节点
     * 
     * @see :
     * @param :
     * @return : List<Node>
     * @param compilationUnit
     * @return
     */
    private Node getFirstNodeOfCertainClass(List<Node> allNodes,
            Class thisClass) {

        List<Node> certainNodes = getNodeOfCertainClass(allNodes, thisClass);
        if (certainNodes.isEmpty()) {
            return null;
        }

        return certainNodes.get(0);
    }

    /**
     * 获取所有的class节点
     * 
     * @see :
     * @param :
     * @return : List<Node>
     * @param compilationUnit
     * @return
     */
    private List<Node> getChildNodesOfNodeList(List<Node> allNodes) {
        List<Node> classNodes = new ArrayList<>();
        for (Node node : allNodes) {
            classNodes.addAll(node.getChildNodes());
        }
        return classNodes;
    }

    /**
     * 获取类级别的requestMapping
     * 
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private String getClassRequestMappingValue(
            CompilationUnit compilationUnit) {
        LOGGER.info("获取class级别的requestMapping");
        List<Node> classNodes = getChildNodesOfNodeList(
                getAllClassNode(compilationUnit));

        // 获取class节点的注解
        List<Node> classAnnotationNode = getNodeOfCertainClass(classNodes,
                AnnotationExpr.class);

        List<Node> requestMappingNodes = new ArrayList<>();
        for (Node node : classAnnotationNode) {
            if (node.toString().contains(
                    JavaParserConstDefine.DEFAULT_CLASS_CONTROLLER_REQUEST_MAPPING)
                    || node.toString().contains(
                            JavaParserConstDefine.DEFAULT_CONTROLLER_REQUEST_MAPPING_SIGN_STRUTS)) {
                LOGGER.debug("class级别的注解:{}", node);
                requestMappingNodes.add(node);
            }
        }

        if (requestMappingNodes.isEmpty()) {
            return "";
        }

        Node node = requestMappingNodes.get(0);
        String classRequestMapping = JavaParserCommonUtil
                .parseRequestMappingValue(node.toString());
        LOGGER.debug("class级别的requestMapping为:{}", classRequestMapping);
        return JavaParserCommonUtil
                .dealRequestMappingValue(classRequestMapping);
    }

    /**
     * 获取类级别的requestMapping
     *
     * @see :
     * @param :
     * @return : String
     * @return
     */
    private List<RequestMappingInfo> getAllMethodRequestMappingInfos(
            CompilationUnit compilationUnit) {
        LOGGER.info("获取class下的所有method相关映射信息");
        List<RequestMappingInfo> allRequestMappingInfos = new ArrayList<>();

        List<Node> allNodes = compilationUnit.getChildNodes();
        /**
         * 获取package声明
         */
        Node packageNode = getFirstNodeOfCertainClass(allNodes,
                PackageDeclaration.class);
        String packageValue = null;
        if (null == packageNode) {
            packageValue = "";
        } else {
            packageValue = JavaParserCommonUtil
                    .parsePackageValue(packageNode.toString());
        }

        /**
         * 获取class声明
         */
        Node classNode = getFirstNodeOfCertainClass(
                getAllClassNode(compilationUnit),
                ClassOrInterfaceDeclaration.class);

        if (null == classNode) {
            return new ArrayList<>();
        }

        String classRequestMapping = getClassRequestMappingValue(
                compilationUnit);

        // 获取class节点子节点
        List<Node> childrenNodesOfClassNodes = classNode.getChildNodes();
        /**
         * 遍历class的子节点
         */
        String className = null;
        RequestMappingInfo tempRequestMappingInfo = null;
        for (Node eachChildrenNodesOfClassNode : childrenNodesOfClassNodes) {
            if (eachChildrenNodesOfClassNode instanceof SimpleName) {
                // 如果是类声明节点
                className = eachChildrenNodesOfClassNode.toString();
            } else if (eachChildrenNodesOfClassNode instanceof MethodDeclaration) {
                // 如果是方法声明节点
                tempRequestMappingInfo = parseRequestMappingInfoFromNode(
                        eachChildrenNodesOfClassNode);
                if (null != tempRequestMappingInfo) {
                    allRequestMappingInfos.add(tempRequestMappingInfo);
                }
            }
        }

        String finalMapping = null;
        String fullMapping = null;
        for (RequestMappingInfo requestMappingInfo : allRequestMappingInfos) {
            requestMappingInfo.setClassName(className);
            requestMappingInfo.setPackageName(packageValue);
            requestMappingInfo.setParentMapping(classRequestMapping);
            finalMapping = dealRequestMappingInfoMultiMappings(
                    requestMappingInfo);
            requestMappingInfo.setThisMapping(finalMapping);
            // 如果父mapping是/,那就不要加多于的/了
            if (classRequestMapping.equals(String.valueOf(
                    JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_START_SEP_CHAR))) {
                if (StringUtil.isStringAvaliable(finalMapping)) {
                    fullMapping = finalMapping;
                } else {
                    fullMapping = String.valueOf(
                            JavaParserConstDefine.DEFAULT_REQUEST_MAPPING_START_SEP_CHAR);
                }
            } else {
                fullMapping = classRequestMapping + finalMapping;
            }
            requestMappingInfo.setFullMapping(fullMapping);
        }

        // LOGGER.debug("获取该文件下的所有方法映射为:{}", allRequestMappingInfos);
        return allRequestMappingInfos;
    }

    /**
     * 判断一个节点是不是映射节点
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param node
     * @return
     */
    private boolean isRequestMappingNode(Node node) {
        if (!(node instanceof AnnotationExpr)) {
            return false;
        }

        boolean isAction = false;
        boolean isRequestMapping = false;

        isRequestMapping = node.toString()
                .contains(JavaParserConstDefine.DEFAULT_METHOD_REQUEST_MAPPING)
                && node.toString().contains(
                        JavaParserConstDefine.DEFAULT_METHOD_ANNOTATION_CHAR);

        isAction = node.toString().contains(
                (JavaParserConstDefine.DEFAULT_METHOD_REQUEST_MAPPING_OF_STRUTS));

        if (!isAction && !isRequestMapping) {
            return false;
        }

        return isAction || isRequestMapping;
    }

    /**
     * 判断一个节点是不是Controller的class节点
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param node
     * @return
     */
    private boolean isControllerNode(Node node) {

        /**
         * 首先得是class节点
         */
        if (!(node instanceof ClassOrInterfaceDeclaration)) {
            return false;
        }

        if (node.toString().indexOf(
                JavaParserConstDefine.DEFAULT_CONTROLLER_CLASS_SIGN) == -1) {
            return false;
        }

        /**
         * 得到class节点的子节点，并从中得到注释节点
         */
        List<Node> childNodes = node.getChildNodes();

        if (childNodes.isEmpty()) {
            return false;
        }

        List<Node> annotationNodes = getNodeOfCertainClass(childNodes,
                AnnotationExpr.class);

        if (annotationNodes.isEmpty()) {
            return false;
        }

        boolean hasComponentSign = false;
        boolean hasControllerSign = false;
        boolean hasRestControllerSign = false;
        boolean hasRequestMapping = false;

        for (Node eachAnnotationNode : annotationNodes) {
            if (eachAnnotationNode.toString().contains(
                    JavaParserConstDefine.DEFAULT_CONTROLLER_ANNOTATION_SIGN)) {
                hasControllerSign = true;
                continue;
            }

            if (eachAnnotationNode.toString().contains(
                    JavaParserConstDefine.DEFAULT_CONTROLLER_COMPONENT_SIGN)) {
                hasComponentSign = true;
                continue;
            }

            if (eachAnnotationNode.toString().contains(
                    JavaParserConstDefine.DEFAULT_CONTROLLER_REQUEST_MAPPING_SIGN)) {
                hasRequestMapping = true;
                continue;
            }

            if (eachAnnotationNode.toString().contains(
                    JavaParserConstDefine.DEFAULT_CONTROLLER_REST_CONTROLLER_SIGN)) {
                hasRestControllerSign = true;
                continue;
            }
        }

        /**
         * 如果任何注解都没有，则表示非controller
         */
        if (!hasComponentSign && !hasControllerSign && !hasRequestMapping
                && !hasRestControllerSign) {
            return false;
        }
        return true;
    }

    /**
     * 判断是不是controller的单元
     * 
     * @see :
     * @param :
     * @return : boolean
     * @param compilationUnit
     * @return
     */
    private boolean isControllerFile(CompilationUnit compilationUnit) {
        List<Node> classNodes = getAllClassNode(compilationUnit);
        for (Node node : classNodes) {
            if (isControllerNode(node)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 从节点中解析方法信息
     * 
     * @see :
     * @param :
     * @return : RequestMappingInfo
     * @param node
     * @return
     */
    private RequestMappingInfo parseRequestMappingInfoFromNode(Node node) {
        if (!(node instanceof MethodDeclaration)) {
            return null;
        }

        /**
         * 开始解析方法节点
         */
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
        List<Node> methodChildNodes = node.getChildNodes();

        StringBuilder methodSignature = new StringBuilder();
        StringBuilder parameters = new StringBuilder();

        boolean isRequestMethod = false;
        for (Node eachMethodChildNode : methodChildNodes) {
            // 如果是映射节点
            if (eachMethodChildNode instanceof AnnotationExpr
                    && isRequestMappingNode(eachMethodChildNode)) {
                requestMappingInfo.setThisMapping(
                        JavaParserCommonUtil.parseRequestMappingValue(
                                eachMethodChildNode.toString()));

                isRequestMethod = true;

            } else if (eachMethodChildNode instanceof SimpleName) {
                // 如果是方法名称节点
                requestMappingInfo
                        .setThisMethodName(eachMethodChildNode.toString());
            } else if (eachMethodChildNode instanceof Parameter) {
                parameters.append(eachMethodChildNode.toString()).append(",");
            }
        }

        if (!isRequestMethod) {
            return null;
        }

        if (parameters.length() != 0) {
            parameters = new StringBuilder(
                    parameters.substring(0, parameters.length() - 1));
        }

        methodSignature = new StringBuilder(
                requestMappingInfo.getThisMethodName()).append("(")
                        .append(parameters).append(")");
        requestMappingInfo.setMethodSignature(methodSignature.toString());
        requestMappingInfo.setParameters(parameters.toString());

        LOGGER.debug("解析出来的方法信息为:{}", requestMappingInfo);
        return requestMappingInfo;

    }

    /**
     * 处理多value的映射
     * 
     * @see :
     * @param :
     * @return : String
     * @param multiMapping
     * @return
     */
    private String dealRequestMappingInfoMultiMappings(
            RequestMappingInfo requestMappingInfo) {
        String multiMapping = requestMappingInfo.getThisMapping();

        if (StringUtil.isEmpty(multiMapping)) {
            return "";
        }

        if (multiMapping.indexOf(
                JavaParserConstDefine.DEFAULT_MULTI_VALUES_SEP_SIGN) == -1) {
            return multiMapping;
        }

        StringBuilder multiValues = new StringBuilder();

        String[] multiValuesAfterSplit = multiMapping
                .split(JavaParserConstDefine.DEFAULT_MULTI_VALUES_SEP_SIGN);
        for (String string : multiValuesAfterSplit) {
            multiValues.append(
                    JavaParserCommonUtil.dealRequestMappingValue(string))
                    .append(",");
        }

        return multiValues.substring(0, multiValues.length() - 2);
    }

    /**
     * 从文件中解析请求
     *
     * @see :
     * @param :
     * @return : List<RequestMappingInfo>
     * @param filePath
     * @return
     */
    public List<RequestMappingInfo> parseRequestMappingFromFile(
            String filePath) {
        LOGGER.info("==========正在解析java文件{}=============", filePath);
        validateFile(filePath);

        /***
         * 这里是取class上方的注解，得到value="/test/api"根URL
         */
        CompilationUnit compilationUnit = getCompilationUnit(filePath);
        if (null == compilationUnit) {
            throw new BusinessValidationException("获取编译单元失败");
        }

        if (!isControllerFile(compilationUnit)) {
            // LOGGER.debug("当前文件不是Controller文件:{}", filePath);
            return new ArrayList<>();
        }

        LOGGER.debug("当前文件是Controller文件:{}", filePath);
        List<RequestMappingInfo> requestMappingInfos = new ArrayList<>();
        requestMappingInfos
                .addAll(getAllMethodRequestMappingInfos(compilationUnit));
        // LOGGER.debug("解析到请求映射为:{}", requestMappingInfos);
        return requestMappingInfos;
    }

    /**
     * 从文件中解析请求
     * 
     * @see :
     * @param :
     * @return : List<RequestMappingInfo>
     * @param filePath
     * @return
     */
    public List<RequestMappingInfo> parseRequestMappingFromFolder(
            String filePath) {
        LOGGER.info("从文件夹:{}下的java文件中解析请求", filePath);
        List<File> javaFiles = FileUtil.getFilesUnderFolder(filePath,
                FileSuffix.JAVA_FILE);

        List<RequestMappingInfo> requestMappingInfos = new ArrayList<>();
        for (File file : javaFiles) {
            requestMappingInfos.addAll(
                    parseRequestMappingFromFile(file.getAbsolutePath()));
        }

        // LOGGER.debug("解析到文件夹下所有的请求为:{}", requestMappingInfos);
        return requestMappingInfos;
    }

    /**
     * 把请求对象写到excel里
     * 
     * @see :
     * @param :
     * @return : void
     */
    public void writeRequestMappingToExcel(File excelPath,
            List<RequestMappingInfo> requestMappingInfos) {

        FileUtil.reCreateFile(excelPath);
        ExcelUtil.createDefaultExcel(excelPath.getParent(),
                excelPath.getName());
        String[] headers = new String[] { "packageName", "className",
                "parentMapping", "thisMapping", "fullMapping", "thisMethodName",
                "parameters", "methodSignature", "requestMethodType" };
        ExcelUtil.writeHeaderToDefaultSheet(excelPath, headers);

        Workbook workbook = ExcelUtil.getWorkbook(excelPath);
        Sheet defaultSheet = ExcelUtil.getDefaultSheet(workbook);
        CellStyle cellStyle = ExcelUtil.createDefaultCellStyle(workbook);

        /**
         * 写内容
         */
        int startRow = 1;
        for (RequestMappingInfo eachRequestMappingInfo : requestMappingInfos) {
            createRowOfRequestMapping(eachRequestMappingInfo, startRow++,
                    defaultSheet, cellStyle);
        }

        ExcelUtil.writeToFile(workbook, excelPath);
    }

    /**
     * 创建一行
     * 
     * @see :
     * @param :
     * @return : Row
     * @param requestMappingInfo
     * @param rowNum
     * @return
     */
    private Row createRowOfRequestMapping(RequestMappingInfo requestMappingInfo,
            int rowNum, Sheet sheet, CellStyle cellStyle) {
        Row thisRow = sheet.createRow(rowNum);
        createCell(thisRow, 0, requestMappingInfo.getPackageName(), cellStyle);
        createCell(thisRow, 1, requestMappingInfo.getClassName(), cellStyle);
        createCell(thisRow, 2, requestMappingInfo.getParentMapping(),
                cellStyle);
        createCell(thisRow, 3, requestMappingInfo.getThisMapping(), cellStyle);
        createCell(thisRow, 4, requestMappingInfo.getFullMapping(), cellStyle);
        createCell(thisRow, 5, requestMappingInfo.getThisMethodName(),
                cellStyle);
        createCell(thisRow, 6, requestMappingInfo.getParameters(), cellStyle);
        createCell(thisRow, 7, requestMappingInfo.getMethodSignature(),
                cellStyle);
        createCell(thisRow, 8, requestMappingInfo.getRequestMethodType(),
                cellStyle);
        return thisRow;
    }

    private Cell createCell(Row row, int columnNumber, String value,
            CellStyle cellStyle) {
        Cell cell = row.createCell(columnNumber);
        cell.setCellValue(value);
        if (null != cellStyle) {
            cell.setCellStyle(cellStyle);
        }

        return cell;
    }

    public static void main(String[] args) {
        String javaFile = "D:\\TianQue\\tq-product\\tq-robot\\branches\\tq-robot-1.0.0\\tq-robot-server\\src\\main\\java\\com\\tianque\\robot\\controller\\UserController.java";
        // String javaFile =
        // "D:\\program_workspaces\\spring_sts_workspace\\SpringBootProjectJdk11\\trunk\\src\\main\\java\\com\\administrator\\platform\\SpringBootProjectJdk11Application.java";

        String javFileFolder = "D:\\\\TianQue\\\\tq-product\\\\tq-robot\\\\branches\\\\tq-robot-1.0.0";
        JavaParserUtil javaParserUtil = new JavaParserUtil();
        // javaParserUtil.parseRequestMappingFromFile(javaFile);
        //
        System.out.println(JSONArray.fromObject(
                javaParserUtil.parseRequestMappingFromFolder(javFileFolder)));
        // CompilationUnit compilationUnit = javaParserUtil
        // .getCompilationUnit(javaFile);

        // System.out.println(
        // javaParserUtil.getMethodRequestMappingInfos(compilationUnit));
        // // System.out.println(javaParserUtil.getNodeOfCertainClass(
        // // javaParserUtil.getAllChildNodes(compilationUnit),
        // // ClassOrInterfaceDeclaration.class));
        //
        // javaParserUtil.getMethodRequestMappingValues(compilationUnit);
    }
}
