package com.lnjecit.service;

import com.lnjecit.util.Result;
import com.lnjecit.view.entity.product.Product;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getProductList(Map<String, Object> params);

    HSSFWorkbook exportProductsToExcel(List<Product> products);

    Result importProducts(MultipartFile file) throws ParseException;

}
