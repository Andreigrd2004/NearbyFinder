package com.java_app.demo.admin.impl;

import com.java_app.demo.admin.AdminService;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public ResponseEntity<List<CustomUser>> getAllUsersFromDatabe() {
        return null;
    }

    @Override
    public ResponseEntity<String> createUserAsAdmin(String email, String password, String role) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateUserAsAdmin(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteUserAsAdmin(String email) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateUserRole(String role, String userId) {
        return null;
    }

    @Override
    public ResponseEntity<List<KeyDto>> getAllUserKeysFromDatabe(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteKeyAsAdmin(String value) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateUSerRole() {
        return null;
    }
}
