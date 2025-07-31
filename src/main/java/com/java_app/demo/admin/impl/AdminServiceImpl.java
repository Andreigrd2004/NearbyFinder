package com.java_app.demo.admin.impl;

import com.java_app.demo.admin.AdminService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final KeysRepository keysRepository;

  @Override
  public ResponseEntity<List<UserDto>> getAllUsers() {
    List<UserDto> users =
        List.of(
            userRepository.getAllUsers().stream()
                .map(UserMapper.INSTANCE::UserToUserDto)
                .toArray(UserDto[]::new));
    log.info("Admin retrieved those users: {}", users);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> updateUserAsAdmin(String userEmail, String userRole) {
    if (userEmail.isEmpty() && userRole.isEmpty() && !userRepository.existsByEmail(userEmail)) {
      log.info(
          "The Admin tried to update the user with the following email {}, but failed.", userEmail);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    CustomUser user = userRepository.findCustomUserByEmail(userEmail);

    user.setEmail(userEmail);
    user.addRole("ROLE_" + userRole);
    userRepository.save(user);

    log.info(
        "Admin updated this user: {}",
        user.getUsername() + " with the following email and role: " + userEmail + ", " + userRole);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> deleteUserAsAdmin(Integer id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteCustomUserById(id);
      keysRepository.deleteByCustomUserId(id);
      log.info("The Admin deleted the user with the following id: {}", id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info("The Admin tried to delete the user with the following id: {}", id);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  public ResponseEntity<List<KeyDto>> getAllUserKeys(Integer userId) {
    CustomUser user = userRepository.findCustomUserById(userId);
    if (user != null) {
      List<KeyDto> keys =
          keysRepository.findAllByCustomUser(user).stream()
              .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
              .toList();
      return new ResponseEntity<>(keys, HttpStatus.OK);
    }
    log.info(
        "Admin tried to retrieve the keys of the user with the following ID, but the user wasn't found.");
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  @Transactional
  public ResponseEntity<String> deleteKeyAsAdmin(Integer id) {
    if (keysRepository.existsById(id)) {
      keysRepository.deleteApiKeyById(id);
      log.info("The Admin deleted the Apikey with the following id: {}", id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info("The Admin tried to delete the Apikey with the following id: {}", id);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  public ResponseEntity<String> updateUserKey(Integer id, String name, Integer user_id) {
    if (keysRepository.existsById(id) && keysRepository.existsApiKeyByCustomUser_Id(user_id)) {
      ApiKey key = keysRepository.findApiKeyById(id);
      String oldName = key.getName();
      key.setName(name);
      keysRepository.save(key);
      log.info("Admin updated this ApiKey: {}", oldName + " with the following name: " + name);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info(
        "The Admin tried to update the key with the following {}, but failed because was not found.",
        name);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
