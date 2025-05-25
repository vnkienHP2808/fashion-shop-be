package com.example.fashionshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrderDetail;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;


    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;


    private Integer quantity;
    private String size;
    private Integer totalAmount;
}