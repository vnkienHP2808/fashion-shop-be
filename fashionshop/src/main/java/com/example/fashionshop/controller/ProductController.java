package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.entity.Product;
import com.example.fashionshop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id_product}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id_product) {
        return ResponseEntity.ok(productService.getProductById(id_product));
    }
    
    @GetMapping("/category/{id_cat}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("id_cat") Long id_cat) {
        return ResponseEntity.ok(productService.getProductsByCategory(id_cat));
    }

    @GetMapping("/category/{id_cat}/subcategory/{id_subcat}")
    public ResponseEntity<List<Product>> getProductsByCategoryAndSubcategory(
            @PathVariable("id_cat") Long id_cat,
            @PathVariable("id_subcat") Long id_subcat) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndSubcategory(id_cat, id_subcat));
    }
}
