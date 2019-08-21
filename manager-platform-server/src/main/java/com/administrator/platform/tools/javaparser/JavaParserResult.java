/**
 * @author : 孙留平
 * @since : 2018年11月23日 上午9:53:00
 * @see:
 */
package com.administrator.platform.tools.javaparser;

/**
 * @author : Administrator
 * @since : 2018年11月23日 上午9:53:00
 * @see :
 */
public class JavaParserResult {
    private String fileToStoreFolder = "files";
    private String fileToStore = "parserFile.txt";

    public String getFileToStoreFolder() {
        return fileToStoreFolder;
    }

    public void setFileToStoreFolder(String fileToStoreFolder) {
        this.fileToStoreFolder = fileToStoreFolder;
    }

    public String getFileToStore() {
        return fileToStore;
    }

    public void setFileToStore(String fileToStore) {
        this.fileToStore = fileToStore;
    }
}
