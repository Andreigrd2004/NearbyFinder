package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.CustomUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<List<CustomUser>> getAllUsersFromDatabe();

    ResponseEntity<String> createUserAsAdmin(String email, String password, String role);

    ResponseEntity<String> updateUserAsAdmin(String userId);

    ResponseEntity<String> deleteUserAsAdmin(String email);

    ResponseEntity<String> updateUserRole(String role, String userId);

    ResponseEntity<List<KeyDto>> getAllUserKeysFromDatabe(String userId);

    ResponseEntity<String> deleteKeyAsAdmin(String value);

    ResponseEntity<String> updateUSerRole();
}
