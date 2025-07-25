package com.java_app.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Integer> {
    Optional<CustomUser> findByUsernameOrEmail(String username, String email);
}
