package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id_category);
}

