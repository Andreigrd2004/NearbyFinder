package com.java_app.demo.country;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class LocationController {

  CountryService countryService;

  @GetMapping("/location")
  public ResponseEntity<LocationDto> getLocation(@RequestParam("ip") String ip) {
    return countryService.getUserLocationByIp(ip);
  }
}
