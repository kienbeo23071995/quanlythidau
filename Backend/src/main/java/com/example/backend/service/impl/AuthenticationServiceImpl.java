package com.example.backend.service.impl;

import com.example.backend.dto.request.SignUpRequest;
import com.example.backend.dto.request.SigninRequest;
import com.example.backend.dto.response.JwtAuthenticationResponse;
import com.example.backend.entity.Role;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.JwtService;
import com.example.backend.ultis.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.backend.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;
    private final Helper helper;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().fullName(request.getFullName()).phone(request.getPhoneNumber())
                .address(request.getAddress())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roleID(roleRepository.findById(1).get()).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        mailSender.send(helper.constructEmail("Verify Account","Click <a href = '#'>here </a> to active account",user));
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
