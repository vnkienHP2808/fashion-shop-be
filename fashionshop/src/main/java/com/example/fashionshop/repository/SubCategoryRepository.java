package com.example.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionshop.entity.SubCategory;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByCategoryId(Long categoryId);
}
