package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.KeyDto;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/keys")
@Validated
public class KeyController {

  private final KeyService keyService;

  @PostMapping("/create")
  public String registerKey(@RequestParam @NotBlank String keyName) {
    return keyService.createApiKey(keyName);
  }

  @GetMapping("/getAll")
  public List<KeyDto> getKeys() {
    return keyService.getCurrentUserApiKeys();
  }

  @DeleteMapping("/delete")
  public String deleteKey(@RequestParam @NotBlank String keyName) {
    return keyService.delete(keyName);
  }

  @PutMapping("/update")
  public String updateKey(@RequestParam @NotBlank String keyName, @RequestParam @NotBlank String newName) {
    return keyService.update(keyName, newName);
  }
}
