package com.java_app.demo.jwt;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);
    ResponseEntity<String> register(RegisterDto registerDto);
}
