package com.github.angel.raa.modules.persistence.repository;

import com.github.angel.raa.modules.persistence.models.auth.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

}
