package com.github.angel.raa.modules.service.implementation;

import com.github.angel.raa.modules.exception.InvalidPasswordException;
import com.github.angel.raa.modules.persistence.dto.user.request.RegisterUserRequest;
import com.github.angel.raa.modules.persistence.models.Users;
import com.github.angel.raa.modules.persistence.repository.UserRepository;
import com.github.angel.raa.modules.service.interfaces.auth.UserService;
import com.github.angel.raa.modules.utils.constants.Message;
import com.github.angel.raa.modules.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Users registerOneCustomer(RegisterUserRequest request) {
        validatePassword(request);
        Users users = new Users();
        users.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        users.setUsername(request.getUsername());
        users.setName(request.getName());
        users.setRole(Role.ROLE_CUSTOMER);

        return userRepository.save(users);
    }

    @Override
    public Optional<Users> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(RegisterUserRequest request) {
        if(!StringUtils.hasText(request.getPassword()) || !StringUtils.hasText(request.getRepeatedPassword())){
            throw new InvalidPasswordException(Message.PASSWORD_NOT_VALID,400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
        if(!request.getPassword().equals(request.getRepeatedPassword())){
            throw new InvalidPasswordException(Message.PASSWORD_NOT_VALID,400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
        }
    }
}
