/**
 * @author : 孙留平
 * @since : 2019年1月18日 下午10:47:55
 * @see:
 */
package com.administrator.platform.tools.xmind;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

import com.administrator.platform.core.base.util.ValidationUtil;
import com.administrator.platform.exception.base.BusinessValidationException;
import com.google.common.base.Objects;

/**
 * @author : Administrator
 * @since : 2019年1月18日 下午10:47:55
 * @see :
 */
public class XmindParser {
    private static final Logger logger = LoggerFactory
            .getLogger(XmindParser.class);

    public void createFileAndWriteTestcases(String filePath,
            List<ChanDaoTestCase> testCaseList) {
        List<ITopic> topicsUnderRoot = getTopicsUnderRoot(
                getRootTopic(getPrimarySheet(parseXmindFile(filePath))));

        getCertainNameTopic("测试设计", topicsUnderRoot);
    }

    private IWorkbook parseXmindFile(String xmindFilePath) {
        IWorkbookBuilder builder = Core.getWorkbookBuilder();

        IWorkbook workbook = null;

        try {
            workbook = builder.loadFromFile(new File(xmindFilePath));
            return workbook;
        } catch (IOException | CoreException e) {
            logger.error("解析xmind错误,错误信息:{}", e.getMessage());
            e.printStackTrace();
            throw new BusinessValidationException("解析xmind失败");
        }
    }

    private ISheet getPrimarySheet(IWorkbook iWorkbook) {
        ValidationUtil.validateNull(iWorkbook, null);
        return iWorkbook.getPrimarySheet();
    }

    private ITopic getRootTopic(ISheet iSheet) {
        return iSheet.getRootTopic();
    }

    private List<ITopic> getTopicsUnderRoot(ITopic iTopic) {
        List<ITopic> childTopicList = iTopic.getAllChildren();

        for (ITopic iTopic2 : childTopicList) {
            System.out.println(iTopic2.getTitleText());
        }

        return childTopicList;
    }

    private ITopic getCertainNameTopic(String certainName,
            List<ITopic> topicList) {
        for (ITopic iTopic : topicList) {
            if (Objects.equal(certainName, iTopic.getTitleText())) {
                logger.debug("找到主题,{},内容为{}", certainName, iTopic.toString());
                return iTopic;
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException, CoreException {

        String path = "D:\\测试方案模板_新版(可自动转换成用例).xmind";

        new XmindParser().createFileAndWriteTestcases(path, null);
    }
}
