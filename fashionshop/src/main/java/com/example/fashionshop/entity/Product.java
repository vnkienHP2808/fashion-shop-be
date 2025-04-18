package com.example.fashionshop.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    private String name_product;

    @Column(name = "id_cat")
    private Long idCat;

    @Column(name = "id_subcat")
    private Long idSubcat;

    private Boolean is_new;
    private Boolean is_sale;
    private Integer price;
    private Integer sale_price;
    private String occasion;
    private Integer sold_quantity;
    private Integer in_stock;
    private String status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageProduct> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

}