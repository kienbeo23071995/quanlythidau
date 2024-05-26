package com.example.be.controller;

import com.example.be.dto.request.SignUpRequest;
import com.example.be.dto.request.SigninRequest;
import com.example.be.dto.response.GenericResponse;
import com.example.be.dto.response.JwtAuthenticationResponse;
import com.example.be.entity.User;
import com.example.be.repository.UserRepository;
import com.example.be.service.AuthenticationService;
import com.example.be.service.UserService;
import com.example.be.ultis.Helper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final Helper helper;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        Optional<User> account = userRepository.findByEmail(userEmail);
        if (account.isEmpty()) {
            throw new RuntimeException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(account, token);
        mailSender.send(helper.constructResetTokenEmail(helper.getAppUrl(request),
                request.getLocale(), token, account.get()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/changePassword")
    public ResponseEntity<String> resetPass(@RequestParam String token, @RequestParam String password){
        return userService.resetPass(token,password);
    }
}
