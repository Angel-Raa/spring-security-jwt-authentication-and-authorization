package com.github.angel.raa.modules.persistence.dto.product;

import com.github.angel.raa.modules.utils.enums.ProductStatus;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductListDto(Long id, String name, String description,
                             BigDecimal price, LocalDateTime createdAt,
                             ProductStatus status) {
}
