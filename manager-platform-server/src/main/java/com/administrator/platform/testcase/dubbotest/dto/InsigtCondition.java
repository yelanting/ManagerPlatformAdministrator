/**
 * @author : 孙留平
 * @since : 2018年12月3日 下午9:08:20
 * @see:
 */
package com.administrator.platform.testcase.dubbotest.dto;

/**
 * @author : Administrator
 * @since : 2018年12月3日 下午9:08:20
 * @see :
 */
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InsigtCondition {
    private String oemCode;
    @ApiModelProperty(value = "透视条目ID", required = true)
    private Long itemId;
    @ApiModelProperty(value = "API唯一标识,如：xx.xx.xx.xx.getInfo", required = true)
    private String uniqueName;
    @ApiModelProperty(value = "API接口名，包名+类名，如：xx.xx.xx.XXXApi", required = true)
    private String interfaceName;
    @ApiModelProperty(value = "方法名，如：getInfo", required = true)
    private String methodName;
    @ApiModelProperty(value = "API版本", required = true)
    private String version;
    @ApiModelProperty(value = "参数列表List")
    private List<InParams> args;

    public String getOemCode() {
        return oemCode;
    }

    public void setOemCode(String oemCode) {
        this.oemCode = oemCode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<InParams> getArgs() {
        return args;
    }

    public void setArgs(List<InParams> args) {
        this.args = args;
    }
}
