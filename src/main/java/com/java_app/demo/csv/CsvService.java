package com.java_app.demo.csv;

import com.java_app.demo.user.dtos.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CsvService {

    void writeUsers(HttpServletResponse httpServletResponse);
    List<UserDto> getAllUsers();
    Map<String, Boolean> getExistingUsersInFile(MultipartFile multipartFile);
}
