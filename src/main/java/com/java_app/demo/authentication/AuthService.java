package com.java_app.demo.authentication;

import com.java_app.demo.authentication.dtos.LoginDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
  ResponseEntity<JwtAuthResponse> login(LoginDto loginDto);

  ResponseEntity<String> register(RegisterDto registerDto);
}
