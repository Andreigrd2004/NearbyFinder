package com.java_app.demo.authentication;

import com.java_app.demo.advice.exceptions.BadRequestException;
import com.java_app.demo.authentication.dtos.LoginDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtAuthResponse;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import com.java_app.demo.user.mapper.RegisterMapper;
import lombok.AllArgsConstructor;
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
  public JwtAuthResponse login(LoginDto loginDto) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtTokenProvider.generateToken(authentication);

    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    jwtAuthResponse.setAccessToken(token);

    return jwtAuthResponse;
  }

  public String register(RegisterDto registerDto) throws BadRequestException {
    if (userRepository.existsByUsername(registerDto.getUsername())) {
      throw new BadRequestException(
          "The user with the following username already exists.");
    }
    CustomUser user = RegisterMapper.map(registerDto, passwordEncoder);

    userRepository.save(user);
    return "User registered successfully";
  }
}
