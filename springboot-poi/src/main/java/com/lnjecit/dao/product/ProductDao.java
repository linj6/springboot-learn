package com.lnjecit.dao.product;


import com.lnjecit.view.entity.product.Product;

import java.util.List;
import java.util.Map;

/**
 * Create by Linj
 * create time: 2017-12-16 19:48:51
 */
public interface ProductDao {
    List<Product> getProductList(Map<String, Object> params);

    void addProduct(Product product);

    void updateProductById(Product product);

    void deleteProductById(int id);

    Product getProductById(int id);

}
