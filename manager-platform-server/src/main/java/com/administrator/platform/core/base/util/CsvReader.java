/**
 * 
 */
package com.administrator.platform.core.base.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.definition.form.GlobalDefination;

/**
 * @author Administrator
 * @see Csv工具类
 * @since 2018年8月12日 10:08:50
 */
public class CsvReader {
    private static Logger logger = LoggerFactory.getLogger(CsvReader.class);

    /**
     * @see 构造函数私有，工具类防止重写
     */
    private CsvReader() {
    }

    public static Object[][] getTestData(String fileName) {

        final List<Object[]> records = new ArrayList<Object[]>();
        String record;

        BufferedReader file = null;

        try {
            /**
             * 设定UTF-8字符集，使用带缓冲区的字符输入流BufferedReader读取文件内容
             */
            file = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName),
                            GlobalDefination.CHAR_SET_DEFAULT));
            /**
             * 忽略读取CSV文件的标题行（第一行）
             */
            String firstLine = file.readLine();

            /**
             * 遍历读取文件中除第一行外的其他所有行内容 并存储在名为records的ArrayList中
             * 每一个recods中存储的对象为一个String数组
             */
            firstLine = null;
            while ((record = file.readLine()) != null) {
                final String[] fileds = record.split(",");
                records.add(fileds);
            }
        } catch (final IOException e) {
            logger.error("操作文件失败:{}", e.getMessage());
        } finally {
            if (null != file) {
                try {
                    file.close();
                } catch (final Exception e2) {
                    logger.error("关闭文件失败");
                }
            }
        }

        /**
         * 定义函数返回值，即Object[][]
         * 将存储测试数据的list转换为一个Object的二维数组
         */

        final int totalSize = records.size();
        final Object[][] result = new Object[totalSize][];
        for (int i = 0; i < totalSize; i++) {
            result[i] = records.get(i);
        }

        return result;
    }

}
