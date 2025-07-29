package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.KeyDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface KeyService {
  ResponseEntity<String> createApiKey(String keyName);

  ResponseEntity<List<KeyDto>> getCurrentUserApiKeys();

  ResponseEntity<String> delete(String keyName);

  ResponseEntity<String> update(String keyName, String newName);
}
