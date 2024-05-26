package com.example.be.service;

import com.example.be.dto.request.SignUpRequest;
import com.example.be.dto.request.SigninRequest;
import com.example.be.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
