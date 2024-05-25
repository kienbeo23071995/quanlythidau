package com.example.backend.service;

import com.example.backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    void createPasswordResetTokenForUser(Optional<User> user, String token);

    ResponseEntity<String> resetPass(String token, String password);
}
