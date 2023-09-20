package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.configuration.jwt.JwtTokenService;
import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;
import com.github.angel.raa.modules.persistence.models.Users;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import com.github.angel.raa.modules.service.interfaces.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;


    @Override
    public RegisterResponse registerOneCustomer(RegisterUserRequest request) {
        Users users = userService.registerOneCustomer(request);
        RegisterResponse response = new RegisterResponse();
        response.setId(users.getId());
        response.setUsername(users.getUsername());
        response.setName(users.getName());
        response.setRole(users.getRole().name());
        response.setJwt(jwtTokenService.generateToken(users));
        return response;
    }
}
