/**
 * @author : 孙留平
 * @since : 2019年1月18日 下午11:33:56
 * @see:
 */
package com.administrator.platform.controller.impl;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.administrator.platform.exception.base.BusinessValidationException;
import com.administrator.platform.util.TempFileUtil;
import com.administrator.platform.vo.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author : Administrator
 * @since : 2019年1月18日 下午11:33:56
 * @see :
 */
@Controller
@RequestMapping("/fileOperation")
@Api("文件上传相关操作")
public class OperationFileController {
    /**
     * 固定错误信息
     */
    private static final String UPLOAD_ERROR_MESSAGE = "上传文件失败!";

    private static final Logger logger = LoggerFactory
            .getLogger(OperationFileController.class);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");

    @PostMapping("/uploadFile")
    @ResponseBody
    @ApiOperation(value = "上传文件")
    public ResponseData uploadFile(
            @RequestParam("uploadFile") MultipartFile multipartFile,
            HttpServletRequest request) {

        try {

            return ResponseData.getSuccessResult(
                    TempFileUtil.multipartFileTransferAndReturnValue(request,
                            multipartFile, null, null));
        } catch (Exception e) {
            logger.error("{}:{}", UPLOAD_ERROR_MESSAGE, e.getMessage());
            throw new BusinessValidationException(UPLOAD_ERROR_MESSAGE);
        }
    }

}
