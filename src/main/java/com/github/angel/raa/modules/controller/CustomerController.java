package com.github.angel.raa.modules.controller;

import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerOneCustomer(@Valid @RequestBody RegisterUserRequest request) {
        RegisterResponse response = authenticationService.registerOneCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
