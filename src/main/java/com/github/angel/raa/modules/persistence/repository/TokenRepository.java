package com.github.angel.raa.modules.persistence.repository;

import com.github.angel.raa.modules.persistence.models.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String jwt);
}
