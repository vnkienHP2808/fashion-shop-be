package com.example.fashionshop.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.example.fashionshop.dto.entity.ProductQuantity;
import com.example.fashionshop.entity.Product;

public interface ProductService {
    Page<Product> getAllProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);
    Page<Product> getNewProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);
    Page<Product> getSaleProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion);

    Product getProductById(Long id_product);

    Page<Product> getProductsByCategory(Long id_cat, int page, int size, String priceRange, String occasion);
    Page<Product> getProductsByCategoryAndSubcategory(Long id_cat, Long id_subcat, int page, int size, String priceRange, String occasion);

    Page<Product> getSearchProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion, String nameProduct);
//~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void updateProductQuantity(List<ProductQuantity> updates);
    void deleteProduct(Long id);
}