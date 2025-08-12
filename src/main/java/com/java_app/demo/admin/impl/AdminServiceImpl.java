package com.java_app.demo.admin.impl;

import com.java_app.demo.admin.AdminService;
import com.java_app.demo.advice.exceptions.NotFoundException;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.apikey.model.KeyMapper;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import com.java_app.demo.user.dtos.UserDto;
import com.java_app.demo.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final KeysRepository keysRepository;
  private final AuthService authService;

  @Override
  public List<UserDto> getAllUsers() {
    List<UserDto> users =
        userRepository.getAllUsers().stream()
            .map(UserMapper.INSTANCE::UserToUserDto)
            .collect(Collectors.toList());
    log.info("Admin retrieved those users: {}", users);
    return users;
  }

  @Override
  @Transactional
  public String updateUserAsAdmin(String userEmail, String userRole)
      throws NotFoundException {
    if (!userRepository.existsByEmail(userEmail)) {
      log.info(
          "The Admin tried to update the user with the following email {}, but failed.", userEmail);
      throw new NotFoundException(String.format("User not found with the following email: %s", userEmail));
    }
    CustomUser user = userRepository.findCustomUserByEmail(userEmail);

    user.setEmail(userEmail);
    user.addRole("ROLE_" + userRole);
    userRepository.save(user);

    log.info("Admin updated this user {} with the following email and role: {}, {}", user.getUsername(), userEmail, userRole);
    return "Updated successfully";
  }

  @Override
  @Transactional
  public String deleteUserAsAdmin(Integer id) throws NotFoundException {
    if (!userRepository.existsById(id)) {
      log.info("The Admin tried to delete the user with the following id: {}", id);
      throw new NotFoundException(String.format("User not found with the following id: %s", id));
    }
    userRepository.deleteCustomUserById(id);
    log.info("The Admin deleted the user with the following id: {}", id);
    return "User deleted successfully";
  }

  @Override
  public List<KeyDto> getAllUserKeys(Integer userId) throws NotFoundException {
    CustomUser user =
        userRepository
            .findCustomUserById(userId)
            .orElseThrow(
                () ->
                    new NotFoundException(String.format("User not found with the following id: %s", userId)));
    return keysRepository.findAllByCustomUser(user).stream()
        .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
        .collect(Collectors.toList());
  }

  @Override
  public String createUser(RegisterDto registerDto) {
    return authService.register(registerDto);
  }

  @Override
  @Transactional
  public String deleteKeyAsAdmin(Integer id) throws NotFoundException {
    ApiKey key =
        keysRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(String.format("ApiKey not found with the following id: %s", id)));
    keysRepository.delete(key);
    log.info("The Admin deleted the Apikey with the following id: {}", id);
    return "User's keys deleted successfully";
  }

  @Override
  public String updateUserKey(Integer id, String name, Integer UserId)
      throws NotFoundException {
    if(!keysRepository.existsApiKeyByCustomUser_Id(UserId)){
      throw new NotFoundException(String.format("User not found with the following id: %s", UserId));
    }
    if (!keysRepository.existsById(id)) {
      log.info(
          "The Admin tried to update the key with the following {}, but failed because was not found.",
          name);
      throw new NotFoundException(String.format("ApiKey not found with the following id: %s", id));
    }
    ApiKey key = keysRepository.findApiKeyById(id);
    log.info("Admin updated this ApiKey: {} with the following name: {}", key.getName(), name);
    key.setName(name);
    keysRepository.save(key);

    return "Updated successfully";
  }
}
