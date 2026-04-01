package com.universafricain.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universafricain.backend.config.JwtUtil;
import com.universafricain.backend.dto.AuthDTO;
import com.universafricain.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.LoginResponse> login(@RequestBody AuthDTO.LoginRequest request) {
        String token = jwtUtil.generateToken(request.getEmail());
        AuthDTO.LoginResponse response = authService.login(request, token);
        return ResponseEntity.ok(response);
    }
}