package com.java_app.demo.controller;

import com.java_app.demo.apikey.KeyController;
import com.java_app.demo.apikey.KeyService;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KeyController.class)
@WithMockUser(roles = {"ADMIN"})
public class KeyControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @MockitoBean private KeyService keyService;

  @Test
    public void testRegisterKeyKeyName_Blank_ReturnsBadRequest() throws Exception {
      String keyName = "";

    mockMvc
        .perform(MockMvcRequestBuilders.post("/keys/create").with(csrf()).param("keyName", keyName))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"keyName\":\"must not be blank\"}"));
  }

  @Test
    public void testDeleteKeyKeyName_Blank_ReturnsBadRequest() throws Exception {
      String keyName = "";

      mockMvc
              .perform(MockMvcRequestBuilders.delete("/keys/delete").with(csrf()).param("keyName", keyName))
              .andDo(print())
              .andExpect(status().isBadRequest())
              .andExpect(content().json("{\"keyName\":\"must not be blank\"}"));
  }

    @Test
    public void testUpdateKey_KeyNameBlank_ReturnsBadRequest() throws Exception {
        String keyName = "";
        String newName = "waesrfgdt";

        mockMvc
                .perform(MockMvcRequestBuilders.put("/keys/update").with(csrf()).param("keyName", keyName).param("newName", newName))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"keyName\":\"must not be blank\"}"));
    }

    @Test
    public void testUpdateKey_NewNameBlank_ReturnsBadRequest() throws Exception {
        String keyName = "waesrfgdt";
        String newName = "";

        mockMvc
                .perform(MockMvcRequestBuilders.put("/keys/update").with(csrf()).param("keyName", keyName).param("newName", newName))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"newName\":\"must not be blank\"}"));
    }
}
