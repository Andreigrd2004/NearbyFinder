package com.java_app.demo.repository.user;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractTest extends PostgreSQLContainer<AbstractTest> {
  private static final String IMAGE_VERSION = "postgres:11.1";
  private static AbstractTest container;

  private AbstractTest() {
    super(IMAGE_VERSION);
  }

  public static AbstractTest getInstance() {
    if (container == null) {
      container = new AbstractTest();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {

  }
}