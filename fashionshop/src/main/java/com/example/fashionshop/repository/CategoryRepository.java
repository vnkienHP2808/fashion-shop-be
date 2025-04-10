package com.example.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionshop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
