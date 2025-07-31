package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface AdminService {
  ResponseEntity<List<UserDto>> getAllUsersFromDatabe();

  ResponseEntity<String> updateUserAsAdmin(String userId, String userRole);

  ResponseEntity<String> deleteUserAsAdmin(int id);

  ResponseEntity<List<KeyDto>> getAllUserKeysFromDatabe(Integer userId);

  ResponseEntity<String> deleteKeyAsAdmin(Integer id);

  ResponseEntity<String> updateUserKey(Integer id, String name);
}
