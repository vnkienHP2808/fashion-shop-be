package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.entity.Cart;
import com.example.fashionshop.entity.CartItem;

public interface CartService {
    Cart getOrCreateCart(Long userId);
    Cart addItemToCart(Long userId, Long productId, int quantity, String size);
    void updateItemQuantity(Long userId, Long productId, int quantity, String size);
    void removeItem(Long userId, Long productId, String size);
    void clearCart(Long userId);
    List<CartItem> getCartItems(Long userId);
}

