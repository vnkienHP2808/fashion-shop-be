package com.example.fashionshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.dto.entity.ProductDTO;
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

//~~~~~~~~~~~~~~~~~~~~User~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long idCat,
            @RequestParam(required = false) Long idSubcat,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getAllProducts(page, size, idCat, idSubcat, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<ProductDTO>> getNewProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long idCat,
            @RequestParam(required = false) Long idSubcat,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getNewProducts(page, size, idCat, idSubcat, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/sale")
    public ResponseEntity<Page<ProductDTO>> getSaleProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long idCat,
            @RequestParam(required = false) Long idSubcat,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getSaleProducts(page, size, idCat, idSubcat, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/search/{input}")
    public ResponseEntity<Page<ProductDTO>> getSearchProducts(
            @PathVariable("input") String input,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long idCat,
            @RequestParam(required = false) Long idSubcat,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getSearchProducts(input, page, size, idCat, idSubcat, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/{id_product}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id_product) {
        Product product = productService.getProductById(id_product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(product));
    }
    
    @GetMapping("/category/{id_cat}")   
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable("id_cat") Long id_cat,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getProductsByCategory(id_cat, page, size, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/category/{id_cat}/subcategory/{id_subcat}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategoryAndSubcategory(
            @PathVariable("id_cat") Long id_cat,
            @PathVariable("id_subcat") Long id_subcat,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String occasion) {
        Page<Product> productPage = productService.getProductsByCategoryAndSubcategory(id_cat, id_subcat, page, size, priceRange, occasion);
        Page<ProductDTO> productDTOPage = productPage.map(DTOMapper::toProductDTO);
        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOPage);
    }
    
//~~~~~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~~
    @PostMapping("/create")
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