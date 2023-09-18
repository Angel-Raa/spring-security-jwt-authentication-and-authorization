package com.github.angel.raa.modules.persistence.dto.category;

import com.github.angel.raa.modules.utils.constants.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank(message = Message.CATEGORY_NAME_REQUIRED)
    @Size(min = 4, max = 50, message = Message.CATEGORY_NAME_NOT_VALID)
    public String name;
}
