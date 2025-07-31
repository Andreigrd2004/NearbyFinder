package com.java_app.demo.authentication;

import com.java_app.demo.user.dtos.LoginDto;
import com.java_app.demo.user.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtAuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
  AuthService authService;

  @PostMapping("/auth/login")
  public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
    return authService.login(loginDto);
  }

  @PostMapping("/auth/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    return authService.register(registerDto);
  }
}
