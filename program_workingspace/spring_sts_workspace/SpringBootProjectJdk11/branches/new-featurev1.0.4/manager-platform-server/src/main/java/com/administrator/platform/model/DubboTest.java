/**
 * @author : 孙留平
 * @since : 2018年11月30日 下午4:52:10
 * @see:
 */
package com.administrator.platform.model;

import com.administrator.platform.model.base.BaseDomain;

/**
 * @author : Administrator
 * @since : 2018年11月30日 下午4:52:10
 * @see :
 */
public class DubboTest extends BaseDomain {
    /**
     * @Fields serialVersionUID : DubboTest的ID
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String caseName;
    private String protocolName;
    private String address;
    private String groupName;
    private String interfaceName;
    private String methodName;
    private String client;
    private String version;

    private String incomeParams;
    private String dubboTestResponse;

    private String dubboTestCheck;

    private String dubboTestResult;

    private String dubboContextParams;

    private String caseDesc;

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

    public String getDubboTestResponse() {
        return dubboTestResponse;
    }

    public void setDubboTestResponse(String dubboTestResponse) {
        this.dubboTestResponse = dubboTestResponse;
    }

    public String getDubboTestCheck() {
        return dubboTestCheck;
    }

    public void setDubboTestCheck(String dubboTestCheck) {
        this.dubboTestCheck = dubboTestCheck;
    }

    public String getDubboTestResult() {
        return dubboTestResult;
    }

    public void setDubboTestResult(String dubboTestResult) {
        this.dubboTestResult = dubboTestResult;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public String getIncomeParams() {
        return incomeParams;
    }

    public void setIncomeParams(String incomeParams) {
        this.incomeParams = incomeParams;
    }

    public String getDubboContextParams() {
        return dubboContextParams;
    }

    public void setDubboContextParams(String dubboContextParams) {
        this.dubboContextParams = dubboContextParams;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    @Override
    public String toString() {
        return "DubboTest [id=" + id + ", caseName=" + caseName
                + ", protocolName=" + protocolName + ", address=" + address
                + ", groupName=" + groupName + ", interfaceName="
                + interfaceName + ", methodName=" + methodName + ", client="
                + client + ", version=" + version + ", incomeParams="
                + incomeParams + ", dubboTestResponse=" + dubboTestResponse
                + ", dubboTestCheck=" + dubboTestCheck + ", dubboTestResult="
                + dubboTestResult + ", dubboContextParams=" + dubboContextParams
                + ", caseDesc=" + caseDesc + "]";
    }
}
