package com.example.backend.controller;

import com.example.backend.dto.request.SignUpRequest;
import com.example.backend.dto.request.SigninRequest;
import com.example.backend.dto.response.GenericResponse;
import com.example.backend.dto.response.JwtAuthenticationResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.UserService;
import com.example.backend.ultis.Helper;
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

    @PostMapping("/change-password")
    public ResponseEntity<String> changePass(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String password){
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
