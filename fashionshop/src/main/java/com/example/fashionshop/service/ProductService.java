package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id_product);
}
