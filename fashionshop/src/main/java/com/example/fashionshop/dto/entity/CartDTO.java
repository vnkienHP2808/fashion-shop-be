package com.example.fashionshop.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long idCart;
    private Long id_user;
    private List<CartItemDTO> cartItems;
    private LocalDateTime createdAt;
}