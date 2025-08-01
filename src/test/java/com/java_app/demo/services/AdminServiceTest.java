package com.java_app.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.java_app.demo.admin.impl.AdminServiceImpl;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.apikey.model.KeyMapper;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import com.java_app.demo.user.dtos.UserDto;
import com.java_app.demo.user.mapper.UserMapper;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

  @Mock UserRepository userRepository;

  @Mock KeysRepository keysRepository;

  @InjectMocks AdminServiceImpl adminService;

  @Test
  void checkIfRetrievesAllUsers() {
    List<CustomUser> dbUsers = new ArrayList<CustomUser>();
    dbUsers.add(new CustomUser());
    dbUsers.getFirst().setId(1);
    dbUsers.getFirst().setEmail("awd");
    dbUsers.getFirst().setUsername("wadwa");
    dbUsers.getFirst().setEnabled(true);
    dbUsers.getFirst().setAccountNonLocked(true);
    dbUsers.getFirst().setRoles(new HashSet<String>(Collections.singleton("ROLE_USER")));
    List<UserDto> users = dbUsers.stream().map(UserMapper.INSTANCE::UserToUserDto).toList();
    when(userRepository.getAllUsers()).thenReturn(dbUsers);

    ResponseEntity<List<UserDto>> response = adminService.getAllUsers();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(users, response.getBody());
    verify(userRepository, times(1)).getAllUsers();
  }

  @Test
  void testIfUserIsUpdatedCorrectly_Successful() {
    String email = "awd";
    String role = "ADMIN";
    CustomUser customUser = new CustomUser();
    customUser.setEmail(email);
    customUser.setRoles(new HashSet<String>(Collections.singleton(role)));
    when(userRepository.findCustomUserByEmail(email)).thenReturn(customUser);

    ResponseEntity<String> response = adminService.updateUserAsAdmin(email, role);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(userRepository, times(1)).findCustomUserByEmail(email);
    verify(userRepository, times(1)).save(any());
  }

  @Test
  void testIfUserIsUpdatedCorrectly_Unsuccessful() {
    String email = "";
    String role = "";
    CustomUser customUser = new CustomUser();
    customUser.setEmail(email);
    customUser.setRoles(new HashSet<String>(Collections.singleton(role)));
    when(userRepository.existsByEmail(email)).thenReturn(false);

    ResponseEntity<String> response = adminService.updateUserAsAdmin(email, role);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verify(userRepository, times(1)).existsByEmail(email);
  }

  @Test
  void testIfItReturnsAllTheApiKeys() {
    Integer id = 1;
    CustomUser customUser = new CustomUser();
    customUser.setId(id);
    Set<ApiKey> keys = new HashSet<ApiKey>();
    ApiKey apiKey1 = new ApiKey();
    ApiKey apiKey2 = new ApiKey();
    ApiKey apiKey3 = new ApiKey();
    apiKey1.setName("1");
    apiKey2.setName("2");
    apiKey3.setName("3");
    apiKey1.setValue("1");
    apiKey2.setValue("2");
    apiKey3.setValue("3");
    keys.add(apiKey1);
    keys.add(apiKey2);
    keys.add(apiKey3);
    customUser.setApi_keySet(keys);
    List<ApiKey> listKeys = new ArrayList<>(keys);
    List<KeyDto> keysDto = keys.stream().map(KeyMapper.INSTANCE::apiKeyToKeyDto).toList();
    when(userRepository.findCustomUserById(any())).thenReturn(customUser);
    when(keysRepository.findAllByCustomUser(customUser)).thenReturn(listKeys);

    ResponseEntity<List<KeyDto>> response = adminService.getAllUserKeys(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(keysDto, response.getBody());
    verify(keysRepository, times(1)).findAllByCustomUser(customUser);
    verify(userRepository, times(1)).findCustomUserById(id);

  }

  @Test
  void testDeletionApiKeyById(){

    Integer id = 1;
    when(keysRepository.existsById(id)).thenReturn(true);

    ResponseEntity<String> response = adminService.deleteKeyAsAdmin(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(keysRepository, times(1)).existsById(id);
    verify(keysRepository, times(1)).deleteApiKeyById(id);

  }

  @Test
  void testUpdatingUserKey(){
    ApiKey apiKey = new ApiKey();
    apiKey.setName("1");
    apiKey.setValue("2");
    String name = "awd";
    Integer id = 1;
    Integer user_id = 1;
    when(keysRepository.existsById(id)).thenReturn(true);
    when(keysRepository.existsApiKeyByCustomUser_Id(user_id)).thenReturn(true);
    when(keysRepository.findApiKeyById(id)).thenReturn(apiKey);

    ResponseEntity<String> response = adminService.updateUserKey(id, name, user_id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(keysRepository, times(1)).existsById(id);
    verify(keysRepository, times(1)).existsApiKeyByCustomUser_Id(user_id);
  }

  @Test
  void testDeletingUser(){
    Integer id = 1;
    when(userRepository.existsById(any())).thenReturn(true);

    ResponseEntity<String> response = adminService.deleteUserAsAdmin(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(userRepository, times(1)).existsById(id);
    verify(userRepository, times(1)).deleteCustomUserById(id);
    verify(keysRepository, times(1)).deleteByCustomUserId(id);
  }

}
