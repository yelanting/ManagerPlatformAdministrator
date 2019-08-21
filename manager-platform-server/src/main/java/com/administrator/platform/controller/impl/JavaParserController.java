/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午8:58:17
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.administrator.platform.service.JavaParserService;
import com.administrator.platform.vo.JavaParserDTO;
import com.administrator.platform.vo.ResponseData;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午8:58:17
 * @see :
 */
@RequestMapping("/fileParser")
@Controller
public class JavaParserController {
    private Logger logger = LoggerFactory.getLogger(JavaParserController.class);
    @Autowired
    private JavaParserService javaParserService;

    @PostMapping("/addFileParser")
    @ResponseBody
    public ResponseData parseJavaFiles(
            @ModelAttribute JavaParserDTO javaParserDto) {
        logger.info("开始添加源代码管理");
        logger.debug("javaParserDto：{}", javaParserDto);
        javaParserService.parseJavaFiles(javaParserDto);
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(new ArrayList<>());
        responseData.setMsg(null);

        return responseData;
    }

    @GetMapping("/getList")
    @ResponseBody
    public ResponseData getList() {
        logger.info("查询源代码管理列表");
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setData(new ArrayList<>());
        responseData.setMsg(null);
        return responseData;
    }
}
