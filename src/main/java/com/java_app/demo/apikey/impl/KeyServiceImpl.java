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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@AllArgsConstructor
@Getter
@Setter
public class KeyServiceImpl implements KeyService {

  private final KeysRepository keysRepository;

  @Override
  @Transactional
  public String createApiKey(String keyName) throws HttpClientErrorException {
    if (keysRepository.existsByNameAndCustomUser(keyName, getAuthenticatedUser())) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }
    ApiKey apiKey = new ApiKey(null, generateApiKey(), keyName, getAuthenticatedUser());
    keysRepository.save(apiKey);

    return "Successfully created!";
  }

  @Override
  public List<KeyDto> getCurrentUserApiKeys() {
    return keysRepository.findByCustomUser(getAuthenticatedUser()).stream()
        .map(KeyMapper.INSTANCE::apiKeyToKeyDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public String delete(String keyName) throws HttpClientErrorException {
    if (!keysRepository.existsByName(keyName)) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
    keysRepository.deleteByName(keyName);

    return "Successfully deleted!";
  }

  @Override
  @Transactional
  public String update(String keyName, String newName) throws HttpClientErrorException {
    if (keyName.isEmpty() && newName.isEmpty() && !keysRepository.existsByName(keyName)) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }
    keysRepository.updateApiKey(keyName, newName);
    return "Successfully modified!";
  }

  public CustomUser getAuthenticatedUser() {
    return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  private String generateApiKey() {
    return String.valueOf(UuidCreator.getTimeBased());
  }
}
