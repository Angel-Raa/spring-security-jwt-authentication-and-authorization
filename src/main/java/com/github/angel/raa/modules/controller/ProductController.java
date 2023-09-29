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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @PreAuthorize("permitAll")
    @GetMapping("/product-list")
    public ResponseEntity<List<ProductListDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @PreAuthorize("permitAll")
    @GetMapping("/product-all")
    public ResponseEntity<List<ProductAllListDto>> productAllList() {
        return ResponseEntity.ok(service.findAllAll());
    }
    @PreAuthorize("permitAll")
    @GetMapping("/all/pageable")
    public ResponseEntity<Page<ProductListDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductListDto> findById(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @PostMapping("/create-product")
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    public ResponseEntity<ApiResponse> saveProduct(@Valid @RequestBody ProductDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(body));
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    public ResponseEntity<ApiResponse> updateProduct(@Valid @PathVariable @Min(1) Long productId, @RequestBody ProductDto body) {
        return ResponseEntity.ok(service.updateProduct(productId, body));
    }

    @PutMapping("/{productId}/disable")
    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    public ResponseEntity<ProductListDto> disableOneById(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.disableOneById(productId));
    }
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('DELETE_ONE_PRODUCT')")
    public ResponseEntity<ApiResponse> deleteProduct(@Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.deleteProduct(productId));
    }


}

