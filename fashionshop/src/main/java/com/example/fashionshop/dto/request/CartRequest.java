package com.example.fashionshop.dto.request;

import lombok.Data;

@Data
public class CartRequest {
    private Long id_user;
    private Long productId;
    private Integer quantity;
    private String size;
}
