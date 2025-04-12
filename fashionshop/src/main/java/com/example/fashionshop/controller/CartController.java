package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.fashionshop.dto.CartRequest;
import com.example.fashionshop.entity.CartItem;
import com.example.fashionshop.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody CartRequest request) {
        cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Added to cart");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody CartRequest request) {
        cartService.updateItemQuantity(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Quantity updated");
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok("Item removed");
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}
