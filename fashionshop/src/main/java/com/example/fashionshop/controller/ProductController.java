package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.dto.ProductDTO;
import com.example.fashionshop.entity.Product;
import com.example.fashionshop.service.ProductService;
import com.example.fashionshop.util.DTOMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(DTOMapper.toProductDTOList(products));
    }

    @GetMapping("/{id_product}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id_product) {
        Product product = productService.getProductById(id_product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(product));
    }
    
    @GetMapping("/category/{id_cat}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable("id_cat") Long id_cat) {
        List<Product> products = productService.getProductsByCategory(id_cat);
        return ResponseEntity.ok(DTOMapper.toProductDTOList(products));
    }

    @GetMapping("/category/{id_cat}/subcategory/{id_subcat}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryAndSubcategory(
            @PathVariable("id_cat") Long id_cat,
            @PathVariable("id_subcat") Long id_subcat) {
        List<Product> products = productService.getProductsByCategoryAndSubcategory(id_cat, id_subcat);
        return ResponseEntity.ok(DTOMapper.toProductDTOList(products));
    }
    //~~~~~~~~~~~~~~~
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(DTOMapper.toProductDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}