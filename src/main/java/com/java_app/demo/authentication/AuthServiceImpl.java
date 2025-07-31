package com.java_app.demo.authentication;

import com.java_app.demo.user.dtos.LoginDto;
import com.java_app.demo.user.dtos.RegisterDto;
import com.java_app.demo.user.mapper.RegisterMapper;
import com.java_app.demo.security.jwt.JwtAuthResponse;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private JwtTokenProvider jwtTokenProvider;
  private AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<JwtAuthResponse> login(LoginDto loginDto) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtTokenProvider.generateToken(authentication);

    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    jwtAuthResponse.setAccessToken(token);

    return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
  }

  public ResponseEntity<String> register(RegisterDto registerDto) {

    if (userRepository.existsByUsername(registerDto.getUsername())) {
      return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
    }
    CustomUser user = RegisterMapper.map(registerDto, passwordEncoder);

    userRepository.save(user);
    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
  }
}
