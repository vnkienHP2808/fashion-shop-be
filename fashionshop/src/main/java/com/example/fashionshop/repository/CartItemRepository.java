package com.example.fashionshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionshop.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_IdCart(Long cartId);
    void deleteByCart_IdCart(Long cartId);

    Optional<CartItem> findByCart_IdCartAndProduct_IdProductAndSize(Long cartId, Long productId, String size);
}
