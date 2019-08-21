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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.config.GlobalExceptionMessage;
import com.administrator.platform.core.base.util.FileUtil;
import com.administrator.platform.core.base.util.StringUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.vo.JavaParserDTO;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Objects;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午3:30:46
 * @see :
 */
@SuppressWarnings(value = { "unchecked" })
public class JavaParserUtil extends VoidVisitorAdapter {
    private Logger logger = LoggerFactory.getLogger(JavaParserUtil.class);
    private String rooturl;
    private Set<MethodDeclaration> allEmement = new HashSet<>();

    private List<String> allParametersAreString = new ArrayList<>();
    private List<String> notAllParametersAreString = new ArrayList<>();
    private List<String> allMethods = new ArrayList<>();

    private File eachFile;

    private Set<MethodDeclaration> getAllMethod(CompilationUnit cu) {
        this.visit(cu, null);
        return allEmement;
    }

    @Override
    public void visit(MethodDeclaration method, Object arg) {
        logger.debug("访问到方法:{}", method.getDeclarationAsString());
        allEmement.add(method);
    }

    /**
     * 遍历java源文件
     * 
     * @see :
     * @param :
     * @return : void
     * @throws IOException
     */
    public void parseJavaCodeAndLogOut(String filePath,
            JavaParserDTO javaParserDTO) throws IOException {
        logger.info("==========正在解析java文件{}=============", filePath);
        if (null == filePath) {
            throw new BusinessValidationException(
                    GlobalExceptionMessage.NULL_PARAMETER_MESSAGE);
        }

        File fileInput = new File(filePath);
        FileInputStream in = new FileInputStream(fileInput);

        this.allParametersAreString = new ArrayList<>();
        this.notAllParametersAreString = new ArrayList<>();
        this.allMethods = new ArrayList<>();

        String fileName = fileInput.getName();

        /***
         * javaParser初始化，得到一个CompilationUnit树
         */
        CompilationUnit cu = JavaParser.parse(in);
        in.close();

        /***
         * 这里是取class上方的注解，得到value="/test/api"根URL
         */
        List<Node> culist = cu.getChildNodes();

        for (Node cuunit : culist) {
            if (cuunit instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) cuunit;
                List<Node> annotationExprs = classOrInterfaceDeclaration
                        .getChildNodes();
                for (Node annotationExpr : annotationExprs) {
                    if (annotationExpr instanceof NormalAnnotationExpr) {
                        NormalAnnotationExpr temp = (NormalAnnotationExpr) annotationExpr;
                        for (Node temp2 : temp.getChildNodes()) {
                            if (temp2 instanceof MemberValuePair) {
                                MemberValuePair memberValuePair = (MemberValuePair) temp2;
                                if ("value".equalsIgnoreCase(
                                        memberValuePair.getName().toString())) {
                                    rooturl = memberValuePair.getValue()
                                            .toString().replace("\"", "");
                                    logger.debug("URL: {}",
                                            memberValuePair.getValue()
                                                    .toString()
                                                    .replace("\"", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        /***
         * 解析方法中的注解、请求方式和参数类型
         */
        String wantedMethodNameType = javaParserDTO.getWantedMethodType();

        if (!StringUtil.isStringAvaliable(wantedMethodNameType)) {
            wantedMethodNameType = "ALL";
        }

        for (MethodDeclaration eachMethod : this.getAllMethod(cu)) {

            String methodName = null;
            List<Parameter> parameterList = eachMethod.getParameters();
            logger.debug("参数列表:{}", parameterList);

            boolean allString = true;

            switch (wantedMethodNameType) {
                case "ALL":
                    methodName = eachMethod.getDeclarationAsString();
                    break;
                case "methodSign":
                    String paramInfo = parameterList.toString();

                    char[] paramInfoChars = paramInfo.toCharArray();

                    if (paramInfoChars.length > 0) {
                        if (paramInfoChars[0] == '[') {
                            paramInfoChars[0] = '(';
                        }

                        if (paramInfoChars[paramInfoChars.length - 1] == ']') {
                            paramInfoChars[paramInfoChars.length - 1] = ')';
                        }
                        paramInfo = new String(paramInfoChars);
                    }
                    methodName = eachMethod.getNameAsString() + paramInfo;
                    break;
                default:
                    methodName = eachMethod.getDeclarationAsString();
                    break;
            }

            logger.debug("=====正在解析方法:{}=====", methodName);
            List<AnnotationExpr> listAnnotation = eachMethod.getAnnotations();
            for (AnnotationExpr annotationExpr : listAnnotation) {
                if ("RequestMapping".equalsIgnoreCase(
                        annotationExpr.getName().toString())) {
                    List<Node> childNode = annotationExpr.getChildNodes();
                    for (Node n : childNode) {
                        if (n instanceof MemberValuePair) {
                            MemberValuePair memberValuePair = (MemberValuePair) n;
                            if ("value".equalsIgnoreCase(
                                    memberValuePair.getName().toString())) {
                                logger.debug("接口地址： {}{}", rooturl,
                                        memberValuePair.getValue().toString()
                                                .replace("\"", ""));
                            }
                            if ("method".equalsIgnoreCase(
                                    memberValuePair.getName().toString())) {
                                logger.debug("请求方式：  {}",
                                        memberValuePair.getValue());
                            }
                        }
                    }
                }
            }

            for (Parameter parameter : parameterList) {
                // 如果是String类型
                if (!Objects.equal(parameter.getTypeAsString(), "String")) {
                    allString = false;
                }
                List<Node> requestParamList = parameter.getChildNodes();
                for (Node requestParam : requestParamList) {
                    if (requestParam instanceof NormalAnnotationExpr) {
                        List<Node> valueList = requestParam.getChildNodes();
                        for (Node value : valueList) {
                            if (value instanceof MemberValuePair) {
                                MemberValuePair tmp = (MemberValuePair) value;
                                if ("value".equalsIgnoreCase(
                                        tmp.getName().toString())) {
                                    logger.debug("参数名： {} ", tmp.getValue()
                                            .toString().replace("\"", ""));
                                }
                                if ("required".equalsIgnoreCase(
                                        tmp.getName().toString())) {
                                    logger.debug("是否必传：{}", tmp.getValue());
                                }
                            }
                        }
                    }
                }

                // if (!parameter.getType().toString()
                // .equalsIgnoreCase("HttpServletRequest")
                // && !parameter.getType().toString()
                // .equalsIgnoreCase("HttpServletResponse")) {
                // // logger.debug("参数类型：{}", parameter.getType());
                // }
            }

            // 如果所有参数都是String
            if (allString) {
                allParametersAreString.add(methodName);
                JavaParserGlobal.ALL_STRING_PARAMS_METHODS_LIST
                        .add(fileName + JavaParserGlobal.RECORD_SEPERATOR
                                + methodName + JavaParserGlobal.RECORD_SEPERATOR
                                + parameterList.size());
            } else {
                notAllParametersAreString.add(methodName);
                JavaParserGlobal.ALL_NOT_ALL_STRING_PARAMS_METHODS_LIST
                        .add(fileName + JavaParserGlobal.RECORD_SEPERATOR
                                + methodName + JavaParserGlobal.RECORD_SEPERATOR
                                + parameterList.size());
            }

            allMethods.add(methodName);

            JavaParserGlobal.ALL_METHODS_LIST.add(fileName
                    + JavaParserGlobal.RECORD_SEPERATOR + methodName
                    + JavaParserGlobal.RECORD_SEPERATOR + parameterList.size());

            logger.debug("=====方法解析完毕=====");
        }
        logger.debug("所有参数都是String的方法:{},一共{}个。", allParametersAreString,
                allParametersAreString.size());
        logger.debug("并非所有参数都是String的方法:{},一共{}个。", notAllParametersAreString,
                notAllParametersAreString.size());
        logger.debug("==========解析完毕=============");

        File tempFile = this.eachFile;

        String choosenTypes = javaParserDTO.getChoosenTypes();
        if (!StringUtil.isStringAvaliable(choosenTypes)) {
            choosenTypes = "ALL";
        }

        switch (choosenTypes) {
            case "ALL":
                FileUtil.writeStringListToFile(tempFile, allMethods);
                break;
            case "allString":
                FileUtil.writeStringListToFile(tempFile,
                        allParametersAreString);
                break;
            case "notAllString":
                FileUtil.writeStringListToFile(tempFile,
                        notAllParametersAreString);
                break;
            default:
                FileUtil.writeStringListToFile(tempFile, allMethods);
                break;
        }
    }

    public File getEachFile() {
        return eachFile;
    }

    public void setEachFile(File eachFile) {
        this.eachFile = eachFile;
    }
}
