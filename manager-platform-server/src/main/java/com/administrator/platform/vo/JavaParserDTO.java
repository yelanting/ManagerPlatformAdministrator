/**
 * @author : 孙留平
 * @since : 2018年11月22日 下午9:01:42
 * @see:
 */
package com.administrator.platform.vo;

/**
 * @author : Administrator
 * @since : 2018年11月22日 下午9:01:42
 * @see : java解析的DTO参数
 */
public class JavaParserDTO {
    private String fileFolder;
    private String choosenTypes;
    private String fileToStore;

    private String wantedMethodType;

    public String getChoosenTypes() {
        return choosenTypes;
    }

    public void setChoosenTypes(String choosenTypes) {
        this.choosenTypes = choosenTypes;
    }

    public String getFileToStore() {
        return fileToStore;
    }

    public void setFileToStore(String fileToStore) {
        this.fileToStore = fileToStore;
    }

    public String getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    public String getWantedMethodType() {
        return wantedMethodType;
    }

    public void setWantedMethodType(String wantedMethodType) {
        this.wantedMethodType = wantedMethodType;
    }

    @Override
    public String toString() {
        return "JavaParserDTO [fileFolder=" + fileFolder + ", choosenTypes="
                + choosenTypes + ", fileToStore=" + fileToStore
                + ", wantedMethodType=" + wantedMethodType + "]";
    }
}
