/**
 * @author : 孙留平
 * @since : 2018年12月3日 下午9:32:51
 * @see:
 */
package com.administrator.platform.testcase.dubbotest.dto;

import java.util.List;

/**
 * @author : Administrator
 * @since : 2018年12月3日 下午9:32:51
 * @see :
 */
public class IncomeParamsParser {
    private String[] paramTypes;
    private Object[] paramValues;

    public void psrseIncomePrams(List<InParams> incomeParams) {
        // 设置参数和参数类型 
        int paramSize = incomeParams.size();
        paramTypes = new String[paramSize];
        paramValues = new Object[paramSize];
        if (!incomeParams.isEmpty()) {
            for (int index = 0; index < paramSize; index++) {
                paramTypes[index] = incomeParams.get(index).getParamType();
                paramValues[index] = incomeParams.get(index).getParamValue();
            }
        }
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(String[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }
}
