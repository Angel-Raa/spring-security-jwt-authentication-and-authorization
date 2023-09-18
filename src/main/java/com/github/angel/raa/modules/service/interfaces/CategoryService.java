package com.github.angel.raa.modules.service.interfaces;

import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.utils.payload.ApiResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryListDto> findAll();
    CategoryListDto findById(Long categoryId);
    ApiResponse saveCategory(CategoryDto body);
    ApiResponse updateCategory(Long categoryId, CategoryDto body);
    ApiResponse deleteCategory(Long categoryId);

}
