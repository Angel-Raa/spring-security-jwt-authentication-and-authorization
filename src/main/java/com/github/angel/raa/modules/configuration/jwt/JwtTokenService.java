package com.github.angel.raa.modules.configuration.jwt;

import com.github.angel.raa.modules.persistence.models.auth.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Service
public class JwtTokenService {
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails users,  Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + (issuedAt.getTime()));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(users.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey() {
        byte[]  passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
       // System.out.println("Test " + passwordDecoded);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public Map<String, Object> generateExtraClaims(Users users) {
        Map<String , Object> extraClaims = new HashMap<>();
        extraClaims.put("Name", users.getUsername() );
        extraClaims.put("Role", users.getRole().name());
        extraClaims.put("Authorities", users.getAuthorities());
        return extraClaims;

    }


    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build().parseClaimsJws(token)
                .getBody();
    }


    public String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        // Obtener token
        return header.substring(7);
    }

    public Date extractExpiration(String jwt) {
        return extractClaims(jwt).getExpiration();
    }
}
