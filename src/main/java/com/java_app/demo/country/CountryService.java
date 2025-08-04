package com.java_app.demo.country;

import org.springframework.http.ResponseEntity;

public interface CountryService {

  ResponseEntity<LocationDto> getUserLocationByIp(String ip);
}
