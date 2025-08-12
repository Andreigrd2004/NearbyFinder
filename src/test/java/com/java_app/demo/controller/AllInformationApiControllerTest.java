package com.java_app.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.java_app.demo.api.AllInformationApiController;
import com.java_app.demo.api.InformationRetriever;
import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = AllInformationApiController.class)
@WithMockUser(roles = {"API"})
public class AllInformationApiControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private InformationRetriever informationRetriever;

  @MockitoBean private KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @Test
  public void testGetLocation_BlankIp_ShouldReturnBadRequest() throws Exception {
    String ip = " ";

    mockMvc
        .perform(MockMvcRequestBuilders.get("/users/location").with(csrf()).param("ip", ip))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetNews_BlankIp_ShouldReturnBadRequest() throws Exception {
    String ip = " ";

    mockMvc
        .perform(MockMvcRequestBuilders.get("/users/news").with(csrf()).param("ip", ip))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetExchange_BlankIp_ShouldReturnBadRequest() throws Exception {
    String ip = " ";
    String target = "target";

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/users/exchangerate")
                .with(csrf())
                .param("ip", ip)
                .param("target", target))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetExchange_BlankTarget_ShouldReturnBadRequest() throws Exception {
    String ip = "85.186.22.132";

    mockMvc
        .perform(MockMvcRequestBuilders.get("/users/exchangerate").with(csrf()).param("ip", ip))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
