package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.user.dtos.RegisterDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  AdminService adminService;
  AuthService authService;

  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getUsers() {
    return adminService.getAllUsersFromDatabe();
  }

  @PostMapping("/users")
  public ResponseEntity<String> addUser(RegisterDto registerDto) {
      return authService.register(registerDto);
  }

  @PutMapping("/users")
  public ResponseEntity<String> updateUser(@RequestParam(required = false) String userEmail, @RequestParam(required = false) String userRole) {
    return adminService.updateUserAsAdmin(userEmail, userRole);
  }

  @DeleteMapping("/users")
  public ResponseEntity<String> deleteUser(@RequestParam int id) {
    return adminService.deleteUserAsAdmin(id);
  }

  @GetMapping("/api/keys")
  public ResponseEntity<List<KeyDto>> getKeys(@RequestParam Integer user_id) {
    return adminService.getAllUserKeysFromDatabe(user_id);
  }

  @DeleteMapping("/api/keys")
  public ResponseEntity<String> deleteKey(@RequestParam Integer id) {
    return adminService.deleteKeyAsAdmin(id);
  }

  @PutMapping("/api/keys")
  public ResponseEntity<String> updateKey(@RequestParam Integer id, @RequestParam String name) {
    return adminService.updateUserKey(id, name);
  }
}
