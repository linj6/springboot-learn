package com.lnjecit.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * excel导入导出工具类
 *
 * @author linj
 * @create 2017-12-16 14:49
 **/
public class ExcelUtil {

    /**
     * 默认的开始读取的行位置为第一行（索引值为0）
     */
    private final static int READ_START_POS = 0;

    /**
     * 设定开始读取的位置，默认为0
     */
    private int startReadPos = READ_START_POS;

    public ExcelUtil() {
    }

    public ExcelUtil(int startReadPos) {
        if (startReadPos > 0) {
            this.startReadPos = startReadPos;
        }
    }

    /**
     * 给Excel工作空间设置字体
     *
     * @param workbook
     * @return
     */
    public static HSSFFont getFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        return font;
    }

    /**
     * 设置单元格样式
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(getFont(workbook));
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        return style;
    }

    public List<List<Object>> readExcel(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        try {
            if (originalFileName.endsWith(".xls")) {
                //使用xls格式读取
                return read2003Excel(file.getInputStream());
            } else if (originalFileName.endsWith(".xlsx")) {
                //使用xlsx方式读取
                return read2007Excel(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<List<Object>> readExcel(File file) throws IOException {
        String originalFileName = file.getName();
        try {
            if (originalFileName.endsWith(".xls")) {
                //使用xls格式读取
                return read2003Excel(new FileInputStream(file));
            } else if (originalFileName.endsWith(".xlsx")) {
                //使用xlsx方式读取
                return read2007Excel(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel(97-03版，xls格式)
     *
     * @param is
     * @param
     * @return
     * @throws IOException
     */
    private List<List<Object>> read2003Excel(InputStream is) throws IOException {
        List<List<Object>> list = new LinkedList<>();
        HSSFWorkbook hwb = new HSSFWorkbook(is);
        int sheetCount = hwb.getNumberOfSheets();
        for (int n = 0; n < sheetCount; n++) {
            HSSFSheet sheet = hwb.getSheetAt(n);
            Object value;
            HSSFRow row;
            HSSFCell cell;
            int counter = 0;
            for (int i = (startReadPos - 1); counter < sheet.getPhysicalNumberOfRows() - (startReadPos - 1); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<Object>();
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    value = getCellValue(cell);
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                    linked.add(value);
                }
                list.add(linked);
            }
        }
        return list;
    }

    /**
     * 读取Excel 2007版，xlsx格式
     *
     * @param is
     * @return
     * @throws IOException
     */
    private List<List<Object>> read2007Excel(InputStream is) throws IOException {
        List<List<Object>> list = new LinkedList<>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(is);
        int sheetCount = xwb.getNumberOfSheets();
        for (int n = 0; n < sheetCount; n++) {
            XSSFSheet sheet = xwb.getSheetAt(n);
            Object value;
            XSSFRow row;
            XSSFCell cell;
            int counter = 0;
            for (int i = (startReadPos - 1); counter < sheet.getPhysicalNumberOfRows() - (startReadPos - 1); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<Object>();
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    value = getCellValue(cell);
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                    linked.add(value);
                }
                list.add(linked);
            }
        }
        return list;
    }

    private Object getCellValue(Cell cell) {
        Object value;
        DecimalFormat df = new DecimalFormat("0");// 格式化 number String
        // 字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
        DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = nf.format(cell.getNumericCellValue());
                } else {
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                value = cell.toString();
        }
        return value;
    }
}
