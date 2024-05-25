package com.example.backend.service;

import com.example.backend.dto.request.SignUpRequest;
import com.example.backend.dto.request.SigninRequest;
import com.example.backend.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
