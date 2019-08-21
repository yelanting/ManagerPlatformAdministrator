/**
 * @author : 孙留平
 * @since : 2019年3月13日 上午11:28:44
 * @see:
 */
package com.administrator.platform.model;

/**
 * @author : Administrator
 * @since : 2019年3月13日 上午11:28:44
 * @see :
 */
public class ExecuteCommandResult {
    private boolean successful;

    private String executeOut;

    private String exitCode;

    private String exitValue;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getExecuteOut() {
        return executeOut;
    }

    public void setExecuteOut(String executeOut) {
        this.executeOut = executeOut;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitValue() {
        return exitValue;
    }

    public void setExitValue(String exitValue) {
        this.exitValue = exitValue;
    }

    @Override
    public String toString() {
        return "ExecuteCommandResult [successful=" + successful
                + ", executeOut=" + executeOut + ", exitCode=" + exitCode
                + ", exitValue=" + exitValue + "]";
    }

}
