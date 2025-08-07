package com.java_app.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.java_app.demo.admin.AdminController;
import com.java_app.demo.admin.AdminService;
import com.java_app.demo.admin.CustomTransferAdmin;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.authentication.AuthService;
import com.java_app.demo.authentication.dtos.RegisterDto;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import com.java_app.demo.user.dtos.UserDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminController.class)
@WithMockUser(roles = {"ADMIN"})
public class AdminControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AdminService adminService;

  @MockitoBean private AuthService authService;

  @MockitoBean private KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @Test
  public void testRetrievingAllUSers() throws Exception {
    List<UserDto> users = new ArrayList<>();
    UserDto user1 =
        new UserDto(
            1, "a@mail.com", "a", true, true, new HashSet<>(Collections.singleton("ROLE_USER")));
    users.add(user1);

    when(adminService.getAllUsers()).thenReturn(users);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/admin/users").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("a@mail.com"));

    verify(adminService, times(1)).getAllUsers();
  }

  @Test
  public void testAddingUser() throws Exception {
    RegisterDto user = new RegisterDto("a", "a", "a", "a");
    CustomTransferAdmin transfer = new CustomTransferAdmin("User registered successfully", HttpStatus.OK);
    when(authService.register(any())).thenReturn(transfer);

    mockMvc.perform(MockMvcRequestBuilders.post("/admin/users").accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.valueOf(user)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));

  }

  @Test
  public void testUpdatingUser() throws Exception {
    String email = "a";
    String role = "ROLE_USER";
    CustomTransferAdmin transfer = new CustomTransferAdmin("", HttpStatus.OK);
    when(adminService.updateUserAsAdmin(any(), any())).thenReturn(transfer);

    mockMvc.perform(MockMvcRequestBuilders.put("/admin/users").accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                    .param("userEmail", email)
            .param("userRole", role))
            .andDo(print())
            .andExpect(status().isOk());
    verify(adminService, times(1)).updateUserAsAdmin(any(), any());
  }

  @Test
  public void testDeletingUser() throws Exception {
    Integer userId = 1;
    CustomTransferAdmin transfer = new CustomTransferAdmin("", HttpStatus.OK);
    when(adminService.deleteUserAsAdmin(userId)).thenReturn(transfer);

    mockMvc.perform(MockMvcRequestBuilders.delete("/admin/users").accept(MediaType.APPLICATION_JSON)
    .with(csrf())
    .param("user_id", String.valueOf(userId)))
            .andDo(print())
            .andExpect(status().isOk());
    verify(adminService, times(1)).deleteUserAsAdmin(userId);
  }

  @Test
  public void testDeletingKey() throws Exception {
    Integer id = 1;
    CustomTransferAdmin response = new CustomTransferAdmin("", HttpStatus.OK);

    when(adminService.deleteKeyAsAdmin(id)).thenReturn(response);

    mockMvc.perform(MockMvcRequestBuilders.delete("/admin/keys").accept(MediaType.APPLICATION_JSON)
            .with(csrf())
            .param("key_id", String.valueOf(id)))
            .andDo(print())
            .andExpect(status().isOk());
    verify(adminService, times(1)).deleteKeyAsAdmin(id);

  }

  @Test
  public void testUpdatingKey() throws Exception {
    Integer id = 1;
    String name = "1";
    Integer user_id = 1;
    CustomTransferAdmin response = new CustomTransferAdmin("",  HttpStatus.OK);

    when(adminService.updateUserKey(any(), any(), any())).thenReturn(response);

    mockMvc.perform(MockMvcRequestBuilders.put("/admin/keys").accept(MediaType.APPLICATION_JSON)
    .with(csrf())
    .param("key_id", String.valueOf(id))
    .param("name", name)
            .param("user_id", String.valueOf(user_id)))
            .andDo(print())
            .andExpect(status().isOk());
    verify(adminService, times(1)).updateUserKey(id, name, user_id);

  }
}
