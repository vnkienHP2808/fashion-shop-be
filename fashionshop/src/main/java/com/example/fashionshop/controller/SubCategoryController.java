
package com.example.fashionshop.controller;

import com.example.fashionshop.dto.SubCategoryDTO;
import com.example.fashionshop.entity.SubCategory;
import com.example.fashionshop.service.SubCategoryService;
import com.example.fashionshop.util.DTOMapper;

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
    public List<SubCategoryDTO> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
        return DTOMapper.toSubCategoryDTOList(subCategories);
    }
}

// không dùng đến