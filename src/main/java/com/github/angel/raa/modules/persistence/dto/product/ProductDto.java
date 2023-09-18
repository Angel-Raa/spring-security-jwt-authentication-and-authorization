package com.github.angel.raa.modules.persistence.dto.product;

import com.github.angel.raa.modules.utils.enums.ProductStatus;
import com.github.angel.raa.modules.utils.constants.Message;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank(message = Message.PRODUCT_NAME_REQUIRED)
        @Size(min = 4, max = 50, message = Message.PRODUCT_NAME_NOT_VALID)
        String name,
        String description,
        @NotNull(message = Message.PRODUCT_PRICE_REQUIRED)
        @DecimalMin(value = "0.01", message = Message.PRODUCT_PRICE_NOT_VALID)
        BigDecimal price,
        ProductStatus status,
        @Min(1)
        Long category) {
}
