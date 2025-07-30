package com.java_app.demo.apikey.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.java_app.demo.apikey.KeyService;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.apikey.model.KeyMapper;
import com.java_app.demo.user.CustomUser;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Getter
@Setter
public class KeyServiceImpl implements KeyService {

  private final KeysRepository keysRepository;

  @Override
  @Transactional
  public ResponseEntity<String> createApiKey(String keyName) {
    if (keysRepository.existsByNameAndCustomUser(keyName, getAuthenticatedUser())) {
      return new ResponseEntity<>("This name already exists", HttpStatus.BAD_REQUEST);
    }
    ApiKey apiKey = new ApiKey(null, generateApiKey(), keyName, getAuthenticatedUser());
    keysRepository.save(apiKey);

    return new ResponseEntity<>("Successfully created!", HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<KeyDto>> getCurrentUserApiKeys() {
    return new ResponseEntity<>(
        keysRepository.findByCustomUser(getAuthenticatedUser()).stream()
            .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> delete(String keyName) {
    if (!keysRepository.existsByName(keyName)) {
      return new ResponseEntity<>("Key Not Found!", HttpStatus.NOT_FOUND);
    }
    keysRepository.deleteByName(keyName);

    return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> update(String keyName, String newName) {
    if (keyName != null && newName != null && keysRepository.existsByName(keyName)) {
      keysRepository.updateApiKey(keyName, newName);
      return new ResponseEntity<>("Successfully modified!", HttpStatus.OK);
    }
    return new ResponseEntity<>("Key Not Found!", HttpStatus.NOT_FOUND);
  }

  private CustomUser getAuthenticatedUser() {
    return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  private String generateApiKey() {
    return String.valueOf(UuidCreator.getTimeBased());
  }
}
