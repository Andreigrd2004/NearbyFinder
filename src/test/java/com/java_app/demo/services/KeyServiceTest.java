package com.java_app.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.apikey.impl.KeyServiceImpl;
import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.apikey.model.KeyMapper;
import com.java_app.demo.user.CustomUser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
public class KeyServiceTest {
  @Mock KeysRepository keysRepository;

  @InjectMocks KeyServiceImpl keyService;

  private CustomUser mockUser;

  @BeforeEach
  void setUpSecurityContext() {
    mockUser = new CustomUser();
    mockUser = new CustomUser();
    mockUser.setUsername("testuser");
    mockUser.setId(1);

    Authentication auth =
        new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(auth);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void testApiKeyGeneration_Not_Successful() {

    String keyName = "weather_key";
    when(keysRepository.existsByNameAndCustomUser(keyName, mockUser)).thenReturn(true);

    HttpClientErrorException thrown =
        assertThrows(
            HttpClientErrorException.class,
            () -> keyService.createApiKey(keyName));
    assertEquals(HttpClientErrorException.class, thrown.getClass());
    verify(keysRepository, never()).save(any());
  }

  @Test
  void testApiKeyGeneration_Successful() {

    String keyName = "weather_key";
    when(keysRepository.existsByNameAndCustomUser(keyName, mockUser)).thenReturn(false);

    String response = keyService.createApiKey(keyName);

    assertEquals("Successfully created!", response);
    verify(keysRepository, times(1)).save(any());
  }

  @Test
  void testGettingALlKeysOfCustomUser() {
    Set<ApiKey> mockList = new HashSet<>();
    mockList.add(new ApiKey(1, "key1", "value1", mockUser));
    mockList.add(new ApiKey(2, "key1", "value1", mockUser));
    mockList.add(new ApiKey(3, "key1", "value1", mockUser));
    List<ApiKey> mockList1 = new ArrayList<>(mockList);
    List<KeyDto> mockList2 = mockList.stream().map(KeyMapper.INSTANCE::apiKeyToKeyDto).toList();
    mockUser.setApi_keySet(mockList);
    when(keysRepository.findByCustomUser(mockUser)).thenReturn(mockList1);

    List<KeyDto> response = keyService.getCurrentUserApiKeys();

    assertEquals(mockList2, response);
    verify(keysRepository, times(1)).findByCustomUser(mockUser);
  }

  @Test
  void testDeletingApiKey_Successful() {
    String keyName = "weather_key";
    when(keysRepository.existsByName(keyName)).thenReturn(true);

    String response = keyService.delete(keyName);

    assertEquals("Successfully deleted!", response);
    verify(keysRepository, times(1)).deleteByName(keyName);
  }

  @Test
  void testDeletingApiKey_Not_Successful() {
    String keyName = "weather_key";
    when(keysRepository.existsByName(keyName)).thenReturn(false);

    HttpClientErrorException thrown =
        assertThrows(
            HttpClientErrorException.class,
            () -> keyService.createApiKey(keyName));
    assertEquals(HttpClientErrorException.class, thrown.getClass());
    verify(keysRepository, never()).deleteByName(keyName);
  }

  @Test
  void testUpdatingApiKey() {
    String keyName = "weather_key";
    String newName = "newkey";
    when(keysRepository.existsByName(keyName)).thenReturn(true);

    String response = keyService.update(keyName, newName);

    assertEquals("Successfully modified!", response);
    verify(keysRepository, times(1)).updateApiKey(keyName, newName);
  }
}
