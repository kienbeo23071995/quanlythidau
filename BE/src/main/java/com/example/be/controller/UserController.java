package com.example.be.controller;

import com.example.be.entity.User;
import com.example.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/change-password")
    public ResponseEntity<String> changePass(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String password){
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<User> editProfile(@AuthenticationPrincipal UserDetails userDetails,@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
