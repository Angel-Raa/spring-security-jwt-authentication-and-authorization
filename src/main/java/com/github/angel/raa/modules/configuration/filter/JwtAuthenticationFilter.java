package com.github.angel.raa.modules.configuration.filter;

import com.github.angel.raa.modules.configuration.jwt.JwtTokenService;
import com.github.angel.raa.modules.exception.UserNotFoundException;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Obtener token
        String token = header.substring(7);
        // Obtener el subject/username del token
        String username = jwtTokenService.extractUsername(token);
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

}
