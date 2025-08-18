package com.java_app.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.java_app.demo.apikey.KeyService;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.authentication.AuthController;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.authentication.dtos.LoginDto;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean AuthService authService;

  @MockitoBean private KeyService keyService;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @MockitoBean KeysRepository keysRepository;

  @Test
  public void testLogin_ReturnsBadRequest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    LoginDto loginDto = new LoginDto("", "");
    String json = objectMapper.writeValueAsString(loginDto);

    mockMvc
        .perform(
            post("/auth/login").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(json))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testRegister_ReturnsBadRequest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    RegisterDto registerDto = new RegisterDto("", "", "", "");
    String json = objectMapper.writeValueAsString(registerDto);

    mockMvc
        .perform(
            post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
