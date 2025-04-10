package com.example.fashionshop.repository;

import com.example.fashionshop.entity.ImageProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    List<ImageProduct> findByProduct_IdProduct(Long idProduct);
}

