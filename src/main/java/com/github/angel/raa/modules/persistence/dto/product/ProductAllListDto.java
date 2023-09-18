package com.github.angel.raa.modules.persistence.dto.product;


import com.github.angel.raa.modules.utils.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAllListDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private String category;

}
