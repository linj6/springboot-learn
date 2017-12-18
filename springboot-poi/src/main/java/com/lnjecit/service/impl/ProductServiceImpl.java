package com.lnjecit.service.impl;

import com.lnjecit.dao.product.ProductDao;
import com.lnjecit.service.ProductService;
import com.lnjecit.util.DateUtil;
import com.lnjecit.util.ExcelUtil;
import com.lnjecit.util.Result;
import com.lnjecit.view.entity.product.Product;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-12-16 20:12
 **/
@Service
public class ProductServiceImpl implements ProductService {

    //sheet标题
    private final static String SHEET1_TITLE = "产品列表";
    //sheet列数
    private final static int SHEET1_COLUNM = 9;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProductList(Map<String, Object> params) {
        return productDao.getProductList(params);
    }

    @Override
    public HSSFWorkbook exportProductsToExcel(List<Product> products) {
        //一个Excel文件的组织形式：一个工作空间，一个工作空间由多个表格（sheet）组成,一个表格（sheet）由多行（row）组成，一行（row)
        //由多个单元格（cell）组成

        //导出Excel文件流程
        //第一步：创建Excel文件工作空间(Workbook)
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (products != null && products.size() > 0) {
            //第二步：创建表格（sheet）
            HSSFSheet sheet1 = workbook.createSheet(SHEET1_TITLE);

            //设置列宽
            sheet1.setDefaultColumnWidth(20);

            // 设置字体样式
            HSSFFont font = ExcelUtil.getFont(workbook);
            HSSFCellStyle style = ExcelUtil.getCellStyle(workbook);

            //统计sheet1表格行数
            int counter = 0;
            //创建标题行
            counter = createProductTitleRow(workbook, sheet1, counter);

            //向表格中写入数据
            Iterator<Product> iter = products.iterator();
            while (iter.hasNext()) {
                Product product = iter.next();
                if (product != null) {
                    HSSFRow newRow = sheet1.createRow(counter);
                    newRow.setHeight((short) 250);
                    for (int j = 0; j < SHEET1_COLUNM; j++) {
                        HSSFCell newCell = newRow.createCell(j);
                        newCell.setCellStyle(style);
                    }
                    //产品名称
                    newRow.getCell(0).setCellValue(product.getProductName());
                    //产品编号
                    newRow.getCell(1).setCellValue(product.getProductCode());
                    //品牌
                    newRow.getCell(2).setCellValue(product.getBrand());
                    //型号/规格
                    newRow.getCell(3).setCellValue(product.getModelnumber());
                    //价格
                    newRow.getCell(4).setCellValue(product.getPrice());
                    //库存数量
                    newRow.getCell(5).setCellValue(product.getInventoryQuantity());
                    //产品单位
                    newRow.getCell(6).setCellValue(product.getProductUnit());
                    //状态
                    newRow.getCell(7).setCellValue(product.getStatus());
                    //创建时间
                    newRow.getCell(8).setCellValue(DateUtil.DateToString(product.getCreateTime(), DateUtil.DATE_TO_STRING_DETAIAL_PATTERN));

                    counter++;
                }
            }
        }
        return workbook;
    }

    @Override
    public Result importProducts(MultipartFile file) throws ParseException {
        //从第二行开始读取
        ExcelUtil excelUtil = new ExcelUtil(2);
        List<List<Object>> list = excelUtil.readExcel(file);
        List<Product> products = new ArrayList<>();
        if (null == list || list.size() == 0) {
            return Result.error("文档数据为空");
        }
        for (List<Object> objList : list) {
            Product product = new Product();
            //产品名称
            product.setProductName(objList.get(0).toString());
            //产品编号
            product.setProductCode(objList.get(1).toString());
            //品牌
            product.setBrand(objList.get(2).toString());
            //型号/规格
            product.setModelnumber(objList.get(3).toString());
            //价格
            product.setPrice(Double.valueOf(objList.get(4).toString()));
            //库存数量
            product.setInventoryQuantity((new Double(objList.get(5).toString())).intValue());
            //产品单位
            product.setProductUnit(objList.get(6).toString());
            //状态
            product.setStatus(new Double(objList.get(7).toString()).intValue());
            //创建时间
            product.setCreateTime(DateUtil.stringToDate(objList.get(8).toString(), DateUtil.DATE_TO_STRING_DETAIAL_PATTERN));

            products.add(product);
        }

        if (null != products && products.size() > 0) {
            for (Product product : products) {
                productDao.addProduct(product);
            }
        }
        System.out.println("products:" + products);
        return Result.success();
    }

    /**
     * 给产品列表表格设置标题行
     *
     * @param workbook
     * @param sheet1
     * @param counter
     * @return
     */
    private int createProductTitleRow(HSSFWorkbook workbook, HSSFSheet sheet1, int counter) {
        //第三步：创建标题行（header)
        HSSFRow titleRow = sheet1.createRow(counter);
        CellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 12);  //字体大小
        cellStyle.setFont(font);

        //创建标题行中的单元格
        for (int i = 0; i < SHEET1_COLUNM; i++) {
            HSSFCell newCell = titleRow.createCell(i);
            newCell.setCellStyle(cellStyle);
        }

        titleRow.getCell(0).setCellValue("产品名称");
        titleRow.getCell(1).setCellValue("产品编号");
        titleRow.getCell(2).setCellValue("品牌");
        titleRow.getCell(3).setCellValue("型号/规格");
        titleRow.getCell(4).setCellValue("价格");
        titleRow.getCell(5).setCellValue("库存数量");
        titleRow.getCell(6).setCellValue("产品单位");
        titleRow.getCell(7).setCellValue("状态");
        titleRow.getCell(8).setCellValue("创建时间");

        counter++;
        return counter;
    }
}
