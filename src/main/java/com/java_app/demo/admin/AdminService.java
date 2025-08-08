package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;

public interface AdminService {
  List<UserDto> getAllUsers();

  String updateUserAsAdmin(String userId, String userRole);

  String deleteUserAsAdmin(Integer id);

    String deleteKeyAsAdmin(Integer id);

  String updateUserKey(Integer id, String name, Integer user_id);

  List<KeyDto> getAllUserKeys(Integer userId);
}
