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
  public ResponseEntity<List<UserDto>> getAllUsersFromDatabe() {
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
    if (!userEmail.isEmpty() || !userRole.isEmpty() && userRepository.existsByEmail(userEmail)) {
      CustomUser user = userRepository.findCustomUserByEmail(userEmail);

      if (!userEmail.isEmpty()) {
        user.setEmail(userEmail);
      }
      if (!userRole.isEmpty()) {
        user.addRole("ROLE_" + userRole);
      }
      userRepository.save(user);
      log.info("Admin updated this user: {}", user.getUsername() + " with the following email and role: " + userEmail + ", " +  userRole);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info("The Admin tried to update the user with the following email {}, but failed.", userEmail);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @Override
  @Transactional
  public ResponseEntity<String> deleteUserAsAdmin(int id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteCustomUserById(id);
      keysRepository.deleteById(id);
        log.info("The Admin deleted the user with the following id: {}", id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info("The Admin tried to delete the user with the following id: {}", id);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  public ResponseEntity<List<KeyDto>> getAllUserKeysFromDatabe(Integer userId) {
    CustomUser user = userRepository.findCustomUserById(userId);
    if (user != null) {
      List<KeyDto> keys =
          List.of(
              keysRepository.findAllByCustomUser(user).stream()
                  .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
                  .toArray(KeyDto[]::new));
      log.info("Admin retrieved the keys of the user with the following ID: {}", userId + " and received the following result: " + keys);
      return new ResponseEntity<>(keys, HttpStatus.OK);
    }
    log.info("Admin tried to retrieve the keys of the user with the following ID, but the user wasn't found.");
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
  public ResponseEntity<String> updateUserKey(Integer id, String name) {
    if (keysRepository.existsById(id)) {
      ApiKey key = keysRepository.findApiKeyById(id);
      String oldName = key.getName();
      key.setName(name);
      keysRepository.save(key);
      log.info("Admin updated this ApiKey: {}", oldName + " with the following name: " + name);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    log.info("The Admin tried to update the key with the following {}, but failed because was not found.", name);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
