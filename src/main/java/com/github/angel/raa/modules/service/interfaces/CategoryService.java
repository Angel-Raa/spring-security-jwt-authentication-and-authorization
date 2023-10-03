package com.github.angel.raa.modules.service.interfaces;

import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.utils.payload.ApisResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryListDto> findAll();
    CategoryListDto findById(Long categoryId);
    ApisResponse saveCategory(CategoryDto body);
    ApisResponse updateCategory(Long categoryId, CategoryDto body);
    ApisResponse deleteCategory(Long categoryId);

}
