package com.github.angel.raa.modules.persistence.dto.user.response;



import com.github.angel.raa.modules.utils.payload.Response;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class LogoutResponse extends Response {
    public LogoutResponse(String message, int code, HttpStatus status, LocalDateTime timestamp) {
        super(message, code, status, timestamp);
    }
}
