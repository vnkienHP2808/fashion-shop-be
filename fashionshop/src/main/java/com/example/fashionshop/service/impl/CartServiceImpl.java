package com.example.fashionshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Cart addItemToCart(Long userId, Long productId, int quantity, String size) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        // tìm item đã có trong giỏ hàng hay chưa
        Optional<CartItem> existing = cartItemRepository.findByCart_IdCartAndProduct_IdProductAndSize(cart.getIdCart(), productId, size);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setSize(size);
            cartItemRepository.save(item); // lưu lại item
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSize(size);
            cartItemRepository.save(newItem); // lưu mới
        }

        return cart;
    }

    @Override
    public void updateItemQuantity(Long userId, Long productId, int quantity, String size) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCart_IdCartAndProduct_IdProductAndSize(cart.getIdCart(), productId, size)
            .ifPresent(item -> {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            });
    }

    @Override
    public void removeItem(Long userId, Long productId, String size) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.findByCart_IdCartAndProduct_IdProductAndSize(cart.getIdCart(), productId, size)
            .ifPresent(cartItemRepository::delete);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCart_IdCart(cart.getIdCart());
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByCart_IdCart(getOrCreateCart(userId).getIdCart());
    }
}
