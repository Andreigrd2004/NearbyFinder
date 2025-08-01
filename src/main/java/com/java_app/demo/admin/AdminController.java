package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.dtos.RegisterDto;
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
    return adminService.getAllUsers();
  }

  @PostMapping("/users")
  public ResponseEntity<String> addUser(RegisterDto registerDto) {
    return authService.register(registerDto);
  }

  @PutMapping("/users")
  public ResponseEntity<String> updateUser(
      @RequestParam String userEmail, @RequestParam String userRole) {
    return adminService.updateUserAsAdmin(userEmail, userRole);
  }

  @DeleteMapping("/users")
  public ResponseEntity<String> deleteUser(@RequestParam Integer user_id) {
    return adminService.deleteUserAsAdmin(user_id);
  }

  @GetMapping("/keys")
  public ResponseEntity<List<KeyDto>> getKeys(@RequestParam Integer user_id) {
    return adminService.getAllUserKeys(user_id);
  }

  @DeleteMapping("/keys")
  public ResponseEntity<String> deleteKey(@RequestParam Integer key_id) {
    return adminService.deleteKeyAsAdmin(key_id);
  }

  @PutMapping("/keys")
  public ResponseEntity<String> updateKey(
      @RequestParam Integer key_id, @RequestParam String name, @RequestParam Integer user_id) {
    return adminService.updateUserKey(key_id, name, user_id);
  }
}
