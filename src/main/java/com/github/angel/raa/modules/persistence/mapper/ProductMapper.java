package com.github.angel.raa.modules.persistence.mapper;

import com.github.angel.raa.modules.persistence.dto.product.ProductDto;
import com.github.angel.raa.modules.persistence.dto.product.ProductListDto;
import com.github.angel.raa.modules.utils.enums.ProductStatus;
import com.github.angel.raa.modules.persistence.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
   public ProductListDto toEntity(Product entity) {
        return new ProductListDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCreatedAt(), entity.getStatus());
    }

    public Product toEntity(ProductDto dto) {
       Product product = new Product();
       product.setName(dto.name());
       product.setDescription(dto.description());
       product.setPrice(dto.price());
       product.setStatus(ProductStatus.ACTIVE);
       return product;

    }
}
