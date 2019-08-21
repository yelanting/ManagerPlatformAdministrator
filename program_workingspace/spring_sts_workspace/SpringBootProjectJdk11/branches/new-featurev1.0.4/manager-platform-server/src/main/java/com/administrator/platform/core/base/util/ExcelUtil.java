package com.administrator.platform.core.base.util;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @since 2018年6月10日 17:15:49
 * @see 读取excel表格内容
 * @author Administrator
 *
 */
public class ExcelUtil {
    public static Object[][] getDataFromExcel(String excelPath) throws Exception {
        Workbook workbook;
        try (FileInputStream excelInputStream = new FileInputStream(excelPath)) {

            workbook = new XSSFWorkbook(excelInputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);
        int rowInExcel = sheet.getPhysicalNumberOfRows();
        int columnInExcel = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] obj = new String[rowInExcel - 1][columnInExcel];
        for (int row = 1; row < rowInExcel; row++) {
            for (int col = 0; col < columnInExcel; col++) {
                obj[row - 1][col] = sheet.getRow(row).getCell(col).getStringCellValue();
                System.out.println(obj[row - 1][col]);
            }
        }
        return obj;
    }
}
