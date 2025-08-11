package com.java_app.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.java_app.demo.authentication.AuthServiceImpl;
import com.java_app.demo.authentication.dtos.LoginDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtAuthResponse;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import com.java_app.demo.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock UserRepository userRepository;

  @Mock AuthenticationManager authenticationManager;

  @Mock JwtTokenProvider jwtTokenProvider;

  @Mock Authentication authentication;

  @Mock PasswordEncoder passwordEncoder;

  @InjectMocks AuthServiceImpl authService;

  @Test
  void testTheLoginWithJwt() {
    LoginDto loginDto = new LoginDto("string", "string");
    String expectedToken = "string";
    when(authenticationManager.authenticate(any())).thenReturn(authentication);
    when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

    ResponseEntity<JwtAuthResponse> response =
        new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(expectedToken, response.getBody().getAccessToken());

    verify(authenticationManager, times(1)).authenticate(any());
    verify(jwtTokenProvider, times(1)).generateToken(authentication);
  }

  @Test
  void testRegisterUser() {
    RegisterDto registerDto = new RegisterDto("string", "string", "string", "string");
    when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
    String response = authService.register(registerDto);

    assertEquals("User registered successfully", response);
    verify(userRepository, times(1)).save(any());
  }
}
