package com.java_app.demo.jwt;

import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import java.util.Collections;
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
  public String login(LoginDto loginDto) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtTokenProvider.generateToken(authentication);
  }

  public ResponseEntity<String> register(RegisterDto registerDto) {

    if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
      return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
    }

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
    userRepository.save(user);
    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
  }
}
