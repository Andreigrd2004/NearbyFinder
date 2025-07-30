package com.java_app.demo.admin;


import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<CustomUser>> getUsers() {
        return adminService.getAllUsersFromDatabe();
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
        return adminService.createUserAsAdmin(email, password, role);
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestParam String user_id) {
        return adminService.updateUserAsAdmin(user_id);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        return adminService.deleteUserAsAdmin(email);
    }

    @PutMapping("/users/role")
    public ResponseEntity<String> addRole(@RequestParam String role, @RequestParam String user_id) {
        return adminService.updateUserRole(role, user_id);
    }

    @GetMapping("/api/keys")
    public ResponseEntity<List<KeyDto>> getKeys(@RequestParam String user_id) {
        return adminService.getAllUserKeysFromDatabe(user_id);
    }

    @DeleteMapping("/api/keys")
    public ResponseEntity<String> deleteKey(@RequestParam String value) {
        return adminService.deleteKeyAsAdmin(value);
    }

    @PutMapping("/api/keys")
    public ResponseEntity<String> addRole(@RequestParam String name) {
        return adminService.updateUSerRole();
    }


}
