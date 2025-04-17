package com.example.fashionshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat") 
    private Long id;

    @Column(name = "name_cat")
    private String name;

    private String status;

    @Column(name = "image_link")
    private String imageLink;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "category-subcategory")
    private List<SubCategory> subCategories;
}