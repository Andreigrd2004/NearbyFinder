package com.java_app.demo.admin;

import com.java_app.demo.user.dtos.UserDto;
import java.util.List;

public interface AdminService {
  List<UserDto> getAllUsers();

  CustomTransferAdmin updateUserAsAdmin(String userId, String userRole);

  CustomTransferAdmin deleteUserAsAdmin(Integer id);

  CustomTransferAdmin getAllUserKeys(Integer userId);

  CustomTransferAdmin deleteKeyAsAdmin(Integer id);

  CustomTransferAdmin updateUserKey(Integer id, String name, Integer user_id);
}
