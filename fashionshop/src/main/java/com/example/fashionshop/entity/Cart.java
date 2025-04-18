package com.example.fashionshop.entity;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;


    private LocalDateTime createdAt = LocalDateTime.now();
}