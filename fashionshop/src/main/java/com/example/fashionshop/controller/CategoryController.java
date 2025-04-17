

package com.example.fashionshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.dto.CategoryDTO;
import com.example.fashionshop.dto.SubCategoryDTO;
import com.example.fashionshop.entity.Category;
import com.example.fashionshop.entity.SubCategory;
import com.example.fashionshop.service.CategoryService;
import com.example.fashionshop.service.SubCategoryService;
import com.example.fashionshop.util.DTOMapper;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return DTOMapper.toCategoryDTOList(categories);
    }

    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategoryDTO> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
        return DTOMapper.toSubCategoryDTOList(subCategories);
    }
}