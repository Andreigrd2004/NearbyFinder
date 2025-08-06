package com.java_app.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.java_app.demo.apikey.KeysRepository;
import com.java_app.demo.config.TestConfig;
import com.java_app.demo.country.CountryService;
import com.java_app.demo.country.LocationController;
import com.java_app.demo.country.LocationDto;
import com.java_app.demo.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = LocationController.class)
@WithMockUser(roles = {"API"})
public class LocationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private CountryService countryService;

  @MockitoBean
  private KeysRepository keysRepository;

  @MockitoBean private JwtTokenProvider jwtTokenProvider;

  @Test
  public void getLocation_Successful() throws Exception {
    LocationDto locationDto =
        new LocationDto(
            "128.181.92.3", "ok", "Romania", "Suceava", "Suceava", "727234", "1", "2", "3");
    ResponseEntity<LocationDto> response = new ResponseEntity<>(locationDto, HttpStatus.OK);

    Mockito.when(countryService.getUserLocationByIp(locationDto.getQuery())).thenReturn(response);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/user/location")
                .param("ip", locationDto.getQuery())
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.query").value(locationDto.getQuery()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.country").value(locationDto.getCountry()));

    Mockito.verify(countryService, Mockito.times(1)).getUserLocationByIp(locationDto.getQuery());
  }
}
