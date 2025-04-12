package com.example.fashionshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
     @JsonBackReference(value = "order-orderDetails")
    private Order order;


    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
     @JsonBackReference(value = "product-orderDetails")
    private Product product;


    private Integer quantity;

    private Integer totalAmount;
}
