package com.example.fashionshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subcategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_subcat;

    @Column(name = "name_subcat")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_cat", nullable = false)
    @JsonBackReference
    private Category category;

}
