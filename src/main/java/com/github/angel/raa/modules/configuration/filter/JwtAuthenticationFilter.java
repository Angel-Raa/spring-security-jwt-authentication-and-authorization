package com.github.angel.raa.modules.configuration.filter;

import com.github.angel.raa.modules.configuration.jwt.JwtTokenService;
import com.github.angel.raa.modules.exception.UserNotFoundException;
import com.github.angel.raa.modules.persistence.models.auth.Token;
import com.github.angel.raa.modules.persistence.repository.TokenRepository;
import com.github.angel.raa.modules.service.interfaces.auth.UserService;
import com.github.angel.raa.modules.utils.constants.Message;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener la authorization header
       String jwt =  jwtTokenService.extractJwtFromRequest(request);
       if(!StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
       }
       // Obtener token no expirado y valido desde base de dato
        Optional<Token> token = tokenRepository.findByToken(jwt);
        boolean isValid = validateToken(token);
        if(!isValid){
           filterChain.doFilter(request, response);
           return;
        }
        // Obtener el subject/username del token
        String username = jwtTokenService.extractUsername(jwt);
        UserDetails userDetails = userService.findOneByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUND, 404, HttpStatus.NOT_FOUND, LocalDateTime.now()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Ejecutar el request
        filterChain.doFilter(request, response);




    }

    private boolean validateToken(Optional<Token> optionalToken) {
        if(optionalToken.isEmpty()){
            System.out.println("Token de existe");
            return false;
        }
        Token token = optionalToken.get();
        Date now = new Date(System.currentTimeMillis());
        return token.isValid()  && token.getExpiration().after(now);
    }

}
