package com.example.fashionshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fashionshop.entity.Cart;
import com.example.fashionshop.entity.CartItem;
import com.example.fashionshop.entity.Product;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.repository.CartItemRepository;
import com.example.fashionshop.repository.CartRepository;
import com.example.fashionshop.repository.ProductRepository;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.CartService;
@Service
public class CartServiceImpl implements CartService {
    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Override
    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tìm cartItem đã có trong giỏ hàng hay chưa
        Optional<CartItem> existing = cartItemRepository.findByCart_IdCartAndProduct_IdProduct(cart.getIdCart(), productId);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item); // lưu lại cartItem
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem); // lưu mới
        }

        return cart; // Không cần cartRepository.save(cart)
    }

    @Override
    public void updateItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCart_IdCartAndProduct_IdProduct(cart.getIdCart(), productId)
            .ifPresent(item -> {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            });
    }

    @Override
    public void removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCart_IdCartAndProduct_IdProduct(cart.getIdCart(), productId)
            .ifPresent(cartItemRepository::delete);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCart_IdCart(cart.getIdCart());
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByCart_IdCart(getOrCreateCart(userId).getIdCart());
    }
}
