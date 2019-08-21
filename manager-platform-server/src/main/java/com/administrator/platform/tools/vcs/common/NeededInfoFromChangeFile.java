/**
 * @author : 孙留平
 * @since : 2019年3月6日 下午2:05:13
 * @see:
 */
package com.administrator.platform.tools.vcs.common;

/**
 * @author : Administrator
 * @since : 2019年3月6日 下午2:05:13
 * @see :
 */
public class NeededInfoFromChangeFile {
    private String sourceFolder;
    private String classFolder;
    private String className;

    public String getSourceFolder() {
        return sourceFolder;
    }

    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public String getClassFolder() {
        return classFolder;
    }

    public void setClassFolder(String classFolder) {
        this.classFolder = classFolder;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "NeededInfoFromChangeFile [sourceFolder=" + sourceFolder
                + ", classFolder=" + classFolder + ", className=" + className
                + "]";
    }

}
