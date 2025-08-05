package com.java_app.demo.repository.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Transactional
public class TestUserRepository {

  @Autowired UserRepository userRepository;

  @Container @ServiceConnection
  public static PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres");

  @MockitoBean private KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void printConnectionDetails() {
    System.out.println("Testcontainers JDBC URL: " + postgreSqlContainer.getJdbcUrl());
    System.out.println("Testcontainers Username: " + postgreSqlContainer.getUsername());
    System.out.println("Testcontainers Password: " + postgreSqlContainer.getPassword());
  }

  @Test
  public void testFindByUsername() {
    CustomUser user = new CustomUser();
    user.setEmail("awd");
    user.setUsername("wadwa");
    user.setEnabled(true);
    user.setAccountNonLocked(true);
    user.setRoles(new HashSet<>(Collections.singleton("ROLE_USER")));
    System.out.println(postgreSqlContainer.getUsername());
    System.out.println(postgreSqlContainer.getJdbcUrl());
    System.out.println(postgreSqlContainer.getPassword());
    userRepository.save(user);

    Optional<CustomUser> retrievedUser = userRepository.findByUsername(user.getUsername());
    assertNotNull(retrievedUser);
    assertTrue(retrievedUser.stream().anyMatch(u -> "wadwa".equals(user.getUsername())));
  }
}
