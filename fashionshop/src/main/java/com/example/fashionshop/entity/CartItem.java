package com.example.fashionshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartItem;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cart", nullable = false)
     @JsonBackReference(value = "cart-items")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    @JsonManagedReference(value = "product-cartItems") // chú thích 1
    //@JsonBackReference("product-cartItems") // chú thích 2
    private Product product;

    private Integer quantity;
}
