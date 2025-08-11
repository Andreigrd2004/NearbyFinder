package com.java_app.demo.authentication;

import com.java_app.demo.authentication.dtos.LoginDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtAuthResponse;

public interface AuthService {
  JwtAuthResponse login(LoginDto loginDto);

  String register(RegisterDto registerDto);
}
