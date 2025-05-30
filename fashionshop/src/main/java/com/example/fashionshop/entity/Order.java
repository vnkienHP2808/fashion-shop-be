package com.example.fashionshop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "`order`") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order") 
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;


    @Column(name = "status") 
    private String status;

    @Column(name = "order_date") 
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "address") 
    private String address;

    @Column(name = "phone_number") 
    private String phoneNumber;

    @Column(name = "payment_method") 
    private String paymentMethod;

    @Column(name = "shipping_fee") 
    private Integer shippingFee;

    @Column(name = "grand_total") 
    private Integer grandTotal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

}