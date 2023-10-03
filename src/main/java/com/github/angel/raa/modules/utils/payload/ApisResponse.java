package com.github.angel.raa.modules.utils.payload;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApisResponse extends Response{
    public ApisResponse(String message, int code, HttpStatus status, LocalDateTime timestamp) {
        super(message, code, status, timestamp);
    }
}
