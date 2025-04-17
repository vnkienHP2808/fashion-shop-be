package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id_product);

    List<Product> getProductsByCategory(Long id_cat);
    List<Product> getProductsByCategoryAndSubcategory(Long id_cat, Long id_subcat);

    Product createProduct(Product product);

}