package com.example.fashionshop.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Integer idOrderDetail;
    private Long orderId;
    private ProductDTO product;
    private Integer quantity;
    private Integer totalAmount;
}