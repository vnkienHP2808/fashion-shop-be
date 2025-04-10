package com.example.fashionshop.controller;

import com.example.fashionshop.entity.SubCategory;
import com.example.fashionshop.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "http://localhost:5173")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    
    @GetMapping("/category/{categoryId}")
    public List<SubCategory> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId);
    }
}
