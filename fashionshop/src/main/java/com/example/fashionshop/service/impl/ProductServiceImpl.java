package com.example.fashionshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fashionshop.entity.Product;
import com.example.fashionshop.dto.entity.ProductQuantity;
import com.example.fashionshop.entity.ImageProduct;
import com.example.fashionshop.exception.ValidationException;
import com.example.fashionshop.repository.ProductRepository;
import com.example.fashionshop.repository.ImageProductRepository;
import com.example.fashionshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~User~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Page<Product> getAllProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            try {
                String[] range = priceRange.split("-");
                minPrice = Double.parseDouble(range[0]);
                maxPrice = Double.parseDouble(range[1]);
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid price range format");
            }
        }

        Page<Product> productPage = productRepository.findByFilters(idCat, idSubcat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
    }

    @Override
    public Page<Product> getNewProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] range = priceRange.split("-");
            minPrice = Double.parseDouble(range[0]);
            maxPrice = Double.parseDouble(range[1]);
        }

        Page<Product> productPage = productRepository.findNewByFilters(idCat, idSubcat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
    }

    @Override
    public Page<Product> getSaleProducts(int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] range = priceRange.split("-");
            minPrice = Double.parseDouble(range[0]);
            maxPrice = Double.parseDouble(range[1]);
        }

        Page<Product> productPage = productRepository.findSaleByFilters(idCat, idSubcat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
    }
    @Override
    public Page<Product> getSearchProducts(String input, int page, int size, Long idCat, Long idSubcat, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] range = priceRange.split("-");
            minPrice = Double.parseDouble(range[0]);
            maxPrice = Double.parseDouble(range[1]);
        }

        Page<Product> productPage = productRepository.searchByInput(input, idCat, idSubcat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
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
    public Page<Product> getProductsByCategory(Long id_cat, int page, int size, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] range = priceRange.split("-");
            minPrice = Double.parseDouble(range[0]);
            maxPrice = Double.parseDouble(range[1]);
        }

        Page<Product> productPage = productRepository.findByIdCatWithFilters(id_cat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
    }

    @Override
    public Page<Product> getProductsByCategoryAndSubcategory(Long id_cat, Long id_subcat, int page, int size, String priceRange, String occasion) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Double minPrice = null;
        Double maxPrice = null;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] range = priceRange.split("-");
            minPrice = Double.parseDouble(range[0]);
            maxPrice = Double.parseDouble(range[1]);
        }

        Page<Product> productPage = productRepository.findByIdCatAndIdSubcatWithFilters(id_cat, id_subcat, minPrice, maxPrice, occasion, pageable);
        productPage.getContent().forEach(p -> {
            List<ImageProduct> images = imageProductRepository.findByProduct_IdProduct(p.getIdProduct());
            p.setImages(images);
        });
        return productPage;
    }

    @Override
    @Transactional
    public void updateProductQuantity(List<ProductQuantity> updates){
        for(ProductQuantity update : updates){
            Product product = productRepository.findById(update.getIdProduct())
                                .orElseThrow(() -> new RuntimeException("Product not found!"));
            
            int newQuantity = product.getIn_stock() - update.getQuantity();
            int newSold = product.getSold_quantity() + update.getQuantity();
            
            if (newQuantity < 0) {
                throw new RuntimeException("Can't update!");
            }
            product.setIn_stock(newQuantity);
            product.setSold_quantity(newSold);

            productRepository.save(product);
        }
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Product createProduct(Product productRequest) {
        validateProduct(productRequest);
        
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
        validateProduct(productRequest);
        
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

    private void validateProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new ValidationException("Price cannot be negative");
        }
        if (product.getSale_price() != null && product.getSale_price() < 0) {
            throw new ValidationException("Sale price cannot be negative");
        }
        if (product.getSale_price() != null && product.getSale_price() > product.getPrice()) {
            throw new ValidationException("Sale price cannot be greater than regular price");
        }
        if (product.getIn_stock() < 0) {
            throw new ValidationException("Stock cannot be negative");
        }
    }
}
