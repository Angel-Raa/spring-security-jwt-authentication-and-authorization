package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.product.ProductAllListDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductListDto;
import com.github.angel.raa.modules.service.interfaces.ProductService;
import com.github.angel.raa.modules.utils.payload.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/product-list")
    public ResponseEntity<List<ProductListDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/product-all")
    public ResponseEntity<List<ProductAllListDto>> productAllList() {
        return ResponseEntity.ok(service.findAllAll());
    }

    @GetMapping("/all/pageable")
    public ResponseEntity<Page<ProductListDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductListDto> findById(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @PostMapping("/create-product")
    public ResponseEntity<ApiResponse> saveProduct(@Valid @RequestBody ProductDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(body));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@Valid @PathVariable @Min(1) Long productId, @RequestBody ProductDto body) {
        return ResponseEntity.ok(service.updateProduct(productId, body));
    }

    @PutMapping("/{productId}/disable")
    public ResponseEntity<ProductListDto> disableOneById(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.disableOneById(productId));
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.deleteProduct(productId));
    }


}

