package com.github.angel.raa.modules.persistence.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse implements Serializable {
    private  Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;

}
