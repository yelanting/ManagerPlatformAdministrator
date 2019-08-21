/**
 * @author : 孙留平
 * @since : 2018年12月3日 下午9:06:28
 * @see:
 */
package com.administrator.platform.testcase.dubbotest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author : Administrator
 * @since : 2018年12月3日 下午9:06:28
 * @see :
 */
@ApiModel(description = "参数列表")
public class InParams {

    @ApiModelProperty(value = "参数名", required = true)
    private String paramName;
    @ApiModelProperty(value = "参数值", required = true)
    private String paramValue;
    @ApiModelProperty(value = "参数类型", required = true)
    private String paramType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}
