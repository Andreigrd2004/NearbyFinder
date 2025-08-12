package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@Validated
public class AdminController {
  AdminService adminService;

  @GetMapping("/users")
  public List<UserDto> getUsers() {
    return adminService.getAllUsers();
  }

  @PostMapping("/users")
  public String addUser(RegisterDto registerDto) {
    return adminService.createUser(registerDto);
  }

  @PutMapping("/users")
  public String updateUser(@RequestParam @Email @NotBlank String userEmail, @NotBlank @RequestParam String userRole) {
    return adminService.updateUserAsAdmin(userEmail, userRole);
  }

  @DeleteMapping("/users")
  public String deleteUser(@RequestParam @NotNull Integer userId) {
    return adminService.deleteUserAsAdmin(userId);
  }

  @GetMapping("/keys")
  public List<KeyDto> getKeys(@RequestParam @NotNull Integer userId) {
    return adminService.getAllUserKeys(userId);
  }

  @DeleteMapping("/keys")
  public String deleteKey(@RequestParam @NotNull Integer keyId) {
    return adminService.deleteKeyAsAdmin(keyId);
  }

  @PutMapping("/keys")
  public String updateKey(
          @RequestParam @NotNull Integer keyId, @RequestParam @NotBlank String name, @RequestParam @NotNull Integer userId) {
    return adminService.updateUserKey(keyId, name, userId);
  }
}
