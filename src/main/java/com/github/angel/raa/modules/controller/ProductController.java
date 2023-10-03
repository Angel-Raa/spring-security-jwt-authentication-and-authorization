package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.product.ProductAllListDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductListDto;
import com.github.angel.raa.modules.service.interfaces.ProductService;
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
@RequiredArgsConstructor
@Tag(name =" Products", description = "Operaciones  para manejar products")
public class ProductController {
    private final ProductService service;
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PreAuthorize("permitAll")
    @GetMapping("/product-list")
    public ResponseEntity<List<ProductListDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PreAuthorize("permitAll")
    @GetMapping("/product-all")
    public ResponseEntity<List<ProductAllListDto>> productAllList() {
        return ResponseEntity.ok(service.findAllAll());
    }
    @Operation(summary = "Get all products pageable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PreAuthorize("permitAll")
    @GetMapping("/all/pageable")
    public ResponseEntity<Page<ProductListDto>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }
    //@CrossOrigin(origins = "http://127.0.0.1:5500/", methods = {RequestMethod.GET})
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductListDto> findById(@ParameterObject @Valid @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @Operation(summary = "Create product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
    })
    @PostMapping("/create-product")
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    public ResponseEntity<ApisResponse> saveProduct(@ParameterObject @Valid @RequestBody   ProductDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(body));
    }
    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    public ResponseEntity<ApisResponse> updateProduct(@ParameterObject @Valid @PathVariable   @Min(1) Long productId, @RequestBody ProductDto body) {
        return ResponseEntity.ok(service.updateProduct(productId, body));
    }
    @Operation(summary = "Disable product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })

    @PutMapping("/{productId}/disable")
    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    public ResponseEntity<ProductListDto> disableOneById(@ParameterObject @Valid @PathVariable   @Min(1) Long productId) {
        return ResponseEntity.ok(service.disableOneById(productId));
    }
    @Operation(summary = "Enable product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('DELETE_ONE_PRODUCT')")
    public ResponseEntity<ApisResponse> deleteProduct(@ParameterObject @Valid @PathVariable   @Min(1) Long productId) {
        return ResponseEntity.ok(service.deleteProduct(productId));
    }


}

