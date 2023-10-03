package com.github.angel.raa.modules.service.interfaces;

import com.github.angel.raa.modules.persistence.dto.product.ProductAllListDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductListDto;
import com.github.angel.raa.modules.utils.payload.ApisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductListDto> findAll();
    List<ProductAllListDto> findAllAll();
    Page<ProductListDto> findAll(Pageable pageable);
    ProductListDto findById(Long productId);
    ApisResponse saveProduct(ProductDto body);
    ApisResponse updateProduct(Long productId, ProductDto body);
    ApisResponse deleteProduct(Long productId);
    ProductListDto disableOneById(Long productId);
}
