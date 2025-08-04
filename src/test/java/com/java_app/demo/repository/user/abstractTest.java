package com.java_app.demo.repository.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(
    basePackages = {
      "com.java_app.demo.user",
      "com.java_app.demo.country",
      "com.java_app.demo.apikey",
      "com.java_app.demo.currency"
    })
public abstract class abstractTest {

  public static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:16-alpine")
          .withDatabaseName("test")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.flyway.enabled", () -> "true");
    registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.flyway.user", postgreSQLContainer::getUsername);
    registry.add("spring.flyway.password", postgreSQLContainer::getPassword);
    registry.add("spring.flyway.schemas", () -> "nearby_finder");
    registry.add("spring.flyway.baseline-on-migrate", () -> "true");

    registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
  }
}
