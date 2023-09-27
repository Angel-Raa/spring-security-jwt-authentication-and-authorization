package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.service.interfaces.CategoryService;
import com.github.angel.raa.modules.utils.payload.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
@Validated
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PreAuthorize("permitAll")
    @GetMapping("/category-list")
    public ResponseEntity<List<CategoryListDto>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    @PreAuthorize("permitAll")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryListDto> category(@Valid @PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PostMapping("/create-category")
    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    public ResponseEntity<ApiResponse> saveCategory(@Valid @RequestBody CategoryDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(body));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @PathVariable @Min(1) Long id, @RequestBody CategoryDto body) {
        return ResponseEntity.ok(categoryService.updateCategory(id, body));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_ONE_CATEGORY')")
    public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
