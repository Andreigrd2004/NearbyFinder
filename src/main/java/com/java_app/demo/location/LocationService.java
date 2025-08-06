package com.java_app.demo.location;

import org.springframework.http.ResponseEntity;

public interface LocationService {

  ResponseEntity<LocationDto> getUserLocationByIp(String ip);
}
