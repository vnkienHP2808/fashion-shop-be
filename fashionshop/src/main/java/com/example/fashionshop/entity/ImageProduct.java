package com.example.fashionshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;

    @Column(name = "image_link")
    private String imageLink;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    @JsonBackReference
    private Product product;
}
