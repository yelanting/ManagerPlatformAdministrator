/**
 * @author : 孙留平
 * @since : 2018年12月8日 下午2:16:29
 * @see:
 */
package com.administrator.platform.autotest;

/**
 * @author : Administrator
 * @since : 2018年12月8日 下午2:16:29
 * @see :
 */
public abstract class BaseTestCase {
    protected String id;
    protected String caseName;
    protected String caseDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }
}
