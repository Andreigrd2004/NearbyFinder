package com.java_app.demo.controller;

import com.java_app.demo.jwt.AuthService;
import com.java_app.demo.jwt.JwtAuthResponse;
import com.java_app.demo.jwt.LoginDto;
import com.java_app.demo.jwt.RegisterDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
    return authService.login(loginDto);
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    return authService.register(registerDto);
  }
}
