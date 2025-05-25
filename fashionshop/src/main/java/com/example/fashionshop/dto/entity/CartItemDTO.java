package com.example.fashionshop.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long idCartItem;
    private Long idCart;
    private ProductDTO product;
    private String size;
    private Integer quantity;  
}