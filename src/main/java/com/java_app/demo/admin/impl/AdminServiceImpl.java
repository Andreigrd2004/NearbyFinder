package com.java_app.demo.admin.impl;

import com.java_app.demo.admin.AdminService;
import com.java_app.demo.admin.CustomTransferAdmin;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.apikey.model.KeyMapper;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import com.java_app.demo.user.dtos.UserDto;
import com.java_app.demo.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final KeysRepository keysRepository;

  @Override
  public List<UserDto> getAllUsers() {
    List<UserDto> users =
        List.of(
            userRepository.getAllUsers().stream()
                .map(UserMapper.INSTANCE::UserToUserDto)
                .toArray(UserDto[]::new));
    log.info("Admin retrieved those users: {}", users);
    return users;
  }

  @Override
  @Transactional
  public CustomTransferAdmin updateUserAsAdmin(String userEmail, String userRole) {
    CustomTransferAdmin transfer;
    if (userEmail.isEmpty() && userRole.isEmpty() && !userRepository.existsByEmail(userEmail)) {
      log.info(
          "The Admin tried to update the user with the following email {}, but failed.", userEmail);
      transfer = new CustomTransferAdmin("", HttpStatus.BAD_REQUEST);
      return transfer;
    }
    CustomUser user = userRepository.findCustomUserByEmail(userEmail);

    user.setEmail(userEmail);
    user.addRole("ROLE_" + userRole);
    userRepository.save(user);

    log.info(
        "Admin updated this user: {}",
        user.getUsername() + " with the following email and role: " + userEmail + ", " + userRole);
    transfer = new CustomTransferAdmin("", HttpStatus.OK);
    return transfer;
  }

  @Override
  @Transactional
  public CustomTransferAdmin deleteUserAsAdmin(Integer id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteCustomUserById(id);
      keysRepository.deleteByCustomUserId(id);
      log.info("The Admin deleted the user with the following id: {}", id);
      return new CustomTransferAdmin("", HttpStatus.OK);
    }
    log.info("The Admin tried to delete the user with the following id: {}", id);
    return new CustomTransferAdmin("", HttpStatus.NOT_FOUND);
  }

  @Override
  public CustomTransferAdmin getAllUserKeys(Integer userId) {
    CustomUser user = userRepository.findCustomUserById(userId);
    if (user != null) {
      List<KeyDto> keys =
          keysRepository.findAllByCustomUser(user).stream()
              .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
              .toList();

      return new CustomTransferAdmin(keys, HttpStatus.OK);
    }
    log.info(
        "Admin tried to retrieve the keys of the user with the following ID, but the user wasn't found.");
    return new CustomTransferAdmin("", HttpStatus.NOT_FOUND);
  }

  @Override
  @Transactional
  public CustomTransferAdmin deleteKeyAsAdmin(Integer id) {
    if (keysRepository.existsById(id)) {
      keysRepository.deleteApiKeyById(id);
      log.info("The Admin deleted the Apikey with the following id: {}", id);
      return new CustomTransferAdmin("", HttpStatus.OK);
    }
    log.info("The Admin tried to delete the Apikey with the following id: {}", id);
    return new CustomTransferAdmin("", HttpStatus.NOT_FOUND);
  }

  @Override
  public CustomTransferAdmin updateUserKey(Integer id, String name, Integer user_id) {
    if (keysRepository.existsById(id) && keysRepository.existsApiKeyByCustomUser_Id(user_id)) {
      ApiKey key = keysRepository.findApiKeyById(id);
      String oldName = key.getName();
      key.setName(name);
      keysRepository.save(key);
      log.info("Admin updated this ApiKey: {}", oldName + " with the following name: " + name);
      return new CustomTransferAdmin("", HttpStatus.OK);
    }
    log.info(
        "The Admin tried to update the key with the following {}, but failed because was not found.",
        name);
    return new CustomTransferAdmin("", HttpStatus.NOT_FOUND);
  }
}
