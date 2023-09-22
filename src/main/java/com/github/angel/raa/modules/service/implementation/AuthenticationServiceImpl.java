package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.configuration.jwt.JwtTokenService;
import com.github.angel.raa.modules.exception.UserNotFoundException;
import com.github.angel.raa.modules.persistence.dto.user.request.AuthenticateRequest;
import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.AuthenticateResponse;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;
import com.github.angel.raa.modules.persistence.models.Users;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import com.github.angel.raa.modules.service.interfaces.auth.UserService;
import com.github.angel.raa.modules.utils.constants.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;


    @Override
    public RegisterResponse registerOneCustomer(RegisterUserRequest request) {
        Users users = userService.registerOneCustomer(request);
        RegisterResponse response = new RegisterResponse();
        response.setId(users.getId());
        response.setUsername(users.getUsername());
        response.setName(users.getName());
        response.setRole(users.getRole().name());
        response.setJwt(jwtTokenService.generateToken(users, jwtTokenService.generateExtraClaims(users)));
        return response;
    }

    @Override
    public AuthenticateResponse login(AuthenticateRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authentication);
         Users users = userService.findOneByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
         String jwt = jwtTokenService.generateToken(users, jwtTokenService.generateExtraClaims(users));
        return new AuthenticateResponse(jwt);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtTokenService.extractUsername(token);
            return true;
        }catch (Exception e){
            log.error(Message.INVALID_TOKEN);
            return false;
        }
    }
}
