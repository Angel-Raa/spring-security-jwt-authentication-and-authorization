package com.github.angel.raa.modules.persistence.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private  Long id;
    private String name;
    private String username;
    private String role;

}
