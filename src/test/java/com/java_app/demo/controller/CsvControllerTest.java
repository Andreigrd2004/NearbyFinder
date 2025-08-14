package com.java_app.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.java_app.demo.apikey.KeyService;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.csv.CsvController;
import com.java_app.demo.csv.CsvService;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = CsvController.class)
@WithMockUser(roles = "ADMIN")
public class CsvControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private CsvService csvService;

  @MockitoBean private KeyService keyService;

  @MockitoBean KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @Test
  public void testGetUser_ShouldReturnCsvFile() throws Exception {

    doAnswer(
            invocation -> {
              HttpServletResponse response = invocation.getArgument(0, HttpServletResponse.class);
              response.setContentType("text/csv;charset=utf-8");
              response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
                PrintWriter writer = response.getWriter();
              writer.println("Id, Email, Username, Enabled, Expired, Roles");
              writer.println("1,test@example.com,testuser,true,false,ROLE_USER");
              writer.flush();
              return null;
            })
        .when(csvService)
        .writeUsers(any(HttpServletResponse.class));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/csv/users"))
        .andExpect(status().isOk())
        .andExpect(header().string("Content-Type", "text/csv;charset=utf-8"))
        .andExpect(header().string("Content-Disposition", "attachment; filename=\"users.csv\""))
        .andExpect(
            content()
                .string(
                    "Id, Email, Username, Enabled, Expired, Roles\r\n1,test@example.com,testuser,true,false,ROLE_USER\r\n"));
  }
}
