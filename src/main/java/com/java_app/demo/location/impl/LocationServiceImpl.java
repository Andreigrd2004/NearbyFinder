package com.java_app.demo.location.impl;

import com.java_app.demo.location.LocationService;
import com.java_app.demo.location.LocationDto;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationServiceImpl implements LocationService {

  public static final String FIELDS = "?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,currency,isp,org,as,query";
  public static final String URL = "http://ip-api.com/json/";
  private final RestTemplate restTemplate;

  static InetAddressValidator validator = InetAddressValidator.getInstance();

  public LocationServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<LocationDto> getUserLocationByIp(String ip) {
    if (!validator.isValid(ip)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      LocationDto locationDto = restTemplate.getForObject(URL + ip + FIELDS, LocationDto.class);
      return new ResponseEntity<>(locationDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
