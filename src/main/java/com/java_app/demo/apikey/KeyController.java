package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.KeyDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/keys")
public class KeyController {

    private final KeyService keyService;

    @PostMapping("/create")
    public ResponseEntity<String> registerKey(@RequestParam String keyName) {
        return keyService.createApiKey(keyName);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<KeyDto>> getKeys(){
        return keyService.getCurrentUserApiKeys();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteKey(@RequestParam String keyName){
        return keyService.delete(keyName);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateKey(@RequestParam String keyName, String newName){
        return keyService.update(keyName, newName);
    }
}
