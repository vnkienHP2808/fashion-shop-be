package com.example.fashionshop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fashionshop.dto.entity.ProductDTO;
import com.example.fashionshop.dto.entity.ProductQuantity;
import com.example.fashionshop.entity.ImageProduct;
import com.example.fashionshop.entity.Product;
import com.example.fashionshop.service.ProductService;
import com.example.fashionshop.util.DTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Value("${image.upload-dir}")
    private String uploadDir;

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
    
    @PutMapping("/update-quantity-and-sold")
    public ResponseEntity<?> updateProductQuantityAndSold(@RequestBody List<ProductQuantity> updateProductQuantity){
        productService.updateProductQuantity(updateProductQuantity);
        return ResponseEntity.ok().build();
    }

//~~~~~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~~

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductDTO> createProduct(
            @RequestParam("product") String productJson,
            @RequestParam(value = "images", required = false) MultipartFile[] images) throws IOException {
        // đọc json của sản phẩm
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);

        // xử lý việc upload ảnh
        if (images != null && images.length > 0) {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            List<ImageProduct> imageEntities = Arrays.stream(images)
                    .filter(file -> !file.isEmpty())
                    .map(file -> {
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        try {
                            Path filePath = Paths.get(uploadDir + fileName);
                            Files.write(filePath, file.getBytes());
                            return ImageProduct.builder()
                                    .imageLink(fileName) // chỉ lưu tên file
                                    .build();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save image: " + fileName, e);
                        }
                    })
                    .collect(Collectors.toList());

            product.setImages(imageEntities);
        }

        Product created = productService.createProduct(product);
        return new ResponseEntity<>(DTOMapper.toProductDTO(created), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestParam("product") String productJson,
            @RequestParam(value = "images", required = false) MultipartFile[] images) throws IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);

        // Lấy danh sách ảnh hiện tại từ product (trong JSON)
        List<ImageProduct> existingImages = product.getImages() != null ? product.getImages() : new ArrayList<>();

        if (images != null && images.length > 0) {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            List<ImageProduct> newImageEntities = Arrays.stream(images)
                    .filter(file -> !file.isEmpty())
                    .map(file -> {
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        try {
                            Path filePath = Paths.get(uploadDir + fileName);
                            Files.write(filePath, file.getBytes());
                            return ImageProduct.builder()
                                    .imageLink(fileName)
                                    .build();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save image: " + fileName, e);
                        }
                    })
                    .collect(Collectors.toList());

            // kết hợp ảnh cũ và mới
            existingImages.addAll(newImageEntities);
            product.setImages(existingImages);
        }

        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}