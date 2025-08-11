package com.java_app.demo.apikey.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.java_app.demo.advice.exceptions.BadRequestException;
import com.java_app.demo.advice.exceptions.NotFoundException;
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
  public String createApiKey(String keyName) throws BadRequestException {
    if (keysRepository.existsByNameAndCustomUser(keyName, getAuthenticatedUser())) {
      throw new BadRequestException(
          "ApiKey already exists with the following name: " + keyName);
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
  public String delete(String keyName) throws NotFoundException {
    if (!keysRepository.existsByName(keyName)) {
      throw new NotFoundException("ApiKey not found with the following name: " + keyName);
    }
    keysRepository.deleteByName(keyName);

    return "Successfully deleted!";
  }

  @Override
  @Transactional
  public String update(String keyName, String newName) throws NotFoundException {
    if (keyName.isEmpty() || newName.isEmpty() || !keysRepository.existsByName(keyName)) {
      throw new NotFoundException("ApiKey not found with the following name: " + keyName);
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
