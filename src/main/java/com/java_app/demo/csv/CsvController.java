package com.java_app.demo.csv;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {
  private final CsvService csvService;

  @GetMapping("/users")
  public void getScvFileWithUsers(HttpServletResponse response) {
    csvService.writeUsers(response);
  }
}
