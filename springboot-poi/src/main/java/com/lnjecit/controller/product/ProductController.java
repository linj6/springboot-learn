package com.lnjecit.controller.product;

import com.lnjecit.service.ProductService;
import com.lnjecit.util.DateUtil;
import com.lnjecit.util.Result;
import com.lnjecit.view.entity.product.Product;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2017-12-16 20:52
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/getProductList")
    public Result getProductList(Map<String, Object> params) {
        List<Product> productList = productService.getProductList(params);
        return Result.success(productList);
    }

    @RequestMapping("/exportProductsToExcel")
    public void exportProductsToExcel(HttpServletResponse response) {
        try {
            List<Product> products = productService.getProductList(null);
            HSSFWorkbook workbook = productService.exportProductsToExcel(products);

            String filename = "export_products_" + DateUtil.getTime(new Date()).toString().replace(" ", "");
            filename = filename.replace("-", "");
            filename = filename.replace(":", "");

            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-disposition", "attachment;filename="
//                    + filename + ".xls");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + ".xls\"");

            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/importProducts")
    public Result importProducts(MultipartFile file) {
        try {
            productService.importProducts(file);
        } catch (ParseException e) {
            e.printStackTrace();
            return Result.error("产品导入失败！");
        }
        return Result.success();
    }
}
