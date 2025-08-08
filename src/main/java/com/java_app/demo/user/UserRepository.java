package com.java_app.demo.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Integer> {
  Optional<CustomUser> findByUsername(String username);

  @Query(value = "SELECT * from nearby_finder.custom_user", nativeQuery = true)
  List<CustomUser> getAllUsers();

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  CustomUser findCustomUserByEmail(String email);

  void deleteCustomUserById(Integer id);

  Optional<CustomUser> findCustomUserById(Integer id);
}
