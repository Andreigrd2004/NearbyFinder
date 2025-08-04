package com.java_app.demo.repository.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.security.jwt.JwtAuthenticationFilter;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@TestPropertySource(locations = "classpath:application-test.properties")
public class TestUserRepository extends abstractTest {

  @Autowired private UserRepository userRepository;

  @MockitoBean KeysRepository keysRepository;

  @MockitoBean JwtAuthenticationFilter jwtAuthenticationFilter;

  @Test
  public void testFindByUsername() {
    CustomUser user = new CustomUser();
    user.setId(1);
    user.setEmail("awd");
    user.setUsername("wadwa");
    user.setEnabled(true);
    user.setAccountNonLocked(true);
    user.setRoles(new HashSet<>(Collections.singleton("ROLE_USER")));
    userRepository.save(user);

    Optional<CustomUser> retrievedUser = userRepository.findByUsername(user.getUsername());
    assertNotNull(retrievedUser);
    assertTrue(retrievedUser.stream().anyMatch(u -> "wadwa".equals(user.getUsername())));
  }
}
