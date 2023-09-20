package com.github.angel.raa.modules.configuration.jwt;

import com.github.angel.raa.modules.persistence.models.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    public String generateToken(UserDetails users) {
        return null;
    }
}
