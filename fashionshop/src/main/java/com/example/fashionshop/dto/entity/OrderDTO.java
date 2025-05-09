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
public class OrderDTO {
    private Long idOrder;
    private Long id_user;
    private String status;
    private LocalDateTime orderDate;
    private String address;
    private String phoneNumber;
    private String paymentMethod;
    private Integer shippingFee;
    private Integer grandTotal;
    private List<OrderDetailDTO> orderDetails;
}