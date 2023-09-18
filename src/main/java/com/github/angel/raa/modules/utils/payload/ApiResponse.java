package com.github.angel.raa.modules.utils.payload;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse extends Response{
    public ApiResponse(String message, int code, HttpStatus status, LocalDateTime timestamp) {
        super(message, code, status, timestamp);
    }
}
