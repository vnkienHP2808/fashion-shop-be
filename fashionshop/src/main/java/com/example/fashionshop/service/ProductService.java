package com.example.fashionshop.service;


import org.springframework.data.domain.Page;

import com.example.fashionshop.entity.Product;

public interface ProductService {
    Page<Product> getAllProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);
    Page<Product> getNewProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);
    Page<Product> getSaleProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);

    Product getProductById(Long id_product);

    Page<Product> getProductsByCategory(Long id_cat, int page, int size, String priceRange, String occasion);
    Page<Product> getProductsByCategoryAndSubcategory(Long id_cat, Long id_subcat, int page, int size, String priceRange, String occasion);

    Page<Product> getSearchProducts(String input, int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);
//~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}