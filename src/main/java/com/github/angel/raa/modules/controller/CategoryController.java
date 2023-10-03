package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.category.CategoryDto;
import com.github.angel.raa.modules.persistence.dto.category.CategoryListDto;
import com.github.angel.raa.modules.service.interfaces.CategoryService;
import com.github.angel.raa.modules.utils.payload.ApisResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name =" Categories", description = "Operaciones para manejar categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",  content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PreAuthorize("permitAll")
    @GetMapping("/category-list")
    public ResponseEntity<List<CategoryListDto>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    @Operation(summary = "Get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",  content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @PreAuthorize("permitAll")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryListDto> category(@ParameterObject  @Valid @PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @Operation(summary = "Create category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @PostMapping("/create-category")
    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    public ResponseEntity<ApisResponse> saveCategory(@ParameterObject @Valid @RequestBody  CategoryDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(body));
    }
    @Operation(summary = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    public ResponseEntity<ApisResponse> updateCategory(@ParameterObject @Valid @PathVariable    @Min(1) Long id, @RequestBody CategoryDto body) {
        return ResponseEntity.ok(categoryService.updateCategory(id, body));
    }
    @Operation(summary = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_ONE_CATEGORY')")
    public ResponseEntity<ApisResponse> deleteCategory(@ParameterObject @Valid @PathVariable   @Min(1) Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
