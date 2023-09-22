package com.github.angel.raa.modules.service.interfaces.auth;


import com.github.angel.raa.modules.persistence.dto.user.request.AuthenticateRequest;
import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.AuthenticateResponse;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse registerOneCustomer(RegisterUserRequest request);

    AuthenticateResponse login(AuthenticateRequest request);

    boolean validateToken(String token);
}
