package com.java_app.demo.country.impl;

import com.java_app.demo.country.CountryService;
import com.java_app.demo.country.LocationDto;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryServiceImpl implements CountryService {

  static InetAddressValidator validator = InetAddressValidator.getInstance();

  @Override
  public ResponseEntity<?> getUserLocationByIp(String ip) {
    if (!validator.isValid(ip)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    String url = "http://ip-api.com/json/";
    RestTemplate restTemplate = new RestTemplate();
    try {
      LocationDto locationDto = restTemplate.getForObject(url + ip, LocationDto.class);
      return new ResponseEntity<>(locationDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
