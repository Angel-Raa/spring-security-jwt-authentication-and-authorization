package com.github.angel.raa.modules.exception;

import com.github.angel.raa.modules.utils.constants.Message;
import com.github.angel.raa.modules.utils.payload.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.toList());
        String errorMessage = "Validation failed for the following fields:\n  " +
                String.join("\n", errorMessages);
        ApiErrorResponse apiError = new ApiErrorResponse(errorMessage, 400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        ApiErrorResponse apiError = new ApiErrorResponse(e.getMessage(), 404, e.getStatus(), e.getTimestamp());
        return ResponseEntity.status(e.getStatus()).body(apiError);

    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        ApiErrorResponse apiError = new ApiErrorResponse(e.getMessage(), 404, e.getStatus(), e.getTimestamp());
        return ResponseEntity.status(e.getStatus()).body(apiError);
    }

}
