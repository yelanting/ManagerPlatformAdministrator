package com.administrator.platform.core.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.administrator.platform.constdefine.ExcelConstDefine;
import com.administrator.platform.exception.base.BusinessValidationException;

/**
 * @since 2018年6月10日 17:15:49
 * @see 读取excel表格内容
 * @author Administrator
 *
 */
public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExcelUtil.class);

    public static Object[][] getDataFromExcel(String excelPath) {
        Workbook workbook = getWorkbook(new File(excelPath));

        if (null == workbook) {
            return new Object[][] {};
        }

        Sheet sheet = workbook.getSheetAt(0);
        int rowInExcel = sheet.getPhysicalNumberOfRows();
        int columnInExcel = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] obj = new String[rowInExcel - 1][columnInExcel];
        for (int row = 1; row < rowInExcel; row++) {
            for (int col = 0; col < columnInExcel; col++) {
                obj[row - 1][col] = sheet.getRow(row).getCell(col)
                        .getStringCellValue();
                System.out.println(obj[row - 1][col]);
            }
        }
        return obj;
    }

    /**
     * 获取工作博
     * 
     * @see :
     * @param :
     * @return : XSSFWorkbook
     * @param file
     * @return
     */
    public static Workbook getWorkbook(File excelPath) {
        Workbook workbook;
        try (FileInputStream excelInputStream = new FileInputStream(
                excelPath)) {
            workbook = new XSSFWorkbook(excelInputStream);
            return workbook;
        } catch (FileNotFoundException e) {
            LOGGER.error("获取工作簿失败,文件不存在:{}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("获取工作博失败,io异常:{}", e.getMessage());
        }

        return null;
    }

    /**
     * 获取工作博
     * 
     * @see :
     * @param :
     * @return : XSSFWorkbook
     * @param file
     * @return
     */
    public static Sheet getSheet(Workbook workbook, int sheetIndex) {
        if (null == workbook) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet;
    }

    /**
     * 获取工作博
     * 
     * @see :
     * @param :
     * @return : XSSFWorkbook
     * @param file
     * @return
     */
    public static Sheet getSheet(Workbook workbook, String sheetName) {
        if (null == workbook) {
            return null;
        }
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }

    /**
     * 
     * @see :
     * @param :
     * @return : File
     * @param parentPath
     * @param excelName
     * @return
     */
    public static File createDefaultExcel(String parentPath, String excelName) {
        ValidationUtil.validateStringNullOrEmpty(excelName, "excel名称");
        ValidationUtil.validateStringNullOrEmpty(parentPath, "存放excel的文件夹");

        File parentFile = new File(parentPath);

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        File excelFile = new File(parentFile, excelName);

        if (!excelFile.exists()) {
            try {
                excelFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error("创建文件失败:{}", e.getMessage());
                throw new BusinessValidationException("创建文件失败");
            }
        }

        createDefaultExcel(excelFile);
        return excelFile;
    }

    /**
     * 创建excel
     * 
     * @see :
     * @param :
     * @return : void
     * @param filePath
     */
    private static void createDefaultExcel(File filePath) {

        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        if (!filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch (IOException e) {
                LOGGER.error("创建文件:{}失败：{}", filePath.getAbsolutePath(),
                        e.getMessage());
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                XSSFWorkbook workbook = new XSSFWorkbook();) {
            workbook.createSheet(ExcelConstDefine.DEFAULT_SHEET_NAME);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            LOGGER.error("创建excel失败：{}", e.getMessage());
        }
    }

    /**
     * 创建标题头
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static void writeHeader(File excelFile, String[] headers,
            String sheetName) {
        if (!excelFile.exists()) {
            createDefaultExcel(excelFile);
        }

        Workbook workbook = getWorkbook(excelFile);
        if (null == workbook) {
            return;
        }

        Sheet sheet = getSheet(workbook, sheetName);
        writeHeader(headers, sheet, createDefaultCellStyle(workbook));
        writeToFile(workbook, excelFile);
    }

    /**
     * 创建标题头
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static void writeHeaderToDefaultSheet(File excelFile,
            String[] headers) {
        writeHeader(excelFile, headers, ExcelConstDefine.DEFAULT_SHEET_NAME);
    }

    /**
     * 创建标题头
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static void writeHeader(File excelFile, String[] headers,
            int sheetIndex) {
        if (!excelFile.exists()) {
            createDefaultExcel(excelFile);
        }
        Workbook workbook = getWorkbook(excelFile);
        if (null == workbook) {
            return;
        }
        Sheet sheet = getSheet(workbook, sheetIndex);
        writeHeader(headers, sheet, createDefaultCellStyle(workbook));
        writeToFile(workbook, excelFile);
    }

    /**
     * 创建标题头
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    private static void writeHeader(String[] headers, Sheet sheet) {
        writeHeader(headers, sheet, null);
    }

    /**
     * 创建标题头
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    private static void writeHeader(String[] headers, Sheet sheet,
            CellStyle cellStyle) {

        sheet.createFreezePane(0, 1);
        Row firstRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(headers[i]);

            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }

        }
    }

    /**
     * 创建获取文件的默认sheet
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static Sheet getDefaultSheet(File excelFile) {
        Workbook workbook = getWorkbook(excelFile);
        return getSheet(workbook, ExcelConstDefine.DEFAULT_SHEET_NAME);
    }

    /**
     * 创建获取文件的默认sheet
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static Sheet getDefaultSheet(Workbook workbook) {
        return getSheet(workbook, ExcelConstDefine.DEFAULT_SHEET_NAME);
    }

    /**
     * 创建获取文件的默认sheet
     * 
     * @see :
     * @param :
     * @return : void
     * @param excelFile
     */
    public static void writeToFile(Workbook workbook, File excelFile) {
        try {
            workbook.write(new FileOutputStream(excelFile));
        } catch (IOException e) {
            LOGGER.error("写入excel文件:{}失败:{}", excelFile, e.getMessage());
        }
    }

    /**
     * 设置样式
     * 
     * @see :
     * @param :
     * @return : XSSFCellStyle
     * @param workbook
     * @return
     */
    public static CellStyle createDefaultCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        // cellStyle.setLocked(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        return cellStyle;
    }

    /**
     * 设置样式
     * 
     * @see :
     * @param :
     * @return : XSSFCellStyle
     * @param workbook
     * @return
     */
    public static CellStyle createDefaultCellStyleOfHeader(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        // cellStyle.setLocked(true);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        return cellStyle;
    }

    public static void main(String[] args) {
        String filePath = "D:\\testworkspace";
        String excelName = "test.xlsx";
        ExcelUtil.writeHeader(new File(filePath, excelName),
                new String[] { "name", "sex" },
                ExcelConstDefine.DEFAULT_SHEET_NAME);
    }
}
