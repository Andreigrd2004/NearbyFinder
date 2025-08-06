package com.java_app.demo.user.mapper;

import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.user.CustomUser;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterMapper {

  public static CustomUser map(RegisterDto registerDto, PasswordEncoder passwordEncoder) {

    CustomUser user = new CustomUser();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setEmail(registerDto.getEmail());
    user.setDisplayName(registerDto.getDisplayName());
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    user.setRoles(Collections.singleton("ROLE_USER"));

    return user;
  }
}
