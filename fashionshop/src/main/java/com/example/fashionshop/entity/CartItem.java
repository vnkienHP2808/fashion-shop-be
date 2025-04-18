package com.example.fashionshop.entity;



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
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    private Integer quantity;
}