package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name =" Customers", description = "Operaciones para manejar customers")
public class CustomerController {
    private final AuthenticationService authenticationService;
    @Operation(summary = "Register one customer")
    @ApiResponse(responseCode = "201", description = "Customer registered successfully")
    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerOneCustomer(@Valid @RequestBody RegisterUserRequest request) {
        RegisterResponse response = authenticationService.registerOneCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
