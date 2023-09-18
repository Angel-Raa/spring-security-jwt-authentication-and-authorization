package com.github.angel.raa.modules.persistence.mapper;

import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.persistence.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryListDto toDto(Category entity) {
        return new CategoryListDto(entity.getId(),entity.getName());
    }

    public Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
}
