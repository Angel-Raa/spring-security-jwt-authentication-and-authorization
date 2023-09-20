package com.github.angel.raa.modules.utils.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationResponse extends Response {
    private String token;

    public AuthenticationResponse(String message, int code, HttpStatus status, LocalDateTime timestamp, String token) {
        super(message, code, status, timestamp);
        this.token = token;
    }
}
