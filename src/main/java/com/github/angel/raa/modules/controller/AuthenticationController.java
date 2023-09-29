package com.github.angel.raa.modules.controller;


import com.github.angel.raa.modules.persistence.dto.user.UserDto;
import com.github.angel.raa.modules.persistence.dto.user.request.AuthenticateRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.AuthenticateResponse;
import com.github.angel.raa.modules.persistence.dto.user.response.LogoutResponse;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import com.github.angel.raa.modules.utils.constants.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticateService;
    @PreAuthorize("permitAll")
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authenticateService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> login(@Valid @RequestBody AuthenticateRequest request) {
        AuthenticateResponse response = authenticateService.login(request);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    public ResponseEntity<UserDto> profile() {
        UserDto userDto = authenticateService.findLoggedInUser();
        return ResponseEntity.ok(userDto);
    }
    @PreAuthorize("permitAll")
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request){
        authenticateService.logout(request);
        LogoutResponse response = new LogoutResponse(Message.LOGOUT_SUCCESSFULLY, 200, HttpStatus.OK, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
