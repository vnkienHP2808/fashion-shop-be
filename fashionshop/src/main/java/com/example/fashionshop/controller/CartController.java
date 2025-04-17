
package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.fashionshop.dto.CartItemDTO;
import com.example.fashionshop.dto.CartRequest;
import com.example.fashionshop.entity.CartItem;
import com.example.fashionshop.service.CartService;
import com.example.fashionshop.util.DTOMapper;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(DTOMapper.toCartItemDTOList(cartItems));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody CartRequest request) {
        cartService.addItemToCart(request.getId_user(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Added to cart");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody CartRequest request) {
        cartService.updateItemQuantity(request.getId_user(), request.getProductId(), request.getQuantity());
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