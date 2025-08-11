package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.KeyDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/keys")
public class KeyController {

  private final KeyService keyService;

  @PostMapping("/create")
  public String registerKey(@RequestParam String keyName) {
    return keyService.createApiKey(keyName);
  }

  @GetMapping("/getAll")
  public List<KeyDto> getKeys() {
    return keyService.getCurrentUserApiKeys();
  }

  @DeleteMapping("/delete")
  public String deleteKey(@RequestParam String keyName) {
    return keyService.delete(keyName);
  }

  @PutMapping("/update")
  public String updateKey(@RequestParam String keyName, String newName) {
    return keyService.update(keyName, newName);
  }
}
