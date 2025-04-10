package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.entity.SubCategory;

public interface SubCategoryService {
    List<SubCategory> getSubCategoriesByCategoryId(Long categoryId);
}
