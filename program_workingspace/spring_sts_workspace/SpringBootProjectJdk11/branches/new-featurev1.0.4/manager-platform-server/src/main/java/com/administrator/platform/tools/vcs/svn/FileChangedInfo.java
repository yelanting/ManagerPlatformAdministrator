/**
 * @author : 孙留平
 * @since : 2019年3月4日 下午9:31:58
 * @see:
 */
package com.administrator.platform.tools.vcs.svn;

/**
 * @author : Administrator
 * @since : 2019年3月4日 下午9:31:58
 * @see :
 */
public class FileChangedInfo {
    private int deletedStartLine;
    private int deletedStartLineCount;
    private int addedStartLine;
    private int addedStartLineCount;

    public int getDeletedStartLine() {
        return deletedStartLine;
    }

    public void setDeletedStartLine(int deletedStartLine) {
        this.deletedStartLine = deletedStartLine;
    }

    public int getDeletedStartLineCount() {
        return deletedStartLineCount;
    }

    public void setDeletedStartLineCount(int deletedStartLineCount) {
        this.deletedStartLineCount = deletedStartLineCount;
    }

    public int getAddedStartLine() {
        return addedStartLine;
    }

    public void setAddedStartLine(int addedStartLine) {
        this.addedStartLine = addedStartLine;
    }

    public int getAddedStartLineCount() {
        return addedStartLineCount;
    }

    public void setAddedStartLineCount(int addedStartLineCount) {
        this.addedStartLineCount = addedStartLineCount;
    }

    @Override
    public String toString() {
        return "FileChangedInfo [deletedStartLine=" + deletedStartLine
                + ", deletedStartLineCount=" + deletedStartLineCount
                + ", addedStartLine=" + addedStartLine
                + ", addedStartLineCount=" + addedStartLineCount + "]";
    }
}
