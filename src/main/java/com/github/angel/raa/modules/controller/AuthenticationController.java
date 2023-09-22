package com.github.angel.raa.modules.controller;


import com.github.angel.raa.modules.persistence.dto.user.request.AuthenticateRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.AuthenticateResponse;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticateService;
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authenticateService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@Valid @RequestBody AuthenticateRequest request) {
        AuthenticateResponse response = authenticateService.login(request);
        return ResponseEntity.ok(response);

    }
}
