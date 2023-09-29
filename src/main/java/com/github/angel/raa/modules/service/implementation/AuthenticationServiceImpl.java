package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.configuration.jwt.JwtTokenService;
import com.github.angel.raa.modules.exception.UserNotFoundException;
import com.github.angel.raa.modules.persistence.dto.user.UserDto;
import com.github.angel.raa.modules.persistence.dto.user.request.AuthenticateRequest;
import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.dto.user.response.AuthenticateResponse;
import com.github.angel.raa.modules.persistence.dto.user.response.RegisterResponse;
import com.github.angel.raa.modules.persistence.models.auth.Token;
import com.github.angel.raa.modules.persistence.models.auth.Users;
import com.github.angel.raa.modules.persistence.repository.TokenRepository;
import com.github.angel.raa.modules.service.interfaces.auth.AuthenticationService;
import com.github.angel.raa.modules.service.interfaces.auth.UserService;
import com.github.angel.raa.modules.utils.constants.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    @Override
    public RegisterResponse registerOneCustomer(RegisterUserRequest request) {
        Users users = userService.registerOneCustomer(request);
        RegisterResponse response = new RegisterResponse();
        String jwt = jwtTokenService.generateToken(users, jwtTokenService.generateExtraClaims(users));
        saverUserToken(users, jwt);
        response.setId(users.getId());
        response.setUsername(users.getUsername());
        response.setName(users.getName());
        response.setRole(users.getRole().name());
        response.setJwt(jwt);
        return response;
    }

    @Override
    public RegisterResponse registerOneAdmin(RegisterUserRequest request) {
        Users users = userService.registerOneAdmin(request);
        RegisterResponse response = new RegisterResponse();
        String jwt = jwtTokenService.generateToken(users, jwtTokenService.generateExtraClaims(users));
        saverUserToken(users, jwt);
        response.setId(users.getId());
        response.setUsername(users.getUsername());
        response.setName(users.getName());
        response.setRole(users.getRole().name());
        response.setJwt(jwt);
        return response;

    }

    @Override
    public AuthenticateResponse login(AuthenticateRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authentication);
         Users users = userService.findOneByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
         String jwt = jwtTokenService.generateToken(users, jwtTokenService.generateExtraClaims(users));
        saverUserToken(users, jwt);
        return new AuthenticateResponse(jwt);
    }

    private void saverUserToken(Users users, String jwt) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUsers(users);
        token.setValid(true);
        token.setExpiration(jwtTokenService.extractExpiration(jwt));
        tokenRepository.save(token);
    }


    @Override
    public boolean validateToken(String token) {
        try {
            jwtTokenService.extractUsername(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserDto findLoggedInUser() {
        //UserDto useDto = new UserDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (auth instanceof UsernamePasswordAuthenticationToken authToke) {
            username = (String) authToke.getPrincipal();
        }
        /*
        useDto.setId(users.getId());
        useDto.setName(users.getName());
        useDto.setUsername(users.getUsername());
        useDto.setRole(users.getRole().name());
         */
        return userService.findOneByUsername(username).map((dto) -> new UserDto(dto.getId(), dto.getName(), dto.getUsername(), dto.getRole().name()))
                .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
    }

    @Override
    public void logout(HttpServletRequest request) {
      String jwt =  jwtTokenService.extractJwtFromRequest(request);
      if(!StringUtils.hasText(jwt)){
          return;
      }
        Optional<Token> token = tokenRepository.findByToken(jwt);
      if(token.isPresent() && token.get().isValid()){
          token.get().setValid(false);
          tokenRepository.save(token.get());
      }

    }
}
