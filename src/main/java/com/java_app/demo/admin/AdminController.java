package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    return new ResponseEntity<>(adminService.getAllUsers(),HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<String> addUser(RegisterDto registerDto) {
    CustomTransferAdmin transfer = authService.register(registerDto);
    return new ResponseEntity<>(transfer.getMessage(), transfer.getStatus());
  }

  @PutMapping("/users")
  public ResponseEntity<String> updateUser(
      @RequestParam String userEmail, @RequestParam String userRole) {
    CustomTransferAdmin transfer = adminService.updateUserAsAdmin(userEmail, userRole);
    return new ResponseEntity<>(transfer.getMessage(), transfer.getStatus());
  }

  @DeleteMapping("/users")
  public ResponseEntity<String> deleteUser(@RequestParam Integer user_id) {
    CustomTransferAdmin transfer = adminService.deleteUserAsAdmin(user_id);
    return new ResponseEntity<>(transfer.getMessage(), transfer.getStatus());
  }

  @GetMapping("/keys")
  public ResponseEntity<List<KeyDto>> getKeys(@RequestParam Integer user_id) {
    CustomTransferAdmin transfer = adminService.getAllUserKeys(user_id);
    return new ResponseEntity<>(transfer.getKeys(), transfer.getStatus());
  }

  @DeleteMapping("/keys")
  public ResponseEntity<String> deleteKey(@RequestParam Integer key_id) {
    CustomTransferAdmin transfer = adminService.deleteKeyAsAdmin(key_id);
    return new  ResponseEntity<>(transfer.getMessage(), transfer.getStatus());
  }

  @PutMapping("/keys")
  public ResponseEntity<String> updateKey(
      @RequestParam Integer key_id, @RequestParam String name, @RequestParam Integer user_id) {
    CustomTransferAdmin transfer = adminService.updateUserKey(key_id, name, user_id);
    return new ResponseEntity<>(transfer.getMessage(), transfer.getStatus());
  }
}
