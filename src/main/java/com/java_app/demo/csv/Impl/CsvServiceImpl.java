package com.java_app.demo.csv.Impl;

import com.java_app.demo.advice.exceptions.InternalServerErrorException;
import com.java_app.demo.csv.CsvService;
import com.java_app.demo.user.UserRepository;
import com.java_app.demo.user.dtos.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvServiceImpl implements CsvService {
  private final UserRepository userRepository;

  @Override
  public void writeUsers(HttpServletResponse httpServletResponse)
      throws InternalServerErrorException {
    httpServletResponse.setContentType("text/csv; charset=utf-8");
    httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");

    try (PrintWriter writer = httpServletResponse.getWriter()) {
      writer.println("Id, Email, Username, Enabled, Expired, Roles");
      List<UserDto> users = getAllUsers();
      for (UserDto user : users) {
        String row =
            String.format(
                Locale.ROOT,
                "%d,%s,%s,%b,%b,%s",
                user.getId(),
                escapeCsv(user.getEmail()),
                escapeCsv(user.getUsername()),
                escapeCsv(user.getEnabled().toString()),
                escapeCsv(user.getAccountNonExpired().toString()),
                escapeCsv(user.getRoles().toArray()[user.getRoles().size() - 1].toString()));
        writer.println(row);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new InternalServerErrorException(
          "An internal error occurred when trying to write CSV file");
    }
  }

  private String escapeCsv(String input) {
    if (input == null) {
      return "";
    }
    if (input.contains(",")
        || input.contains("\"")
        || input.contains("\n")
        || input.contains("\r")) {
      return "\"" + input.replace("\"", "\"\"") + "\"";
    }
    return input;
  }

  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(
            user ->
                new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getEnabled(),
                    user.getAccountNonExpired(),
                    user.getRoles()))
        .collect(Collectors.toList());
  }
}
