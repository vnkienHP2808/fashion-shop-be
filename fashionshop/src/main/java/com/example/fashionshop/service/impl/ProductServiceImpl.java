package com.example.fashionshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionshop.entity.Product;
import com.example.fashionshop.entity.ImageProduct;
import com.example.fashionshop.repository.ProductRepository;
import com.example.fashionshop.repository.ImageProductRepository;
import com.example.fashionshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct()); 
            p.setImages(images); // set đúng kiểu List<ImageProduct>
        });
        return products;
    }

    @Override
    public Product getProductById(Long id_product) {
        Product product = productRepository.findById(id_product)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id_product));

        List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(product.getIdProduct()); 
        product.setImages(images); 

        return product;
    }

    @Override
    public List<Product> getProductsByCategory(Long idCat) {
        return productRepository.findByIdCat(idCat);
    }

    @Override
    public List<Product> getProductsByCategoryAndSubcategory(Long idCat, Long idSubcat) {
        return productRepository.findByIdCatAndIdSubcat(idCat, idSubcat);
    }

}