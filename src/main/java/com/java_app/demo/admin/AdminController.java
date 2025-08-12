package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
  public String deleteUser(@RequestParam @NotBlank Integer UserId) {
    return adminService.deleteUserAsAdmin(UserId);
  }

  @GetMapping("/keys")
  public List<KeyDto> getKeys(@RequestParam @NotBlank Integer UserId) {
    return adminService.getAllUserKeys(UserId);
  }

  @DeleteMapping("/keys")
  public String deleteKey(@RequestParam @NotBlank Integer KeyId) {
    return adminService.deleteKeyAsAdmin(KeyId);
  }

  @PutMapping("/keys")
  public String updateKey(
          @RequestParam @NotBlank Integer KeyId, @RequestParam @NotBlank String name, @RequestParam @NotBlank Integer UserId) {
    return adminService.updateUserKey(KeyId, name, UserId);
  }
}
