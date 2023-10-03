package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.exception.CategoryNotFoundException;
import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.persistence.mapper.CategoryMapper;
import com.github.angel.raa.modules.persistence.models.Category;
import com.github.angel.raa.modules.persistence.repository.CategoryRepository;
import com.github.angel.raa.modules.service.interfaces.CategoryService;
import com.github.angel.raa.modules.utils.constants.Message;
import com.github.angel.raa.modules.utils.payload.ApisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CategoryListDto> findAll() {
        return categoryRepository.findAll().stream()
                .map((dto) -> new CategoryListDto(dto.getId(), dto.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryListDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(Message.CATEGORY_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public ApisResponse saveCategory(CategoryDto body) {
        Category category = new Category();
        category.setName(body.getName());
        categoryRepository.save(category);
        return new ApisResponse(Message.CATEGORY_SAVED_SUCCESSFULLY, 201, HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    @Transactional
    public ApisResponse updateCategory(Long categoryId, CategoryDto body) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(Message.CATEGORY_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        category.setName(body.getName());
        categoryRepository.save(category);
        return new ApisResponse(Message.CATEGORY_UPDATED_SUCCESSFULLY, 200, HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    @Transactional
    public ApisResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(Message.CATEGORY_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        categoryRepository.delete(category);
        return new ApisResponse(Message.CATEGORY_DELETED_SUCCESSFULLY, 200, HttpStatus.OK, LocalDateTime.now());
    }
}
