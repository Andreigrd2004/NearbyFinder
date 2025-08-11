package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.KeyDto;
import java.util.List;

public interface KeyService {
  String createApiKey(String keyName);

  List<KeyDto> getCurrentUserApiKeys();

  String delete(String keyName);

  String update(String keyName, String newName);
}
