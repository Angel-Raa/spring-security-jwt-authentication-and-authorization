package com.github.angel.raa.modules.persistence.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse implements Serializable {
    private  String jwt;
}
