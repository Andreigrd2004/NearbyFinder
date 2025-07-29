package com.java_app.demo.jwt;

import org.springframework.http.ResponseEntity;

public interface AuthService {
  ResponseEntity<JwtAuthResponse> login(LoginDto loginDto);

  ResponseEntity<String> register(RegisterDto registerDto);
}
