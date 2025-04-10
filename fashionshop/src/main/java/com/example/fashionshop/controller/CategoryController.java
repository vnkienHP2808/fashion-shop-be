package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.entity.SubCategory;
import com.example.fashionshop.service.CategoryService;
import com.example.fashionshop.service.SubCategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public List<com.example.fashionshop.entity.Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategory> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId);
    }
}