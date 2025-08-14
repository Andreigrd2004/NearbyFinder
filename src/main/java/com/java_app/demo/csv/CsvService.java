package com.java_app.demo.csv;

import com.java_app.demo.user.dtos.UserDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CsvService {

    void writeUsers(HttpServletResponse httpServletResponse);
    List<UserDto> getAllUsers();
}
