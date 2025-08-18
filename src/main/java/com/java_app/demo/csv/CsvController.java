package com.java_app.demo.csv;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
@Validated
public class CsvController {
  private final CsvService csvService;

  @GetMapping("/users")
  public void getScvFileWithUsers(HttpServletResponse response) {
    csvService.writeUsers(response);
  }

  @PostMapping("/find")
  public Map<String, Boolean> getScvFileAndGetIfUsersExist(@Valid @NotNull @RequestParam("File") MultipartFile file) {
    return csvService.getExistingUsersInFile(file);
  }
}
