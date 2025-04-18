package com.example.fashionshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
            p.setImages(images);
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

    @Override
    public Product createProduct(Product productRequest) {
        Product savedProduct = productRepository.save(Product.builder()
            .name_product(productRequest.getName_product())
            .price(productRequest.getPrice())
            .sale_price(productRequest.getSale_price())
            .is_new(productRequest.getIs_new())
            .is_sale(productRequest.getIs_sale())
            .occasion(productRequest.getOccasion())
            .sold_quantity(productRequest.getSold_quantity())
            .in_stock(productRequest.getIn_stock())
            .status(productRequest.getStatus())
            .idCat(productRequest.getIdCat())
            .idSubcat(productRequest.getIdSubcat())
            .build()
        );

        if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
            List<ImageProduct> imageEntities = productRequest.getImages().stream()
                .map(image -> ImageProduct.builder()
                    .imageLink(image.getImageLink())
                    .product(savedProduct)
                    .build())
                .collect(Collectors.toList());

            savedProduct.setImages(imageProductRepository.saveAll(imageEntities));
        }

        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, Product productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setName_product(productRequest.getName_product());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setSale_price(productRequest.getSale_price());
        existingProduct.setIs_new(productRequest.getIs_new());
        existingProduct.setIs_sale(productRequest.getIs_sale());
        existingProduct.setOccasion(productRequest.getOccasion());
        existingProduct.setSold_quantity(productRequest.getSold_quantity());
        existingProduct.setIn_stock(productRequest.getIn_stock());
        existingProduct.setStatus(productRequest.getStatus());
        existingProduct.setIdCat(productRequest.getIdCat());
        existingProduct.setIdSubcat(productRequest.getIdSubcat());

        if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
            imageProductRepository.deleteAll(imageProductRepository.findByProduct_IdProduct(id));
            List<ImageProduct> newImages = productRequest.getImages().stream()
                .map(img -> ImageProduct.builder()
                    .imageLink(img.getImageLink())
                    .product(existingProduct)
                    .build())
                .collect(Collectors.toList());
            existingProduct.setImages(imageProductRepository.saveAll(newImages));
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        imageProductRepository.deleteAll(imageProductRepository.findByProduct_IdProduct(id));
        productRepository.delete(product);
    }
}
